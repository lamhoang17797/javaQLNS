package DTO;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

public class DTOSach extends RecursiveTreeObject<DTOSach>{
    private String MaSach,TenSach,TheLoai,TacGia,NXB,Anh,Mota;
    private int SLT,GB,GM;

    public DTOSach() {
    }

    public DTOSach(String MaSach, String TenSach, String TheLoai, String TacGia, String NXB, String Anh, String Mota, int SLT,int giamua,int giaban) {
        this.MaSach = MaSach;
        this.TenSach = TenSach;
        this.TheLoai = TheLoai;
        this.TacGia = TacGia;
        this.NXB = NXB;
        this.Anh = Anh;
        this.Mota = Mota;
        this.SLT = SLT;
        this.GM = giamua;
        this.GB = giaban;
    }

    
    public String getMaSach() {
        return MaSach;
    }

    public void setMaSach(String MaSach) {
        this.MaSach = MaSach;
    }

    public String getTenSach() {
        return TenSach;
    }

    public void setTenSach(String TenSach) {
        this.TenSach = TenSach;
    }

    public String getTheLoai() {
        return TheLoai;
    }

    public void setTheLoai(String TheLoai) {
        this.TheLoai = TheLoai;
    }

    public String getTacGia() {
        return TacGia;
    }

    public void setTacGia(String TacGia) {
        this.TacGia = TacGia;
    }

    public String getNXB() {
        return NXB;
    }

    public void setNXB(String NXB) {
        this.NXB = NXB;
    }

    public String getAnh() {
        return Anh;
    }

    public void setAnh(String Anh) {
        this.Anh = Anh;
    }

    public String getMota() {
        return Mota;
    }

    public void setMota(String Mota) {
        this.Mota = Mota;
    }

    public int getSLT() {
        return SLT;
    }

    public void setSLT(int SLT) {
        this.SLT = SLT;
    }

    public int getGB() {
        return GB;
    }

    public void setGB(int GB) {
        this.GB = GB;
    }

    public int getGM() {
        return GM;
    }

    public void setGM(int GM) {
        this.GM = GM;
    }
    
    
}
