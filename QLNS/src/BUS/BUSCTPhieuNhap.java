package BUS;

import DAL.DALCTPhieuNhap;
import DTO.DTOCTPhieuNhap;
import javafx.collections.ObservableList;

public class BUSCTPhieuNhap {
//    Lấy dữ liệu chi tiết phiếu nhập

    public static ObservableList<DTOCTPhieuNhap> DetailImportAll(String mapn) {
        return DALCTPhieuNhap.DetailImportAll(mapn);
    }
//    Thêm và sửa chi tiết phiếu nhập

    public static void IUDetailImport(DTOCTPhieuNhap ctpn) {
        DALCTPhieuNhap.IUDetailImport(ctpn);
    }
//    Xóa chi tiết phiếu nhập

    public static void DeleteDetailImport(String ma) {
        DALCTPhieuNhap.DeleteDetailImport(ma);
    }
//    Tăng mã

    public static String Tangma() {
        return DALCTPhieuNhap.Tangma();
    }
//    Lấy mã chi tiết phiếu nhập

    public static String GetMaCTPN(String mapn, String masach) {
        return DALCTPhieuNhap.GetMaCTPN(mapn, masach);
    }
//    Lấy giá mua

    public static int GetDG(String mapn, String masach) {
        return DALCTPhieuNhap.GetDG(mapn, masach);
    }
//    lấy số thứ tự

    public static int STT(String mahd, String masach) {
        return DALCTPhieuNhap.STT(mahd, masach);
    }
//    Tính tổng số lượng nhập theo tháng và năm

    public static int TongSLN(String ngay, String masach) {
        return DALCTPhieuNhap.TongSLN(ngay, masach);
    }
    
//    Kiểm tra mã chi tiết phiếu nhập
    public static boolean Check(String mapn, String masach){
        return DALCTPhieuNhap.Checkma(mapn, masach);
    }
}
