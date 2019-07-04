package DAL;

import DTO.DTOPhieuNhap;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DALPhieuNhap {

    static ConnectData dataaccess = new ConnectData();
    static ResultSet rs;

//    Lấy dữ liệu phiếu nhập
    public static ObservableList<DTOPhieuNhap> ImportAll() {
        String query = "SELECT PHIEUNHAP.*,NCC.TenNCC FROM PHIEUNHAP,NCC WHERE PHIEUNHAP.MaNCC = NCC.MaNCC ";
        ObservableList<DTOPhieuNhap> arrPhieuNhap = FXCollections.observableArrayList();
        try {
            rs = dataaccess.Showdata(query);
            while (rs.next()) {
                DTOPhieuNhap phieunhapDTO = new DTOPhieuNhap();
                phieunhapDTO.setMaPN(rs.getNString("MaPN"));
                phieunhapDTO.setMaNCC(rs.getNString("TenNCC"));
                phieunhapDTO.setNgayNhap(rs.getNString("NgayNhap"));
                phieunhapDTO.setTongTT(rs.getInt("TongTT"));
                arrPhieuNhap.add(phieunhapDTO);
            }
        } catch (SQLException ex) {
            dataaccess.displayError(ex);
        } finally {
            dataaccess.close();
        }
        return arrPhieuNhap;
    }

//    Thêm phiếu nhập
    public static void IUImport(DTOPhieuNhap phieunhap) {
        String insert = "INSERT INTO PHIEUNHAP(MaPN,NgayNhap,MaNCC,TongTT)"
                + "VALUES(N'" + phieunhap.getMaPN() + "',N'" + phieunhap.getNgayNhap() + "',N'" + phieunhap.getMaNCC() + "'," + phieunhap.getTongTT() + ")";
        dataaccess.ExecuteQuery(insert, "Thêm", 1);
    }
//    Cập nhật tổng thành tiền

    public static void UpdateTT(int tongtt, String mapn) {
        String update = "UPDATE PHIEUNHAP SET TongTT = " + tongtt + " WHERE MaPN =N'" + mapn + "'";
        dataaccess.ExecuteQuery(update, "Cập nhật phiếu nhập", 1);
    }
//    tăng mã

    public static String Tangma() {
        String ma = "PN000001";
        String query = "SELECT * FROM PHIEUNHAP WHERE PHIEUNHAP.MaPN = (SELECT MAX(MaPN) FROM PHIEUNHAP)";
        try {
            rs = dataaccess.Showdata(query);
            while (rs.next()) {
                ma = ConvertMa(Integer.parseInt(rs.getNString("MaPN").subSequence(2, 8).toString()), 1);
            }
        } catch (SQLException ex) {
            dataaccess.displayError(ex);
        } finally {
            dataaccess.close();
        }
        return ma;
    }

//    Chuyển đổi mã phiếu nhập
    public static String ConvertMa(int i, int order) {
        String ma = "";
        if (order == 0) {
            i--;
        } else {
            i++;
        }
        if (i < 10) {
            ma = "PN00000" + i;
        } else if (i < 100) {
            ma = "PN0000" + i;
        } else if (i < 1000) {
            ma = "PN000" + i;
        } else if (i < 10000) {
            ma = "PN00" + i;
        } else if (i < 100000) {
            ma = "PN0" + i;
        } else if (i < 1000000) {
            ma = "PN" + i;
        }
        return ma;
    }

    public static int GetTT(String thang, int nam) {
        String ngay = nam + "-" + thang;
        String query = "SELECT SUM(TongTT) AS SL FROM PHIEUNHAP WHERE PHIEUNHAP.NgayNhap LIKE '%" + ngay + "%'";
        int ma = 0;
        try {
            rs = dataaccess.Showdata(query);
            while (rs.next()) {
                ma = rs.getInt("SL");
            }
        } catch (SQLException ex) {
            dataaccess.displayError(ex);
        } finally {
            dataaccess.close();
        }
        return ma;

    }
}
