package BUS;

import DAL.DALBaoCao;
import DTO.DTOBaoCao;

public class BUSBaoCao {

//    Tạo báo cáo
    public static void InsertBC(DTOBaoCao baocao) {
        DALBaoCao.InsertBC(baocao);
    }
//    Kiểm tra báo cáo

    public static boolean checkMaBC(int thang, int nam, int loai) {
        return DALBaoCao.CheckMaBC(thang, nam, loai);
    }
//    Lấy mã báo cáo

    public static String GetMaBC(int thang, int nam, int loai) {
        return DALBaoCao.GetMaBC(thang, nam, loai);
    }
//    Tăng mã

    public static String Tangma() {
        return DALBaoCao.Tangma();
    }
}
