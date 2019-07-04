package DTO;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

public class DTOCTPhieuNhap extends RecursiveTreeObject<DTOCTPhieuNhap>{
    private String MaCTPN,MaPN,MaSach;
    private int SLN,GiaMua;

    public DTOCTPhieuNhap() {
    }

    public DTOCTPhieuNhap(String MaCTPN, String MaPN, String MaSach, int GiaMua, int SLN) {
        this.MaCTPN = MaCTPN;
        this.MaPN = MaPN;
        this.MaSach = MaSach;
        this.GiaMua = GiaMua;
        this.SLN = SLN;
    }

    public String getMaCTPN() {
        return MaCTPN;
    }

    public void setMaCTPN(String MaCTPN) {
        this.MaCTPN = MaCTPN;
    }

    public String getMaPN() {
        return MaPN;
    }

    public void setMaPN(String MaPN) {
        this.MaPN = MaPN;
    }

    public String getMaSach() {
        return MaSach;
    }

    public void setMaSach(String MaSach) {
        this.MaSach = MaSach;
    }

    public int getGiaMua() {
        return GiaMua;
    }

    public void setGiaMua(int GiaMua) {
        this.GiaMua = GiaMua;
    }

    public int getSLN() {
        return SLN;
    }

    public void setSLN(int SLN) {
        this.SLN = SLN;
    }
    
    
}
