# Frontend - Khai báo Y tế (HTML/CSS/JS thuần)

Toàn bộ giao diện cho 4 vai trò: khách vãng lai, người dân, cán bộ y tế phường,
admin. Dữ liệu hiển thị tạm dùng `mock-data.js`, sẽ thay bằng `fetch()` đến API
Spring Boot khi nối backend.

## Chạy local

Vì code dùng đường dẫn tuyệt đối `/frontend/...` nên cần serve thư mục cha
(`KhaiBaoYTe/`) làm gốc, **không phải** thư mục `frontend/`.

```bash
# Trong thư mục KhaiBaoYTe (cha của frontend/)
python3 -m http.server 5500
# Mở http://localhost:5500/frontend/index.html
```

Hoặc dùng VS Code Live Server:

1. Mở thư mục `KhaiBaoYTe/` (không phải `frontend/`).
2. Right-click `frontend/index.html` → **Open with Live Server**.

## Tài khoản demo

| Tài khoản | Mật khẩu | Vai trò |
|---|---|---|
| `an`    | `123` | Người dân |
| `binh`  | `123` | Người dân |
| `ha`    | `123` | Cán bộ y tế |
| `admin` | `123` | Admin |

## Cấu trúc

```
frontend/
├── index.html                 Trang chủ (public)
├── assets/
│   ├── css/
│   │   ├── base.css           Reset + design tokens + utilities
│   │   ├── components.css     Button, card, form, table, modal, toast...
│   │   └── layout.css         Sidebar, topbar, public header, auth shell...
│   ├── js/
│   │   ├── auth.js            Mock đăng nhập + role guard (localStorage)
│   │   ├── utils.js           toast, modal, fmtDate, debounce
│   │   ├── mock-data.js       Dữ liệu hiển thị tạm
│   │   ├── layout.js          Render sidebar+topbar theo role (citizen/officer/admin)
│   │   └── public-layout.js   Render header/footer cho public
│   └── img/
├── components/                (chừa cho HTML partial sau này)
└── pages/
    ├── public/    news.html, guide.html, contact.html
    ├── auth/      login.html, register.html, change-password.html
    ├── citizen/   dashboard, declare, history, health-update,
    │              requests, notifications, profile
    ├── officer/   dashboard, declarations, tracking, requests,
    │              news, notifications, reports
    └── admin/     dashboard, accounts, todanpho, trangthai
```

## Quy ước

- Tất cả trang sau khi đăng nhập đều gọi `Layout.mount({ role, active, ... })`
  để inject sidebar + topbar. `role` dùng để chặn truy cập và chọn menu.
- Trang public gọi `PublicLayout.mount('home' | 'news' | 'guide' | 'contact')`.
- Mọi action giả lập đều dùng `toast()` để thông báo, hoặc `modal()` để mở
  popup form. Khi nối API thật, chỉ cần thay phần xử lý submit.

## Khi gắn API Spring Boot

1. Trong `auth.js`: thay `Auth.login` bằng `fetch('/api/auth/login', ...)`,
   lưu token JWT vào localStorage.
2. Tạo `assets/js/api.js` bao gói `fetch` (auto thêm header `Authorization`).
3. Tại mỗi trang, thay `MOCK.xxx` bằng `await api.get('/...')` rồi render lại.
