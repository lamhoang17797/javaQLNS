package DAL;

import DTO.DTOBaoCao;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DALBaoCao {

    static ConnectData dataaccess = new ConnectData();
    static ResultSet rs;

//    Thêm báo cáo
    public static void InsertBC(DTOBaoCao baocao) {
        String insert = "INSERT INTO BAOCAO(MaBC,Thang,Nam,loai)"
                + "VALUES(N'" + baocao.getMaBC() + "'," + baocao.getThang() + "," + baocao.getNam() + "," + baocao.getLoai() + ")";
        dataaccess.ExecuteQuery(insert, "Thêm", 1);
    }
//    Kiểm tra báo cáo

    public static boolean CheckMaBC(int thang, int nam, int loai) {
        String query = "SELECT MaBC FROM "
                + "BAOCAO WHERE Thang = " + thang + " AND Nam = " + nam + " AND loai = " + loai;
        rs = dataaccess.Showdata(query);
        try {
            rs = dataaccess.Showdata(query);
            while (!rs.next()) {
                return false;
            }
        } catch (SQLException ex) {
            dataaccess.displayError(ex);
        } finally {
            dataaccess.close();
        }
        return true;
    }
//    Lấy mã báo cáo

    public static String GetMaBC(int thang, int nam, int loai) {
        String query = "SELECT MaBC FROM BAOCAO WHERE Thang = " + thang + " AND Nam = " + nam + " AND loai = " + loai;
        String ma = null;
        try {
            rs = dataaccess.Showdata(query);
            while (rs.next()) {
                ma = rs.getNString("MaBC");
            }
        } catch (SQLException ex) {
            dataaccess.displayError(ex);
        } finally {
            dataaccess.close();
        }
        return ma;
    }
//    Tăng mã

    public static String Tangma() {
        String ma = "BC000001";
        String query = "SELECT * FROM BAOCAO WHERE MaBC = (SELECT MAX(MaBC) FROM BAOCAO)";
        try {
            rs = dataaccess.Showdata(query);
            while (rs.next()) {
                ma = ConvertMa(Integer.parseInt(rs.getNString("MaBC").subSequence(2, 8).toString()), 1);
            }
        } catch (SQLException ex) {
            dataaccess.displayError(ex);
        } finally {
            dataaccess.close();
        }
        return ma;
    }
//    Chuyển đổi mã

    public static String ConvertMa(int i, int order) {
        String ma = "";
        if (order == 0) {
            i--;
        } else {
            i++;
        }
        if (i < 10) {
            ma = "BC00000" + i;
        } else if (i < 100) {
            ma = "BC0000" + i;
        } else if (i < 1000) {
            ma = "BC000" + i;
        } else if (i < 10000) {
            ma = "BC00" + i;
        } else if (i < 100000) {
            ma = "BC0" + i;
        } else if (i < 1000000) {
            ma = "BC" + i;
        }
        return ma;
    }
}
