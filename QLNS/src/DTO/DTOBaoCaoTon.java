package DTO;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

public class DTOBaoCaoTon extends RecursiveTreeObject<DTOBaoCaoTon>{
    private String MaBCT,MaBC,MaSach;
    private int TonDau,TonCuoi,TonBan,TonNhap;

    public DTOBaoCaoTon() {
    }

    public DTOBaoCaoTon(String MaBCT, String MaBC, String MaSach, int TonDau, int TonCuoi, int TonBan, int TonNhap) {
        this.MaBCT = MaBCT;
        this.MaBC = MaBC;
        this.MaSach = MaSach;
        this.TonDau = TonDau;
        this.TonCuoi = TonCuoi;
        this.TonBan = TonBan;
        this.TonNhap = TonNhap;
    }

    
    public String getMaBCT() {
        return MaBCT;
    }

    public void setMaBCT(String MaBCT) {
        this.MaBCT = MaBCT;
    }

    public String getMaBC() {
        return MaBC;
    }

    public void setMaBC(String MaBC) {
        this.MaBC = MaBC;
    }

    public String getMaSach() {
        return MaSach;
    }

    public void setMaSach(String MaSach) {
        this.MaSach = MaSach;
    }

    public int getTonDau() {
        return TonDau;
    }

    public void setTonDau(int TonDau) {
        this.TonDau = TonDau;
    }

    public int getTonCuoi() {
        return TonCuoi;
    }

    public void setTonCuoi(int TonCuoi) {
        this.TonCuoi = TonCuoi;
    }

    public int getTonBan() {
        return TonBan;
    }

    public void setTonBan(int TonBan) {
        this.TonBan = TonBan;
    }

    public int getTonNhap() {
        return TonNhap;
    }

    public void setTonNhap(int TonNhap) {
        this.TonNhap = TonNhap;
    }
   
}
