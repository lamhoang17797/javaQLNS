package Controller;

import BUS.BUSKhachHang;
import DTO.DTOKhachHang;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import java.awt.HeadlessException;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
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
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

public class FXML_QuanLyKhachHangController implements Initializable {

    @FXML
    private JFXButton updatecustomer, deletecustomer;

    @FXML
    private ImageView customerimage;

    @FXML
    private Label nametext, addresstext, emailtext, phonetext;

    @FXML
    private JFXTreeTableView<DTOKhachHang> customertable;

    @FXML
    private JFXTextField searchname, searchaddress, searchemail;

    DecimalFormat df = new DecimalFormat("###,###,###");

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        Initcustomtertable();
    }
//    Thêm khách hàng

    @FXML
    void addcustomer_click(ActionEvent event) throws IOException {
        updatecustomer.setDisable(true);
        deletecustomer.setDisable(true);
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/GUI/FXML_InUpKhachHang.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Thông tin khách hàng");
        stage.show();
        stage.setOnHidden(e -> {
            Initcustomtertable();
        });
    }

    //    Cập nhật khách hàng
    @FXML
    void updatecustomer_click(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/GUI/FXML_InUpKhachHang.fxml"));
        Parent root1 = loader.load();
        Scene scene = new Scene(root1);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Thông tin khách hàng");
        stage.show();
        stage.setOnHidden(e -> {
            Initcustomtertable();
        });
        FXML_InUpKhachHangController data = loader.getController();
        data.getData(customertable.getSelectionModel().getSelectedItem().getValue());
    }

//    Xóa khách hàng
    @FXML
    void deletecustomer_click(ActionEvent event) {
        int p = JOptionPane.showConfirmDialog(null, "Bạn thật sự muốn xóa?", "Xóa", JOptionPane.YES_NO_OPTION);
        if (p == 0) {
            BUSKhachHang.DeleteCustomer(customertable.getSelectionModel().getSelectedItem().getValue().getMaKH());
            Initcustomtertable();
        }
        updatecustomer.setDisable(true);
        deletecustomer.setDisable(true);
    }

//    Nhập file khách hàng
    @FXML
    void importcustomer_click(ActionEvent event) throws BiffException {
        FileChooser chooser = new FileChooser();
        chooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Excel (*.xls)", "*.xls"));
        chooser.setTitle("Open File");
        DTOKhachHang khachhang = new DTOKhachHang();
        File file = chooser.showOpenDialog(new Stage());
        if (file != null) {
            String path = file.getPath();
            Workbook workbook;
            try {
                workbook = Workbook.getWorkbook(new File(path));
                Sheet sheet = workbook.getSheet(0);
                for (int row = 0; row < sheet.getRows(); row++) {

                    khachhang.setMaKH(sheet.getCell(0, row).getContents());
                    khachhang.setTenKH(sheet.getCell(1, row).getContents());
                    khachhang.setDiaChiKH(sheet.getCell(2, row).getContents());
                    khachhang.setDienThoai(sheet.getCell(3, row).getContents());
                    khachhang.setEmail(sheet.getCell(4, row).getContents());
                    khachhang.setDienThoai(sheet.getCell(5, row).getContents());
                    khachhang.setSTN(0);
                    khachhang.setHinhAnh("null");
                    BUSKhachHang.IUCustomer(khachhang);
                }
                Initcustomtertable();
                workbook.close();
            } catch (IOException | BiffException ex) {
                JOptionPane.showMessageDialog(null, "Tải dữ liệu thất bại");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Chưa chọn đường dẫn");
        }
    }
//    Xuất file khách hàng

    @FXML
    void exportcustomer_click(ActionEvent event) {
        FileChooser chooser = new FileChooser();
        chooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Excel (*.xls)", "*.xls"));
        chooser.setTitle("Save File");
        File file = chooser.showSaveDialog(new Stage());
        if (file != null) {
            String path = file.getPath();
            try {
                WritableWorkbook workbook1 = Workbook.createWorkbook(new File(path));
                WritableSheet sheet1 = workbook1.createSheet("First Sheet", 0);

                for (int i = 0; i < customertable.getCurrentItemsCount(); i++) {
                    for (int j = 0; j < customertable.getColumns().size(); j++) {
                        jxl.write.Label row = new jxl.write.Label(j, i, customertable.getColumns().get(j).getCellData(i).toString());
                        sheet1.addCell(row);
                    }
                }
                workbook1.write();
                workbook1.close();
                JOptionPane.showMessageDialog(null, "Lưu thành công");
            } catch (HeadlessException | IOException | WriteException ex) {
                JOptionPane.showMessageDialog(null, "xuất danh sách thất bại");
            }
        } else {
            JOptionPane.showMessageDialog(null, "chưa chọn đường dẫn");
        }
    }

    @FXML
    void customertable_keypress(KeyEvent event) throws IOException {
        if (event.getCode() == KeyCode.DELETE) {
            int p = JOptionPane.showConfirmDialog(null, "Bạn thật sự muốn xóa?", "Xóa", JOptionPane.YES_NO_OPTION);
            if (p == 0) {
                BUSKhachHang.DeleteCustomer(customertable.getSelectionModel().getSelectedItem().getValue().getMaKH());
                Initcustomtertable();
            }
        } else {
            if (event.getCode() == KeyCode.INSERT) {
                updatecustomer.setDisable(true);
                deletecustomer.setDisable(true);
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/GUI/FXML_InUpKhachHang.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.centerOnScreen();
                stage.setTitle("Thông tin khách hàng");
                stage.show();
                stage.setOnHidden(e -> {
                    Initcustomtertable();
                });
            }

        }
    }

    public void Initcustomtertable() {
        JFXTreeTableColumn<DTOKhachHang, String> makh = new JFXTreeTableColumn<>("Mã khách hàng");
        makh.setStyle("-fx-alignment: CENTER;");
        makh.setPrefWidth(100);
        makh.setCellValueFactory((TreeTableColumn.CellDataFeatures<DTOKhachHang, String> param) -> new SimpleObjectProperty(param.getValue().getValue().getMaKH().trim()));

        JFXTreeTableColumn<DTOKhachHang, String> tenkh = new JFXTreeTableColumn<>("Họ tên khách hàng");
        tenkh.setPrefWidth(200);
        tenkh.setCellValueFactory((TreeTableColumn.CellDataFeatures<DTOKhachHang, String> param) -> new SimpleObjectProperty(param.getValue().getValue().getTenKH().trim()));

        JFXTreeTableColumn<DTOKhachHang, String> dc = new JFXTreeTableColumn<>("Địa chỉ");
        dc.setPrefWidth(320);
        dc.setCellValueFactory((TreeTableColumn.CellDataFeatures<DTOKhachHang, String> param) -> new SimpleObjectProperty(param.getValue().getValue().getDiaChiKH().trim()));

        JFXTreeTableColumn<DTOKhachHang, String> email = new JFXTreeTableColumn<>("Email");
        email.setPrefWidth(260);
        email.setCellValueFactory((TreeTableColumn.CellDataFeatures<DTOKhachHang, String> param) -> new SimpleObjectProperty(param.getValue().getValue().getEmail().trim()));

        JFXTreeTableColumn<DTOKhachHang, String> dt = new JFXTreeTableColumn<>("Số điện thoại");
        dt.setStyle("-fx-alignment: CENTER;");
        dt.setPrefWidth(110);
        dt.setCellValueFactory((TreeTableColumn.CellDataFeatures<DTOKhachHang, String> param) -> new SimpleObjectProperty(param.getValue().getValue().getDienThoai().trim()));

        JFXTreeTableColumn<DTOKhachHang, String> stn = new JFXTreeTableColumn<>("Số tiền nợ");
        stn.setStyle("-fx-alignment: CENTER;");
        stn.setPrefWidth(114);
        stn.setCellValueFactory((TreeTableColumn.CellDataFeatures<DTOKhachHang, String> param) -> new SimpleObjectProperty(df.format(param.getValue().getValue().getSTN())));

        JFXTreeTableColumn<DTOKhachHang, String> hinhanh = new JFXTreeTableColumn<>("Hình ảnh");
        hinhanh.setVisible(false);
        hinhanh.setCellValueFactory((TreeTableColumn.CellDataFeatures<DTOKhachHang, String> param) -> new SimpleObjectProperty(param.getValue().getValue().getHinhAnh()));

        ObservableList<DTOKhachHang> khachhang;
        khachhang = BUSKhachHang.CustomerALL();
        final TreeItem<DTOKhachHang> root = new RecursiveTreeItem<>(khachhang, RecursiveTreeObject::getChildren);
        customertable.getColumns().setAll(makh, tenkh, dc, email, dt, stn, hinhanh);
        customertable.setRoot(root);
        customertable.setShowRoot(false);

        searchname.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            customertable.setPredicate((TreeItem<DTOKhachHang> t) -> {
                boolean flag = t.getValue().getTenKH().contains(newValue);
                return flag;
            });
        });

        searchaddress.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            customertable.setPredicate((TreeItem<DTOKhachHang> t) -> {
                boolean flag = t.getValue().getDiaChiKH().contains(newValue);
                return flag;
            });
        });

        searchemail.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            customertable.setPredicate((TreeItem<DTOKhachHang> t) -> {
                boolean flag = t.getValue().getEmail().contains(newValue);
                return flag;
            });
        });

    }

    @FXML
    private void customertable_click(MouseEvent event) {
        if (customertable.getSelectionModel().getSelectedItem() != null) {
            updatecustomer.setDisable(false);
            deletecustomer.setDisable(false);
            nametext.setText(customertable.getSelectionModel().getSelectedItem().getValue().getTenKH().trim());
            addresstext.setText(customertable.getSelectionModel().getSelectedItem().getValue().getDiaChiKH().trim());
            emailtext.setText(customertable.getSelectionModel().getSelectedItem().getValue().getEmail().trim());
            phonetext.setText(customertable.getSelectionModel().getSelectedItem().getValue().getDienThoai().trim());
            if (!customertable.getSelectionModel().getSelectedItem().getValue().getHinhAnh().trim().equals("null")) {
                Image image = new Image(customertable.getSelectionModel().getSelectedItem().getValue().getHinhAnh().trim());
                customerimage.setImage(image);
                customerimage.setFitWidth(160);
                customerimage.setFitHeight(200);
                customerimage.setPreserveRatio(true);
            } else {
                customerimage.setImage(null);
            }
        }
    }

}
