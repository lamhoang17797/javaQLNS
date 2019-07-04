package DAL;

import DTO.DTOBaoCaoTon;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DALBaoCaoTon {

    static ConnectData dataaccess = new ConnectData();
    static ResultSet rs;
//    Lấy dữ liệu báo cáo tồn

    public static ObservableList<DTOBaoCaoTon> ReportAlL(String mabc) {
        String query = "SELECT * FROM BCT,SACH WHERE BCT.MaSach = Sach.MaSach AND BCT.MaBC = N'" + mabc + "'";
        ObservableList<DTOBaoCaoTon> arrBaoCaoTon = FXCollections.observableArrayList();
        try {
            rs = dataaccess.Showdata(query);
            while (rs.next()) {
                DTOBaoCaoTon baocaotonDTO = new DTOBaoCaoTon();
                baocaotonDTO.setMaBCT(rs.getNString("MaBCT"));
                baocaotonDTO.setMaBC(rs.getNString("MaBC"));
                baocaotonDTO.setMaSach(rs.getNString("TenSach"));
                baocaotonDTO.setTonDau(rs.getInt("TonDau"));
                baocaotonDTO.setTonNhap(rs.getInt("TonNhap"));
                baocaotonDTO.setTonBan(rs.getInt("TonBan"));
                baocaotonDTO.setTonCuoi(rs.getInt("TonCuoi"));
                arrBaoCaoTon.add(baocaotonDTO);
            }
        } catch (SQLException ex) {
            dataaccess.displayError(ex);
        } finally {
            dataaccess.close();
        }
        return arrBaoCaoTon;
    }

//    Thêm báo cáo tồn
    public static void InsertReport(DTOBaoCaoTon baocaoton) {
        String insert = "INSERT INTO BCT(MaBCT,MaBC,MaSach,TonDau,TonCuoi,TonBan,TonNhap)"
                + "VALUES(N'" + baocaoton.getMaBCT() + "',N'" + baocaoton.getMaBC() + "',N'" + baocaoton.getMaSach() + "'," + baocaoton.getTonDau() + "," + baocaoton.getTonCuoi() + "," + baocaoton.getTonBan() + "," + baocaoton.getTonNhap() + ")";
        dataaccess.ExecuteQuery(insert, "Thêm ", 0);
    }

    //    Tăng mã
    public static String Tangma() {
        String ma = "BT000001";
        String query = "SELECT * FROM BCT WHERE MaBCT = (SELECT MAX(MaBCT) FROM BCT)";
        try {
            rs = dataaccess.Showdata(query);
            while (rs.next()) {
                ma = ConvertMa(Integer.parseInt(rs.getNString("MaBCT").subSequence(2, 8).toString()), 1);
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
            ma = "BT00000" + i;
        } else if (i < 100) {
            ma = "BT0000" + i;
        } else if (i < 1000) {
            ma = "BT000" + i;
        } else if (i < 10000) {
            ma = "BT00" + i;
        } else if (i < 100000) {
            ma = "BT0" + i;
        } else if (i < 1000000) {
            ma = "BT" + i;
        }
        return ma;
    }
//    Lấy mã báo cáo tồn từ mã báo cáo và mã sách

    public static String GetMaBCT(String mabc, String masach) {
        String query = "SELECT * FROM BCT WHERE MaBC = N'" + mabc + "' AND MaSach = N'" + masach + "'";
        String ma = null;
        try {
            rs = dataaccess.Showdata(query);
            while (rs.next()) {
                ma = rs.getNString("MaBCT");
            }
        } catch (SQLException ex) {
            dataaccess.displayError(ex);
        } finally {
            dataaccess.close();
        }
        return ma;
    }

    public static int STT(String mabc, String masach) {
        String mabct = GetMaBCT(mabc, masach);
        int i = 0;
        String query = "SELECT T1.rn AS STT "
                + "from (SELECT ROW_NUMBER() OVER (ORDER BY MaBCT) AS rn, *"
                + " FROM	BCT"
                + " WHERE BCT.MaBC = N'" + mabc + "') AS T1 "
                + "WHERE T1.MaBCT = N'" + mabct + "'";
        try {
            rs = dataaccess.Showdata(query);
            while (rs.next()) {
                i = rs.getInt("STT");
            }
        } catch (SQLException ex) {
            dataaccess.displayError(ex);
        } finally {
            dataaccess.close();
        }
        return i;
    }
}
