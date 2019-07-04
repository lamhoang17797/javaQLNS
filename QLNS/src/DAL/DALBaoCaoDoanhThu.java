package DAL;

import DTO.DTOBaoCaoDoanhThu;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DALBaoCaoDoanhThu {

    static ConnectData dataaccess = new ConnectData();
    static ResultSet rs;
//    Lấy dữ liệu báo cáo doanh thu

    public static ObservableList<DTOBaoCaoDoanhThu> ReportAlL(String mabc) {
        String query = "SELECT * FROM BCDT WHERE BCDT.MaBC = N'" + mabc + "'";
        ObservableList<DTOBaoCaoDoanhThu> arrBaoCaoDoanhThu = FXCollections.observableArrayList();
        try {
            rs = dataaccess.Showdata(query);
            while (rs.next()) {
                DTOBaoCaoDoanhThu baocaodoanhthuDTO = new DTOBaoCaoDoanhThu();
                baocaodoanhthuDTO.setMaBCDT(rs.getNString("MaBCDT"));
                baocaodoanhthuDTO.setMaBC(rs.getNString("MaBC"));
                baocaodoanhthuDTO.setThang(rs.getInt("Thang"));
                baocaodoanhthuDTO.setNhap(rs.getInt("Nhap"));
                baocaodoanhthuDTO.setXuat(rs.getInt("Xuat"));
                arrBaoCaoDoanhThu.add(baocaodoanhthuDTO);
            }
        } catch (SQLException ex) {
            dataaccess.displayError(ex);
        } finally {
            dataaccess.close();
        }
        return arrBaoCaoDoanhThu;
    }

//    Thêm báo cáo doanh thu
    public static void InsertReport(DTOBaoCaoDoanhThu baocaodoanhthu) {
        String insert = "INSERT INTO BCDT(MaBCDT,MaBC,Thang,Nhap,Xuat)"
                + "VALUES(N'" + baocaodoanhthu.getMaBCDT() + "',N'" + baocaodoanhthu.getMaBC() + "'," + baocaodoanhthu.getThang() + "," + baocaodoanhthu.getNhap() + "," + baocaodoanhthu.getXuat() + ")";
        dataaccess.ExecuteQuery(insert, "Thêm ", 0);
    }
//    Cập nhật báo cáo doanh thu
    public static void UpdateReport(DTOBaoCaoDoanhThu baocaodoanhthu) {
        String update = "UPDATE BCDT SET Nhap = "+baocaodoanhthu.getNhap()+", Xuat = "+baocaodoanhthu.getXuat() + " WHERE MaBCDT = N'"+ baocaodoanhthu.getMaBCDT()+"'";
        dataaccess.ExecuteQuery(update, "Cập nhật ", 0);
    }

    //    Tăng mã
    public static String Tangma() {
        String ma = "DT000001";
        String query = "SELECT * FROM BCDT WHERE MaBCDT = (SELECT MAX(MaBCDT) FROM BCDT)";
        try {
            rs = dataaccess.Showdata(query);
            while (rs.next()) {
                ma = ConvertMa(Integer.parseInt(rs.getNString("MaBCDT").subSequence(2, 8).toString()), 1);
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
            ma = "DT00000" + i;
        } else if (i < 100) {
            ma = "DT0000" + i;
        } else if (i < 1000) {
            ma = "DT000" + i;
        } else if (i < 10000) {
            ma = "DT00" + i;
        } else if (i < 100000) {
            ma = "DT0" + i;
        } else if (i < 1000000) {
            ma = "DT" + i;
        }
        return ma;
    }
//    Lấy mã báo cáo tồn từ mã báo cáo và mã sách

    public static String GetMaBCDT(String mabc, int thang) {
        String query = "SELECT * FROM BCDT WHERE MaBC = N'" + mabc + "' AND Thang = " + thang;
        String ma = null;
        try {
            rs = dataaccess.Showdata(query);
            while (rs.next()) {
                ma = rs.getNString("MaBCDT");
            }
        } catch (SQLException ex) {
            dataaccess.displayError(ex);
        } finally {
            dataaccess.close();
        }
        return ma;
    }

    public static int STT(String mabc, int thang) {
        String mabcdt = GetMaBCDT(mabc, thang);
        int i = 0;
        String query = "SELECT T1.rn AS STT "
                + "from (SELECT ROW_NUMBER() OVER (ORDER BY MaBCDT) AS rn, *"
                + " FROM	BCDT"
                + " WHERE BCDT.MaBC = N'" + mabc + "') AS T1 "
                + "WHERE T1.MaBCDT = N'" + mabcdt + "'";
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
