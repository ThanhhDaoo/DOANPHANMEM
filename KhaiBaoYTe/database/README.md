# CSDL - Hệ thống Khai báo Y tế phường Thanh Khê

Đồ án phần mềm - Nhóm 02. CSDL được thiết kế cho **Microsoft SQL Server**, tuân
theo Mục 4 trong báo cáo (`Nhóm 2_ĐAPM_Lần 6.docx`).

## Cấu trúc thư mục

```
database/
├── 01_create_database.sql   # Tạo database + 14 bảng + index
├── 02_seed_data.sql         # Dữ liệu mẫu để demo/test
└── README.md
```

## Cách chạy

### SQL Server Management Studio (SSMS)
1. Kết nối đến SQL Server.
2. Mở `01_create_database.sql` → Execute (F5).
3. Mở `02_seed_data.sql` → Execute (F5).

### Dòng lệnh `sqlcmd`
```bash
sqlcmd -S localhost -U sa -P <password> -i 01_create_database.sql
sqlcmd -S localhost -U sa -P <password> -i 02_seed_data.sql
```

## Danh sách 14 bảng (theo Mục 4)

| # | Bảng | Mô tả |
|---|---|---|
| 1 | `NGUOIDAN` | Tài khoản người dân & cán bộ (phân biệt qua `vaiTro`) |
| 2 | `KHAIBAOYTE` | Phiếu khai báo y tế |
| 3 | `TINHTRANGSUCKHOE` | Người dân tự cập nhật theo phiếu khai báo |
| 4 | `YEUCAUHOTRO` | Yêu cầu hỗ trợ y tế của người dân |
| 5 | `TEPDINHKEM_YEUCAU` | Tệp đính kèm cho yêu cầu hỗ trợ |
| 6 | `THEODOISUCKHOE` | Cán bộ y tế cập nhật theo dõi |
| 7 | `PHANHOIYEUCAU` | Phản hồi của cán bộ cho yêu cầu |
| 8 | `TEPDINHKEM_PHANHOI` | Tệp đính kèm cho phản hồi |
| 9 | `BAIDANGTINTUC` | Bài đăng tin tức/hướng dẫn |
| 10 | `THONGBAO` | Thông báo gửi tới người dân |
| 11 | `THONGBAO_NGUOINHAN` | Bảng trung gian thông báo - người nhận |
| 12 | `CANBOYTE` | Hồ sơ cán bộ y tế (mở rộng từ NGUOIDAN) |
| 13 | `TODANPHO` | Danh mục tổ dân phố |
| 14 | `TRANGTHAI_SUCKHOE` | Danh mục trạng thái sức khoẻ |

## Ghi chú thiết kế

- `NGUOIDAN.matKhau` dùng `VARCHAR(60)` đúng độ dài chuỗi hash bcrypt; ứng dụng
  phải hash trước khi lưu, **không** lưu plain text.
- `vaiTro`: `0` = người dân, `1` = cán bộ y tế / admin. Phân biệt admin với cán
  bộ thường có thể bổ sung thêm cột `quyenAdmin` khi cần.
- `CANBOYTE.maNguoiDan` được đặt `UNIQUE` để 1 người dân chỉ tương ứng tối đa 1
  hồ sơ cán bộ y tế.
- Các trường thời gian dùng `DATETIME` mặc định `GETDATE()` để giảm việc set tay.
- Index đã tạo cho các truy vấn thường gặp (lọc theo người dân, sắp xếp theo
  thời gian).
- Mã CCCD và số điện thoại có ràng buộc `UNIQUE` để tránh trùng tài khoản.

## Khi cần xoá và tạo lại

File `01_create_database.sql` đã có sẵn block `DROP DATABASE` ở đầu, chỉ cần
chạy lại là sạch dữ liệu cũ. Tránh chạy trên môi trường có dữ liệu thật.
