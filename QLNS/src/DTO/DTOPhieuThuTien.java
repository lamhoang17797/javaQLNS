package DTO;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

public class DTOPhieuThuTien extends RecursiveTreeObject<DTOPhieuThuTien> {
    private String MaPT,NgayThu,MaKH;
    private int TienThu;

    public DTOPhieuThuTien(){
    }

    public DTOPhieuThuTien(String MaPT, String NgayThu, String MaKH, int TienThu) {
        this.MaPT = MaPT;
        this.NgayThu = NgayThu;
        this.MaKH = MaKH;
        this.TienThu = TienThu;
    }

    public String getMaPT() {
        return MaPT;
    }

    public void setMaPT(String MaPT) {
        this.MaPT = MaPT;
    }

    public String getNgayThu() {
        return NgayThu;
    }

    public void setNgayThu(String NgayThu) {
        this.NgayThu = NgayThu;
    }

    public String getMaKH() {
        return MaKH;
    }

    public void setMaKH(String MaKH) {
        this.MaKH = MaKH;
    }

    public int getTienThu() {
        return TienThu;
    }

    public void setTienThu(int TienThu) {
        this.TienThu = TienThu;
    }
    
    
}
