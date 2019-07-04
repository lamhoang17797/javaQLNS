package BUS;

import DAL.DALTacGia;
import DTO.DTOTacGia;
import javafx.collections.ObservableList;

public class BUSTacGia {
//    Lấy dữ liệu tác giả

    public static ObservableList<DTOTacGia> AuthorALL() {
        return DALTacGia.AuthorAll();
    }
//    Lấy mã tác giả

    public static String GetMaTG(String TenTG) {
        return DALTacGia.GetMaTG(TenTG);
    }
//    Xóa thông tin tác giả

    public static void DeleteAuthor(String matg) {
        DALTacGia.DeleteAuthor(matg);
    }
//    Thêm và cập nhật tác giả

    public static void UIAuthor(DTOTacGia tacgia) {
        DALTacGia.IUAuthor(tacgia);
    }
//    Tăng mã

    public static String Tangma() {
        return DALTacGia.Tangma();
    }
//    Đếm số lượng tác giả

    public static int CountAuthor(String matg) {
        return DALTacGia.CountAuthor(matg);
    }
}
