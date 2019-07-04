package BUS;

import DAL.DALTheLoai;
import DTO.DTOTheLoai;
import javafx.collections.ObservableList;

public class BUSTheLoai {
//    Lấy dữ liệu thể loại

    public static ObservableList<DTOTheLoai> KindALL() {
        return DALTheLoai.KindAll();
    }
//    Lấy mã thể loại

    public static String GetMaTL(String TenTL) {
        return DALTheLoai.GetMaTL(TenTL);
    }
//    Xóa thông tin thể loại

    public static void DeleteKind(String matl) {
        DALTheLoai.DeleteKind(matl);
    }
//    Thêm và cập nhật thể loại

    public static void UIKind(DTOTheLoai theloai) {
        DALTheLoai.IUKind(theloai);
    }
//    Tăng mã

    public static String Tangma() {
        return DALTheLoai.Tangma();
    }
//    Đếm thể loại

    public static int CountKind(String matl) {
        return DALTheLoai.CountKind(matl);
    }
}
