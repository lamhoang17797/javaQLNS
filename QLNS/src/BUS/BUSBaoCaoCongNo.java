package BUS;

import DAL.DALBaoCaoCongNo;
import DTO.DTOBaoCaoCongNo;
import javafx.collections.ObservableList;

public class BUSBaoCaoCongNo {

    //    Lấy dữ liệu báo cáo tồn
    public static ObservableList<DTOBaoCaoCongNo> ReportAlL(String mabc) {
        return DALBaoCaoCongNo.ReportAlL(mabc);
    }
//    Thêm báo cáo tồn

    public static void InsertReport(DTOBaoCaoCongNo baocaocongno) {
        DALBaoCaoCongNo.InsertReport(baocaocongno);
    }
//    Tăng mã

    public static String Tangma() {
        return DALBaoCaoCongNo.Tangma();
    }

    public static int STT(String mabc, String makh) {
        return DALBaoCaoCongNo.STT(mabc, makh);
    }
}
