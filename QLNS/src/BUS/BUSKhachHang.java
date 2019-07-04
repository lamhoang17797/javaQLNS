package BUS;

import DAL.DALKhachHang;
import DTO.DTOKhachHang;
import java.util.ArrayList;
import javafx.collections.ObservableList;

public class BUSKhachHang {
//    Lấy dữ liệu khách hàng
    public static ObservableList<DTOKhachHang> CustomerALL() {
        return DALKhachHang.CustomerAll();
    }
//    Thêm và cập nhật khách hàng
    public static void IUCustomer(DTOKhachHang khachhang) {
        DALKhachHang.IUCustomer(khachhang);
    }
//    Cập nhật số tiền nợ
    public static void UpdateSTN(int slt, String masach, int i) {
        DALKhachHang.UpdateSTN(slt, masach, i);
    }
//    Xóa thông tin khách hàng
    public static void DeleteCustomer(String makh) {
        DALKhachHang.DeleteCustomer(makh);
    }
//    Tăng mã
    public static String Tangma() {
        return DALKhachHang.Tangma();
    }
//    Lấy mã khách hàng
    public static String GetMaKH(String tenkh) {
        return DALKhachHang.GetMaKH(tenkh);
    }
//    Lấy số tiền nợ
    public static int GetSTN (String tenkh,String makh) {
        return DALKhachHang.GetSTN(tenkh,makh);
    }

    public static ArrayList<String> IdCustomer() {
        return DALKhachHang.IdCustomer();
    }
}
