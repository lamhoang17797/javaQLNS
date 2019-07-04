package BUS;

import DAL.DALThamSo;
import DTO.DTOThamSo;

public class BUSThamSo {

//    Cập nhật tham số
    public static void UpdateThamSo(DTOThamSo thamso) {
        DALThamSo.UpdateThamSo(thamso);
    }
//    Lấy số lượng nhập tối đa

    public static int GetSLNToiThieu(int mats) {
        return DALThamSo.GetSLNToiThieu(mats);
    }
//    lấy số tiền nợ tối đa

    public static int GetSTNToiDa(int mats) {
        return DALThamSo.GetSTNToiDa(mats);
    }
//    lấy số lượng tồn tối thiểu

    public static int GetSLTToiThieu(int mats) {
        return DALThamSo.GetSLTToiThieu(mats);
    }
//    Lấy số lượng tồn tối đa

    public static int GetSLTToida(int mats) {
        return DALThamSo.GetSLTToiDa(mats);
    }
//    lấy quy định

    public static int GetQuyDinh(int mats) {
        return DALThamSo.GetQuydinh(mats);
    }

}
