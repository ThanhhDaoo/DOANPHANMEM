# Hệ thống Khai báo Y tế - Phường Thanh Khê

Đồ án phần mềm - Nhóm 02.

## Stack

- **Database**: Microsoft SQL Server
- **Backend**: Java 17 + Spring Boot 3 (Maven)
- **Frontend**: HTML + CSS + JavaScript thuần (không dùng framework)

## Cấu trúc khung dự án

```
KhaiBaoYTe/
├── database/                          # Đã có: scripts SQL Server
│   ├── 01_create_database.sql
│   ├── 02_seed_data.sql
│   └── README.md
│
├── backend/                           # Spring Boot project
│   ├── pom.xml                        # (sẽ thêm khi bắt đầu code)
│   └── src/
│       ├── main/
│       │   ├── java/com/khaibaoyte/
│       │   │   ├── config/            # Cấu hình: CORS, Security, Bean...
│       │   │   ├── controller/        # REST controllers
│       │   │   ├── dto/               # Request/Response DTO
│       │   │   ├── entity/            # JPA entity (14 bảng)
│       │   │   ├── exception/         # Exception + global handler
│       │   │   ├── repository/        # Spring Data JPA repositories
│       │   │   ├── security/          # JWT filter, UserDetailsService
│       │   │   ├── service/           # Business logic
│       │   │   └── util/              # Helper (id generator, mapper...)
│       │   └── resources/
│       │       ├── application.yml    # (sẽ thêm khi bắt đầu code)
│       │       └── static/uploads/    # Thư mục lưu file đính kèm
│       └── test/java/com/khaibaoyte/  # Unit test
│
└── frontend/                          # Static site
    ├── index.html                     # (sẽ thêm khi bắt đầu code)
    ├── assets/
    │   ├── css/                       # File style chung
    │   ├── js/                        # File JS chung (api, auth, utils)
    │   └── img/                       # Hình ảnh, logo
    ├── components/                    # Đoạn HTML tái sử dụng (header, footer)
    └── pages/
        ├── public/                    # Trang cho khách vãng lai
        ├── auth/                      # Đăng nhập, đăng ký, đổi mật khẩu
        ├── citizen/                   # Trang dành cho người dân
        ├── officer/                   # Trang dành cho cán bộ y tế
        └── admin/                     # Trang dành cho admin
```

## Lộ trình

1. ✅ Thiết kế CSDL (`database/`)
2. ⬜ Dựng khung dự án (đang ở bước này)
3. ⬜ Code backend: cấu hình kết nối SQL Server, entity, repository, auth/JWT
4. ⬜ Code các module nghiệp vụ theo chức năng từng tác nhân (Mục 3)
5. ⬜ Code frontend: layout, gọi API, các trang chức năng

## Phân quyền (theo Mục 3 báo cáo)

- **Khách vãng lai**: xem tin tức, hướng dẫn, đăng ký tài khoản.
- **Người dân**: khai báo y tế, cập nhật sức khoẻ, gửi yêu cầu hỗ trợ, xem
  thông báo.
- **Cán bộ y tế phường**: theo dõi sức khoẻ người dân, phản hồi yêu cầu, đăng
  bài tin tức, gửi thông báo, thống kê báo cáo.
- **Admin**: quản lý tài khoản, quản lý danh mục hệ thống.
