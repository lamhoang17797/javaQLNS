package DTO;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

public class DTONhaXB extends RecursiveTreeObject<DTONhaXB>{
    private String MaNXB,TenNXB,DiaChiNXB,Email,DT;

    public DTONhaXB() {
    }

    public DTONhaXB(String MaNXB, String TenNXB, String DiaChiNXB, String Email, String DT) {
        this.MaNXB = MaNXB;
        this.TenNXB = TenNXB;
        this.DiaChiNXB = DiaChiNXB;
        this.Email = Email;
        this.DT = DT;
    }

    public String getMaNXB() {
        return MaNXB;
    }

    public void setMaNXB(String MaNXB) {
        this.MaNXB = MaNXB;
    }

    public String getTenNXB() {
        return TenNXB;
    }

    public void setTenNXB(String TenNXB) {
        this.TenNXB = TenNXB;
    }

    public String getDiaChiNXB() {
        return DiaChiNXB;
    }

    public void setDiaChiNXB(String DiaChiNXB) {
        this.DiaChiNXB = DiaChiNXB;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getDT() {
        return DT;
    }

    public void setDT(String DT) {
        this.DT = DT;
    }
    
    
}
