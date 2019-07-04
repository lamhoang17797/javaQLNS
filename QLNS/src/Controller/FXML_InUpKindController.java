package Controller;

import BUS.BUSTheLoai;
import DTO.DTOTheLoai;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

public class FXML_InUpKindController implements Initializable {

    @FXML
    private JFXButton close;
    @FXML
    private JFXButton save;
    @FXML
    private JFXTextField namekind;
    String idkind;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        idkind = BUSTheLoai.Tangma();
    }
//    Nhấn nút đóng

    @FXML
    private void close_click(ActionEvent event) {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
    }
//    Nhấn nút save

    @FXML
    private void save_click(ActionEvent event) {
        DTOTheLoai theloaiDTO = new DTOTheLoai();
        theloaiDTO.setMaTL(idkind);
        theloaiDTO.setTenTL(namekind.getText().trim());
        if (!namekind.getText().equals("")) {
            BUSTheLoai.UIKind(theloaiDTO);
        } else {
            JOptionPane.showMessageDialog(null, "Tên thể loại không được để trống");
        }
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
    }
//    Lấy dữ liệu

    public void getData(DTOTheLoai theloai) {
        idkind = theloai.getMaTL().trim();
        namekind.setText(theloai.getTenTL().trim());
    }
}
