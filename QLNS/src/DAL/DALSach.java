package DAL;

import DTO.DTOSach;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DALSach {

    static ConnectData dataaccess = new ConnectData();
    static ResultSet rs;

//    Lấy dữ liệu sách
    public static ObservableList<DTOSach> BookAll() {
        String query = "SELECT *,THELOAI.TenTL,TACGIA.TenTG,NXB.TenNXB FROM "
                + "SACH,THELOAI,TACGIA,NXB WHERE SACH.MaTL = THELOAI.MaTL AND SACH.MaTG = TACGIA.MaTG AND SACH.MaNXB = NXB.MaNXB";
        ObservableList<DTOSach> arrSach = FXCollections.observableArrayList();
        try {
            rs = dataaccess.Showdata(query);
            while (rs.next()) {
                DTOSach sachDTO = new DTOSach();
                sachDTO.setMaSach(rs.getNString("MaSach"));
                sachDTO.setTenSach(rs.getNString("TenSach"));
                sachDTO.setTheLoai(rs.getNString("TenTL"));
                sachDTO.setTacGia(rs.getNString("TenTG"));
                sachDTO.setNXB(rs.getNString("TenNXB"));
                sachDTO.setAnh(rs.getNString("HinhAnh"));
                sachDTO.setMota(rs.getNString("Mota"));
                sachDTO.setSLT(rs.getInt("SoLuongTon"));
                sachDTO.setGB(rs.getInt("GiaBan"));
                sachDTO.setGM(rs.getInt("GiaMua"));
                arrSach.add(sachDTO);
            }
        } catch (SQLException ex) {
            dataaccess.displayError(ex);
        } finally {
            dataaccess.close();
        }
        return arrSach;
    }
//    Lấy tất cả mã sách

    public static ArrayList<String> IdBook() {
        String query = "SELECT MaSach FROM SACH";
        ArrayList<String> arrSach = new ArrayList<>();
        try {
            rs = dataaccess.Showdata(query);
            while (rs.next()) {
                arrSach.add(rs.getNString("MaSach"));
            }
        } catch (SQLException ex) {
            dataaccess.displayError(ex);
        } finally {
            dataaccess.close();
        }
        return arrSach;
    }

//    Thêm và cập nhật sách
    public static void IUBook(DTOSach sach) {
        String insert = "INSERT INTO SACH(MaSach,TenSach,MaTL,MaTG,MaNXB,Mota,SoLuongTon,GiaBan,GiaMua,HinhAnh)"
                + "VALUES(N'" + sach.getMaSach() + "',N'" + sach.getTenSach() + "',"
                + "N'" + sach.getTheLoai() + "',N'" + sach.getTacGia() + "',N'" + sach.getNXB() + ""
                + "',N'" + sach.getMota() + "'," + sach.getSLT() + "," + sach.getGB() + "," + sach.getGM() + ",N'" + sach.getAnh() + "')";
        String update = "UPDATE SACH SET TenSach = N'" + sach.getTenSach() + "',MaTL = N'" + sach.getTheLoai()
                + "',MaTG = N'" + sach.getTacGia() + "',MaNXB = N'" + sach.getNXB() + "',Mota = N'" + sach.getMota()
                + "',SoLuongTon = " + sach.getSLT() + ",GiaBan = " + sach.getGB() + ",GiaMua = " + sach.getGM() + ",HinhAnh = N'" + sach.getAnh() + "' WHERE MaSach = '" + sach.getMaSach() + "'";
        if (Check(sach.getMaSach()) == false) {
            if (Check(sach.getTenSach(), sach.getTacGia()) == false) {
                dataaccess.ExecuteQuery(insert, "Thêm", 1);
            } else {
                JOptionPane.showMessageDialog(null, "Sách đã tồn tại. Vui lòng kiểm tra lại");
            }
        } else if (Check(sach.getTenSach(), sach.getTacGia(), sach.getTheLoai(), sach.getNXB(), sach.getMota(), sach.getAnh(), sach.getGB(), sach.getGM()) == false) {
            dataaccess.ExecuteQuery(update, "Cập nhật", 1);
        } else {
            JOptionPane.showMessageDialog(null, "Sách đã tồn tại. Vui lòng kiểm tra lại");
        }

    }
//    Cập nhật số lượng tồn

    public static void UpdateSLT(int slt, String masach, int i) {
        String query = "SELECT SoLuongTon FROM SACH WHERE MaSach = N'" + masach + "'";
        try {
            rs = dataaccess.Showdata(query);
            while (rs.next()) {
                int sl;
                switch (i) {
                    case 1: {
                        if (slt != rs.getInt("SoLuongTon")) {
                            sl = rs.getInt("SoLuongTon") + slt;
                            String update = "UPDATE SACH set SoLuongTon = " + sl + " WHERE MaSach = N'" + masach + "'";
                            dataaccess.ExecuteQuery(update, "Cập nhật số lượng tồn ", 0);
                        }
                        break;
                    }
                    case 2: {

                        sl = rs.getInt("Soluongton") - slt;
                        String update = "UPDATE SACH set SoLuongTon = " + sl + " WHERE MaSach = N'" + masach + "'";
                        dataaccess.ExecuteQuery(update, "Cập nhật số lượng tồn ", 0);
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

//    Xóa sách
    public static void DeleteBook(String masach) {
        String delete = "DELETE FROM SACH WHERE SACH.MaSach = '" + masach + "'";
        dataaccess.ExecuteQuery(delete, "Xóa", 1);
        dataaccess.close();
        String query = "SELECT * FROM SACH WHERE SACH.MaSach > '" + masach + "'";
        try {
            String ma = "";
            rs = dataaccess.Showdata(query);
            while (rs.next()) {
                ma = ConvertMa(Integer.parseInt(rs.getNString("MaSach").subSequence(2, 8).toString()), 0);
                String updateMS = "UPDATE SACH SET SACH.MaSach = '" + ma + "' WHERE SACH.MaSach = '" + rs.getNString("MaSach") + "'";
                dataaccess.ExecuteQuery(updateMS, "Cập nhật", 0);
            }
        } catch (SQLException ex) {
            dataaccess.displayError(ex);
        } finally {
            dataaccess.close();
        }
    }

//    Kiểm tra thông tin sách
    public static boolean Check(String ten, String tacgia, String theloai, String nxb, String mota, String anh, int gb, int gm) {
        String query = "SELECT * FROM "
                + "SACH WHERE SACH.TenSach = N'" + ten + "' AND SACH.MaTG = N'" + tacgia
                + "' AND SACH.MaTL = N'" + theloai + "' AND SACH.MaNXB =N'" + nxb + "' AND SACH.Mota =N'" + mota + "' AND SACH.HinhAnh = N'" + anh + "' AND SACH.GiaBan = " + gb + "AND SACH.GiaMua = " + gm + "";
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

//    Kiểm tra mã sách
    public static boolean Check(String masach) {
        String query = "SELECT * FROM "
                + "SACH WHERE SACH.MaSach = N'" + masach + "'";
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

//    Kiểm tra ten và tác giả
    public static boolean Check(String ten, String tacgia) {
        String query = "SELECT * FROM "
                + "SACH WHERE SACH.TenSach = N'" + ten + "' AND SACH.MaTG ='" + tacgia + "'";
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
        String ma = "MS000001";
        String query = "SELECT * FROM SACH WHERE SACH.MaSach = (SELECT MAX(MaSach) FROM SACH)";
        try {
            rs = dataaccess.Showdata(query);
            while (rs.next()) {
                ma = ConvertMa(Integer.parseInt(rs.getNString("MaSach").subSequence(2, 8).toString()), 1);
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
            ma = "MS00000" + i;
        } else if (i < 100) {
            ma = "MS0000" + i;
        } else if (i < 1000) {
            ma = "MS000" + i;
        } else if (i < 10000) {
            ma = "MS00" + i;
        } else if (i < 100000) {
            ma = "MS0" + i;
        } else if (i < 1000000) {
            ma = "MS" + i;
        }
        return ma;
    }

//    Lấy mã sách từ tên sach
    public static String GetMaSach(String ten) {
        String query = "SELECT * FROM SACH WHERE TenSach = N'" + ten + "'";
        String ma = null;
        try {
            rs = dataaccess.Showdata(query);
            while (rs.next()) {
                ma = rs.getNString("MaSach");
            }
        } catch (SQLException ex) {
            dataaccess.displayError(ex);
        } finally {
            dataaccess.close();
        }
        return ma;
    }

//    Lấy gia mua từ tên sach
    public static int GetGM(String ten) {
        String query = "SELECT GiaMua FROM SACH WHERE TenSach = N'" + ten + "'";
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
//    Lấy gia bán từ tên sach

    public static int GetGB(String ten) {
        String query = "SELECT GiaBan FROM SACH WHERE TenSach = N'" + ten + "'";
        int ma = 0;
        try {
            rs = dataaccess.Showdata(query);
            while (rs.next()) {
                ma = rs.getInt("GiaBan");
            }
        } catch (SQLException ex) {
            dataaccess.displayError(ex);
        } finally {
            dataaccess.close();
        }
        return ma;
    }
//    Lấy số lượng tồn

    public static int GetSLT(String ten,String masach) {
        String query = "SELECT Soluongton FROM SACH WHERE TenSach = N'" + ten + "' OR MaSach = N'"+masach+"'";
        int ma = 0;
        try {
            rs = dataaccess.Showdata(query);
            while (rs.next()) {
                ma = rs.getInt("Soluongton");
            }
        } catch (SQLException ex) {
            dataaccess.displayError(ex);
        } finally {
            dataaccess.close();
        }
        return ma;
    }
}
