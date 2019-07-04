package BUS;

import DAL.DALNguoiDung;
import DTO.DTONguoiDung;
import javafx.collections.ObservableList;

public class BUSNguoiDung {
//    Lấy dữ liệu người dùng

    public static ObservableList<DTONguoiDung> UserALL() {
        return DALNguoiDung.UserAll();
    }
//    Thêm và cập nhật người dùng

    public static void IUUser(DTONguoiDung nguoidung) {
        DALNguoiDung.IUUser(nguoidung);
    }
//    Xóa thông tin người dùng

    public static void DeleteUser(String mand) {
        DALNguoiDung.DeleteUser(mand);
    }
//    Tăng mã

    public static String Tangma() {
        return DALNguoiDung.Tangma();
    }
//    Kiểm tra người dùng

    public static boolean checkuser(String tentk, String matkhau) {
        return DALNguoiDung.Checkuser(tentk, matkhau);
    }
    
    public static int getQuyen(String tennd,String matkhau){
        return DALNguoiDung.getQuyen(tennd, matkhau);
    }
}
