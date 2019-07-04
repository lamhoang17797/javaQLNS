package BUS;

import DTO.DTOBaoCaoDoanhThu;
import DAL.DALBaoCaoDoanhThu;
import javafx.collections.ObservableList;

public class BUSBaoCaoDoanhThu {

    //    Lấy dữ liệu báo cáo tồn
    public static ObservableList<DTOBaoCaoDoanhThu> ReportAlL(String mabc) {
        return DALBaoCaoDoanhThu.ReportAlL(mabc);
    }
//    Thêm báo cáo tồn

    public static void InsertReport(DTOBaoCaoDoanhThu baocaodoanhthu) {
        DALBaoCaoDoanhThu.InsertReport(baocaodoanhthu);
    }
//    Cập nhật báo cáo doanh thu

    public static void UpdateReport(DTOBaoCaoDoanhThu baocaodoanhthu) {
        DALBaoCaoDoanhThu.UpdateReport(baocaodoanhthu);
    }
//    Tăng mã

    public static String Tangma() {
        return DALBaoCaoDoanhThu.Tangma();
    }

    public static int STT(String mabc, int thang) {
        return DALBaoCaoDoanhThu.STT(mabc, thang);
    }

    public static String GetMaBCDT(String mabc, int thang) {
        return DALBaoCaoDoanhThu.GetMaBCDT(mabc, thang);
    }
}
