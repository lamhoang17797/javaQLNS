package BUS;

import DAL.DALBaoCaoTon;
import DTO.DTOBaoCaoTon;
import javafx.collections.ObservableList;

public class BUSBaoCaoTon {

//    Lấy dữ liệu báo cáo tồn
    public static ObservableList<DTOBaoCaoTon> ReportAlL(String mabc) {
        return DALBaoCaoTon.ReportAlL(mabc);
    }
//    Thêm báo cáo tồn

    public static void InsertReport(DTOBaoCaoTon baocaoton) {
        DALBaoCaoTon.InsertReport(baocaoton);
    }
//    Tăng mã

    public static String Tangma() {
        return DALBaoCaoTon.Tangma();
    }

    public static int STT(String mabc, String masach) {
        return DALBaoCaoTon.STT(mabc, masach);
    }
}
