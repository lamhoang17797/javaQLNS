package BUS;

import DAL.DALPhieuThuTien;
import DTO.DTOPhieuThuTien;
import javafx.collections.ObservableList;

public class BUSPhieuThuTien {
//    Lấy dữ liệu phiếu thu tiền

    public static ObservableList<DTOPhieuThuTien> ReceiptsAll() {
        return DALPhieuThuTien.ReceiptsAll();
    }
//    Thêm phiếu thu tiền

    public static void InsertReceipt(DTOPhieuThuTien phieuthu) {
        DALPhieuThuTien.InsertReceipt(phieuthu);
    }
//    Tăng mã

    public static String Tangma() {
        return DALPhieuThuTien.Tangma();
    }
//    Lấy số tiền thu

    public static int GetTienThu(String mapt) {
        return DALPhieuThuTien.GetTienThu(mapt);
    }
    
        public static int GetTienThu(String ngay,String makh) {
        return DALPhieuThuTien.GetTienThu(ngay,makh);
    }
}
