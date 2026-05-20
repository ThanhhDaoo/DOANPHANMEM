/* =====================================================================
   ĐỒ ÁN PHẦN MỀM - NHÓM 02
   Đề tài: WEBSITE QUẢN LÝ KHAI BÁO Y TẾ - PHƯỜNG THANH KHÊ
   File  : 01_create_database.sql
   Mô tả : Tạo database và toàn bộ bảng theo Mục 4 - THIẾT KẾ CSDL
   DBMS  : Microsoft SQL Server (T-SQL)
   ===================================================================== */

/* ---------- 1. Tạo database ---------- */
IF DB_ID(N'KhaiBaoYTe') IS NOT NULL
BEGIN
    ALTER DATABASE KhaiBaoYTe SET SINGLE_USER WITH ROLLBACK IMMEDIATE;
    DROP DATABASE KhaiBaoYTe;
END
GO

CREATE DATABASE KhaiBaoYTe
    COLLATE Vietnamese_CI_AS;
GO

USE KhaiBaoYTe;
GO

/* =====================================================================
   2. Bảng danh mục (không phụ thuộc khoá ngoại)
   ===================================================================== */

/* ---------- TODANPHO: Tổ dân phố ---------- */
CREATE TABLE TODANPHO (
    maToDanPho   CHAR(4)         NOT NULL,
    tenToDanPho  NVARCHAR(100)   NOT NULL,
    moTa         NVARCHAR(255)   NULL,
    CONSTRAINT PK_TODANPHO PRIMARY KEY (maToDanPho)
);
GO

/* ---------- TRANGTHAI_SUCKHOE: Trạng thái sức khoẻ ---------- */
CREATE TABLE TRANGTHAI_SUCKHOE (
    maTrangThai   CHAR(5)        NOT NULL,
    tenTrangThai  NVARCHAR(100)  NOT NULL,
    CONSTRAINT PK_TRANGTHAI_SUCKHOE PRIMARY KEY (maTrangThai)
);
GO

/* =====================================================================
   3. Bảng người dân & cán bộ y tế
   ===================================================================== */

/* ---------- NGUOIDAN ---------- */
CREATE TABLE NGUOIDAN (
    maNguoiDan    CHAR(6)         NOT NULL,
    maToDanPho    CHAR(4)         NULL,
    CCCD          CHAR(12)        NOT NULL,
    hoTen         NVARCHAR(70)    NOT NULL,
    ngaySinh      DATE            NOT NULL,
    gioiTinh      BIT             NOT NULL,           -- 0: Nữ, 1: Nam
    soDienThoai   VARCHAR(10)     NOT NULL,
    eMail         VARCHAR(50)     NULL,
    diaChiNha     NVARCHAR(100)   NOT NULL,
    matKhau       VARCHAR(60)     NOT NULL,           -- bcrypt hash
    ngayTao       DATETIME        NOT NULL CONSTRAINT DF_NGUOIDAN_ngayTao DEFAULT (GETDATE()),
    trangThai     NVARCHAR(50)    NOT NULL CONSTRAINT DF_NGUOIDAN_trangThai DEFAULT (N'Hoạt động'),
    anhDaiDien    VARCHAR(255)    NULL,
    vaiTro        BIT             NOT NULL,           -- 0: Người dân, 1: Cán bộ/Admin
    CONSTRAINT PK_NGUOIDAN PRIMARY KEY (maNguoiDan),
    CONSTRAINT UQ_NGUOIDAN_CCCD UNIQUE (CCCD),
    CONSTRAINT UQ_NGUOIDAN_SDT  UNIQUE (soDienThoai),
    CONSTRAINT FK_NGUOIDAN_TODANPHO FOREIGN KEY (maToDanPho)
        REFERENCES TODANPHO(maToDanPho)
);
GO

/* ---------- CANBOYTE ---------- */
CREATE TABLE CANBOYTE (
    maCanBo     CHAR(6)         NOT NULL,
    maNguoiDan  CHAR(6)         NOT NULL,
    chucVu      NVARCHAR(100)   NOT NULL,
    CONSTRAINT PK_CANBOYTE PRIMARY KEY (maCanBo),
    CONSTRAINT UQ_CANBOYTE_NGUOIDAN UNIQUE (maNguoiDan),
    CONSTRAINT FK_CANBOYTE_NGUOIDAN FOREIGN KEY (maNguoiDan)
        REFERENCES NGUOIDAN(maNguoiDan)
);
GO

/* =====================================================================
   4. Khai báo y tế & cập nhật tình trạng
   ===================================================================== */

/* ---------- KHAIBAOYTE ---------- */
CREATE TABLE KHAIBAOYTE (
    maKhaiBao         CHAR(7)         NOT NULL,
    maNguoiDan        CHAR(6)         NOT NULL,
    thoiGianKhaiBao   DATETIME        NOT NULL CONSTRAINT DF_KHAIBAOYTE_TG DEFAULT (GETDATE()),
    nhietDo           FLOAT           NOT NULL,
    trieuChung        NVARCHAR(MAX)   NULL,
    lichSuDiChuyen    NVARCHAR(MAX)   NULL,
    lichSuTiepXuc     NVARCHAR(MAX)   NULL,
    ghiChu            NVARCHAR(MAX)   NULL,
    trangThaiXuLy     NVARCHAR(50)    NOT NULL CONSTRAINT DF_KHAIBAOYTE_TT DEFAULT (N'Chờ xử lý'),
    CONSTRAINT PK_KHAIBAOYTE PRIMARY KEY (maKhaiBao),
    CONSTRAINT FK_KHAIBAOYTE_NGUOIDAN FOREIGN KEY (maNguoiDan)
        REFERENCES NGUOIDAN(maNguoiDan)
);
GO

/* ---------- TINHTRANGSUCKHOE: Người dân tự cập nhật theo khai báo ---------- */
CREATE TABLE TINHTRANGSUCKHOE (
    maCapNhat          CHAR(7)         NOT NULL,
    maKhaiBao          CHAR(7)         NOT NULL,
    ngayCapNhat        DATETIME        NOT NULL CONSTRAINT DF_TTSK_ngay DEFAULT (GETDATE()),
    nhietDo            FLOAT           NOT NULL,
    trieuChungHienTai  NVARCHAR(MAX)   NULL,
    ghiChu             NVARCHAR(MAX)   NULL,
    CONSTRAINT PK_TINHTRANGSUCKHOE PRIMARY KEY (maCapNhat),
    CONSTRAINT FK_TINHTRANGSUCKHOE_KHAIBAO FOREIGN KEY (maKhaiBao)
        REFERENCES KHAIBAOYTE(maKhaiBao)
);
GO

/* =====================================================================
   5. Yêu cầu hỗ trợ - Phản hồi - Tệp đính kèm
   ===================================================================== */

/* ---------- YEUCAUHOTRO ---------- */
CREATE TABLE YEUCAUHOTRO (
    maYeuCau       CHAR(7)        NOT NULL,
    maNguoiDan     CHAR(6)        NOT NULL,
    loaiYeuCau     NVARCHAR(30)   NOT NULL,
    tieuDe         NVARCHAR(50)   NOT NULL,
    noiDung        NVARCHAR(255)  NOT NULL,
    thoiGianGui    DATETIME       NOT NULL CONSTRAINT DF_YCHT_TG DEFAULT (GETDATE()),
    trangThai      VARCHAR(20)    NOT NULL CONSTRAINT DF_YCHT_TT DEFAULT ('PENDING'),
    CONSTRAINT PK_YEUCAUHOTRO PRIMARY KEY (maYeuCau),
    CONSTRAINT FK_YEUCAUHOTRO_NGUOIDAN FOREIGN KEY (maNguoiDan)
        REFERENCES NGUOIDAN(maNguoiDan)
);
GO

/* ---------- TEPDINHKEM_YEUCAU ---------- */
CREATE TABLE TEPDINHKEM_YEUCAU (
    maTep      CHAR(7)        NOT NULL,
    maYeuCau   CHAR(7)        NOT NULL,
    tenTep     NVARCHAR(255)  NOT NULL,
    loaiTep    VARCHAR(50)    NOT NULL,
    duongDan   VARCHAR(255)   NOT NULL,
    CONSTRAINT PK_TEPDINHKEM_YEUCAU PRIMARY KEY (maTep),
    CONSTRAINT FK_TDKYC_YEUCAU FOREIGN KEY (maYeuCau)
        REFERENCES YEUCAUHOTRO(maYeuCau)
);
GO

/* ---------- PHANHOIYEUCAU ---------- */
CREATE TABLE PHANHOIYEUCAU (
    maPhanHoi        CHAR(7)        NOT NULL,
    maYeuCau         CHAR(7)        NOT NULL,
    maCanBo          CHAR(6)        NOT NULL,
    noiDung          NVARCHAR(255)  NOT NULL,
    thoiGianPhanHoi  DATETIME       NOT NULL CONSTRAINT DF_PHYC_TG DEFAULT (GETDATE()),
    CONSTRAINT PK_PHANHOIYEUCAU PRIMARY KEY (maPhanHoi),
    CONSTRAINT FK_PHANHOI_YEUCAU FOREIGN KEY (maYeuCau)
        REFERENCES YEUCAUHOTRO(maYeuCau),
    CONSTRAINT FK_PHANHOI_CANBO FOREIGN KEY (maCanBo)
        REFERENCES CANBOYTE(maCanBo)
);
GO

/* ---------- TEPDINHKEM_PHANHOI ---------- */
CREATE TABLE TEPDINHKEM_PHANHOI (
    maTep       CHAR(7)        NOT NULL,
    maPhanHoi   CHAR(7)        NOT NULL,
    tenTep      NVARCHAR(255)  NOT NULL,
    loaiTep     VARCHAR(50)    NOT NULL,
    duongDan    VARCHAR(255)   NOT NULL,
    CONSTRAINT PK_TEPDINHKEM_PHANHOI PRIMARY KEY (maTep),
    CONSTRAINT FK_TDKPH_PHANHOI FOREIGN KEY (maPhanHoi)
        REFERENCES PHANHOIYEUCAU(maPhanHoi)
);
GO

/* =====================================================================
   6. Theo dõi sức khoẻ (do cán bộ y tế cập nhật)
   ===================================================================== */
CREATE TABLE THEODOISUCKHOE (
    maTheoDoi         CHAR(7)         NOT NULL,
    maNguoiDan        CHAR(6)         NOT NULL,
    maCanBo           CHAR(6)         NOT NULL,
    trangThaiSucKhoe  CHAR(5)         NOT NULL,
    mucDoNguyCo       NVARCHAR(30)    NOT NULL,   -- Thấp / Trung bình / Cao
    ghiChuChiTiet     NVARCHAR(MAX)   NOT NULL,
    thoiGianCapNhat   DATETIME        NOT NULL CONSTRAINT DF_TDSK_TG DEFAULT (GETDATE()),
    CONSTRAINT PK_THEODOISUCKHOE PRIMARY KEY (maTheoDoi),
    CONSTRAINT FK_TDSK_NGUOIDAN FOREIGN KEY (maNguoiDan)
        REFERENCES NGUOIDAN(maNguoiDan),
    CONSTRAINT FK_TDSK_CANBO FOREIGN KEY (maCanBo)
        REFERENCES CANBOYTE(maCanBo),
    CONSTRAINT FK_TDSK_TRANGTHAI FOREIGN KEY (trangThaiSucKhoe)
        REFERENCES TRANGTHAI_SUCKHOE(maTrangThai)
);
GO

/* =====================================================================
   7. Tin tức & Thông báo
   ===================================================================== */

/* ---------- BAIDANGTINTUC ---------- */
CREATE TABLE BAIDANGTINTUC (
    maBaiDang    CHAR(7)        NOT NULL,
    maCanBo      CHAR(6)        NOT NULL,
    tieuDe       NVARCHAR(100)  NOT NULL,
    tomTat       NVARCHAR(255)  NOT NULL,
    noiDung      NVARCHAR(MAX)  NOT NULL,
    loaiTin      NVARCHAR(50)   NOT NULL,
    anhDaiDien   VARCHAR(255)   NULL,
    ngayDang     DATETIME       NOT NULL CONSTRAINT DF_BAIDANG_TG DEFAULT (GETDATE()),
    trangThai    NVARCHAR(50)   NOT NULL CONSTRAINT DF_BAIDANG_TT DEFAULT (N'Hiển thị'),
    CONSTRAINT PK_BAIDANGTINTUC PRIMARY KEY (maBaiDang),
    CONSTRAINT FK_BAIDANG_CANBO FOREIGN KEY (maCanBo)
        REFERENCES CANBOYTE(maCanBo)
);
GO

/* ---------- THONGBAO ---------- */
CREATE TABLE THONGBAO (
    maThongBao    CHAR(7)        NOT NULL,
    maCanBo       CHAR(6)        NOT NULL,
    tieuDe        NVARCHAR(50)   NOT NULL,
    noiDung       NVARCHAR(255)  NOT NULL,
    loaiThongBao  NVARCHAR(30)   NOT NULL,
    thoiGianGui   DATETIME       NOT NULL CONSTRAINT DF_THONGBAO_TG DEFAULT (GETDATE()),
    CONSTRAINT PK_THONGBAO PRIMARY KEY (maThongBao),
    CONSTRAINT FK_THONGBAO_CANBO FOREIGN KEY (maCanBo)
        REFERENCES CANBOYTE(maCanBo)
);
GO

/* ---------- THONGBAO_NGUOINHAN: bảng trung gian N-N ---------- */
CREATE TABLE THONGBAO_NGUOINHAN (
    maThongBao    CHAR(7)    NOT NULL,
    maNguoiDan    CHAR(6)    NOT NULL,
    trangThaiDoc  BIT        NOT NULL CONSTRAINT DF_TBNN_doc DEFAULT (0),
    thoiGianDoc   DATETIME   NULL,
    CONSTRAINT PK_THONGBAO_NGUOINHAN PRIMARY KEY (maThongBao, maNguoiDan),
    CONSTRAINT FK_TBNN_THONGBAO FOREIGN KEY (maThongBao)
        REFERENCES THONGBAO(maThongBao),
    CONSTRAINT FK_TBNN_NGUOIDAN FOREIGN KEY (maNguoiDan)
        REFERENCES NGUOIDAN(maNguoiDan)
);
GO

/* =====================================================================
   8. Index hỗ trợ truy vấn thường gặp
   ===================================================================== */
CREATE INDEX IX_NGUOIDAN_TODANPHO        ON NGUOIDAN(maToDanPho);
CREATE INDEX IX_KHAIBAOYTE_NGUOIDAN      ON KHAIBAOYTE(maNguoiDan, thoiGianKhaiBao DESC);
CREATE INDEX IX_TINHTRANGSUCKHOE_KHAIBAO ON TINHTRANGSUCKHOE(maKhaiBao, ngayCapNhat DESC);
CREATE INDEX IX_YEUCAUHOTRO_NGUOIDAN     ON YEUCAUHOTRO(maNguoiDan, thoiGianGui DESC);
CREATE INDEX IX_THEODOISUCKHOE_NGUOIDAN  ON THEODOISUCKHOE(maNguoiDan, thoiGianCapNhat DESC);
CREATE INDEX IX_BAIDANGTINTUC_NGAYDANG   ON BAIDANGTINTUC(ngayDang DESC);
CREATE INDEX IX_THONGBAO_THOIGIAN        ON THONGBAO(thoiGianGui DESC);
GO

PRINT N'>>> Hoàn tất tạo database KhaiBaoYTe và 14 bảng theo Mục 4.';
GO
