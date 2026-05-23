/* =====================================================================
   File  : 02_seed_data.sql
   Mô tả : Dữ liệu mẫu để kiểm thử nhanh.
   Lưu ý : Chạy SAU file 01_create_database.sql
   ===================================================================== */
USE KhaiBaoYTe;
GO

/* ---------- TODANPHO ---------- */
INSERT INTO TODANPHO (maToDanPho, tenToDanPho, moTa) VALUES
('TDP1', N'Tổ dân phố 1', N'Khu vực đường Hà Huy Tập'),
('TDP2', N'Tổ dân phố 2', N'Khu vực đường Lê Độ'),
('TDP3', N'Tổ dân phố 3', N'Khu vực đường Trần Cao Vân'),
('TDP4', N'Tổ dân phố 4', N'Khu vực đường Điện Biên Phủ');
GO

/* ---------- TRANGTHAI_SUCKHOE ---------- */
INSERT INTO TRANGTHAI_SUCKHOE (maTrangThai, tenTrangThai) VALUES
('TT001', N'Bình thường'),
('TT002', N'Nghi nhiễm'),
('TT003', N'F0 - Đang điều trị'),
('TT004', N'F1 - Cách ly tại nhà'),
('TT005', N'Đã khỏi bệnh');
GO

/* ---------- NGUOIDAN ----------
   Quy ước:
   - vaiTro = 0: người dân thường
   - vaiTro = 1: cán bộ y tế / admin
   - matKhau bên dưới là placeholder cho hash bcrypt (60 ký tự)
*/
INSERT INTO NGUOIDAN
(maNguoiDan, maToDanPho, CCCD, hoTen, ngaySinh, gioiTinh, soDienThoai, eMail,
 diaChiNha, matKhau, ngayTao, trangThai, anhDaiDien, vaiTro)
VALUES
('ND0001', 'TDP1', '048203001001', N'Nguyễn Văn An',  '1990-05-12', 1,
 '0905111001', 'an.nv@example.com',  N'12 Hà Huy Tập, Thanh Khê, Đà Nẵng',
 '$2a$10$abcdefghijklmnopqrstuvABCDEFGHIJKLMNOPQRSTUVWXYZ01234',
 GETDATE(), N'Hoạt động', NULL, 0),

('ND0002', 'TDP1', '048203001002', N'Trần Thị Bình',  '1995-08-20', 0,
 '0905111002', 'binh.tt@example.com', N'25 Hà Huy Tập, Thanh Khê, Đà Nẵng',
 '$2a$10$abcdefghijklmnopqrstuvABCDEFGHIJKLMNOPQRSTUVWXYZ01234',
 GETDATE(), N'Hoạt động', NULL, 0),

('ND0003', 'TDP2', '048203001003', N'Lê Hoàng Cường', '1988-02-03', 1,
 '0905111003', 'cuong.lh@example.com', N'48 Lê Độ, Thanh Khê, Đà Nẵng',
 '$2a$10$abcdefghijklmnopqrstuvABCDEFGHIJKLMNOPQRSTUVWXYZ01234',
 GETDATE(), N'Hoạt động', NULL, 0),

-- Cán bộ y tế (cũng là người dân, vaiTro = 1)
('CB0001', 'TDP3', '048203009001', N'Bác sĩ Phạm Thu Hà', '1985-11-10', 0,
 '0905222001', 'ha.pt@yte.danang.gov.vn',
 N'Trạm y tế phường Thanh Khê',
 '$2a$10$abcdefghijklmnopqrstuvABCDEFGHIJKLMNOPQRSTUVWXYZ01234',
 GETDATE(), N'Hoạt động', NULL, 1),

('CB0002', 'TDP3', '048203009002', N'Y tá Đỗ Minh Khôi',  '1992-04-22', 1,
 '0905222002', 'khoi.dm@yte.danang.gov.vn',
 N'Trạm y tế phường Thanh Khê',
 '$2a$10$abcdefghijklmnopqrstuvABCDEFGHIJKLMNOPQRSTUVWXYZ01234',
 GETDATE(), N'Hoạt động', NULL, 1);
GO

/* ---------- CANBOYTE ---------- */
INSERT INTO CANBOYTE (maCanBo, maNguoiDan, chucVu) VALUES
('CB0001', 'CB0001', N'Trưởng trạm y tế phường'),
('CB0002', 'CB0002', N'Y tá');
GO

/* ---------- KHAIBAOYTE ---------- */
INSERT INTO KHAIBAOYTE
(maKhaiBao, maNguoiDan, thoiGianKhaiBao, nhietDo, trieuChung,
 lichSuDiChuyen, lichSuTiepXuc, ghiChu, trangThaiXuLy)
VALUES
('KB00001', 'ND0001', GETDATE(), 36.7,
 N'Không có triệu chứng',
 N'Đi làm tại công ty ABC, quận Hải Châu',
 N'Tiếp xúc với đồng nghiệp', NULL, N'Đã xử lý'),

('KB00002', 'ND0002', GETDATE(), 38.5,
 N'Sốt, ho khan, đau họng',
 N'Đi siêu thị Co.opmart',
 N'Không xác định', N'Cần theo dõi', N'Đang xử lý'),

('KB00003', 'ND0003', GETDATE(), 36.5,
 N'Không có triệu chứng', NULL, NULL, NULL, N'Chờ xử lý');
GO

/* ---------- TINHTRANGSUCKHOE ---------- */
INSERT INTO TINHTRANGSUCKHOE
(maCapNhat, maKhaiBao, ngayCapNhat, nhietDo, trieuChungHienTai, ghiChu)
VALUES
('CN00001', 'KB00002', GETDATE(), 38.0, N'Vẫn còn sốt nhẹ, ho giảm', N'Đã uống thuốc hạ sốt');
GO

/* ---------- YEUCAUHOTRO ---------- */
INSERT INTO YEUCAUHOTRO
(maYeuCau, maNguoiDan, loaiYeuCau, tieuDe, noiDung, thoiGianGui, trangThai)
VALUES
('YC00001', 'ND0002', N'Tư vấn y tế',
 N'Cần tư vấn về triệu chứng sốt',
 N'Tôi bị sốt 38.5 độ kèm ho, mong được tư vấn cách xử lý.',
 GETDATE(), 'PENDING');
GO

/* ---------- PHANHOIYEUCAU ---------- */
INSERT INTO PHANHOIYEUCAU
(maPhanHoi, maYeuCau, maCanBo, noiDung, thoiGianPhanHoi)
VALUES
('PH00001', 'YC00001', 'CB0001',
 N'Anh/chị vui lòng tự cách ly tại nhà, theo dõi nhiệt độ 2 lần/ngày và liên hệ trạm y tế nếu sốt trên 39 độ.',
 GETDATE());
GO

/* ---------- THEODOISUCKHOE ---------- */
INSERT INTO THEODOISUCKHOE
(maTheoDoi, maNguoiDan, maCanBo, trangThaiSucKhoe, mucDoNguyCo,
 ghiChuChiTiet, thoiGianCapNhat)
VALUES
('TD00001', 'ND0002', 'CB0001', 'TT002', N'Trung bình',
 N'Người dân có biểu hiện sốt, ho. Đề nghị test nhanh COVID-19.',
 GETDATE());
GO

/* ---------- BAIDANGTINTUC ---------- */
INSERT INTO BAIDANGTINTUC
(maBaiDang, maCanBo, tieuDe, tomTat, noiDung, loaiTin, anhDaiDien, ngayDang, trangThai)
VALUES
('BD00001', 'CB0001',
 N'Hướng dẫn khai báo y tế trực tuyến',
 N'Các bước khai báo y tế nhanh chóng tại phường Thanh Khê.',
 N'Bước 1: Đăng nhập tài khoản... Bước 2: Vào mục Khai báo y tế... Bước 3: Điền đầy đủ thông tin...',
 N'Hướng dẫn', NULL, GETDATE(), N'Hiển thị');
GO

/* ---------- THONGBAO + THONGBAO_NGUOINHAN ---------- */
INSERT INTO THONGBAO
(maThongBao, maCanBo, tieuDe, noiDung, loaiThongBao, thoiGianGui)
VALUES
('TB00001', 'CB0001',
 N'Nhắc nhở khai báo y tế định kỳ',
 N'Đề nghị bà con tổ dân phố 1 và 2 thực hiện khai báo y tế tuần này trước 17h thứ Sáu.',
 N'Nhắc nhở', GETDATE());
GO

INSERT INTO THONGBAO_NGUOINHAN (maThongBao, maNguoiDan, trangThaiDoc) VALUES
('TB00001', 'ND0001', 0),
('TB00001', 'ND0002', 0),
('TB00001', 'ND0003', 0);
GO

PRINT N'>>> Đã chèn dữ liệu mẫu thành công.';
GO
