package Controller;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class FXML_ManHinhChinhController implements Initializable {

    @FXML
    private AnchorPane rootpane;
    @FXML
    private JFXButton receipt;
    @FXML
    private JFXButton reportsach;
    @FXML
    private JFXButton reportdoanhthu;
    int quyen;
    @FXML
    private JFXButton book;
    @FXML
    private JFXButton customer;
    @FXML
    private JFXButton export;
    @FXML
    private JFXButton bill;
    @FXML
    private JFXButton report;
    @FXML
    private JFXButton user;
    @FXML
    private JFXButton reportkhachhang;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

    }

//    Button quản lý sách
    @FXML
    private void book_click(ActionEvent event) throws IOException {
        AnchorPane qls = FXMLLoader.load(getClass().getResource("/GUI/FXML_QuanLySach.fxml"));
        rootpane.getChildren().setAll(qls);
    }
//    Button quản lý khách hàng

    @FXML
    private void customer_click(ActionEvent event) throws IOException {
        AnchorPane qlkh = FXMLLoader.load(getClass().getResource("/GUI/FXML_QuanLyKhachHang.fxml"));
        rootpane.getChildren().setAll(qlkh);
    }
//    Button phiếu nhập sách

    @FXML
    private void export_click(ActionEvent event) throws IOException {
        AnchorPane pns = FXMLLoader.load(getClass().getResource("/GUI/FXML_PhieuNhapSach.fxml"));
        rootpane.getChildren().setAll(pns);
    }

    @FXML
    private void bill_click(ActionEvent event) throws IOException {
        AnchorPane hd = FXMLLoader.load(getClass().getResource("/GUI/FXML_HoaDon.fxml"));
        rootpane.getChildren().setAll(hd);
    }

    @FXML
    private void receipt_click(ActionEvent event) throws IOException {
        AnchorPane pt = FXMLLoader.load(getClass().getResource("/GUI/FXML_PhieuThuTien.fxml"));
        rootpane.getChildren().setAll(pt);
    }

    @FXML
    private void report_click(ActionEvent event) {
        rootpane.getChildren().clear();
        rootpane.getChildren().add(reportsach);
        rootpane.getChildren().add(reportdoanhthu);
        rootpane.getChildren().add(reportkhachhang);
        reportsach.setVisible(true);
        reportdoanhthu.setVisible(true);
        reportkhachhang.setVisible(true);
    }

    @FXML
    private void user_click(ActionEvent event) throws IOException {
        AnchorPane nd = FXMLLoader.load(getClass().getResource("/GUI/FXML_NguoiDung.fxml"));
        rootpane.getChildren().setAll(nd);
    }

    @FXML
    private void changerule_menu(ActionEvent event) throws IOException {
        Parent root1 = FXMLLoader.load(getClass().getResource("/GUI/FXML_ThayDoiQuyDinh.fxml"));
        Scene scene = new Scene(root1);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Thay đổi quy định");
        stage.toBack();
        stage.centerOnScreen();
        stage.show();
    }

    @FXML
    private void user_menu(ActionEvent event) throws IOException {
        AnchorPane nd = FXMLLoader.load(getClass().getResource("/GUI/FXML_NguoiDung.fxml"));
        rootpane.getChildren().setAll(nd);
    }

    @FXML
    private void close_menu(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    private void book_menu(ActionEvent event) throws IOException {
        AnchorPane qls = FXMLLoader.load(getClass().getResource("/GUI/FXML_QuanLySach.fxml"));
        rootpane.getChildren().setAll(qls);
    }

    @FXML
    private void customer_menu(ActionEvent event) throws IOException {
        AnchorPane qlkh = FXMLLoader.load(getClass().getResource("/GUI/FXML_QuanLyKhachHang.fxml"));
        rootpane.getChildren().setAll(qlkh);
    }

    @FXML
    private void export_menu(ActionEvent event) throws IOException {
        AnchorPane pns = FXMLLoader.load(getClass().getResource("/GUI/FXML_PhieuNhapSach.fxml"));
        rootpane.getChildren().setAll(pns);
    }

    @FXML
    private void bill_menu(ActionEvent event) throws IOException {
        AnchorPane hd = FXMLLoader.load(getClass().getResource("/GUI/FXML_HoaDon.fxml"));
        rootpane.getChildren().setAll(hd);
    }

    @FXML
    private void receipt_menu(ActionEvent event) throws IOException {
        AnchorPane pt = FXMLLoader.load(getClass().getResource("/GUI/FXML_PhieuThuTien.fxml"));
        rootpane.getChildren().setAll(pt);
    }

    @FXML
    private void report_book(ActionEvent event) throws IOException {
        AnchorPane bct = FXMLLoader.load(getClass().getResource("/GUI/FXML_BaoCaoTon.fxml"));
        rootpane.getChildren().setAll(bct);
    }

    @FXML
    private void report_revenue(ActionEvent event) throws IOException {
        AnchorPane bcdt = FXMLLoader.load(getClass().getResource("/GUI/FXML_BaoCaoDoanhThu.fxml"));
        rootpane.getChildren().setAll(bcdt);
    }

    @FXML
    private void reportsach_click(ActionEvent event) throws IOException {
        AnchorPane bcdt = FXMLLoader.load(getClass().getResource("/GUI/FXML_BaoCaoTon.fxml"));
        rootpane.getChildren().setAll(bcdt);
    }

    @FXML
    private void reportdoanhthu_click(ActionEvent event) throws IOException {
        AnchorPane bcdt = FXMLLoader.load(getClass().getResource("/GUI/FXML_BaoCaoDoanhThu.fxml"));
        rootpane.getChildren().setAll(bcdt);
    }

    public void getData(int i) {
        quyen = i;
        switch (quyen) {
            case 0: {
                book.setDisable(false);
                customer.setDisable(false);
                export.setDisable(false);
                bill.setDisable(false);
                report.setDisable(false);
                receipt.setDisable(false);
                user.setDisable(false);
                break;
            }
            case 1: {
                book.setDisable(true);
                customer.setDisable(false);
                export.setDisable(true);
                bill.setDisable(false);
                report.setDisable(true);
                receipt.setDisable(false);
                user.setDisable(true);
                break;
            }
            case 2: {
                book.setDisable(false);
                customer.setDisable(true);
                export.setDisable(false);
                bill.setDisable(true);
                report.setDisable(true);
                receipt.setDisable(true);
                user.setDisable(true);
                break;
            }
            case 3: {
                book.setDisable(false);
                customer.setDisable(true);
                export.setDisable(true);
                bill.setDisable(true);
                report.setDisable(false);
                receipt.setDisable(true);
                user.setDisable(true);
                break;
            }
        }
    }

    @FXML
    private void report_customer(ActionEvent event) throws IOException {
        AnchorPane bccn = FXMLLoader.load(getClass().getResource("/GUI/FXML_BaoCaoCongNo.fxml"));
        rootpane.getChildren().setAll(bccn);
    }

    @FXML
    private void reportkhachhang_click(ActionEvent event) throws IOException {
        AnchorPane bccn = FXMLLoader.load(getClass().getResource("/GUI/FXML_BaoCaoCongNo.fxml"));
        rootpane.getChildren().setAll(bccn);
    }

}
