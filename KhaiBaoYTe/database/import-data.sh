#!/bin/bash

# Script to wait for SQL Server and import schema & seed data in Docker
# Author: Antigravity

echo "============================================================"
echo "KHỞI TẠO CƠ SỞ DỮ LIỆU TRONG DOCKER"
echo "============================================================"

# Port checks and container checks
CONTAINER_NAME="khaibaoyte_mssql"
PASSWORD="Your_password123"

# Find if sqlcmd is tools18 or older
SQLCMD_PATH="/opt/mssql-tools18/bin/sqlcmd"
TRUST_CERT_FLAG="-C"

echo "Đang chờ SQL Server trong container '${CONTAINER_NAME}' khởi động..."

# Loop to wait for SQL Server to accept connections
for i in {1..30}; do
  # Try checking connection
  docker exec -i ${CONTAINER_NAME} ${SQLCMD_PATH} -S localhost -U sa -P "${PASSWORD}" ${TRUST_CERT_FLAG} -Q "SELECT 1" &> /dev/null
  status=$?
  
  if [ $status -eq 0 ]; then
    echo ""
    echo "✔ SQL Server đã sẵn sàng kết nối!"
    break
  fi
  
  # Fallback to older sqlcmd path if tools18 is not present
  if [ $i -eq 5 ]; then
    # Test if we need to switch sqlcmd path
    docker exec -i ${CONTAINER_NAME} /opt/mssql-tools/bin/sqlcmd -S localhost -U sa -P "${PASSWORD}" -Q "SELECT 1" &> /dev/null
    if [ $? -eq 0 ]; then
      SQLCMD_PATH="/opt/mssql-tools/bin/sqlcmd"
      TRUST_CERT_FLAG=""
      echo ""
      echo "✔ Phát hiện SQL Server phiên bản cũ. Sử dụng sqlcmd cũ."
      break
    fi
  fi

  echo -n "."
  sleep 2
done

if [ $status -ne 0 ] && [ "$SQLCMD_PATH" = "/opt/mssql-tools18/bin/sqlcmd" ]; then
  # Final fallback check
  docker exec -i ${CONTAINER_NAME} /opt/mssql-tools/bin/sqlcmd -S localhost -U sa -P "${PASSWORD}" -Q "SELECT 1" &> /dev/null
  if [ $? -eq 0 ]; then
    SQLCMD_PATH="/opt/mssql-tools/bin/sqlcmd"
    TRUST_CERT_FLAG=""
    echo "✔ Kết nối thành công bằng sqlcmd cũ."
  else
    echo ""
    echo "❌ Lỗi: Không thể kết nối tới SQL Server sau 60 giây."
    echo "Vui lòng kiểm tra trạng thái container bằng cách chạy: docker ps"
    exit 1
  fi
fi

echo "------------------------------------------------------------"
echo "1. Đang tạo CSDL và các bảng (01_create_database.sql)..."
docker exec -i ${CONTAINER_NAME} ${SQLCMD_PATH} -S localhost -U sa -P "${PASSWORD}" ${TRUST_CERT_FLAG} < 01_create_database.sql

if [ $? -eq 0 ]; then
  echo "✔ Đã tạo CSDL và 14 bảng thành công."
else
  echo "❌ Lỗi khi khởi tạo CSDL."
  exit 1
fi

echo "------------------------------------------------------------"
echo "2. Đang nạp dữ liệu mẫu (02_seed_data.sql)..."
docker exec -i ${CONTAINER_NAME} ${SQLCMD_PATH} -S localhost -U sa -P "${PASSWORD}" ${TRUST_CERT_FLAG} < 02_seed_data.sql

if [ $? -eq 0 ]; then
  echo "✔ Đã nạp dữ liệu mẫu thành công."
else
  echo "❌ Lỗi khi nạp dữ liệu mẫu."
  exit 1
fi

echo "============================================================"
echo "✔ QUÁ TRÌNH KHỞI TẠO CSDL KHAIBAOYTE HOÀN TẤT!"
echo "============================================================"
