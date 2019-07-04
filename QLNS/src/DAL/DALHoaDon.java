package DAL;

import DTO.DTOHoaDon;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DALHoaDon {

    static ConnectData dataaccess = new ConnectData();
    static ResultSet rs;

//    Lấy dữ liệu hóa đơn
    public static ObservableList<DTOHoaDon> BillAll() {
        String query = "SELECT HOADON.*,KHACHHANG.TenKH FROM HOADON,KHACHHANG WHERE HOADON.MaKH= KHACHHANG.MaKH ";
        ObservableList<DTOHoaDon> arrHoaDon = FXCollections.observableArrayList();
        try {
            rs = dataaccess.Showdata(query);
            while (rs.next()) {
                DTOHoaDon hoadonDTO = new DTOHoaDon();
                hoadonDTO.setMaHD(rs.getNString("MaHD"));
                hoadonDTO.setMaKH(rs.getNString("TenKH"));
                hoadonDTO.setNgaylap(rs.getNString("NgayLap"));
                hoadonDTO.setTongTT(rs.getInt("TongTT"));
                hoadonDTO.setTienThu(rs.getInt("TienThu"));
                arrHoaDon.add(hoadonDTO);
            }
        } catch (SQLException ex) {
            dataaccess.displayError(ex);
        } finally {
            dataaccess.close();
        }
        return arrHoaDon;
    }

//    Thêm hóa đơn
    public static void IUBill(DTOHoaDon hoadon) {
        String insert = "INSERT INTO HOADON(MaHD,MaKH,NgayLap,TongTT,TienThu)"
                + "VALUES(N'" + hoadon.getMaHD() + "',N'" + hoadon.getMaKH() + "',N'" + hoadon.getNgaylap() + "'," + hoadon.getTongTT() + "," + hoadon.getTienThu() + ")";
        dataaccess.ExecuteQuery(insert, "Thêm", 1);
    }
//    Cập nhật tổng thành tiền

    public static void UpdateTT(int tongtt, int tienthu, String mahd) {
        String update = "UPDATE HOADON SET TongTT = " + tongtt + ",TienThu = " + tienthu + " WHERE MaHD =N'" + mahd + "'";
        dataaccess.ExecuteQuery(update, "Cập nhật phiếu nhập", 0);
    }
//    tăng mã

    public static String Tangma() {
        String ma = "HD000001";
        String query = "SELECT * FROM HOADON WHERE MaHD = (SELECT MAX(MaHD) FROM HOADON)";
        try {
            rs = dataaccess.Showdata(query);
            while (rs.next()) {
                ma = ConvertMa(Integer.parseInt(rs.getNString("MaHD").subSequence(2, 8).toString()), 1);
            }
        } catch (SQLException ex) {
            dataaccess.displayError(ex);
        } finally {
            dataaccess.close();
        }
        return ma;
    }

//    Chuyển đổi mã hóa đơn
    public static String ConvertMa(int i, int order) {
        String ma = "";
        if (order == 0) {
            i--;
        } else {
            i++;
        }
        if (i < 10) {
            ma = "HD00000" + i;
        } else if (i < 100) {
            ma = "HD0000" + i;
        } else if (i < 1000) {
            ma = "HD000" + i;
        } else if (i < 10000) {
            ma = "HD00" + i;
        } else if (i < 100000) {
            ma = "HD0" + i;
        } else if (i < 1000000) {
            ma = "HD" + i;
        }
        return ma;
    }
//    Lấy tổng tiền

    public static int GetTT(String mahd) {
        String query = "SELECT TongTT FROM HOADON WHERE MaHD = N'" + mahd + "'";
        int ma = 0;
        try {
            rs = dataaccess.Showdata(query);
            while (rs.next()) {
                ma = rs.getInt("TongTT");
            }
        } catch (SQLException ex) {
            dataaccess.displayError(ex);
        } finally {
            dataaccess.close();
        }
        return ma;
    }

    public static int GetTT(String thang, int nam) {
        String ngay = nam + "-" + thang;
        String query = "SELECT SUM(TongTT) AS SL FROM HOADON WHERE HOADON.NgayLap LIKE '%" + ngay + "%'";
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

    public static int GetTNo(String ngay,String makh) {
        String query = "SELECT SUM(TongTT - TienThu) AS SL FROM HOADON WHERE TongTT > TienThu AND MaKH = N'"+makh+"' AND NgayLap LIKE '%"+ngay+"%'";
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
