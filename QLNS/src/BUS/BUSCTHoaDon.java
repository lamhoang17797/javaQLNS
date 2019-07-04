package BUS;

import DAL.DALCTHoaDon;
import DTO.DTOCTHoaDon;
import javafx.collections.ObservableList;

public class BUSCTHoaDon {
//    Lấy dữ liệu chi tiết hóa đơn

    public static ObservableList<DTOCTHoaDon> DetailBillAll(String mahd) {
        return DALCTHoaDon.DetailBillAll(mahd);
    }
//    Lấy dữ liệu chi tiết hóa đơn tạm

    public static ObservableList<DTOCTHoaDon> DetailBillAllTemp() {
        return DALCTHoaDon.DetailBillAllTemp();
    }
//    Thêm và sửa chi tiết hóa đơn

    public static void IUDetailBill(DTOCTHoaDon cthd) {
        DALCTHoaDon.IUDetailBill(cthd);
    }
//    Thêm và sửa chi tiết hóa đơn tạm

    public static void IUDetailBillTemp(DTOCTHoaDon cthd) {
        DALCTHoaDon.IUDetailBillTemp(cthd);
    }
//    Xóa chi tiết hóa đơn tạm

    public static void DeleteDetailBillTemp(String ma) {
        DALCTHoaDon.DeleteDetailBillTemp(ma);
    }
//    Xóa dữ liệu chi tiết hóa đơn tạm

    public static void DeleteAllDetailBillTemp() {
        DALCTHoaDon.DeleteAllDetailBillTemp();
    }
//    Tăng mã

    public static String Tangma() {
        return DALCTHoaDon.Tangma();
    }
//    Lấy mã chi tiết hóa đơn

    public static String GetMaCTHD(String mahd, String masach) {
        return DALCTHoaDon.GetMaCTHD(mahd, masach);
    }
//    Lấy số thứ tự

    public static int STT(String mahd, String masach) {
        return DALCTHoaDon.STT(mahd, masach);
    }
//    Tính tổng số lượng bán trong tháng và năm

    public static int TongSLB(String ngay, String masach) {
        return DALCTHoaDon.TongSLB(ngay, masach);
    }
}
