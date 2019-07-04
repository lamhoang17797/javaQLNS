package DAL;

import DTO.DTOCTHoaDon;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DALCTHoaDon {

    static ConnectData dataaccess = new ConnectData();
    static ResultSet rs;

//    Lấy dữ liệu chi tiết hóa đơn
    public static ObservableList<DTOCTHoaDon> DetailBillAll(String mahd) {
        String query = "SELECT CHITIETHOADON.*,SACH.TenSach FROM CHITIETHOADON,SACH WHERE CHITIETHOADON.MaSach = SACH.MaSach AND CHITIETHOADON.MaHD = N'" + mahd + "'";
        ObservableList<DTOCTHoaDon> arrCTHoaDon = FXCollections.observableArrayList();
        try {
            rs = dataaccess.Showdata(query);
            while (rs.next()) {
                DTOCTHoaDon CThoadonDTO = new DTOCTHoaDon();
                CThoadonDTO.setMaCTHD(rs.getNString("MaCTHD"));
                CThoadonDTO.setMaHD(rs.getNString("MaHD"));
                CThoadonDTO.setMaSach(rs.getNString("TenSach"));
                CThoadonDTO.setGiaBan(rs.getInt("GiaBan"));
                CThoadonDTO.setSLB(rs.getInt("SLB"));
                CThoadonDTO.setKM(rs.getInt("KM"));
                arrCTHoaDon.add(CThoadonDTO);
            }
        } catch (SQLException ex) {
            dataaccess.displayError(ex);
        } finally {
            dataaccess.close();
        }
        return arrCTHoaDon;
    }
//    Lấy dữ liệu chi tiết hóa đơn tạm

    public static ObservableList<DTOCTHoaDon> DetailBillAllTemp() {
        String query = "SELECT CTHD.*,SACH.TenSach FROM CTHD,SACH WHERE CTHD.MaSach = SACH.MaSach";
        ObservableList<DTOCTHoaDon> arrCTHoaDon = FXCollections.observableArrayList();
        try {
            rs = dataaccess.Showdata(query);
            while (rs.next()) {
                DTOCTHoaDon CThoadonDTO = new DTOCTHoaDon();
                CThoadonDTO.setMaCTHD(rs.getNString("MaCTHD"));
                CThoadonDTO.setMaHD(rs.getNString("MaHD"));
                CThoadonDTO.setMaSach(rs.getNString("TenSach"));
                CThoadonDTO.setGiaBan(rs.getInt("GiaBan"));
                CThoadonDTO.setSLB(rs.getInt("SLB"));
                CThoadonDTO.setKM(rs.getInt("KM"));
                arrCTHoaDon.add(CThoadonDTO);
            }
        } catch (SQLException ex) {
            dataaccess.displayError(ex);
        } finally {
            dataaccess.close();
        }
        return arrCTHoaDon;
    }

//    Thêm và cập nhật chi tiết hóa đơn
    public static void IUDetailBill(DTOCTHoaDon cthoadon) {
        String insert = "INSERT INTO CHITIETHOADON(MaCTHD,MaHD,MaSach,GiaBan,SLB,KM)"
                + "VALUES(N'" + cthoadon.getMaCTHD() + "',N'" + cthoadon.getMaHD() + "',N'" + cthoadon.getMaSach() + "'," + cthoadon.getGiaBan() + "," + cthoadon.getSLB() + "," + cthoadon.getKM() + ")";
        String update = "UPDATE CHITIETHOADON SET GiaBan = " + cthoadon.getGiaBan() + ",SLB = " + cthoadon.getSLB() + ",KM = " + cthoadon.getKM() + " WHERE MaHD = '" + cthoadon.getMaHD() + "' AND MaSach = N'" + cthoadon.getMaSach() + "'";
        if (Checkma(cthoadon.getMaHD(), cthoadon.getMaSach()) == false) {
            dataaccess.ExecuteQuery(insert, "Thêm ", 0);
        } else {
            dataaccess.ExecuteQuery(update, "Cập nhật", 0);
        }
    }

    //    Thêm và cập nhật chi tiết hóa đơn
    public static void IUDetailBillTemp(DTOCTHoaDon cthoadon) {
        String insert = "INSERT INTO CTHD(MaCTHD,MaHD,MaSach,GiaBan,SLB,KM)"
                + "VALUES(N'" + cthoadon.getMaCTHD() + "',N'" + cthoadon.getMaHD() + "',N'" + cthoadon.getMaSach() + "'," + cthoadon.getGiaBan() + "," + cthoadon.getSLB() + "," + cthoadon.getKM() + ")";
        dataaccess.ExecuteQuery(insert, "Thêm ", 0);
    }
//    xóa chi tiết hóa đơn tạm

    public static void DeleteDetailBillTemp(String macthd) {
        String delete = "DELETE FROM CTHD WHERE MaCTHD = N'" + macthd + "'";
        dataaccess.ExecuteQuery(delete, "Xóa", 1);
        dataaccess.close();
        String query = "SELECT * FROM CTHD WHERE MaCTHD > N'" + macthd + "'";
        try {
            int ma;
            rs = dataaccess.Showdata(query);
            while (rs.next()) {
                ma = Integer.parseInt(rs.getNString("MaCTHD").trim()) - 1;
                System.out.println(ma);
                String updateMS = "UPDATE CTHD SET MaCTHD = N'" + Integer.toString(ma) + "' WHERE MaCTHD = '" + rs.getNString("MaCTHD") + "'";
                dataaccess.ExecuteQuery(updateMS, "Cập nhật", 0);
            }
        } catch (SQLException ex) {
            dataaccess.displayError(ex);
        } finally {
            dataaccess.close();
        }
    }

//    Xóa dữ liệu chi tiết hóa đơn tạm
    public static void DeleteAllDetailBillTemp() {
        String delete = "DELETE FROM CTHD";
        dataaccess.ExecuteQuery(delete, "Xóa", 0);
        dataaccess.close();
    }
//    Kiểm tra mã hóa đơn và mã sách

    public static boolean Checkma(String mahd, String masach) {
        String query = "SELECT * FROM "
                + "CHITIETHOADON WHERE MaHD = N'" + mahd + "' AND MaSach = N'" + masach + "'";
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
        String ma = "CD000001";
        String query = "SELECT * FROM CHITIETHOADON WHERE MaCTHD = (SELECT MAX(MaCTHD) FROM CHITIETHOADON)";
        try {
            rs = dataaccess.Showdata(query);
            while (rs.next()) {
                ma = ConvertMa(Integer.parseInt(rs.getNString("MaCTHD").subSequence(2, 8).toString()), 1);
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
            ma = "CD00000" + i;
        } else if (i < 100) {
            ma = "CD0000" + i;
        } else if (i < 1000) {
            ma = "CD000" + i;
        } else if (i < 10000) {
            ma = "CD00" + i;
        } else if (i < 100000) {
            ma = "CD0" + i;
        } else if (i < 1000000) {
            ma = "CD" + i;
        }
        return ma;
    }

//    Lấy mã chi tiết phiếu nhập từ mã phiếu nhập và mã sách
    public static String GetMaCTHD(String mahd, String masach) {
        String query = "SELECT * FROM CHITIETHOADON WHERE MaHD = N'" + mahd + "' AND MaSach = N'" + masach + "'";
        String ma = null;
        try {
            rs = dataaccess.Showdata(query);
            while (rs.next()) {
                ma = rs.getNString("MaCTHD");
            }
        } catch (SQLException ex) {
            dataaccess.displayError(ex);
        } finally {
            dataaccess.close();
        }
        return ma;
    }

    //  Lấy stt
    public static int STT(String mahd, String masach) {
        String macthd = GetMaCTHD(mahd, masach);
        int i = 0;
        String query = "SELECT T1.rn AS STT "
                + "from (SELECT ROW_NUMBER() OVER (ORDER BY MaCTHD) AS rn, *"
                + " FROM	CHITIETHOADON"
                + " WHERE CHITIETHOADON.MaHD = N'" + mahd + "') AS T1 "
                + "WHERE T1.MaCTHD = N'" + macthd + "'";
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
//    Tổng số lượng bán trong tháng và năm

    public static int TongSLB(String ngay, String masach) {
        String query = "SELECT SUM(SLB) AS SL FROM CHITIETHOADON,HOADON WHERE CHITIETHOADON.MaHD = HOADON.MaHD  AND HOADON.NgayLap LIKE '%"+ngay+"%' AND CHITIETHOADON.MaSach = N'"+masach+"'";
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
