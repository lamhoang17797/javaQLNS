package Controller;

import BUS.BUSNhaXB;
import DTO.DTONhaXB;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

public class FXML_InUpPublishController implements Initializable {

    @FXML
    private JFXTextField idpublish;
    @FXML
    private JFXTextField namepublish;
    @FXML
    private JFXTextField addresspublish;
    @FXML
    private JFXTextField emailpublish;
    @FXML
    private JFXTextField phonepublish;
    @FXML
    private Label phonepublishvalidation;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        idpublish.setText(BUSNhaXB.Tangma());
    }
//    Nhập số
    @FXML
    private void phonepublish_keypress(KeyEvent event) {
        try {
            int i = Integer.parseInt(phonepublish.getText().trim());
            phonepublishvalidation.setText("");
        } catch (NumberFormatException e) {
            phonepublishvalidation.setText("nhập số!");
        }
    }
//    Nhấn nút save
    @FXML
    private void save_click(ActionEvent event) {
        DTONhaXB nxbDTO = new DTONhaXB();
        nxbDTO.setMaNXB(idpublish.getText().trim());
        nxbDTO.setTenNXB(namepublish.getText().trim());
        nxbDTO.setDiaChiNXB(addresspublish.getText().trim());
        nxbDTO.setEmail(emailpublish.getText().trim());
        nxbDTO.setDT(phonepublish.getText().trim());
        if (!namepublish.getText().equals("") || !addresspublish.getText().equals("")) {
            BUSNhaXB.UIPublish(nxbDTO);
        } else {
            JOptionPane.showMessageDialog(null, "Tên và địa chỉ không được để trống");
        }
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
    }
//    Nhấn nút xóa
    @FXML
    private void delete_click(ActionEvent event) {
        namepublish.setText("");
        addresspublish.setText("");
        emailpublish.setText("");
        phonepublish.setText("");
    }
//    Nhấn nút đóng
    @FXML
    private void close_click(ActionEvent event) {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
    }

    public void getData(DTONhaXB nhaxb) {
        idpublish.setText(nhaxb.getMaNXB().trim());
        namepublish.setText(nhaxb.getTenNXB().trim());
        addresspublish.setText(nhaxb.getDiaChiNXB().trim());
        emailpublish.setText(nhaxb.getEmail().trim());
        phonepublish.setText(nhaxb.getDT().trim());
    }

}
