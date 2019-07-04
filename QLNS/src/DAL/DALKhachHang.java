package DAL;

import DTO.DTOKhachHang;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DALKhachHang {

    static ConnectData dataaccess = new ConnectData();
    static ResultSet rs;

//    Lấy dữ liệu khách hàng
    public static ObservableList<DTOKhachHang> CustomerAll() {
        String query = "SELECT * FROM KHACHHANG";
        ObservableList<DTOKhachHang> arrKhachHang = FXCollections.observableArrayList();
        try {
            rs = dataaccess.Showdata(query);
            while (rs.next()) {
                DTOKhachHang khachhangDTO = new DTOKhachHang();
                khachhangDTO.setMaKH(rs.getNString("MaKH"));
                khachhangDTO.setTenKH(rs.getNString("TenKH"));
                khachhangDTO.setDiaChiKH(rs.getNString("DiaChi"));
                khachhangDTO.setDienThoai(rs.getNString("DienThoai"));
                khachhangDTO.setEmail(rs.getNString("Email"));
                khachhangDTO.setSTN(rs.getInt("STN"));
                khachhangDTO.setHinhAnh(rs.getNString("HinhAnh"));
                arrKhachHang.add(khachhangDTO);
            }
        } catch (SQLException ex) {
            dataaccess.displayError(ex);
        } finally {
            dataaccess.close();
        }
        return arrKhachHang;
    }
//    Lấy tất cả mã khách hàng

    public static ArrayList<String> IdCustomer() {
        String query = "SELECT MaKH FROM KHACHHANG";
        ArrayList<String> arrSach = new ArrayList<>();
        try {
            rs = dataaccess.Showdata(query);
            while (rs.next()) {
                arrSach.add(rs.getNString("MaKH"));
            }
        } catch (SQLException ex) {
            dataaccess.displayError(ex);
        } finally {
            dataaccess.close();
        }
        return arrSach;
    }
//    Thêm và cập nhật khách hàng

    public static void IUCustomer(DTOKhachHang khachhang) {
        String insert = "INSERT INTO KHACHHANG(MaKH,TenKH,DiaChi,DienThoai,Email,STN,HinhAnh)"
                + "VALUES(N'" + khachhang.getMaKH() + "',N'" + khachhang.getTenKH() + "',"
                + "N'" + khachhang.getDiaChiKH() + "',N'" + khachhang.getDienThoai() + "',N'" + khachhang.getEmail() + ""
                + "'," + khachhang.getSTN() + ",N'" + khachhang.getHinhAnh() + "')";
        String update = "UPDATE KHACHHANG SET TenKH = N'" + khachhang.getTenKH() + "',DiaChi = N'" + khachhang.getDiaChiKH()
                + "',DienThoai = N'" + khachhang.getDienThoai() + "',Email = N'" + khachhang.getEmail() + "',STN = " + khachhang.getSTN()
                + ",HinhAnh = N'" + khachhang.getHinhAnh() + "' WHERE MaKH = '" + khachhang.getMaKH() + "'";
        if (Check(khachhang.getMaKH()) == false) {
            if (Check(khachhang.getTenKH(), khachhang.getDiaChiKH()) == false) {
                dataaccess.ExecuteQuery(insert, "Thêm", 1);
            } else {
                JOptionPane.showMessageDialog(null, "Khách hàng đã tồn tại. Vui lòng kiểm tra lại");
            }
        } else if (Check(khachhang.getTenKH(), khachhang.getDiaChiKH(), khachhang.getDienThoai(), khachhang.getEmail(), khachhang.getHinhAnh()) == false) {
            dataaccess.ExecuteQuery(update, "Cập nhật", 1);
        } else {
            JOptionPane.showMessageDialog(null, "Khách hàng đã tồn tại. Vui lòng kiểm tra lại");
        }
    }

//    Xóa khách hàng
    public static void DeleteCustomer(String makh) {
        String delete = "DELETE FROM KHACHHANG WHERE KHACHHANG.MaKH = '" + makh + "'";
        dataaccess.ExecuteQuery(delete, "Xóa", 1);
        dataaccess.close();
        String query = "SELECT * FROM KHACHHANG WHERE KHACHHANG.MaKH > '" + makh + "'";
        try {
            String ma = "";
            rs = dataaccess.Showdata(query);
            while (rs.next()) {
                ma = ConvertMa(Integer.parseInt(rs.getNString("MaKH").subSequence(2, 8).toString()), 0);
                System.out.println(ma);
                String updateMS = "UPDATE KHACHHANG SET KHACHHANG.MaKH = '" + ma + "' WHERE KHACHHANG.MaKH = '" + rs.getNString("MaKH") + "'";
                dataaccess.ExecuteQuery(updateMS, "Cập nhật", 0);
            }
        } catch (SQLException ex) {
            dataaccess.displayError(ex);
        } finally {
            dataaccess.close();
        }
    }

//    Cập nhật số tiền nợ
    public static void UpdateSTN(int stn, String makh, int i) {
        String query = "SELECT STN FROM KHACHHANG WHERE MaKH = N'" + makh + "'";
        try {
            rs = dataaccess.Showdata(query);
            while (rs.next()) {
                int sl;
                switch (i) {
                    case 1: {
                        sl = rs.getInt("STN") + stn;
                        String update = "UPDATE KHACHHANG set STN = " + sl + " WHERE MaKH = N'" + makh + "'";
                        dataaccess.ExecuteQuery(update, "Cập nhật số tiền nợ ", 0);
                        break;
                    }
                    case 2: {

                        sl = rs.getInt("STN") - stn;
                        if (sl < 0) {
                            sl = 0;
                        }
                        String update = "UPDATE KHACHHANG set STN = " + sl + " WHERE MaKH = N'" + makh + "'";
                        dataaccess.ExecuteQuery(update, "Cập nhật số tiền nợ ", 0);
                        break;
                    }
                    default:
                        break;
                }
            }
        } catch (SQLException ex) {
            dataaccess.displayError(ex);
        } finally {
            dataaccess.close();
        }

    }

//    Kiểm tra thông tin khách hàng
    public static boolean Check(String ten, String diachi, String dienthoai, String email, String anh) {
        String query = "SELECT * FROM "
                + "KHACHHANG WHERE KHACHHANG.TenKH = N'" + ten + "' AND KHACHHANG.DiaChi = N'" + diachi
                + "' AND KHACHHANG.DienThoai = N'" + dienthoai + "' AND KHACHHANG.Email = N'" + email
                + "' AND KHACHHANG.HinhAnh = N'" + anh + "'";
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
    public static boolean Check(String makh) {
        String query = "SELECT * FROM "
                + "KHACHHANG WHERE KHACHHANG.MaKH = N'" + makh + "'";
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
    public static boolean Check(String tenkh, String diachi) {
        String query = "SELECT * FROM "
                + "KHACHHANG WHERE KHACHHANG.TenKH = N'" + tenkh + "' AND KHACHHANG.DiaChi = N'" + diachi + "'";
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
        String ma = "KH000001";
        String query = "SELECT * FROM KHACHHANG WHERE KHACHHANG.MaKH = (SELECT MAX(MaKH) FROM KHACHHANG)";
        try {
            rs = dataaccess.Showdata(query);
            while (rs.next()) {
                ma = ConvertMa(Integer.parseInt(rs.getNString("MaKH").subSequence(2, 8).toString()), 1);
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
            ma = "KH00000" + i;
        } else if (i < 100) {
            ma = "KH0000" + i;
        } else if (i < 1000) {
            ma = "KH000" + i;
        } else if (i < 10000) {
            ma = "KH00" + i;
        } else if (i < 100000) {
            ma = "KH0" + i;
        } else if (i < 1000000) {
            ma = "KH" + i;
        }
        return ma;
    }

//    Lấy mã khách hàng từ tên khách hàng
    public static String GetMaKH(String tenkh) {
        String query = "SELECT MaKH FROM KHACHHANG WHERE TenKH = N'" + tenkh + "'";
        String ma = null;
        try {
            rs = dataaccess.Showdata(query);
            while (rs.next()) {
                ma = rs.getNString("MaKH");
            }
        } catch (SQLException ex) {
            dataaccess.displayError(ex);
        } finally {
            dataaccess.close();
        }
        return ma;
    }
//    Lấy số tiền nợ

    public static int GetSTN(String tenkh,String makh) {
        String query = "SELECT STN FROM KHACHHANG WHERE TenKH = N'" + tenkh + "' OR MaKH = N'"+makh+"'";
        int ma = 0;
        try {
            rs = dataaccess.Showdata(query);
            while (rs.next()) {
                ma = rs.getInt("STN");
            }
        } catch (SQLException ex) {
            dataaccess.displayError(ex);
        } finally {
            dataaccess.close();
        }
        return ma;
    }
}
