CREATE DATABASE QLNS
USE QLNS
DROP DATABASE QLNS

CREATE TABLE THELOAI
(
	MaTL nchar(10) PRIMARY KEY not null,
	TenTL nchar(500),
)

CREATE TABLE TACGIA
(
	MaTG nchar(10) PRIMARY KEY not null,
	TenTG nchar(500),
)

CREATE TABLE NXB
(
	MaNXB nchar(10) PRIMARY KEY not null,
	TenNXB nchar(500),
	DiaChiNXB nchar(500),
	Email nchar(500),
	DienThoai nchar(12),
)

CREATE TABLE SACH
(
	MaSach		nchar(10)PRIMARY KEY not null,
	TenSach		nchar(500),
	MaTL		nchar(10) FOREIGN KEY REFERENCES THELOAI(MaTL),
	MaTG		nchar(10) FOREIGN KEY REFERENCES TACGIA(MaTG),
	MaNXB		nchar(10) FOREIGN KEY REFERENCES NXB(MaNXB),
	Mota		nchar(500),
	SoLuongTon	int,
	GiaBan		int,
	GiaMua		int,
	HinhAnh		nchar(500),
)

CREATE TABLE NCC
(
MaNCC	nchar(10)PRIMARY KEY not null,
TenNCC nchar(500),
DiaChiNCC nchar(500),
DienThoai nchar(12),
Email nchar(50),
HinhAnh		nchar(500),
)

CREATE TABLE PHIEUNHAP
(
MaPN	nchar(10)PRIMARY KEY not null,
NgayNhap nchar(30),
MaNCC	nchar(10)FOREIGN KEY REFERENCES NCC(MaNCC),
TongTT int,
)

CREATE TABLE CHITIETPHIEUNHAP
(
MaCTPN	nchar(10)PRIMARY KEY not null,
MaPN	nchar(10)FOREIGN KEY REFERENCES PHIEUNHAP(MaPN),
MaSach	nchar(10)FOREIGN KEY REFERENCES SACH(MaSach),
GiaMua int,
SLN	int,
)

CREATE TABLE KHACHHANG
(
MaKH	nchar(10)PRIMARY KEY not null,
TenKH nchar(500),
DiaChi nchar(500),
DienThoai nchar(12),
Email nchar(500),
STN float,
HinhAnh		nchar(500),
)

CREATE TABLE HOADON
(
MaHD	nchar(10)PRIMARY KEY not null,
MaKH nchar(10)FOREIGN KEY REFERENCES KHACHHANG(MaKH),
NgayLap nchar(30),
TongTT int,
TienThu int,
)

CREATE TABLE CHITIETHOADON
(
MaCTHD	nchar(10)PRIMARY KEY not null,
MaHD nchar(10)FOREIGN KEY REFERENCES HOADON(MaHD),
MaSach nchar(10)FOREIGN KEY REFERENCES SACH(MaSach),
GiaBan int,
SLB int,
KM int,
)

CREATE TABLE CTHD
(
MaCTHD	nchar(10) PRIMARY KEY not null,
MaHD nchar(10)FOREIGN KEY REFERENCES HOADON(MaHD),
MaSach nchar(10)FOREIGN KEY REFERENCES SACH(MaSach),
GiaBan int,
SLB int,
KM int,
)

CREATE TABLE PHIEUTHUTIEN
(
MaPT	nchar(10)PRIMARY KEY not null,
MaKH nchar(10)FOREIGN KEY REFERENCES KHACHHANG(MaKH),
NgayThu nchar(30),
TienThu int,
)

CREATE TABLE BAOCAO
(
MaBC	nchar(10)PRIMARY KEY not null,
Thang int,
Nam int,
loai int,
)

CREATE TABLE BCT
(
MaBCT nchar(10) PRIMARY KEY not null,
MaBC nchar(10) FOREIGN KEY REFERENCES BAOCAO(MaBC),
MaSach nchar(10)FOREIGN KEY REFERENCES SACH(MaSach),
TonDau int,
TonCuoi int,
TonBan int,
TonNhap int,
)

CREATE TABLE BCDT
(
MaBCDT nchar(10) PRIMARY KEY not null,
MaBC nchar(10) FOREIGN KEY REFERENCES BAOCAO(MaBC),
Thang int,
Nhap int,
Xuat int,
)

CREATE TABLE BCCN
(
MaBCCN nchar(10) PRIMARY KEY not null,
MaBC nchar(10) FOREIGN KEY REFERENCES BAOCAO(MaBC),
MaKH nchar(10)FOREIGN KEY REFERENCES KHACHHANG(MaKH),
NoDau int,
NoPhatSinh int,
NoCuoi int,
)

CREATE TABLE THAMSO
(
MaTS	int identity(1,1) PRIMARY KEY not null,
SLNToiThieu int,
SLTToiDa int,
SLTToiThieu int,
STNToiDa int,
SuDungQuyDinh4 int,
)

CREATE TABLE NGUOIDUNG
(
MaND	nchar(10) PRIMARY KEY not null,
TenND nchar(500),
DienThoai nchar(12),
Email nchar(500),
TenTK nchar(500),
MatKhau nchar(500),
Quyen int,
HinhAnh nchar(500),
)


insert into THAMSO values(150,300,20,20000,1)
insert into THAMSO values(N'ND000001',N'Admin',N'',N'',N'Admin',N'123',0,N'')
