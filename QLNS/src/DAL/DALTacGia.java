package DAL;

import DTO.DTOTacGia;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DALTacGia {

    static ConnectData dataaccess = new ConnectData();
    static ResultSet rs;

//    Lấy dữ liệu tác giả
    public static ObservableList<DTOTacGia> AuthorAll() {
        String query = "SELECT * FROM TACGIA";
        ObservableList<DTOTacGia> arrTacGia = FXCollections.observableArrayList();
        try {
            rs = dataaccess.Showdata(query);
            while (rs.next()) {
                DTOTacGia theloaiDTO = new DTOTacGia();
                theloaiDTO.setMaTG(rs.getNString("MaTG"));
                theloaiDTO.setTenTG(rs.getNString("TenTG"));
                arrTacGia.add(theloaiDTO);
            }
        } catch (SQLException ex) {
            dataaccess.displayError(ex);
        } finally {
            dataaccess.close();
        }
        return arrTacGia;
    }

//    Thêm và cập nhật tác giả
    public static void IUAuthor(DTOTacGia tacgia) {
        String insert = "INSERT INTO TACGIA(MaTG,TenTG)"
                + "VALUES(N'" + tacgia.getMaTG() + "',N'" + tacgia.getTenTG() + "')";
        String update = "UPDATE TACGIA SET TenTG = N'" + tacgia.getTenTG() + "' WHERE MaTG = '" + tacgia.getMaTG() + "'";
        if (Checkma(tacgia.getMaTG()) == false) {
            if (Check(tacgia.getTenTG()) == false) {
                dataaccess.ExecuteQuery(insert, "Thêm", 1);
            } else {
                JOptionPane.showMessageDialog(null, "Tác giả đã tồn tại. Vui lòng kiểm tra lại");
            }
        } else if (Check(tacgia.getTenTG()) == false) {
            dataaccess.ExecuteQuery(update, "Cập nhật", 1);
        } else {
            JOptionPane.showMessageDialog(null, "Thông tin tác giả không thay đổi");
        }

    }

//    Xóa tác giả
    public static void DeleteAuthor(String matg) {
        String delete = "DELETE FROM TACGIA WHERE TACGIA.MaTG = '" + matg + "'";
        dataaccess.ExecuteQuery(delete, "Xóa", 1);
        dataaccess.close();
        String query = "SELECT * FROM TACGIA WHERE TACGIA.MaTG > '" + matg + "'";
        try {
            String ma = "";
            rs = dataaccess.Showdata(query);
            while (rs.next()) {
                ma = ConvertMa(Integer.parseInt(rs.getNString("MaTG").subSequence(2, 8).toString()), 0);
                System.out.println(ma);
                String updateMS = "UPDATE TACGIA SET TACGIA.MaTG = '" + ma + "' WHERE TACGIA.MaTG = '" + rs.getNString("MaTG") + "'";
                dataaccess.ExecuteQuery(updateMS, "Cập nhật", 0);
            }
        } catch (SQLException ex) {
            dataaccess.displayError(ex);
        } finally {
            dataaccess.close();
        }
    }

//    Kiểm tra tên tác giả
    public static boolean Check(String ten) {
        String query = "SELECT * FROM "
                + "TACGIA WHERE TACGIA.TenTG = N'" + ten + "'";
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

//    Kiểm tra mã tác giả
    public static boolean Checkma(String matg) {
        String query = "SELECT * FROM "
                + "TACGIA WHERE TACGIA.MaTG = N'" + matg + "'";
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

//    Tăng mã
    public static String Tangma() {
        String ma = "TG000001";
        String query = "SELECT * FROM TACGIA WHERE TACGIA.MaTG = (SELECT MAX(MaTG) FROM TACGIA)";
        try {
            rs = dataaccess.Showdata(query);
            while (rs.next()) {
                ma = ConvertMa(Integer.parseInt(rs.getNString("MaTG").subSequence(2, 8).toString()), 1);
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
            ma = "TG00000" + i;
        } else if (i < 100) {
            ma = "TG0000" + i;
        } else if (i < 1000) {
            ma = "TG000" + i;
        } else if (i < 10000) {
            ma = "TG00" + i;
        } else if (i < 100000) {
            ma = "TG0" + i;
        } else if (i < 1000000) {
            ma = "TG" + i;
        }
        return ma;
    }

//    Lấy mã tác giả từ tên tác giả
    public static String GetMaTG(String TenTG) {
        String query = "SELECT TACGIA.MaTG FROM TACGIA WHERE TACGIA.TenTG = N'" + TenTG + "'";
        String ma = null;
        try {
            rs = dataaccess.Showdata(query);
            while (rs.next()) {
                ma = rs.getNString("MaTG");
            }
        } catch (SQLException ex) {
            dataaccess.displayError(ex);
        } finally {
            dataaccess.close();
        }
        return ma;
    }

//    Đêm tác giả
    public static int CountAuthor(String matg) {
        String query = "SELECT COUNT(MaTG) AS SL FROM SACH WHERE SACH.MaTG =N'" + matg + "'";
        rs = dataaccess.Showdata(query);
        int i = 0;
        try {
            rs = dataaccess.Showdata(query);
            while (rs.next()) {
                i = rs.getInt("SL");
            }
        } catch (SQLException ex) {
            dataaccess.displayError(ex);
        } finally {
            dataaccess.close();
        }
        return i;
    }
}
