package DAL;

import DTO.DTOCTPhieuNhap;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DALCTPhieuNhap {

    static ConnectData dataaccess = new ConnectData();
    static ResultSet rs;

//    Lấy dữ liêụ chi tiết phiếu nhập
    public static ObservableList<DTOCTPhieuNhap> DetailImportAll(String mapn) {
        String query = "SELECT CHITIETPHIEUNHAP.*,SACH.TenSach FROM CHITIETPHIEUNHAP,SACH WHERE CHITIETPHIEUNHAP.MaSach = SACH.MaSach AND CHITIETPHIEUNHAP.MaPN = N'" + mapn + "'";
        ObservableList<DTOCTPhieuNhap> arrCTPhieuNhap = FXCollections.observableArrayList();
        try {
            rs = dataaccess.Showdata(query);
            while (rs.next()) {
                DTOCTPhieuNhap CTphieunhapDTO = new DTOCTPhieuNhap();
                CTphieunhapDTO.setMaCTPN(rs.getNString("MaCTPN"));
                CTphieunhapDTO.setMaPN(rs.getNString("MaPN"));
                CTphieunhapDTO.setMaSach(rs.getNString("TenSach"));
                CTphieunhapDTO.setGiaMua(rs.getInt("GiaMua"));
                CTphieunhapDTO.setSLN(rs.getInt("SLN"));
                arrCTPhieuNhap.add(CTphieunhapDTO);
            }
        } catch (SQLException ex) {
            dataaccess.displayError(ex);
        } finally {
            dataaccess.close();
        }
        return arrCTPhieuNhap;
    }

//    Thêm và cập nhật chi tiết phiếu nhập
    public static void IUDetailImport(DTOCTPhieuNhap ctphieunhap) {
        String insert = "INSERT INTO CHITIETPHIEUNHAP(MaCTPN,MaPN,MaSach,GiaMua,SLN)"
                + "VALUES(N'" + ctphieunhap.getMaCTPN() + "',N'" + ctphieunhap.getMaPN() + "',N'" + ctphieunhap.getMaSach() + "'," + ctphieunhap.getGiaMua() + "," + ctphieunhap.getSLN() + ")";
        String update = "UPDATE CHITIETPHIEUNHAP SET GiaMua = " + ctphieunhap.getGiaMua() + ",SLN = " + ctphieunhap.getSLN() + " WHERE MaPN = '" + ctphieunhap.getMaPN() + "' AND MaSach = N'" + ctphieunhap.getMaSach() + "'";
        if (Checkma(ctphieunhap.getMaPN(), ctphieunhap.getMaSach()) == false) {
            dataaccess.ExecuteQuery(insert, "Thêm ", 0);
        } else {
            dataaccess.ExecuteQuery(update, "Cập nhật", 0);
        }
    }
//    xóa chi tiết phiếu nhập

    public static void DeleteDetailImport(String mactpn) {
        String delete = "DELETE FROM CHITIETPHIEUNHAP WHERE MaCTPN = N'" + mactpn + "'";
        dataaccess.ExecuteQuery(delete, "Xóa", 1);
        dataaccess.close();
        String query = "SELECT * FROM CHITIETPHIEUNHAP WHERE MaCTPN > '" + mactpn + "'";
        try {
            String ma;
            rs = dataaccess.Showdata(query);
            while (rs.next()) {
                ma = ConvertMa(Integer.parseInt(rs.getNString("MaCTPN").subSequence(2, 8).toString()), 0);
                System.out.println(ma);
                String updateMS = "UPDATE CHITIETPHIEUNHAP SET MaCTPN = '" + ma + "' WHERE MaCTPN = '" + rs.getNString("MaCTPN") + "'";
                dataaccess.ExecuteQuery(updateMS, "Cập nhật", 0);
            }
        } catch (SQLException ex) {
            dataaccess.displayError(ex);
        } finally {
            dataaccess.close();
        }
    }
//    Kiểm tra mã phiếu nhập và mã sách

    public static boolean Checkma(String mapn, String masach) {
        String query = "SELECT * FROM "
                + "CHITIETPHIEUNHAP WHERE MaPN = N'" + mapn + "' AND MaSach = N'" + masach + "'";
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
        String ma = "CN000001";
        String query = "SELECT * FROM CHITIETPHIEUNHAP WHERE MaCTPN = (SELECT MAX(MaCTPN) FROM CHITIETPHIEUNHAP)";
        try {
            rs = dataaccess.Showdata(query);
            while (rs.next()) {
                ma = ConvertMa(Integer.parseInt(rs.getNString("MaCTPN").subSequence(2, 8).toString()), 1);
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
            ma = "CN00000" + i;
        } else if (i < 100) {
            ma = "CN0000" + i;
        } else if (i < 1000) {
            ma = "CN000" + i;
        } else if (i < 10000) {
            ma = "CN00" + i;
        } else if (i < 100000) {
            ma = "CN0" + i;
        } else if (i < 1000000) {
            ma = "CN" + i;
        }
        return ma;
    }

//    Lấy mã chi tiết phiếu nhập từ mã phiếu nhập và mã sách
    public static String GetMaCTPN(String mapn, String masach) {
        String query = "SELECT * FROM CHITIETPHIEUNHAP WHERE MaPN = N'" + mapn + "' AND MaSach = N'" + masach + "'";
        String ma = null;
        try {
            rs = dataaccess.Showdata(query);
            while (rs.next()) {
                ma = rs.getNString("MaCTPN");
            }
        } catch (SQLException ex) {
            dataaccess.displayError(ex);
        } finally {
            dataaccess.close();
        }
        return ma;
    }

    public static int GetDG(String mapn, String masach) {
        String query = "SELECT GiaMua FROM CHITIETPHIEUNHAP WHERE MaPN = N'" + mapn + "' AND MaSach = N'" + masach + "'";
        int ma = 0;
        try {
            rs = dataaccess.Showdata(query);
            while (rs.next()) {
                ma = rs.getInt("GiaMua");
            }
        } catch (SQLException ex) {
            dataaccess.displayError(ex);
        } finally {
            dataaccess.close();
        }
        return ma;
    }

    //  Lấy stt
    public static int STT(String mapn, String masach) {
        String mactpn = GetMaCTPN(mapn, masach);
        int i = 0;
        String query = "SELECT T1.rn AS STT "
                + "from (SELECT ROW_NUMBER() OVER (ORDER BY MaCTPN) AS rn, *"
                + " FROM	CHITIETPHIEUNHAP"
                + " WHERE MaPN = N'" + mapn + "') AS T1 "
                + "WHERE T1.MaCTPN = N'" + mactpn + "'";
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
//    Tổng số lượng nhập trong thang và nam

    public static int TongSLN(String ngay,String masach) {
        String query = "SELECT SUM(SLN) AS SL FROM CHITIETPHIEUNHAP,PHIEUNHAP WHERE CHITIETPHIEUNHAP.MaPN = PHIEUNHAP.MaPN AND PHIEUNHAP.NgayNhap LIKE  '%" + ngay + "%' AND CHITIETPHIEUNHAP.MaSach = N'"+masach+"'";
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
