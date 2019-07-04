package DTO;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

public class DTOBaoCaoDoanhThu extends RecursiveTreeObject<DTOBaoCaoDoanhThu> {

    private String MaBCDT, MaBC;
    private int Thang, Nhap, Xuat;

    public DTOBaoCaoDoanhThu() {
    }

    public DTOBaoCaoDoanhThu(String MaBCDT, String MaBC, int Thang, int Nhap, int Xuat) {
        this.MaBCDT = MaBCDT;
        this.MaBC = MaBC;
        this.Thang = Thang;
        this.Nhap = Nhap;
        this.Xuat = Xuat;
    }

    public String getMaBCDT() {
        return MaBCDT;
    }

    public void setMaBCDT(String MaBCDT) {
        this.MaBCDT = MaBCDT;
    }

    public String getMaBC() {
        return MaBC;
    }

    public void setMaBC(String MaBC) {
        this.MaBC = MaBC;
    }

    public int getThang() {
        return Thang;
    }

    public void setThang(int Thang) {
        this.Thang = Thang;
    }

    public int getNhap() {
        return Nhap;
    }

    public void setNhap(int Nhap) {
        this.Nhap = Nhap;
    }

    public int getXuat() {
        return Xuat;
    }

    public void setXuat(int Xuat) {
        this.Xuat = Xuat;
    }

}
