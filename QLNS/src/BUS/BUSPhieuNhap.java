package BUS;

import javafx.collections.ObservableList;
import DAL.DALPhieuNhap;
import DTO.DTOPhieuNhap;

public class BUSPhieuNhap {
//    Lấy dữ liệu phiếu nhập

    public static ObservableList<DTOPhieuNhap> ImportALL() {
        return DALPhieuNhap.ImportAll();
    }
//    Thêm và cập nhật phiếu nhập

    public static void IUImport(DTOPhieuNhap phieunhap) {
        DALPhieuNhap.IUImport(phieunhap);
    }
//    Cập nhật tổng tiền

    public static void UpdateTT(int tongtt, String mapn) {
        DALPhieuNhap.UpdateTT(tongtt, mapn);
    }
//    Tăng mã

    public static String Tangma() {
        return DALPhieuNhap.Tangma();
    }

    public static int GetTT(String thang,int nam){
        return DALPhieuNhap.GetTT(thang, nam);
    }
}
