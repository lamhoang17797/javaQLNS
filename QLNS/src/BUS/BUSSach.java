package BUS;

import DTO.DTOSach;
import DAL.DALSach;
import java.util.ArrayList;
import javafx.collections.ObservableList;

public class BUSSach {

//    Lấy dữ liệu sách
    public static ObservableList<DTOSach> BookALL() {
        return DALSach.BookAll();
    }
//    Thêm và cập nhật sách

    public static void IUBook(DTOSach sach) {
        DALSach.IUBook(sach);
    }

//    Lấy tất cả mã sách
    public static ArrayList<String> IdBook() {
        return DALSach.IdBook();
    }
//    Cập nhật số lượng tồn

    public static void UpdateSLN(int slt, String masach, int i) {
        DALSach.UpdateSLT(slt, masach, i);
    }
//    Xóa thông tin sách

    public static void DeleteBook(String ma) {
        DALSach.DeleteBook(ma);
    }
//    Tăng mã

    public static String Tangma() {
        return DALSach.Tangma();
    }
//    Lấy mã sách

    public static String GetMaSach(String ten) {
        return DALSach.GetMaSach(ten);
    }
//    Lấy giá mua

    public static int GetGM(String ten) {
        return DALSach.GetGM(ten);
    }
//    Lấy giá bán

    public static int GetGB(String ten) {
        return DALSach.GetGB(ten);
    }
//    Lấy số lượng tồn

    public static int GetSLT(String ten,String masach) {
        return DALSach.GetSLT(ten,masach);
    }
}
