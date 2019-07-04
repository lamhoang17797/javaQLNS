package DTO;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

public class DTOCTHoaDon extends RecursiveTreeObject<DTOCTHoaDon>{
    private String MaCTHD,MaHD,MaSach;
    private int GiaBan,SLB,KM;

    public DTOCTHoaDon() {
    }

    public DTOCTHoaDon(String MaCTHD, String MaHD, String MaSach, int GiaBan, int SLB,int KM) {
        this.MaCTHD = MaCTHD;
        this.MaHD = MaHD;
        this.MaSach = MaSach;
        this.GiaBan = GiaBan;
        this.SLB = SLB;
        this.KM = KM;
    }

    public String getMaCTHD() {
        return MaCTHD;
    }

    public void setMaCTHD(String MaCTHD) {
        this.MaCTHD = MaCTHD;
    }

    public String getMaHD() {
        return MaHD;
    }

    public void setMaHD(String MaHD) {
        this.MaHD = MaHD;
    }

    public String getMaSach() {
        return MaSach;
    }

    public void setMaSach(String MaSach) {
        this.MaSach = MaSach;
    }

    public int getGiaBan() {
        return GiaBan;
    }

    public void setGiaBan(int GiaBan) {
        this.GiaBan = GiaBan;
    }

    public int getSLB() {
        return SLB;
    }

    public void setSLB(int SLB) {
        this.SLB = SLB;
    }

    public int getKM() {
        return KM;
    }

    public void setKM(int KM) {
        this.KM = KM;
    }
    
    
}
