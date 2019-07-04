package DTO;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

public class DTOKhachHang extends RecursiveTreeObject<DTOKhachHang>{
    private String MaKH,TenKH,DiaChiKH,DienThoai,Email,HinhAnh;
    private int STN;

    public DTOKhachHang() {
    }

    public DTOKhachHang(String MaKH, String TenKH, String DiaChiKH, String DienThoai, String Email, String HinhAnh, int STN) {
        this.MaKH = MaKH;
        this.TenKH = TenKH;
        this.DiaChiKH = DiaChiKH;
        this.DienThoai = DienThoai;
        this.Email = Email;
        this.HinhAnh = HinhAnh;
        this.STN = STN;
    }

    public String getMaKH() {
        return MaKH;
    }

    public void setMaKH(String MaKH) {
        this.MaKH = MaKH;
    }

    public String getTenKH() {
        return TenKH;
    }

    public void setTenKH(String TenKH) {
        this.TenKH = TenKH;
    }

    public String getDiaChiKH() {
        return DiaChiKH;
    }

    public void setDiaChiKH(String DiaChiKH) {
        this.DiaChiKH = DiaChiKH;
    }

    public String getDienThoai() {
        return DienThoai;
    }

    public void setDienThoai(String DienThoai) {
        this.DienThoai = DienThoai;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getHinhAnh() {
        return HinhAnh;
    }

    public void setHinhAnh(String HinhAnh) {
        this.HinhAnh = HinhAnh;
    }

    public int getSTN() {
        return STN;
    }

    public void setSTN(int STN) {
        this.STN = STN;
    }
    
    
}
