package Controller;

import BUS.BUSNguoiDung;
import DTO.DTONguoiDung;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

public class FXML_NguoiDungController implements Initializable {

    @FXML
    private JFXButton updateuser, deleteuser;

    @FXML
    private ImageView userimage;

    @FXML
    private Label nametext, emailtext, phonetext;

    @FXML
    private JFXTreeTableView<DTONguoiDung> usertable;

    @FXML
    private JFXTextField searchname, searchphone, searchemail;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        Initusertable();
    }

    @FXML
    void adduser_click(ActionEvent event) throws IOException {
        updateuser.setDisable(true);
        deleteuser.setDisable(true);
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/GUI/FXML_InUpNguoiDung.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
        stage.setOnHidden(e -> {
            Initusertable();
        });
    }

    @FXML
    void deleteuser_click(ActionEvent event) {
        int p = JOptionPane.showConfirmDialog(null, "Bạn thật sự muốn xóa?", "Xóa", JOptionPane.YES_NO_OPTION);
        if (p == 0) {
            BUSNguoiDung.DeleteUser(usertable.getSelectionModel().getSelectedItem().getValue().getMaND());
            Initusertable();
        }
        updateuser.setDisable(true);
        deleteuser.setDisable(true);
    }

    @FXML
    void updateuser_click(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/GUI/FXML_InUpNguoiDung.fxml"));
        Parent root1 = loader.load();
        Scene scene = new Scene(root1);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Người dùng");
        stage.show();
        stage.setOnHidden(e -> {
            Initusertable();
        });
        FXML_InUpNguoiDungController data = loader.getController();
        data.getData(usertable.getSelectionModel().getSelectedItem().getValue());
    }

    @FXML
    void usertable_click(MouseEvent event) {
        if (usertable.getSelectionModel().getSelectedItem() != null) {
            updateuser.setDisable(false);
            deleteuser.setDisable(false);
            nametext.setText(usertable.getSelectionModel().getSelectedItem().getValue().getTenND().trim());
            emailtext.setText(usertable.getSelectionModel().getSelectedItem().getValue().getEmail().trim());
            phonetext.setText(usertable.getSelectionModel().getSelectedItem().getValue().getDienThoai().trim());
            if (!usertable.getSelectionModel().getSelectedItem().getValue().getHinhAnh().trim().equals("null")) {
                Image image = new Image(usertable.getSelectionModel().getSelectedItem().getValue().getHinhAnh().trim());
                userimage.setImage(image);
                userimage.setFitWidth(160);
                userimage.setFitHeight(200);
                userimage.setPreserveRatio(true);
            } else {
                userimage.setImage(null);
            }
        }
    }

    @FXML
    void usertable_keypress(KeyEvent event) throws IOException {
        if (event.getCode() == KeyCode.DELETE) {
            int p = JOptionPane.showConfirmDialog(null, "Bạn thật sự muốn xóa?", "Xóa", JOptionPane.YES_NO_OPTION);
            if (p == 0) {
                BUSNguoiDung.DeleteUser(usertable.getSelectionModel().getSelectedItem().getValue().getMaND());
                Initusertable();
            }
        } else {
            if (event.getCode() == KeyCode.INSERT) {
                updateuser.setDisable(true);
                deleteuser.setDisable(true);
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/GUI/FXML_InUpNguoiDung.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.centerOnScreen();
                stage.setTitle("Người dùng");
                stage.show();
                stage.setOnHidden(e -> {
                    Initusertable();
                });
            }

        }
    }

    public void Initusertable() {
        JFXTreeTableColumn<DTONguoiDung, String> mand = new JFXTreeTableColumn<>("Mã người dùng");
        mand.setStyle("-fx-alignment: CENTER;");
        mand.setPrefWidth(100);
        mand.setCellValueFactory((TreeTableColumn.CellDataFeatures<DTONguoiDung, String> param) -> new SimpleObjectProperty(param.getValue().getValue().getMaND().trim()));

        JFXTreeTableColumn<DTONguoiDung, String> tennd = new JFXTreeTableColumn<>("Tên người dùng");
        tennd.setStyle("-fx-alignment: CENTER;");
        tennd.setPrefWidth(179);
        tennd.setCellValueFactory((TreeTableColumn.CellDataFeatures<DTONguoiDung, String> param) -> new SimpleObjectProperty(param.getValue().getValue().getTenND().trim()));

        JFXTreeTableColumn<DTONguoiDung, String> dt = new JFXTreeTableColumn<>("Điện thoại");
        dt.setStyle("-fx-alignment: CENTER;");
        dt.setPrefWidth(120);
        dt.setCellValueFactory((TreeTableColumn.CellDataFeatures<DTONguoiDung, String> param) -> new SimpleObjectProperty(param.getValue().getValue().getDienThoai().trim()));

        JFXTreeTableColumn<DTONguoiDung, String> email = new JFXTreeTableColumn<>("Email");
        email.setStyle("-fx-alignment: CENTER;");
        email.setPrefWidth(226);
        email.setCellValueFactory((TreeTableColumn.CellDataFeatures<DTONguoiDung, String> param) -> new SimpleObjectProperty(param.getValue().getValue().getEmail().trim()));

        JFXTreeTableColumn<DTONguoiDung, String> tentk = new JFXTreeTableColumn<>("Tên tài khoản");
        tentk.setStyle("-fx-alignment: CENTER;");
        tentk.setPrefWidth(200);
        tentk.setCellValueFactory((TreeTableColumn.CellDataFeatures<DTONguoiDung, String> param) -> new SimpleObjectProperty(param.getValue().getValue().getTenTK().trim()));

        JFXTreeTableColumn<DTONguoiDung, String> matkhau = new JFXTreeTableColumn<>("Mật khẩu");
        matkhau.setStyle("-fx-alignment: CENTER;");
        matkhau.setPrefWidth(200);
        matkhau.setCellValueFactory((TreeTableColumn.CellDataFeatures<DTONguoiDung, String> param) -> new SimpleObjectProperty(param.getValue().getValue().getMatKhau().trim()));

        JFXTreeTableColumn<DTONguoiDung, Integer> quyen = new JFXTreeTableColumn<>("Quyền");
        quyen.setStyle("-fx-alignment: CENTER;");
        quyen.setPrefWidth(65);
        quyen.setCellValueFactory((TreeTableColumn.CellDataFeatures<DTONguoiDung, Integer> param) -> new SimpleObjectProperty(param.getValue().getValue().getQuyen()));

        JFXTreeTableColumn<DTONguoiDung, String> hinhanh = new JFXTreeTableColumn<>("Hình ảnh");
        hinhanh.setVisible(false);
        hinhanh.setCellValueFactory((TreeTableColumn.CellDataFeatures<DTONguoiDung, String> param) -> new SimpleObjectProperty(param.getValue().getValue().getHinhAnh().trim()));

        ObservableList<DTONguoiDung> nguoidung;
        nguoidung = BUSNguoiDung.UserALL();
        final TreeItem<DTONguoiDung> root = new RecursiveTreeItem<>(nguoidung, RecursiveTreeObject::getChildren);
        usertable.getColumns().setAll(mand, tennd, dt, email, tentk, matkhau, quyen, hinhanh);
        usertable.setRoot(root);
        usertable.setShowRoot(false);

        searchname.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            usertable.setPredicate((TreeItem<DTONguoiDung> t) -> {
                boolean flag = t.getValue().getTenND().contains(newValue);
                return flag;
            });
        });

        searchphone.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            usertable.setPredicate((TreeItem<DTONguoiDung> t) -> {
                boolean flag = t.getValue().getDienThoai().contains(newValue);
                return flag;
            });
        });

        searchemail.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            usertable.setPredicate((TreeItem<DTONguoiDung> t) -> {
                boolean flag = t.getValue().getEmail().contains(newValue);
                return flag;
            });
        });

    }
}
