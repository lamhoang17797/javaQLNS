package DTO;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

public class DTONhaCungCap extends RecursiveTreeObject<DTONhaCungCap>{
    String MaNCC,TenNCC,DiaChiNCC,DienThoai,Email,HinhAnh;

    public DTONhaCungCap() {
    }

    public DTONhaCungCap(String MaNCC, String TenNCC, String DiaChiNCC, String DienThoai, String Email, String HinhAnh) {
        this.MaNCC = MaNCC;
        this.TenNCC = TenNCC;
        this.DiaChiNCC = DiaChiNCC;
        this.DienThoai = DienThoai;
        this.Email = Email;
        this.HinhAnh = HinhAnh;
    }

    public String getMaNCC() {
        return MaNCC;
    }

    public void setMaNCC(String MaNCC) {
        this.MaNCC = MaNCC;
    }

    public String getTenNCC() {
        return TenNCC;
    }

    public void setTenNCC(String TenNCC) {
        this.TenNCC = TenNCC;
    }

    public String getDiaChiNCC() {
        return DiaChiNCC;
    }

    public void setDiaChiNCC(String DiaChiNCC) {
        this.DiaChiNCC = DiaChiNCC;
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
    
    
}
