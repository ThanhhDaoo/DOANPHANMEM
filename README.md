# 🏥 Hệ thống Khai báo Y tế Phường Thanh Khê

> **Đồ án môn học: Phân tích & Thiết kế Phần mềm (Nhóm 02)**
> Dự án được xây dựng với mục tiêu số hóa quy trình khai báo y tế, hỗ trợ người dân tự theo dõi sức khỏe và giúp cán bộ y tế phường Thanh Khê quản lý, giám sát tình hình dịch tễ một cách nhanh chóng, chính xác.

---

## 🛠️ Công nghệ Sử dụng (Tech Stack)

| Thành phần | Công nghệ | Chi tiết |
| :--- | :--- | :--- |
| **Database** | Microsoft SQL Server | Lưu trữ dữ liệu quan hệ với 14 bảng chuẩn hóa |
| **Backend** | Java 17 + Spring Boot 3 | API RESTful, Bảo mật với Spring Security & JWT |
| **Frontend** | HTML5 / CSS3 / Vanilla JS | Giao diện Responsive thuần, không sử dụng Framework |
| **Build Tool**| Maven | Quản lý thư viện và vòng đời dự án Java |

---

## 📁 Cấu trúc Dự án (Project Structure)

```text
KhaiBaoYTe/
├── database/                         # Kịch bản CSDL SQL Server
│   ├── 01_create_database.sql        # Lược đồ cơ sở dữ liệu (Schema)
│   ├── 02_seed_data.sql              # Dữ liệu mẫu phục vụ kiểm thử
│   └── README.md                     # Hướng dẫn chi tiết setup database
│
├── backend/                          # Dự án Spring Boot
│   ├── pom.xml                       # Tệp cấu hình thư viện Maven
│   └── src/
│       ├── main/
│       │   ├── java/com/khaibaoyte/  # Mã nguồn ứng dụng Spring Boot
│       │   │   ├── config/           # Cấu hình hệ thống (CORS, Security...)
│       │   │   ├── controller/       # Lớp điều hướng REST Controller
│       │   │   ├── dto/              # Đối tượng truyền tải dữ liệu (DTO)
│       │   │   ├── entity/           # Các JPA Entities mapping với CSDL
│       │   │   ├── exception/        # Quản lý lỗi tập trung (Global Exception Handler)
│       │   │   ├── repository/       # Lớp tương tác CSDL (Spring Data JPA)
│       │   │   ├── security/         # Bảo mật JWT Filter, Authentication
│       │   │   ├── service/          # Xử lý nghiệp vụ chính (Business Logic)
│       │   │   └── util/             # Tiện ích bổ trợ (Token, Mapper...)
│       │   └── resources/
│       │       └── application.yml   # Cấu hình dự án (Port, Database connection, JWT...)
│       └── test/                     # Các kịch bản kiểm thử (Unit test)
│
└── frontend/                         # Giao diện tĩnh (Static Website)
    ├── index.html                    # Trang chủ hệ thống
    ├── assets/
    │   ├── css/                      # Stylesheets (base, layout, components)
    │   ├── js/                       # Scripts (auth, API, mock-data, utils)
    │   └── img/                      # Hình ảnh & Icon tĩnh
    ├── components/                   # Các partial template (Header, Footer...)
    └── pages/                        # Các trang phân quyền theo phân hệ
        ├── public/                   # Dành cho khách vãng lai (tin tức, hướng dẫn)
        ├── auth/                     # Đăng nhập, đăng ký tài khoản, đổi mật khẩu
        ├── citizen/                  # Các trang nghiệp vụ dành cho Người dân
        ├── officer/                  # Các trang dành cho Cán bộ Y tế phường
        └── admin/                    # Các trang quản trị hệ thống của Admin
```

---

## 🚀 Hướng dẫn Cài đặt & Chạy ứng dụng (Getting Started)

> [!IMPORTANT]  
> Hãy chắc chắn máy tính của bạn đã được cài đặt sẵn: **SQL Server**, **Java SDK 17** (hoặc cao hơn) và **Python 3** (hoặc công cụ HTTP Server tương đương).

### Step 1: Cài đặt Cơ sở dữ liệu
1. Kết nối với instance **SQL Server** thông qua SSMS hoặc extension trên VS Code.
2. Mở file [01_create_database.sql](file:///Users/thanhdao/Documents/Đồ án phần mềm/KhaiBaoYTe/database/01_create_database.sql) và thực thi (`F5`) để tạo cơ sở dữ liệu `KhaiBaoYTe`.
3. Mở tiếp file [02_seed_data.sql](file:///Users/thanhdao/Documents/Đồ án phần mềm/KhaiBaoYTe/database/02_seed_data.sql) và thực thi để nạp dữ liệu mẫu chạy thử.

### Step 2: Khởi chạy Backend (Spring Boot)
1. Di chuyển vào thư mục backend:
   ```bash
   cd backend
   ```
2. Cập nhật thông tin kết nối CSDL (nếu cần) tại file [application.yml](file:///Users/thanhdao/Documents/Đồ án phần mềm/KhaiBaoYTe/backend/src/main/resources/application.yml) (dòng 11 - 13: `username`, `password`).
3. Chạy ứng dụng Spring Boot bằng lệnh Maven:
   ```bash
   mvn spring-boot:run
   ```
   *Lúc này backend sẽ được khởi chạy tại cổng **`4000`** với context path là **`/api`** (http://localhost:4000/api).*

### Step 3: Khởi chạy Frontend (HTML/CSS/JS)
> [!WARNING]  
> Do ứng dụng frontend sử dụng đường dẫn tuyệt đối bắt đầu bằng `/frontend/...` để nạp tài nguyên, **bạn phải chạy Web Server từ thư mục gốc của dự án (`KhaiBaoYTe/`), không được chạy trực tiếp từ trong thư mục `frontend/`**.

1. Trở ra thư mục gốc `KhaiBaoYTe`:
   ```bash
   cd ..
   ```
2. Khởi chạy HTTP Server bằng Python tại cổng `5500`:
   ```bash
   python3 -m http.server 5500
   ```
3. Mở trình duyệt và truy cập vào địa chỉ chính xác:
   👉 **[http://localhost:5500/frontend/index.html](http://localhost:5500/frontend/index.html)**

---

## 🔑 Tài khoản Kiểm thử (Demo Accounts)

Hệ thống đã phân quyền chặt chẽ thông qua phân hệ Dashboard riêng biệt cho từng vai trò dưới đây:

| Tài khoản | Mật khẩu | Phân hệ (Role) | Chức năng chính |
| :--- | :--- | :--- | :--- |
| `an` | `123` | **Người dân** (Citizen) | Khai báo y tế, cập nhật sức khỏe, gửi yêu cầu hỗ trợ |
| `binh` | `123` | **Người dân** (Citizen) | Khai báo y tế, cập nhật sức khỏe, gửi yêu cầu hỗ trợ |
| `ha` | `123` | **Cán bộ y tế** (Officer) | Theo dõi sức khỏe người dân, xử lý yêu cầu hỗ trợ, đăng tin tức |
| `admin` | `123` | **Admin** (System Administrator) | Quản lý tài khoản, quản lý danh mục tổ dân phố |

---

## 📝 Quy ước Phát triển (Code Conventions)

- **RESTful API**: Tất cả endpoints backend đều tuân theo chuẩn RESTful, trả về dữ liệu dạng JSON thông qua cấu trúc DTO thống nhất.
- **Bảo mật**: Mọi API yêu cầu phân quyền đều kiểm tra Header `Authorization: Bearer <JWT_TOKEN>`.
- **CSS Tokens**: Frontend sử dụng CSS Variables định nghĩa trong [base.css](file:///Users/thanhdao/Documents/Đồ án phần mềm/KhaiBaoYTe/frontend/assets/css/base.css) để đảm bảo đồng nhất về thiết kế (màu sắc, khoảng cách, font chữ).
- **Phân tách giao diện**: Phần sidebar và topbar được render động theo quyền thông qua module JS [layout.js](file:///Users/thanhdao/Documents/Đồ án phần mềm/KhaiBaoYTe/frontend/assets/js/layout.js) giúp dễ bảo trì và mở rộng sau này.
