package DTO;

public class DTOBaoCao {
    private String MaBC;
    int Thang,nam,loai;

    public DTOBaoCao() {
    }

    public DTOBaoCao(String MaBC, int Thang, int nam,int loai) {
        this.MaBC = MaBC;
        this.Thang = Thang;
        this.nam = nam;
        this.loai = loai;
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

    public int getNam() {
        return nam;
    }

    public void setNam(int nam) {
        this.nam = nam;
    }

    public int getLoai() {
        return loai;
    }

    public void setLoai(int loai) {
        this.loai = loai;
    }
    
    
}
