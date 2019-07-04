package DTO;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

public class DTOHoaDon extends RecursiveTreeObject<DTOHoaDon>{
    private String MaHD,MaKH,Ngaylap;
    private int TongTT,TienThu;

    public DTOHoaDon() {
    }

    public DTOHoaDon(String MaHD, String MaKH, String Ngaylap, int TongTT,int TienThu) {
        this.MaHD = MaHD;
        this.MaKH = MaKH;
        this.Ngaylap = Ngaylap;
        this.TongTT = TongTT;
        this.TienThu = TienThu;
    }

    public String getMaHD() {
        return MaHD;
    }

    public void setMaHD(String MaHD) {
        this.MaHD = MaHD;
    }

    public String getMaKH() {
        return MaKH;
    }

    public void setMaKH(String MaKH) {
        this.MaKH = MaKH;
    }

    public String getNgaylap() {
        return Ngaylap;
    }

    public void setNgaylap(String Ngaylap) {
        this.Ngaylap = Ngaylap;
    }

    public int getTongTT() {
        return TongTT;
    }

    public void setTongTT(int TongTT) {
        this.TongTT = TongTT;
    }

    public int getTienThu() {
        return TienThu;
    }

    public void setTienThu(int TienThu) {
        this.TienThu = TienThu;
    }
    
    
}
