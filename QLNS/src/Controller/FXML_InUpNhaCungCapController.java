package Controller;

import BUS.BUSNhaCungCap;
import DTO.DTONhaCungCap;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

public class FXML_InUpNhaCungCapController implements Initializable {

    @FXML
    private JFXTextField idsupply, namesupply, addresssupply, emailsupply, phonesupply;
    @FXML
    private ImageView supplyimage;
    @FXML
    private JFXButton chooseimage;
    @FXML
    private Label phonesupplyvalidation;

    String path = "null";

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        idsupply.setText(BUSNhaCungCap.Tangma().trim());
    }
//    Nhập số

    @FXML
    private void phonessupply_keypress(KeyEvent event) {
        try {
            int i = Integer.parseInt(phonesupply.getText().trim());
            phonesupplyvalidation.setText("");
        } catch (NumberFormatException e) {
            phonesupplyvalidation.setText("nhập số!");
        }
    }
//    Nhấn nút save

    @FXML
    private void save_click(ActionEvent event) {
        DTONhaCungCap nhacungcap = new DTONhaCungCap();
        nhacungcap.setMaNCC(idsupply.getText().trim());
        nhacungcap.setTenNCC(namesupply.getText().trim());
        nhacungcap.setDiaChiNCC(addresssupply.getText().trim());
        nhacungcap.setEmail(emailsupply.getText().trim());
        nhacungcap.setDienThoai(phonesupply.getText().trim());
        nhacungcap.setHinhAnh(path);
        if (!namesupply.getText().equals("") || !addresssupply.getText().equals("")) {
            BUSNhaCungCap.IUSupply(nhacungcap);
        } else {
            JOptionPane.showMessageDialog(null, "Tên và địa chỉ không được để trống");
        }
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
    }
//    Nhấn nút xóa

    @FXML
    private void delete_click(ActionEvent event) {
        namesupply.setText("");
        addresssupply.setText("");
        emailsupply.setText("");
        phonesupply.setText("");
        supplyimage.setImage(null);
    }
//    Nhấn nút đóng

    @FXML
    private void close_click(ActionEvent event) {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
    }
//    Chọn ảnh

    @FXML
    private void supplyimage_click(MouseEvent event) throws MalformedURLException {
        chooseimage();
    }
//    Nhấn nút chọn ảnh

    @FXML
    private void choose_click(ActionEvent event) throws MalformedURLException {
        chooseimage();
    }

    public void chooseimage() throws MalformedURLException {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Open File");
        File file = chooser.showOpenDialog(new Stage());
        if (file != null) {
            path = file.toURI().toURL().toString();
            Image image = new Image(path, 150, 150, true, true);
            supplyimage.setImage(image);
            supplyimage.setFitWidth(150);
            supplyimage.setFitHeight(150);
            supplyimage.setPreserveRatio(true);
        } else {
            JOptionPane.showMessageDialog(null, "Chưa chọn ảnh");
        }
    }

    public void getData(DTONhaCungCap nhacungcap) {
        idsupply.setText(nhacungcap.getMaNCC().trim());
        namesupply.setText(nhacungcap.getTenNCC().trim());
        addresssupply.setText(nhacungcap.getDiaChiNCC().trim());
        emailsupply.setText(nhacungcap.getEmail().trim());
        phonesupply.setText(nhacungcap.getDienThoai().trim());
        if (!nhacungcap.getHinhAnh().trim().equals("null")) {
            Image image = new Image(nhacungcap.getHinhAnh().trim(), 150, 150, true, true);
            supplyimage.setImage(image);
            supplyimage.setFitWidth(150);
            supplyimage.setFitHeight(150);
            supplyimage.setPreserveRatio(true);
            path = nhacungcap.getHinhAnh().trim();
        } else {
            supplyimage.setImage(null);
        }
    }
}
