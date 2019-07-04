package DAL;

import DTO.DTOTheLoai;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DALTheLoai {

    static ConnectData dataaccess = new ConnectData();
    static ResultSet rs;

//    Lấy dữ liệu thể loại
    public static ObservableList<DTOTheLoai> KindAll() {
        String query = "SELECT * FROM THELOAI";
        ObservableList<DTOTheLoai> arrTheLoai = FXCollections.observableArrayList();
        try {
            rs = dataaccess.Showdata(query);
            while (rs.next()) {
                DTOTheLoai theloaiDTO = new DTOTheLoai();
                theloaiDTO.setMaTL(rs.getNString("MaTL"));
                theloaiDTO.setTenTL(rs.getNString("TenTL"));
                arrTheLoai.add(theloaiDTO);
            }
        } catch (SQLException ex) {
            dataaccess.displayError(ex);
        } finally {
            dataaccess.close();
        }
        return arrTheLoai;
    }

//    Thêm và cập nhật thể loại
    public static void IUKind(DTOTheLoai theloai) {
        String insert = "INSERT INTO THELOAI(MaTl,TenTL)"
                + "VALUES(N'" + theloai.getMaTL() + "',N'" + theloai.getTenTL() + "')";
        String update = "UPDATE THELOAI SET TenTL = N'" + theloai.getTenTL() + "' WHERE MaTL = '" + theloai.getMaTL() + "'";
        if (Checkma(theloai.getMaTL()) == false) {
            if (Check(theloai.getTenTL()) == false) {
                dataaccess.ExecuteQuery(insert, "Thêm", 1);
            } else {
                JOptionPane.showMessageDialog(null, "Thể loại đã tồn tại. Vui lòng kiểm tra lại");
            }
        } else if (Check(theloai.getTenTL()) == false) {
            dataaccess.ExecuteQuery(update, "Cập nhật", 1);
        } else {
            JOptionPane.showMessageDialog(null, "Thông tin thể loại không thay đổi");
        }

    }

//    Xóa thể loại
    public static void DeleteKind(String matl) {
        String delete = "DELETE FROM THELOAI WHERE THELOAI.MaTL = '" + matl + "'";
        dataaccess.ExecuteQuery(delete, "Xóa", 1);
        dataaccess.close();
        String query = "SELECT * FROM THELOAI WHERE THELOAI.MaTL > '" + matl + "'";
        try {
            String ma = "";
            rs = dataaccess.Showdata(query);
            while (rs.next()) {
                ma = ConvertMa(Integer.parseInt(rs.getNString("MaTL").subSequence(2, 8).toString()), 0);
                System.out.println(ma);
                String updateMS = "UPDATE THELOAI SET THELOAI.MaTL = '" + ma + "' WHERE THELOAI.MaTL = '" + rs.getNString("MaTL") + "'";
                dataaccess.ExecuteQuery(updateMS, "Cập nhật", 0);
            }
        } catch (SQLException ex) {
            dataaccess.displayError(ex);
        } finally {
            dataaccess.close();
        }
    }

//    Kiểm tra tên thể loại
    public static boolean Check(String ten) {
        String query = "SELECT * FROM "
                + "THELOAI WHERE THELOAI.TenTL = N'" + ten + "'";
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

//    Kiểm tra mã thể loại
    public static boolean Checkma(String matl) {
        String query = "SELECT * FROM "
                + "THELOAI WHERE THELOAI.MaTL = N'" + matl + "'";
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
        String ma = "TL000001";
        String query = "SELECT * FROM THELOAI WHERE THELOAI.MaTL = (SELECT MAX(MaTL) FROM THELOAI)";
        try {
            rs = dataaccess.Showdata(query);
            while (rs.next()) {
                ma = ConvertMa(Integer.parseInt(rs.getNString("MaTL").subSequence(2, 8).toString()), 1);
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
            ma = "TL00000" + i;
        } else if (i < 100) {
            ma = "TL0000" + i;
        } else if (i < 1000) {
            ma = "TL000" + i;
        } else if (i < 10000) {
            ma = "TL00" + i;
        } else if (i < 100000) {
            ma = "TL0" + i;
        } else if (i < 1000000) {
            ma = "TL" + i;
        }
        return ma;
    }

//    Lấy mã thể loại từ tên thể loại
    public static String GetMaTL(String TenTL) {
        String query = "SELECT * FROM THELOAI WHERE THELOAI.TenTL = N'" + TenTL.trim() + "'";
        String ma = null;
        try {
            rs = dataaccess.Showdata(query);
            while (rs.next()) {
                ma = rs.getNString("MaTL");
            }
        } catch (SQLException ex) {
            dataaccess.displayError(ex);
        } finally {
            dataaccess.close();
        }
        return ma;
    }

//    Đếm thể loại
    public static int CountKind(String matl) {
        String query = "SELECT COUNT(MaTL) AS SL FROM SACH WHERE SACH.MaTL =N'" + matl + "'";
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
