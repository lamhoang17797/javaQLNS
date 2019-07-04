package BUS;

import DAL.DALHoaDon;
import DTO.DTOHoaDon;
import javafx.collections.ObservableList;

public class BUSHoaDon {
//    Lấy dữ liệu hóa đơn

    public static ObservableList<DTOHoaDon> BillALL() {
        return DALHoaDon.BillAll();
    }
//    Thêm hóa đơn

    public static void IUBill(DTOHoaDon hoadon) {
        DALHoaDon.IUBill(hoadon);
    }
//    Cập nhật tổng tiền

    public static void UpdateTT(int tongtt, int tienthu, String mahd) {
        DALHoaDon.UpdateTT(tongtt, tienthu, mahd);
    }
//    Tăng mã

    public static String Tangma() {
        return DALHoaDon.Tangma();
    }
//    Lấy tổng tiền

    public static int GetTT(String mahd) {
        return DALHoaDon.GetTT(mahd);
    }

//    Lấy tiền nợ khi mua sách
    public static int GetTNo(String ngay, String makh) {
        return DALHoaDon.GetTNo(ngay, makh);
    }

    public static int GetTT(String thang, int nam) {
        return DALHoaDon.GetTT(thang, nam);
    }
}
