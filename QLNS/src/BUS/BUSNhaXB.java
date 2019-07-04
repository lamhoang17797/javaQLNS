package BUS;

import DAL.DALNhaXB;
import DTO.DTONhaXB;
import javafx.collections.ObservableList;

public class BUSNhaXB {
//    Lấy dữ liệu nhà xuất bản

    public static ObservableList<DTONhaXB> PublishALL() {
        return DALNhaXB.PublishAll();
    }
//    lấy mã nhà xuất bản

    public static String GetMaNXB(String TenNXB) {
        return DALNhaXB.GetMaNXB(TenNXB);
    }
//    Xóa nhà xuất bản

    public static void DeletePublish(String manxb) {
        DALNhaXB.DeletePublish(manxb);
    }
//    Thêm và cập nhật nhà xuất bản

    public static void UIPublish(DTONhaXB nxb) {
        DALNhaXB.IUPublish(nxb);
    }
//    Tăng mã

    public static String Tangma() {
        return DALNhaXB.Tangma();
    }
//    Đếm số lượng nhà xuất bản 

    public static int CountPubish(String Manxb) {
        return DALNhaXB.CountPublish(Manxb);
    }
}
