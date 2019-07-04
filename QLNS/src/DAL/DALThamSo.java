package DAL;

import DTO.DTOThamSo;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DALThamSo {

    static ConnectData dataaccess = new ConnectData();
    static ResultSet rs;

//    Cập nhật tham số
    public static void UpdateThamSo(DTOThamSo thamso) {
        String update = "UPDATE THAMSO SET SLNToiThieu = " + thamso.getSLNToiThieu() + ",SLTToiDa = " + thamso.getSLTToiDa() + ",SLTToiThieu = " + thamso.getSLTToiThieu() + ",STNToiDa = " + thamso.getSTNToiDa() + ",SuDungQuyDinh4 = " + thamso.getQuyDinh4() + " WHERE MaTS = 1";
        dataaccess.ExecuteQuery(update, "Cập nhật", 0);
    }

//    lấy số lượng nhập tối đa
    public static int GetSLNToiThieu(int mats) {
        String query = "SELECT SLNToiThieu FROM THAMSO WHERE MaTS = " + mats;
        int ma = 0;
        try {
            rs = dataaccess.Showdata(query);
            while (rs.next()) {
                ma = rs.getInt("SLNToiThieu");
            }
        } catch (SQLException ex) {
            dataaccess.displayError(ex);
        } finally {
            dataaccess.close();
        }
        return ma;
    }
//    Lấy số tiền nợ tối đa

    public static int GetSTNToiDa(int mats) {
        String query = "SELECT STNToiDa FROM THAMSO WHERE MaTS = " + mats;
        int ma = 0;
        try {
            rs = dataaccess.Showdata(query);
            while (rs.next()) {
                ma = rs.getInt("STNToiDa");
            }
        } catch (SQLException ex) {
            dataaccess.displayError(ex);
        } finally {
            dataaccess.close();
        }
        return ma;
    }
//    Lấy số lượng tồn tối thiểu

    public static int GetSLTToiThieu(int mats) {
        String query = "SELECT SLTToiThieu FROM THAMSO WHERE MaTS = " + mats;
        int ma = 0;
        try {
            rs = dataaccess.Showdata(query);
            while (rs.next()) {
                ma = rs.getInt("SLTToiThieu");
            }
        } catch (SQLException ex) {
            dataaccess.displayError(ex);
        } finally {
            dataaccess.close();
        }
        return ma;
    }
//    Lấy số lượng tồn tối đa

    public static int GetSLTToiDa(int mats) {
        String query = "SELECT SLTToiDa FROM THAMSO WHERE MaTS = " + mats;
        int ma = 0;
        try {
            rs = dataaccess.Showdata(query);
            while (rs.next()) {
                ma = rs.getInt("SLTToiDa");
            }
        } catch (SQLException ex) {
            dataaccess.displayError(ex);
        } finally {
            dataaccess.close();
        }
        return ma;
    }
//    Lấy quy định

    public static int GetQuydinh(int mats) {
        String query = "SELECT SuDungQuyDinh4 FROM THAMSO WHERE MaTS = " + mats;
        int ma = 0;
        try {
            rs = dataaccess.Showdata(query);
            while (rs.next()) {
                ma = rs.getInt("SuDungQuyDinh4");
            }
        } catch (SQLException ex) {
            dataaccess.displayError(ex);
        } finally {
            dataaccess.close();
        }
        return ma;
    }
}
