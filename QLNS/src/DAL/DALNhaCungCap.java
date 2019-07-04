package DAL;

import DTO.DTONhaCungCap;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DALNhaCungCap {

    static ConnectData dataaccess = new ConnectData();
    static ResultSet rs;

//    Lấy dữ liệu nhà cung cấp
    public static ObservableList<DTONhaCungCap> SupplyAll() {
        String query = "SELECT * FROM NCC";
        ObservableList<DTONhaCungCap> arrNCC = FXCollections.observableArrayList();
        try {
            rs = dataaccess.Showdata(query);
            while (rs.next()) {
                DTONhaCungCap nccDTO = new DTONhaCungCap();
                nccDTO.setMaNCC(rs.getNString("MaNCC"));
                nccDTO.setTenNCC(rs.getNString("TenNCC"));
                nccDTO.setDiaChiNCC(rs.getNString("DiaChiNCC"));
                nccDTO.setDienThoai(rs.getNString("DienThoai"));
                nccDTO.setEmail(rs.getNString("Email"));
                nccDTO.setHinhAnh(rs.getNString("HinhAnh"));
                arrNCC.add(nccDTO);
            }
        } catch (SQLException ex) {
            dataaccess.displayError(ex);
        } finally {
            dataaccess.close();
        }
        return arrNCC;
    }

//    Thêm và cập nhật nhà cung cấp
    public static void IUSupply(DTONhaCungCap nhaccungcap) {
        String insert = "INSERT INTO NCC(MaNCC,TenNCC,DiaChiNCC,DienThoai,Email,HinhAnh)"
                + "VALUES(N'" + nhaccungcap.getMaNCC() + "',N'" + nhaccungcap.getTenNCC() + "',"
                + "N'" + nhaccungcap.getDiaChiNCC() + "',N'" + nhaccungcap.getDienThoai() + "',N'" + nhaccungcap.getEmail() + ""
                + "',N'" + nhaccungcap.getHinhAnh() + "')";
        String update = "UPDATE NCC SET TenNCC = N'" + nhaccungcap.getTenNCC() + "',DiaChiNCC = N'" + nhaccungcap.getDiaChiNCC()
                + "',DienThoai = N'" + nhaccungcap.getDienThoai() + "',Email = N'" + nhaccungcap.getEmail()
                + "',HinhAnh = N'" + nhaccungcap.getHinhAnh() + "' WHERE MaNCC = N'" + nhaccungcap.getMaNCC() + "'";
        if (Check(nhaccungcap.getMaNCC()) == false) {
            if (Check(nhaccungcap.getTenNCC(), nhaccungcap.getDiaChiNCC()) == false) {
                dataaccess.ExecuteQuery(insert, "Thêm", 1);
            } else {
                JOptionPane.showMessageDialog(null, "Khách hàng đã tồn tại. Vui lòng kiểm tra lại");
            }
        } else if (Check(nhaccungcap.getTenNCC(), nhaccungcap.getDiaChiNCC(), nhaccungcap.getDienThoai(), nhaccungcap.getEmail(), nhaccungcap.getHinhAnh()) == false) {
            dataaccess.ExecuteQuery(update, "Cập nhật", 1);
        } else {
            JOptionPane.showMessageDialog(null, "Thông tin nhà cung cấp không thay đổi");
        }
    }

//    Xóa nhà cung cấp
    public static void DeleteSupply(String mancc) {
        String delete = "DELETE FROM NCC WHERE NCC.MaNCC = '" + mancc + "'";
        dataaccess.ExecuteQuery(delete, "Xóa", 1);
        dataaccess.close();
        String query = "SELECT * FROM NCC WHERE NCC.MaNCC > '" + mancc + "'";
        try {
            String ma = "";
            rs = dataaccess.Showdata(query);
            while (rs.next()) {
                ma = ConvertMa(Integer.parseInt(rs.getNString("MaNCC").subSequence(2, 8).toString()), 0);
                System.out.println(ma);
                String updateMS = "UPDATE NCC SET NCC.MaNCC = '" + ma + "' WHERE NCC.MaNCC = '" + rs.getNString("MaNCC") + "'";
                dataaccess.ExecuteQuery(updateMS, "Cập nhật", 0);
            }
        } catch (SQLException ex) {
            dataaccess.displayError(ex);
        } finally {
            dataaccess.close();
        }
    }

//    Kiểm tra thông tin nhà cung cấp
    public static boolean Check(String ten, String diachi, String dienthoai, String email, String anh) {
        String query = "SELECT * FROM "
                + "NCC WHERE NCC.TenNCC = N'" + ten + "' AND NCC.DiaChiNCC = N'" + diachi
                + "' AND NCC.DienThoai = N'" + dienthoai + "' AND NCC.Email = N'" + email
                + "' AND NCC.HinhAnh = N'" + anh + "'";
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

//    Kiểm tra mã nhà cung cấp
    public static boolean Check(String mancc) {
        String query = "SELECT * FROM "
                + "NCC WHERE NCC.MaNCC = N'" + mancc + "'";
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

//    Kiểm tra tên ,địa chỉ
    public static boolean Check(String ten, String diachi) {
        String query = "SELECT * FROM "
                + "NCC WHERE NCC.TenNCC = N'" + ten + "' AND NCC.DiaChiNCC = N'" + diachi + "'";
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
        String ma = "CC000001";
        String query = "SELECT * FROM NCC WHERE NCC.MaNCC = (SELECT MAX(MaNCC) FROM NCC)";
        try {
            rs = dataaccess.Showdata(query);
            while (rs.next()) {
                ma = ConvertMa(Integer.parseInt(rs.getNString("MaNCC").subSequence(2, 8).toString()), 1);
            }
        } catch (SQLException ex) {
            dataaccess.displayError(ex);
        } finally {
            dataaccess.close();
        }
        return ma;
    }
//    Chuyển đổi mã nhà cung cấp

    public static String ConvertMa(int i, int order) {
        String ma = "";
        if (order == 0) {
            i--;
        } else {
            i++;
        }
        if (i < 10) {
            ma = "CC00000" + i;
        } else if (i < 100) {
            ma = "CC0000" + i;
        } else if (i < 1000) {
            ma = "CC000" + i;
        } else if (i < 10000) {
            ma = "CC00" + i;
        } else if (i < 100000) {
            ma = "CC0" + i;
        } else if (i < 1000000) {
            ma = "CC" + i;
        }
        return ma;
    }

//    Lấy mã nhà cung cấp từ tên nhà cung cấp
    public static String GetMaNCC(String TenNCC) {
        String query = "SELECT NCC.MaNCC FROM NCC WHERE NCC.TenNCC = N'" + TenNCC + "'";
        String ma = null;
        try {
            rs = dataaccess.Showdata(query);
            while (rs.next()) {
                ma = rs.getNString("MaNCC");
            }
        } catch (SQLException ex) {
            dataaccess.displayError(ex);
        } finally {
            dataaccess.close();
        }
        return ma;
    }

    //    Lấy mã nhà cung cấp từ tên nhà cung cấp
    public static String GetDT(String TenNCC) {
        String query = "SELECT DienThoai FROM NCC WHERE NCC.TenNCC = N'" + TenNCC + "'";
        String ma = null;
        try {
            rs = dataaccess.Showdata(query);
            while (rs.next()) {
                ma = rs.getNString("DienThoai");
            }
        } catch (SQLException ex) {
            dataaccess.displayError(ex);
        } finally {
            dataaccess.close();
        }
        return ma;
    }
}
