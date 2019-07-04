package BUS;

import DAL.DALNhaCungCap;
import DTO.DTONhaCungCap;
import javafx.collections.ObservableList;

public class BUSNhaCungCap {
//    lấy dữ liệu nhà cung cấp

    public static ObservableList<DTONhaCungCap> SupplyALL() {
        return DALNhaCungCap.SupplyAll();
    }
//    Lấy mã nhà cung cấp

    public static String GetMaNCC(String TenNCC) {
        return DALNhaCungCap.GetMaNCC(TenNCC);
    }
//    Lấy điện thoại nhà cung cấp

    public static String GetDT(String TenNCC) {
        return DALNhaCungCap.GetDT(TenNCC);
    }
//    Thêm và cập nhật nhà cung cấp

    public static void IUSupply(DTONhaCungCap nhaccungcap) {
        DALNhaCungCap.IUSupply(nhaccungcap);
    }
//    Xóa thông tin nhà cung cấp

    public static void DeleteSupply(String mancc) {
        DALNhaCungCap.DeleteSupply(mancc);
    }
//    Tăng mã

    public static String Tangma() {
        return DALNhaCungCap.Tangma();
    }
}
