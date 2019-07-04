package Controller;

import BUS.BUSNguoiDung;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import sun.plugin.com.Dispatch;

public class FXML_DangNhapController implements Initializable {

    @FXML
    private JFXTextField user;
    @FXML
    private JFXPasswordField pass;
    @FXML
    private Label message;
    @FXML
    private AnchorPane loginform;
    @FXML
    private JFXButton close;
    @FXML
    private JFXButton minimize;
    @FXML
    private JFXButton exit;
    @FXML
    private JFXButton login;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
//    Đóng ứng dụng

    @FXML
    private void exit_click(ActionEvent event) {
        System.exit(0);

    }
//    Đăng nhập

    @FXML
    private void login_click(ActionEvent event) throws IOException {
        check(event);
    }
//    Xử lý button đóng

    @FXML
    private void close_click(ActionEvent event) {
        System.exit(0);
    }
//    Thu nhỏ

    @FXML
    private void minimize_click(ActionEvent event) {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    private void user_press(KeyEvent event) throws IOException {
        if (event.getCode() == KeyCode.ENTER) {
            check(event);
        }
    }

    @FXML
    private void pass_press(KeyEvent event) throws IOException {
        if (event.getCode() == KeyCode.ENTER) {
            check(event);
        }
    }
//    Kiểm tra 

    public void check(Event event) throws IOException {
        if (!user.getText().equals("")) {
            if (!pass.getText().equals("")) {
                if (BUSNguoiDung.checkuser(user.getText().trim(), pass.getText().trim()) == false) {
                    message.setText("Tên tài khoản/mật khẩu không chính xác!");
                    message.setTextFill(Color.rgb(210, 39, 30));
                } else {
                    message.setText("Đăng nhập thành công.");
                    message.setTextFill(Color.rgb(21, 117, 84));
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/GUI/FXML_ManHinhChinh.fxml"));
                    Parent root1 = loader.load();

                    Scene scene = new Scene(root1);
                    Stage stage = new Stage();
                    stage.setTitle("Quản lý nhà sách");
                    stage.setScene(scene);
                    stage.toBack();
                    stage.centerOnScreen();
                    stage.show();
                    stage.setOnHidden(e -> {
                        System.exit(0);
                    });
                    FXML_ManHinhChinhController data = loader.getController();
                    data.getData(BUSNguoiDung.getQuyen(user.getText().trim(), pass.getText().trim()));
                    Node node = (Node) event.getSource();
                    Stage stage1 = (Stage) node.getScene().getWindow();
                    stage1.close();
                }
            } else {
                message.setText("Mật khẩu không được để trống.");
                message.setTextFill(Color.rgb(210, 39, 30));
            }
        } else {
            message.setText("Tên tài khoản không được để trống.");
            message.setTextFill(Color.rgb(210, 39, 30));
        }
    }

}
