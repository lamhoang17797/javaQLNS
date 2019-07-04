package DAL;

import DTO.DTOBaoCaoCongNo;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DALBaoCaoCongNo {

    static ConnectData dataaccess = new ConnectData();
    static ResultSet rs;
//    Lấy dữ liệu báo cáo tồn

    public static ObservableList<DTOBaoCaoCongNo> ReportAlL(String mabc) {
        String query = "SELECT * FROM BCCN,KHACHHANG WHERE BCCN.MaKH = KHACHHANG.MaKH AND BCCN.MaBC = N'" + mabc + "'";
        ObservableList<DTOBaoCaoCongNo> arrBaoCaoTon = FXCollections.observableArrayList();
        try {
            rs = dataaccess.Showdata(query);
            while (rs.next()) {
                DTOBaoCaoCongNo baocaocongnoDTO = new DTOBaoCaoCongNo();
                baocaocongnoDTO.setMaBCCN(rs.getNString("MaBCCN"));
                baocaocongnoDTO.setMaBC(rs.getNString("MaBC"));
                baocaocongnoDTO.setMaKH(rs.getNString("TenKH"));
                baocaocongnoDTO.setNoDau(rs.getInt("NoDau"));
                baocaocongnoDTO.setNoPhatSinh(rs.getInt("NoPhatSinh"));
                baocaocongnoDTO.setNoCuoi(rs.getInt("NoCuoi"));
                arrBaoCaoTon.add(baocaocongnoDTO);
            }
        } catch (SQLException ex) {
            dataaccess.displayError(ex);
        } finally {
            dataaccess.close();
        }
        return arrBaoCaoTon;
    }

//    Thêm báo cáo tồn
    public static void InsertReport(DTOBaoCaoCongNo baocaocongno) {
        String insert = "INSERT INTO BCCN(MaBCCN,MaBC,MaKH,NoDau,NoPhatSinh,NoCuoi)"
                + "VALUES(N'" + baocaocongno.getMaBCCN() + "',N'" + baocaocongno.getMaBC() + "',N'" + baocaocongno.getMaKH() + "'," + baocaocongno.getNoDau() + "," + baocaocongno.getNoPhatSinh() + "," + baocaocongno.getNoCuoi() + ")";
        dataaccess.ExecuteQuery(insert, "Thêm ", 0);
    }

    //    Tăng mã
    public static String Tangma() {
        String ma = "BN000001";
        String query = "SELECT * FROM BCCN WHERE MaBCCN = (SELECT MAX(MaBCCN) FROM BCCN)";
        try {
            rs = dataaccess.Showdata(query);
            while (rs.next()) {
                ma = ConvertMa(Integer.parseInt(rs.getNString("MaBCCN").subSequence(2, 8).toString()), 1);
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
            ma = "BN00000" + i;
        } else if (i < 100) {
            ma = "BN0000" + i;
        } else if (i < 1000) {
            ma = "BN000" + i;
        } else if (i < 10000) {
            ma = "BN00" + i;
        } else if (i < 100000) {
            ma = "BN0" + i;
        } else if (i < 1000000) {
            ma = "BN" + i;
        }
        return ma;
    }
//    Lấy mã báo cáo tồn từ mã báo cáo và mã sách

    public static String GetMaBCCN(String mabc, String makh) {
        String query = "SELECT * FROM BCCN WHERE MaBC = N'" + mabc + "' AND MaKH = N'" + makh + "'";
        String ma = null;
        try {
            rs = dataaccess.Showdata(query);
            while (rs.next()) {
                ma = rs.getNString("MaBCCN");
            }
        } catch (SQLException ex) {
            dataaccess.displayError(ex);
        } finally {
            dataaccess.close();
        }
        return ma;
    }

    public static int STT(String mabc, String makh) {
        String mabccn = GetMaBCCN(mabc, makh);
        int i = 0;
        String query = "SELECT T1.rn AS STT "
                + "from (SELECT ROW_NUMBER() OVER (ORDER BY MaBCCN) AS rn, *"
                + " FROM	BCCN"
                + " WHERE BCCN.MaBC = N'" + mabc + "') AS T1 "
                + "WHERE T1.MaBCCN = N'" + mabccn + "'";
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
