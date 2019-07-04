package DTO;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
public class DTOTheLoai extends RecursiveTreeObject<DTOTheLoai>{
    private String MaTL,TenTL;

    public DTOTheLoai() {
    }

    public DTOTheLoai(String MaTL, String TenTL) {
        this.MaTL = MaTL;
        this.TenTL = TenTL;
    }

    public String getMaTL() {
        return MaTL;
    }

    public void setMaTL(String MaTL) {
        this.MaTL = MaTL;
    }

    public String getTenTL() {
        return TenTL;
    }

    public void setTenTL(String TenTL) {
        this.TenTL = TenTL;
    }
    
    
}
