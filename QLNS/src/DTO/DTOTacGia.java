package DTO;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
public class DTOTacGia extends RecursiveTreeObject<DTOTacGia>{
    private String MaTG,TenTG;

    public DTOTacGia() {
    }

    public DTOTacGia(String MaTG, String TenTG) {
        this.MaTG = MaTG;
        this.TenTG = TenTG;
    }

    public String getMaTG() {
        return MaTG;
    }

    public void setMaTG(String MaTG) {
        this.MaTG = MaTG;
    }

    public String getTenTG() {
        return TenTG;
    }

    public void setTenTG(String TenTG) {
        this.TenTG = TenTG;
    }
    
    
}
