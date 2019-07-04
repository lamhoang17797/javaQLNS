package Controller;

import BUS.BUSKhachHang;
import DTO.DTOKhachHang;
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

public class FXML_InUpKhachHangController implements Initializable {

    @FXML
    private JFXTextField idcustomer, namecustomer, addresscustomer, emailcustomer, phonecustomer;
    @FXML
    private ImageView customerimage;
    @FXML
    private JFXButton chooseimage;
    @FXML
    private Label phonecustomervalidation;

    String path = "null";

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        idcustomer.setText(BUSKhachHang.Tangma().trim());
    }
//    Nhập số

    @FXML
    private void phonecustomer_keypress(KeyEvent event) {
        try {
            int i = Integer.parseInt(phonecustomer.getText().trim());
            phonecustomervalidation.setText("");
        } catch (NumberFormatException e) {
            phonecustomervalidation.setText("nhập số!");
        }
    }
//    Nhấn nút save

    @FXML
    private void save_click(ActionEvent event) {
        if (phonecustomervalidation.getText().trim().equals("")){
        DTOKhachHang khachhang = new DTOKhachHang();
        khachhang.setMaKH(idcustomer.getText().trim());
        khachhang.setTenKH(namecustomer.getText().trim());
        khachhang.setDiaChiKH(addresscustomer.getText().trim());
        khachhang.setEmail(emailcustomer.getText().trim());
        khachhang.setDienThoai(phonecustomer.getText().trim());
        khachhang.setHinhAnh(path);
        if (!namecustomer.getText().equals("") || !addresscustomer.getText().equals("")) {
            BUSKhachHang.IUCustomer(khachhang);
        } else {
            JOptionPane.showMessageDialog(null, "Tên và địa chỉ không được để trống");
        }
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
        }
        else
        {
            JOptionPane.showMessageDialog(null,"Điện thoại phải là số");
        }

    }
//    Nhấn nút xóa

    @FXML
    private void delete_click(ActionEvent event) {
        namecustomer.setText("");
        addresscustomer.setText("");
        emailcustomer.setText("");
        phonecustomer.setText("");
        customerimage.setImage(null);
    }
//    Nhấn nút đóng

    @FXML
    private void close_click(ActionEvent event) {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
    }
//    Chọn ảnh

    @FXML
    private void customerimage_click(MouseEvent event) throws MalformedURLException {
        chooseimage();
    }
//    Nhấn nút chọn ảnh

    @FXML
    private void choose_click(ActionEvent event) throws MalformedURLException {
        chooseimage();

    }
//    Xử lý chọn ảnh

    public void chooseimage() throws MalformedURLException {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Open File");
        File file = chooser.showOpenDialog(new Stage());
        if (file != null) {
            path = file.toURI().toURL().toString();
            Image image = new Image(path, 150, 150, true, true);
            customerimage.setImage(image);
            customerimage.setFitWidth(150);
            customerimage.setFitHeight(150);
            customerimage.setPreserveRatio(true);
        } else {
            JOptionPane.showMessageDialog(null, "Chưa chọn ảnh");
        }
    }
//    Lấy dữ liệu

    public void getData(DTOKhachHang khachhang) {
        idcustomer.setText(khachhang.getMaKH().trim());
        namecustomer.setText(khachhang.getTenKH().trim());
        addresscustomer.setText(khachhang.getDiaChiKH().trim());
        emailcustomer.setText(khachhang.getEmail().trim());
        phonecustomer.setText(khachhang.getDienThoai().trim());
        if (!khachhang.getHinhAnh().trim().equals("null")) {
            Image image = new Image(khachhang.getHinhAnh().trim(), 150, 150, true, true);
            customerimage.setImage(image);
            customerimage.setFitWidth(150);
            customerimage.setFitHeight(150);
            customerimage.setPreserveRatio(true);
            path = khachhang.getHinhAnh().trim();
        } else {
            customerimage.setImage(null);
        }
    }

}
