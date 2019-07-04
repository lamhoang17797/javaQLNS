package DAL;

import DTO.DTOPhieuThuTien;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DALPhieuThuTien {

    static ConnectData dataaccess = new ConnectData();
    static ResultSet rs;

//    Lấy dữ liệu phiếu thu tiền
    public static ObservableList<DTOPhieuThuTien> ReceiptsAll() {
        String query = "SELECT PHIEUTHUTIEN.*,KHACHHANG.TenKH FROM PHIEUTHUTIEN,KHACHHANG WHERE PHIEUTHUTIEN.MaKH = KHACHHANG.MaKH";
        ObservableList<DTOPhieuThuTien> arrPhieuThu = FXCollections.observableArrayList();
        try {
            rs = dataaccess.Showdata(query);
            while (rs.next()) {
                DTOPhieuThuTien phieuthuDTO = new DTOPhieuThuTien();
                phieuthuDTO.setMaPT(rs.getNString("MaPT"));
                phieuthuDTO.setMaKH(rs.getNString("TenKH"));
                phieuthuDTO.setNgayThu(rs.getNString("NgayThu"));
                phieuthuDTO.setTienThu(rs.getInt("TienThu"));
                arrPhieuThu.add(phieuthuDTO);
            }
        } catch (SQLException ex) {
            dataaccess.displayError(ex);
        } finally {
            dataaccess.close();
        }
        return arrPhieuThu;
    }

//    Thêm phiêu thu tiền
    public static void InsertReceipt(DTOPhieuThuTien phieuthu) {
        String insert = "INSERT INTO PHIEUTHUTIEN(MaPT,MaKH,NgayThu,TienThu)"
                + "VALUES(N'" + phieuthu.getMaPT() + "',N'" + phieuthu.getMaKH() + "',N'" + phieuthu.getNgayThu() + "'," + phieuthu.getTienThu() + ")";
        dataaccess.ExecuteQuery(insert, "Thêm", 1);
    }

//    Tăng mã
    public static String Tangma() {
        String ma = "PT000001";
        String query = "SELECT * FROM PHIEUTHUTIEN WHERE MaPT = (SELECT MAX(MaPT) FROM PHIEUTHUTIEN)";
        try {
            rs = dataaccess.Showdata(query);
            while (rs.next()) {
                ma = ConvertMa(Integer.parseInt(rs.getNString("MaPT").subSequence(2, 8).toString()), 1);
            }
        } catch (SQLException ex) {
            dataaccess.displayError(ex);
        } finally {
            dataaccess.close();
        }
        return ma;
    }

//    Chuyển đổi mã sách
    public static String ConvertMa(int i, int order) {
        String ma = "";
        if (order == 0) {
            i--;
        } else {
            i++;
        }
        if (i < 10) {
            ma = "PT00000" + i;
        } else if (i < 100) {
            ma = "PT0000" + i;
        } else if (i < 1000) {
            ma = "PT000" + i;
        } else if (i < 10000) {
            ma = "PT00" + i;
        } else if (i < 100000) {
            ma = "PT0" + i;
        } else if (i < 1000000) {
            ma = "PT" + i;
        }
        return ma;
    }
//    Lấy tiền thu

    public static int GetTienThu(String mapt) {
        String query = "SELECT TienThu FROM PHIEUTHUTIEN WHERE MaPT = N'" + mapt + "'";
        int ma = 0;
        try {
            rs = dataaccess.Showdata(query);
            while (rs.next()) {
                ma = rs.getInt("TienThu");
            }
        } catch (SQLException ex) {
            dataaccess.displayError(ex);
        } finally {
            dataaccess.close();
        }
        return ma;
    }

    public static int GetTienThu(String ngay, String makh) {
        String query = "SELECT SUM(TienThu) AS SL FROM PHIEUTHUTIEN WHERE NgayThu LIKE '%" + ngay + "%' AND MaKH = N'" + makh + "'";
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
