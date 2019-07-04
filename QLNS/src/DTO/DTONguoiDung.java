package DTO;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

public class DTONguoiDung extends RecursiveTreeObject<DTONguoiDung>{
    private String MaND,TenND,DienThoai,Email,TenTK,MatKhau,HinhAnh;
    private int Quyen;

    public DTONguoiDung() {
    }

    public DTONguoiDung(String MaND, String TenND, String DienThoai, String Email, String TenTK, String MatKhau, int Quyen,String HinhAnh) {
        this.MaND = MaND;
        this.TenND = TenND;
        this.DienThoai = DienThoai;
        this.Email = Email;
        this.TenTK = TenTK;
        this.MatKhau = MatKhau;
        this.Quyen = Quyen;
        this.HinhAnh = HinhAnh;
    }

    public String getMaND() {
        return MaND;
    }

    public void setMaND(String MaND) {
        this.MaND = MaND;
    }

    public String getTenND() {
        return TenND;
    }

    public void setTenND(String TenND) {
        this.TenND = TenND;
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

    public String getTenTK() {
        return TenTK;
    }

    public void setTenTK(String TenTK) {
        this.TenTK = TenTK;
    }

    public String getMatKhau() {
        return MatKhau;
    }

    public void setMatKhau(String MatKhau) {
        this.MatKhau = MatKhau;
    }

    public int getQuyen() {
        return Quyen;
    }

    public void setQuyen(int Quyen) {
        this.Quyen = Quyen;
    }

    public String getHinhAnh() {
        return HinhAnh;
    }

    public void setHinhAnh(String HinhAnh) {
        this.HinhAnh = HinhAnh;
    }
    
    
}
