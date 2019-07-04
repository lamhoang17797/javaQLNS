package DTO;

public class DTOThamSo {

    private int MaTS, SLNToiThieu, SLTToiDa, SLTToiThieu, STNToiDa, QuyDinh4;

    public DTOThamSo(int MaTS, int SLNToiThieu, int SLTToiDa, int SLTToiThieu, int STNToiDa, int QuyDinh4) {
        this.MaTS = MaTS;
        this.SLNToiThieu = SLNToiThieu;
        this.SLTToiDa = SLTToiDa;
        this.SLTToiThieu = SLTToiThieu;
        this.STNToiDa = STNToiDa;
        this.QuyDinh4 = QuyDinh4;
    }

    public DTOThamSo() {
    }

    public int getMaTS() {
        return MaTS;
    }

    public void setMaTS(int MaTS) {
        this.MaTS = MaTS;
    }

    public int getSLNToiThieu() {
        return SLNToiThieu;
    }

    public void setSLNToiThieu(int SLNToiThieu) {
        this.SLNToiThieu = SLNToiThieu;
    }

    public int getSLTToiDa() {
        return SLTToiDa;
    }

    public void setSLTToiDa(int SLTToiDa) {
        this.SLTToiDa = SLTToiDa;
    }

    public int getSLTToiThieu() {
        return SLTToiThieu;
    }

    public void setSLTToiThieu(int SLTToiThieu) {
        this.SLTToiThieu = SLTToiThieu;
    }

    public int getSTNToiDa() {
        return STNToiDa;
    }

    public void setSTNToiDa(int STNToiDa) {
        this.STNToiDa = STNToiDa;
    }

    public int getQuyDinh4() {
        return QuyDinh4;
    }

    public void setQuyDinh4(int QuyDinh4) {
        this.QuyDinh4 = QuyDinh4;
    }

    
}
