package DAL;

import DTO.DTONguoiDung;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.swing.JOptionPane;

public class DALNguoiDung {

    static ConnectData dataaccess = new ConnectData();
    static ResultSet rs;

//    Lấy dữ liệu người dùng
    public static ObservableList<DTONguoiDung> UserAll() {
        String query = "SELECT * FROM NGUOIDUNG";
        ObservableList<DTONguoiDung> arrNguoiDung = FXCollections.observableArrayList();
        try {
            rs = dataaccess.Showdata(query);
            while (rs.next()) {
                DTONguoiDung nguoidungDTO = new DTONguoiDung();
                nguoidungDTO.setMaND(rs.getNString("MaND"));
                nguoidungDTO.setTenND(rs.getNString("TenND"));
                nguoidungDTO.setDienThoai(rs.getNString("DienThoai"));
                nguoidungDTO.setEmail(rs.getNString("Email"));
                nguoidungDTO.setTenTK(rs.getNString("TenTK"));
                nguoidungDTO.setMatKhau(rs.getNString("MatKhau"));
                nguoidungDTO.setQuyen(rs.getInt("Quyen"));
                nguoidungDTO.setHinhAnh(rs.getNString("HinhAnh"));
                arrNguoiDung.add(nguoidungDTO);
            }
        } catch (SQLException ex) {
            dataaccess.displayError(ex);
        } finally {
            dataaccess.close();
        }
        return arrNguoiDung;
    }

//    Thêm và cập nhật khách hàng
    public static void IUUser(DTONguoiDung nguoidung) {
        String insert = "INSERT INTO NGUOIDUNG(MaND,TenND,DienThoai,Email,TenTK,MatKhau,Quyen,HinhAnh)"
                + "VALUES(N'" + nguoidung.getMaND() + "',N'" + nguoidung.getTenND() + "',"
                + "N'" + nguoidung.getDienThoai() + "',N'" + nguoidung.getEmail() + "',N'" + nguoidung.getTenTK() + ""
                + "',N'" + nguoidung.getMatKhau() + "'," + nguoidung.getQuyen() + ",N'" + nguoidung.getHinhAnh() + "')";
        String update = "UPDATE NGUOIDUNG SET TenND = N'" + nguoidung.getTenND() + "',DienThoai = N'" + nguoidung.getDienThoai()
                + "',Email = N'" + nguoidung.getEmail() + "',TenTK = N'" + nguoidung.getTenTK() + "',MatKhau = N'" + nguoidung.getMatKhau()
                + "',Quyen = " + nguoidung.getQuyen() + ",HinhAnh = N'" + nguoidung.getHinhAnh() + "' WHERE MaND = N'" + nguoidung.getMaND() + "'";
        if (Check(nguoidung.getMaND()) == false) {
            if (Check(nguoidung.getTenND(), nguoidung.getDienThoai()) == false) {
                dataaccess.ExecuteQuery(insert, "Thêm", 1);
            } else {
                JOptionPane.showMessageDialog(null, "Người dùng đã tồn tại. Vui lòng kiểm tra lại");
            }
        } else if (Check(nguoidung.getTenND(), nguoidung.getDienThoai(), nguoidung.getEmail(), nguoidung.getTenTK(), nguoidung.getMatKhau(), nguoidung.getQuyen(), nguoidung.getHinhAnh()) == false) {
            dataaccess.ExecuteQuery(update, "Cập nhật", 1);
        } else {
            JOptionPane.showMessageDialog(null, "Người dùng đã tồn tại. Vui lòng kiểm tra lại");
        }
    }

//    Xóa khách hàng
    public static void DeleteUser(String mand) {
        String delete = "DELETE FROM NGUOIDUNG WHERE MaND = N'" + mand + "'";
        dataaccess.ExecuteQuery(delete, "Xóa", 1);
        dataaccess.close();
        String query = "SELECT * FROM NGUOIDUNG WHERE MaND > N'" + mand + "'";
        try {
            String ma = "";
            rs = dataaccess.Showdata(query);
            while (rs.next()) {
                ma = ConvertMa(Integer.parseInt(rs.getNString("MaND").subSequence(2, 8).toString()), 0);
                System.out.println(ma);
                String updateMS = "UPDATE NGUOIDUNG SET MaND = N'" + ma + "' WHERE MaND = N'" + rs.getNString("MaND") + "'";
                dataaccess.ExecuteQuery(updateMS, "Cập nhật", 0);
            }
        } catch (SQLException ex) {
            dataaccess.displayError(ex);
        } finally {
            dataaccess.close();
        }
    }

//    Kiểm tra thông tin khách hàng
    public static boolean Check(String ten, String dienthoai, String email, String tentk, String matkhau, int quyen, String anh) {
        String query = "SELECT * FROM "
                + "NGUOIDUNG WHERE TenND = N'" + ten + "' AND DienThoai = N'" + dienthoai
                + "' AND Email = N'" + email + "' AND TenTK = N'" + tentk
                + "' AND MatKhau = N'" + matkhau + "' AND Quyen = " + quyen + " AND HinhAnh = N'" + anh + "'";
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

//    Kiểm tra mã khách hàng
    public static boolean Check(String mand) {
        String query = "SELECT * FROM "
                + "NGUOIDUNG WHERE MaND = N'" + mand + "'";
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

//    Kiểm tra tên và địa chỉ
    public static boolean Check(String ten, String dienthoai) {
        String query = "SELECT * FROM "
                + "NGUOIDUNG WHERE TenND = N'" + ten + "' AND DienThoai = N'" + dienthoai + "'";
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

    //    Kiểm tra tên tài khoản và mật khẩu
    public static boolean Checkuser(String tentk, String matkhau) {
        String query = "SELECT * FROM "
                + "NGUOIDUNG WHERE TenTK = N'" + tentk + "' AND MatKhau = N'" + matkhau + "'";
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
        String ma = "ND000001";
        String query = "SELECT * FROM NGUOIDUNG WHERE MaND = (SELECT MAX(MaND) FROM NGUOIDUNG)";
        try {
            rs = dataaccess.Showdata(query);
            while (rs.next()) {
                ma = ConvertMa(Integer.parseInt(rs.getNString("MaND").subSequence(2, 8).toString()), 1);
            }
        } catch (SQLException ex) {
            dataaccess.displayError(ex);
        } finally {
            dataaccess.close();
        }
        return ma;
    }

//    Chuyển đổi mã khách hàng
    public static String ConvertMa(int i, int order) {
        String ma = "";
        if (order == 0) {
            i--;
        } else {
            i++;
        }
        if (i < 10) {
            ma = "ND00000" + i;
        } else if (i < 100) {
            ma = "ND0000" + i;
        } else if (i < 1000) {
            ma = "ND000" + i;
        } else if (i < 10000) {
            ma = "ND00" + i;
        } else if (i < 100000) {
            ma = "ND0" + i;
        } else if (i < 1000000) {
            ma = "ND" + i;
        }
        return ma;
    }

    public static int getQuyen(String tennd,String matkhau) {
        String query = "SELECT Quyen FROM NGUOIDUNG WHERE TenTK = N'" + tennd + "' AND MatKhau = N'"+matkhau+"'";
        int ma = 0;
        try {
            rs = dataaccess.Showdata(query);
            while (rs.next()) {
                ma = rs.getInt("Quyen");
            }
        } catch (SQLException ex) {
            dataaccess.displayError(ex);
        } finally {
            dataaccess.close();
        }
        return ma;
    }
}
