package DAL;

import DTO.DTONhaXB;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DALNhaXB {

    static ConnectData dataaccess = new ConnectData();
    static ResultSet rs;

//    Lấy dữ liệu nhà xuất bản
    public static ObservableList<DTONhaXB> PublishAll() {
        String query = "SELECT * FROM NXB";
        ObservableList<DTONhaXB> arrNhaXB = FXCollections.observableArrayList();
        try {
            rs = dataaccess.Showdata(query);
            while (rs.next()) {
                DTONhaXB nhaxbDTO = new DTONhaXB();
                nhaxbDTO.setMaNXB(rs.getNString("MaNXB"));
                nhaxbDTO.setTenNXB(rs.getNString("TenNXB"));
                nhaxbDTO.setDiaChiNXB(rs.getNString("DiaChiNXB"));
                nhaxbDTO.setEmail(rs.getNString("Email"));
                nhaxbDTO.setDT(rs.getNString("DienThoai"));
                arrNhaXB.add(nhaxbDTO);
            }
        } catch (SQLException ex) {
            dataaccess.displayError(ex);
        } finally {
            dataaccess.close();
        }
        return arrNhaXB;
    }

//    Thêm và cập nhật nhà xuất bản
    public static void IUPublish(DTONhaXB nxb) {
        String insert = "INSERT INTO NXB(MaNXB,TenNXB,DiaChiNXB,Email,DienThoai)"
                + "VALUES(N'" + nxb.getMaNXB() + "',N'" + nxb.getTenNXB() + "',N'"
                + nxb.getDiaChiNXB() + "',N'" + nxb.getEmail() + "',N'" + nxb.getDT() + "')";
        String update = "UPDATE NXB SET TenNXB = N'" + nxb.getTenNXB() + "',DiaChiNXB = N'" + nxb.getDiaChiNXB()
                + "',Email = N'" + nxb.getEmail() + "',DienThoai = N'" + nxb.getDT() + "' WHERE MaNXB = N'" + nxb.getMaNXB() + "'";
        if (Checkma(nxb.getMaNXB()) == false) {
            if (Check(nxb.getDiaChiNXB()) == false) {
                dataaccess.ExecuteQuery(insert, "Thêm", 1);
            } else {
                JOptionPane.showMessageDialog(null, "Nhà xuất bản đã tồn tại. Vui lòng kiểm tra lại");
            }
        } else if (Check(nxb.getDiaChiNXB()) == false) {
            dataaccess.ExecuteQuery(update, "Cập nhật", 1);
        } else {
            JOptionPane.showMessageDialog(null, "Thông tin nhà xuất bản không thay đổi");
        }

    }

//    Xóa nhà xuất bản
    public static void DeletePublish(String manxb) {
        String delete = "DELETE FROM NXB WHERE NXB.MaNXB = '" + manxb + "'";
        dataaccess.ExecuteQuery(delete, "Xóa", 1);
        dataaccess.close();
        String query = "SELECT * FROM NXB WHERE NXB.MaNXB > '" + manxb + "'";
        try {
            String ma = "";
            rs = dataaccess.Showdata(query);
            while (rs.next()) {
                ma = ConvertMa(Integer.parseInt(rs.getNString("MaNXB").subSequence(2, 8).toString()), 0);
                System.out.println(ma);
                String updateMS = "UPDATE NXB SET NXB.MaNXB = '" + ma + "' WHERE NXB.MaNXB = '" + rs.getNString("MaNXB") + "'";
                dataaccess.ExecuteQuery(updateMS, "Cập nhật", 0);
            }
        } catch (SQLException ex) {
            dataaccess.displayError(ex);
        } finally {
            dataaccess.close();
        }
    }

//    Kiểm tra tên nhà xuất bản
    public static boolean Check(String ten) {
        String query = "SELECT * FROM "
                + "NXB WHERE NXB.TenNXB = N'" + ten + "'";
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
//    Kiểm tra tên nhà xuât bản

    public static boolean Checkma(String manxb) {
        String query = "SELECT * FROM "
                + "NXB WHERE NXB.MaNXB = N'" + manxb + "'";
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
        String ma = "XB000001";
        String query = "SELECT * FROM NXB WHERE NXB.MaNXB = (SELECT MAX(MaNXB) FROM NXB)";
        try {
            rs = dataaccess.Showdata(query);
            while (rs.next()) {
                ma = ConvertMa(Integer.parseInt(rs.getNString("MaNXB").subSequence(2, 8).toString()), 1);
            }
        } catch (SQLException ex) {
            dataaccess.displayError(ex);
        } finally {
            dataaccess.close();
        }
        return ma;
    }

//    Chuyển đổi mã nhà xuất bản
    public static String ConvertMa(int i, int order) {
        String ma = "";
        if (order == 0) {
            i--;
        } else {
            i++;
        }
        if (i < 10) {
            ma = "XB00000" + i;
        } else if (i < 100) {
            ma = "XB0000" + i;
        } else if (i < 1000) {
            ma = "XB000" + i;
        } else if (i < 10000) {
            ma = "XB00" + i;
        } else if (i < 100000) {
            ma = "XB0" + i;
        } else if (i < 1000000) {
            ma = "XB" + i;
        }
        return ma;
    }
//    Lấy mã nhà xuất bản

    public static String GetMaNXB(String TenNXB) {
        String query = "SELECT NXB.MaNXB FROM NXB WHERE NXB.TenNXB = N'" + TenNXB + "'";
        String ma = null;
        try {
            rs = dataaccess.Showdata(query);
            while (rs.next()) {
                ma = rs.getNString("MaNXB");
            }
        } catch (SQLException ex) {
            dataaccess.displayError(ex);
        } finally {
            dataaccess.close();
        }
        return ma;
    }
//    Đếm nhà xuất bản

    public static int CountPublish(String manxb) {
        String query = "SELECT COUNT(MaNXB) AS SL FROM SACH WHERE SACH.MaNXB =N'" + manxb + "'";
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
