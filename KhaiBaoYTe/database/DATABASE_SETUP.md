# Hướng dẫn thiết lập Cơ sở dữ liệu KhaiBaoYTe

Dự án sử dụng hệ quản trị cơ sở dữ liệu **Microsoft SQL Server**. Dưới đây là hai phương án để cài đặt và chạy cơ sở dữ liệu.

---

## Tùy chọn A: Sử dụng Docker (Khuyến nghị cho macOS)

Tùy chọn này sử dụng container Docker chạy Microsoft SQL Server, không yêu cầu cài đặt SQL Server trực tiếp trên máy Mac của bạn.

### Yêu cầu
- Đã cài đặt **Docker Desktop** trên macOS và ứng dụng đang được khởi chạy.

### Các bước thực hiện
1. **Khởi chạy container CSDL:**
   Mở terminal tại thư mục `KhaiBaoYTe/database/` và chạy lệnh:
   ```bash
   docker compose up -d
   ```
   Lệnh này sẽ tải image SQL Server 2022 và khởi chạy một container ngầm trên cổng `1433`.

2. **Nạp schema và dữ liệu mẫu (Seed Data):**
   Trong cùng thư mục `KhaiBaoYTe/database/`, chạy script tự động:
   ```bash
   ./import-data.sh
   ```
   Script sẽ tự động đợi container khởi động hoàn toàn, sau đó nạp schema từ `01_create_database.sql` và dữ liệu mẫu từ `02_seed_data.sql`.

3. **Cấu hình Spring Boot kết nối:**
   Đảm bảo cấu hình trong `backend/src/main/resources/application.yml` khớp với các thông số:
   - `url`: `jdbc:sqlserver://localhost:1433;databaseName=KhaiBaoYTe;encrypt=true;trustServerCertificate=true`
   - `username`: `sa`
   - `password`: `Your_password123`

---

## Tùy chọn B: Sử dụng SQL Server cục bộ hoặc từ xa (Cài đặt thủ công)

Sử dụng tùy chọn này nếu bạn chạy SQL Server trên một máy tính khác (ví dụ: máy Windows chạy SQL Server trực tiếp) hoặc sử dụng dịch vụ đám mây SQL Server.

### Các bước thực hiện
1. **Khởi tạo cơ sở dữ liệu và bảng:**
   - Kết nối tới SQL Server của bạn bằng công cụ quản lý như **SQL Server Management Studio (SSMS)** hoặc **Azure Data Studio**.
   - Mở và chạy file [01_create_database.sql](file:///Users/thanhdao/Documents/Đồ%20án%20phần%20mềm/KhaiBaoYTe/database/01_create_database.sql) để tạo cơ sở dữ liệu `KhaiBaoYTe` cùng với 14 bảng cấu trúc.

2. **Nạp dữ liệu mẫu (Seed Data):**
   - Mở và chạy file [02_seed_data.sql](file:///Users/thanhdao/Documents/Đồ%20án%20phần%20mềm/KhaiBaoYTe/database/02_seed_data.sql) để nạp các bản ghi mẫu phục vụ kiểm thử.

3. **Cập nhật cấu hình Spring Boot:**
   Mở file [application.yml](file:///Users/thanhdao/Documents/Đồ%20án%20phần%20mềm/KhaiBaoYTe/backend/src/main/resources/application.yml) và cập nhật thông tin kết nối khớp với máy chủ của bạn:
   ```yaml
   spring:
     datasource:
       url: jdbc:sqlserver://<HOST>:<PORT>;databaseName=KhaiBaoYTe;encrypt=true;trustServerCertificate=true
       username: <USERNAME>
       password: <PASSWORD>
   ```
   *Ví dụ:* Thay thế `<HOST>` bằng địa chỉ IP của máy chạy SQL Server (hoặc tên máy nếu trong cùng mạng nội bộ).
