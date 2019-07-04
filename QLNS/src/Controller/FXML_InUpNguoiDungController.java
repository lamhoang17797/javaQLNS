package Controller;

import BUS.BUSNguoiDung;
import DTO.DTONguoiDung;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
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

public class FXML_InUpNguoiDungController implements Initializable {

    @FXML
    private JFXTextField iduser, nameuser, emailuser, phoneuser, user, password;

    @FXML
    private ImageView userimage;

    @FXML
    private JFXButton chooseimage;

    @FXML
    private Label phonecustomervalidation;

    @FXML
    private JFXComboBox<String> combo;

    String path = "null";

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        iduser.setText(BUSNguoiDung.Tangma().trim());
        loadcombobox();
    }
//    Xử lý nhập số

    @FXML
    private void phonecustomer_keypress(KeyEvent event) {
        try {
            int i = Integer.parseInt(phoneuser.getText().trim());
            phonecustomervalidation.setText("");
        } catch (NumberFormatException e) {
            phonecustomervalidation.setText("nhập số!");
        }
    }
//    Lưu người dùng

    @FXML
    private void save_click(ActionEvent event) {
        DTONguoiDung nguoidung = new DTONguoiDung();
        nguoidung.setMaND(iduser.getText().trim());
        nguoidung.setTenND(nameuser.getText().trim());
        nguoidung.setDienThoai(phoneuser.getText().trim());
        nguoidung.setEmail(emailuser.getText().trim());
        nguoidung.setTenTK(user.getText().trim());
        nguoidung.setMatKhau(password.getText().trim());
        nguoidung.setQuyen(combo.getSelectionModel().getSelectedIndex());
        nguoidung.setHinhAnh(path);
        if (!nameuser.getText().equals("") || !phoneuser.getText().equals("")) {
            BUSNguoiDung.IUUser(nguoidung);
        } else {
            JOptionPane.showMessageDialog(null, "Tên và điện thoại không được để trống");
        }
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();

    }

    @FXML
    private void delete_click(ActionEvent event) {
        nameuser.setText("");
        phoneuser.setText("");
        emailuser.setText("");
        user.setText("");
        password.setText("");
        combo.getSelectionModel().clearSelection();
        userimage.setImage(null);
    }

    @FXML
    private void close_click(ActionEvent event) {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    private void userimage_click(MouseEvent event) throws MalformedURLException {
        chooseimage();
    }

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
            userimage.setImage(image);
            userimage.setFitWidth(150);
            userimage.setFitHeight(150);
            userimage.setPreserveRatio(true);
        } else {
            JOptionPane.showMessageDialog(null, "Chưa chọn ảnh");
        }
    }

    public void getData(DTONguoiDung nguoidung) {
        iduser.setText(nguoidung.getMaND().trim());
        nameuser.setText(nguoidung.getTenND().trim());
        phoneuser.setText(nguoidung.getDienThoai().trim());
        emailuser.setText(nguoidung.getEmail().trim());
        user.setText(nguoidung.getTenTK().trim());
        password.setText(nguoidung.getMatKhau().trim());
        combo.getSelectionModel().select(nguoidung.getQuyen());
        if (!nguoidung.getHinhAnh().trim().equals("null")) {
            Image image = new Image(nguoidung.getHinhAnh().trim(), 150, 150, true, true);
            userimage.setImage(image);
            userimage.setFitWidth(150);
            userimage.setFitHeight(150);
            userimage.setPreserveRatio(true);
            path = nguoidung.getHinhAnh().trim();
        } else {
            userimage.setImage(null);
        }
    }

    public void loadcombobox() {
        combo.getItems().add("1-Quản lý");
        combo.getItems().add("2-Bán hàng");
        combo.getItems().add("3-Đặt hàng");
        combo.getItems().add("4-Thủ kho");
    }
}
