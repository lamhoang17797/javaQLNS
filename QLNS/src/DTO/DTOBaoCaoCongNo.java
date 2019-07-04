package DTO;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

public class DTOBaoCaoCongNo extends RecursiveTreeObject<DTOBaoCaoCongNo>{

    private String MaBCCN, MaBC, MaKH;
    private int NoDau,NoPhatSinh,noCuoi;

    public DTOBaoCaoCongNo() {
    }

    public DTOBaoCaoCongNo(String MaBCCN, String MaBC, String MaKH, int NoDau, int NoPhatSinh, int noCuoi) {
        this.MaBCCN = MaBCCN;
        this.MaBC = MaBC;
        this.MaKH = MaKH;
        this.NoDau = NoDau;
        this.NoPhatSinh = NoPhatSinh;
        this.noCuoi = noCuoi;
    }

    public String getMaBCCN() {
        return MaBCCN;
    }

    public void setMaBCCN(String MaBCCN) {
        this.MaBCCN = MaBCCN;
    }

    public String getMaBC() {
        return MaBC;
    }

    public void setMaBC(String MaBC) {
        this.MaBC = MaBC;
    }

    public String getMaKH() {
        return MaKH;
    }

    public void setMaKH(String MaKH) {
        this.MaKH = MaKH;
    }

    public int getNoDau() {
        return NoDau;
    }

    public void setNoDau(int NoDau) {
        this.NoDau = NoDau;
    }

    public int getNoPhatSinh() {
        return NoPhatSinh;
    }

    public void setNoPhatSinh(int NoPhatSinh) {
        this.NoPhatSinh = NoPhatSinh;
    }

    public int getNoCuoi() {
        return noCuoi;
    }

    public void setNoCuoi(int noCuoi) {
        this.noCuoi = noCuoi;
    }
    
    
}
