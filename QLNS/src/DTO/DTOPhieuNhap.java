package DTO;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

public class DTOPhieuNhap extends RecursiveTreeObject<DTOPhieuNhap>{
    private String MaPN,MaNCC,NgayNhap;
    private int TongTT;

    public DTOPhieuNhap() {
    }

    public DTOPhieuNhap(String MaPN, String MaNCC, String NgayNhap, int TongTT) {
        this.MaPN = MaPN;
        this.MaNCC = MaNCC;
        this.NgayNhap = NgayNhap;
        this.TongTT = TongTT;
    }

    public String getMaPN() {
        return MaPN;
    }

    public void setMaPN(String MaPN) {
        this.MaPN = MaPN;
    }

    public String getMaNCC() {
        return MaNCC;
    }

    public void setMaNCC(String MaNCC) {
        this.MaNCC = MaNCC;
    }

    public String getNgayNhap() {
        return NgayNhap;
    }

    public void setNgayNhap(String NgayNhap) {
        this.NgayNhap = NgayNhap;
    }

    public int getTongTT() {
        return TongTT;
    }

    public void setTongTT(int TongTT) {
        this.TongTT = TongTT;
    }
    
   
    
}
