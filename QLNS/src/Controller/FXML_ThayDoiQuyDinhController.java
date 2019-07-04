package Controller;

import BUS.BUSThamSo;
import DTO.DTOThamSo;
import com.jfoenix.controls.JFXCheckBox;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

public class FXML_ThayDoiQuyDinhController implements Initializable {

    @FXML
    private JFXCheckBox quydinh4;
    @FXML
    private TextField SLNMin;
    @FXML
    private TextField SLNMin1;
    @FXML
    private TextField SLTMax;
    @FXML
    private TextField SLTMax1;
    @FXML
    private TextField SLTMin;
    @FXML
    private TextField SLTMin1;
    @FXML
    private TextField STNMax;
    @FXML
    private TextField STNMax1;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadtvalue();
        // TODO
    }

    @FXML
    private void update_click(ActionEvent event) {
        DTOThamSo thamso = new DTOThamSo();
        thamso.setSLNToiThieu(Integer.parseInt(SLNMin.getText().trim()));
        thamso.setSLTToiDa(Integer.parseInt(SLTMax.getText().trim()));
        thamso.setSLTToiThieu(Integer.parseInt(SLTMin.getText().trim()));
        thamso.setSTNToiDa(Integer.parseInt(STNMax.getText().trim()));
        if (quydinh4.isSelected()) {
            thamso.setQuyDinh4(Integer.parseInt("1"));
        } else {
            thamso.setQuyDinh4(Integer.parseInt("0"));
        }
        update(thamso);
        loadtvalue();
    }

    @FXML
    private void defaultvalue_click(ActionEvent event) {
        DTOThamSo thamso = new DTOThamSo();
        thamso.setSLNToiThieu(Integer.parseInt("150"));
        thamso.setSLTToiDa(Integer.parseInt("300"));
        thamso.setSLTToiThieu(Integer.parseInt("20"));
        thamso.setSTNToiDa(Integer.parseInt("20000"));
        thamso.setQuyDinh4(Integer.parseInt("1"));
        update(thamso);
        loadtvalue();

    }

    @FXML
    private void delete_click(ActionEvent event) {
        SLNMin.setText("");
        SLTMax.setText("");
        SLTMin.setText("");
        STNMax.setText("");
        quydinh4.setSelected(false);
    }

    public void loadtvalue() {
        SLNMin.setText(Integer.toString(BUSThamSo.GetSLNToiThieu(1)));
        SLTMax.setText(Integer.toString(BUSThamSo.GetSLTToida(1)));
        SLTMin.setText(Integer.toString(BUSThamSo.GetSLTToiThieu(1)));
        STNMax.setText(Integer.toString(BUSThamSo.GetSTNToiDa(1)));
        SLNMin1.setText(Integer.toString(BUSThamSo.GetSLNToiThieu(1)));
        SLTMax1.setText(Integer.toString(BUSThamSo.GetSLTToida(1)));
        SLTMin1.setText(Integer.toString(BUSThamSo.GetSLTToiThieu(1)));
        STNMax1.setText(Integer.toString(BUSThamSo.GetSTNToiDa(1)));
        if (BUSThamSo.GetQuyDinh(1) == 1) {
            quydinh4.setSelected(true);
        } else {
            quydinh4.setSelected(false);
        }
    }

    public void update(DTOThamSo thamso) {
        BUSThamSo.UpdateThamSo(thamso);
    }
}
