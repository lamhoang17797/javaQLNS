package Controller;

import BUS.BUSNhaXB;
import BUS.BUSSach;
import BUS.BUSTacGia;
import BUS.BUSTheLoai;
import DTO.DTONhaXB;
import DTO.DTOSach;
import DTO.DTOTacGia;
import DTO.DTOTheLoai;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import java.awt.HeadlessException;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
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

public class FXML_QuanLySachController implements Initializable {

    @FXML
    private JFXTreeTableView<DTOSach> booktable;
    @FXML
    private JFXTreeTableView<DTOTheLoai> kindtable;
    @FXML
    private JFXTreeTableView<DTOTacGia> authortable;
    @FXML
    private JFXTreeTableView<DTONhaXB> publishtable;
    @FXML
    private JFXTextField searchname, searchkind, searchauthor, searchnamekind,
            searchnameauthor, searchnamepublish, searchaddress, searchphone;
    @FXML
    private Label nametext, authortext, decriptext;
    @FXML
    private ImageView bookimage;
    @FXML
    private JFXButton updatebook, deletebook, updatekind, deletekind, updateauthor, deleteauthor, updatepublish, deletepublish;

    DecimalFormat df = new DecimalFormat("###,###,###");

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        refresh();
        tablemousepress();
    }
//    Thêm sách

    @FXML
    private void addbook_click(ActionEvent event) throws IOException {
        updatebook.setDisable(true);
        deletebook.setDisable(true);
        loadwindow("/GUI/FXML_InUpSach.fxml","Thông tin sách");
    }
//    Cập nhật sách

    @FXML
    private void updatebook_click(ActionEvent event) throws MalformedURLException, IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/GUI/FXML_InUpSach.fxml"));
        Parent root1 = loader.load();
        Scene scene = new Scene(root1);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Thông tin sách");
        stage.show();
        stage.setOnHidden(e -> {
            Initbooktable();
        });
        FXML_InUpSachController data = loader.getController();
        data.getdata(booktable.getSelectionModel().getSelectedItem().getValue());
    }
//    Xóa sách

    @FXML
    private void deletebook_click(ActionEvent event) {
        int p = JOptionPane.showConfirmDialog(null, "Bạn thật sự muốn xóa?", "Xóa", JOptionPane.YES_NO_OPTION);
        if (p == 0) {
            BUSSach.DeleteBook(booktable.getSelectionModel().getSelectedItem().getValue().getMaSach());
            refresh();
        }
        updatebook.setDisable(true);
        deletebook.setDisable(true);
    }
//    Import sách

    @FXML
    private void importbook_click(ActionEvent event) {
        FileChooser chooser = new FileChooser();
        chooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Excel (*.xls)", "*.xls"));
        chooser.setTitle("Open File");
        DTOSach sach = new DTOSach();
        File file = chooser.showOpenDialog(new Stage());
        if (file != null) {
            String path = file.getPath();
            Workbook workbook;
            try {
                workbook = Workbook.getWorkbook(new File(path));
                Sheet sheet = workbook.getSheet(0);
                for (int row = 0; row < sheet.getRows(); row++) {

                    sach.setMaSach(sheet.getCell(0, row).getContents().trim());
                    sach.setTenSach(sheet.getCell(1, row).getContents().trim());
                    sach.setTheLoai(sheet.getCell(2, row).getContents().trim());
                    sach.setTacGia(sheet.getCell(3, row).getContents().trim());
                    sach.setNXB(sheet.getCell(4, row).getContents().trim());
                    sach.setSLT(0);
                    sach.setGB(Integer.parseInt(sheet.getCell(5, row).getContents().trim()));
                    sach.setGM(Integer.parseInt(sheet.getCell(6, row).getContents().trim()));
                    sach.setMota(sheet.getCell(7, row).getContents().trim());
                    sach.setAnh("null");
                    BUSSach.IUBook(sach);
                }
                Initbooktable();
                workbook.close();
            } catch (IOException | BiffException ex) {
                JOptionPane.showMessageDialog(null, "Tải dữ liệu thất bại");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Chưa chọn đường dẫn");
        }
    }
//    Export sách

    @FXML
    private void exportbook_click(ActionEvent event) {
        FileChooser chooser = new FileChooser();
        chooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Excel (*.xls)", "*.xls"));
        chooser.setTitle("Save File");
        File file = chooser.showSaveDialog(new Stage());
        if (file != null) {
            String path = file.getPath();
            try {
                WritableWorkbook workbook1 = Workbook.createWorkbook(new File(path));
                WritableSheet sheet1 = workbook1.createSheet("First Sheet", 0);

                for (int i = 0; i < booktable.getCurrentItemsCount(); i++) {
                    for (int j = 0; j < booktable.getColumns().size(); j++) {
                        jxl.write.Label row = new jxl.write.Label(j, i, booktable.getColumns().get(j).getCellData(i).toString());
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
//    Thêm thể loại

    @FXML
    private void addkind_click(ActionEvent event) throws IOException {
        updatekind.setDisable(false);
        deletekind.setDisable(false);
        loadwindow("/GUI/FXML_InUpKind.fxml","Nhập thể loại");
    }
//    Cập nhật thể loại

    @FXML
    private void updatekind_click(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/GUI/FXML_InUpKind.fxml"));
        Parent root1 = loader.load();
        Scene scene = new Scene(root1);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Thông tin thể loại");
        stage.show();
        stage.setOnHidden(e -> {
            Initkindtable();
        });
        FXML_InUpKindController data = loader.getController();
        data.getData(kindtable.getSelectionModel().getSelectedItem().getValue());
    }
//    Xóa thể loại

    @FXML
    private void deletekind_click(ActionEvent event) {
        int p = JOptionPane.showConfirmDialog(null, "Bạn thật sự muốn xóa?", "Xóa", JOptionPane.YES_NO_OPTION);
        if (p == 0) {
            if (Integer.parseInt(kindtable.getColumns().get(2).getCellData(kindtable.getSelectionModel().getSelectedIndex()).toString()) == 0) {
                BUSTheLoai.DeleteKind(kindtable.getSelectionModel().getSelectedItem().getValue().getMaTL());
                refresh();
            } else {
                JOptionPane.showMessageDialog(null, "Không thể xóa dữ liệu");
            }
        }
        updatekind.setDisable(true);
        deletekind.setDisable(true);
    }
//    Thêm tác giả

    @FXML
    private void addauthor_click(ActionEvent event) throws IOException {
        updateauthor.setDisable(false);
        deleteauthor.setDisable(false);
        loadwindow("/GUI/FXML_InUpAuthor.fxml","Thông tin tác giả");
    }
//    Cập nhật tác giả

    @FXML
    private void updateauthor_click(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/GUI/FXML_InUpAuthor.fxml"));
        Parent root1 = loader.load();
        Scene scene = new Scene(root1);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Thông tin tác giả");
        stage.show();
        stage.setOnHidden(e -> {
            Initauthortable();
        });
        FXML_InUpAuthorController data = loader.getController();
        data.getData(authortable.getSelectionModel().getSelectedItem().getValue());
    }
//    Xóa tác giả

    @FXML
    private void deleteauthor_click(ActionEvent event) {
        int p = JOptionPane.showConfirmDialog(null, "Bạn thật sự muốn xóa?", "Xóa", JOptionPane.YES_NO_OPTION);
        if (p == 0) {
            if (Integer.parseInt(authortable.getColumns().get(2).getCellData(authortable.getSelectionModel().getSelectedIndex()).toString()) == 0) {
                BUSTacGia.DeleteAuthor(authortable.getSelectionModel().getSelectedItem().getValue().getMaTG());
                refresh();
            } else {
                JOptionPane.showMessageDialog(null, "Không thể xóa dữ liệu");
            }
        }
        updateauthor.setDisable(true);
        deleteauthor.setDisable(true);
    }
//    Thêm nhà xuất bản

    @FXML
    private void addpublish_click(ActionEvent event) throws IOException {
        updatepublish.setDisable(false);
        deletepublish.setDisable(false);
        loadwindow("/GUI/FXML_InUpPuplish.fxml","Thông tin nhà xuất bản");
    }
//    Cập nhật nhà xuất bản

    @FXML
    private void updatepublish_click(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/GUI/FXML_InUpPuplish.fxml"));
        Parent root1 = loader.load();
        Scene scene = new Scene(root1);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Thông tin nhà xuất bản");
        stage.show();
        stage.setOnHidden(e -> {
            Initpublishtable();
        });
        FXML_InUpPublishController data = loader.getController();
        data.getData(publishtable.getSelectionModel().getSelectedItem().getValue());
    }
//    Xóa nhà xuất bản

    @FXML
    private void deletepublish_click(ActionEvent event) {
        int p = JOptionPane.showConfirmDialog(null, "Bạn thật sự muốn xóa?", "Xóa", JOptionPane.YES_NO_OPTION);
        if (p == 0) {
            if (Integer.parseInt(publishtable.getColumns().get(5).getCellData(publishtable.getSelectionModel().getSelectedIndex()).toString()) == 0) {
                BUSNhaXB.DeletePublish(publishtable.getSelectionModel().getSelectedItem().getValue().getMaNXB());
                refresh();
            } else {
                JOptionPane.showMessageDialog(null, "Không thể xóa dữ liệu");
            }
        }
        updatepublish.setDisable(true);
        deletepublish.setDisable(true);
    }
//    

    @FXML
    private void booktable_keypress(KeyEvent event) throws IOException {
        if (event.getCode() == KeyCode.DELETE) {
            int p = JOptionPane.showConfirmDialog(null, "Bạn thật sự muốn xóa?", "Xóa", JOptionPane.YES_NO_OPTION);
            if (p == 0) {
                BUSSach.DeleteBook(booktable.getSelectionModel().getSelectedItem().getValue().getMaSach());
                Initbooktable();
            }
        } else {
            if (event.getCode() == KeyCode.INSERT) {
                updatebook.setDisable(true);
                deletebook.setDisable(true);
                loadwindow("/GUI/FXML_InUpSach.fxml","Thông tin sách");
            }
        }
    }
//    tạo và hiển thị bảng sách

    public void Initbooktable() {
        JFXTreeTableColumn<DTOSach, String> masach = new JFXTreeTableColumn<>("Mã sách");
        masach.setStyle("-fx-alignment: CENTER_LEFT;-fx-padding: 0 0 0 10;");
        masach.setPrefWidth(100);
        masach.setCellValueFactory((TreeTableColumn.CellDataFeatures<DTOSach, String> param) -> new SimpleObjectProperty(param.getValue().getValue().getMaSach().trim()));

        JFXTreeTableColumn<DTOSach, String> tensach = new JFXTreeTableColumn<>("Tên sách");
        tensach.setStyle("-fx-alignment: CENTER_LEFT;-fx-padding: 0 0 0 10;");
        tensach.setPrefWidth(200);
        tensach.setCellValueFactory((TreeTableColumn.CellDataFeatures<DTOSach, String> param) -> new SimpleObjectProperty(param.getValue().getValue().getTenSach().trim()));

        JFXTreeTableColumn<DTOSach, String> tl = new JFXTreeTableColumn<>("Thể loại");
        tl.setStyle("-fx-alignment: CENTER_LEFT;-fx-padding: 0 0 0 10;");
        tl.setPrefWidth(137);
        tl.setCellValueFactory((TreeTableColumn.CellDataFeatures<DTOSach, String> param) -> new SimpleObjectProperty(param.getValue().getValue().getTheLoai().trim()));

        JFXTreeTableColumn<DTOSach, String> tacgia = new JFXTreeTableColumn<>("Tác giả");
        tacgia.setStyle("-fx-alignment: CENTER_LEFT;-fx-padding: 0 0 0 10;");
        tacgia.setPrefWidth(137);
        tacgia.setCellValueFactory((TreeTableColumn.CellDataFeatures<DTOSach, String> param) -> new SimpleObjectProperty(param.getValue().getValue().getTacGia().trim()));

        JFXTreeTableColumn<DTOSach, String> nxb = new JFXTreeTableColumn<>("Nhà xuất bản");
        nxb.setStyle("-fx-alignment: CENTER_LEFT;-fx-padding: 0 0 0 10;");
        nxb.setPrefWidth(180);
        nxb.setCellValueFactory((TreeTableColumn.CellDataFeatures<DTOSach, String> param) -> new SimpleObjectProperty(param.getValue().getValue().getNXB().trim()));

        JFXTreeTableColumn<DTOSach, Integer> giamua = new JFXTreeTableColumn<>("Giá mua");
        giamua.setStyle("-fx-alignment: CENTER_RIGHT;-fx-padding: 0 10 0 0;");
        giamua.setPrefWidth(137);
        giamua.setCellValueFactory((TreeTableColumn.CellDataFeatures<DTOSach, Integer> param) -> new SimpleObjectProperty(df.format(param.getValue().getValue().getGM())));

        JFXTreeTableColumn<DTOSach, String> giaban = new JFXTreeTableColumn<>("Giá bán");
        giaban.setStyle("-fx-alignment: CENTER_RIGHT;-fx-padding: 0 10 0 0;");
        giaban.setPrefWidth(100);
        giaban.setCellValueFactory((TreeTableColumn.CellDataFeatures<DTOSach, String> param) -> new SimpleObjectProperty(df.format(param.getValue().getValue().getGB())));

        JFXTreeTableColumn<DTOSach, Integer> slt = new JFXTreeTableColumn<>("Số lượng tồn");
        slt.setStyle("-fx-alignment: CENTER_RIGHT;-fx-padding: 0 10 0 0;");
        slt.setPrefWidth(100);
        slt.setCellValueFactory((TreeTableColumn.CellDataFeatures<DTOSach, Integer> param) -> new SimpleObjectProperty(param.getValue().getValue().getSLT()));

        JFXTreeTableColumn<DTOSach, String> mota = new JFXTreeTableColumn<>("Mô tả");
        mota.setVisible(false);
        mota.setCellValueFactory((TreeTableColumn.CellDataFeatures<DTOSach, String> param) -> new SimpleObjectProperty(param.getValue().getValue().getMota().trim()));

        JFXTreeTableColumn<DTOSach, String> hinhanh = new JFXTreeTableColumn<>("Hình ảnh");
        hinhanh.setVisible(false);
        hinhanh.setCellValueFactory((TreeTableColumn.CellDataFeatures<DTOSach, String> param) -> new SimpleObjectProperty(param.getValue().getValue().getAnh().trim()));

        ObservableList<DTOSach> sach;
        sach = BUSSach.BookALL();
        final TreeItem<DTOSach> root = new RecursiveTreeItem<>(sach, RecursiveTreeObject::getChildren);
        booktable.getColumns().setAll(masach, tensach, tl, tacgia, nxb, giamua, giaban, slt, mota, hinhanh);
        booktable.setRoot(root);
        booktable.setShowRoot(false);

        searchname.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            booktable.setPredicate((TreeItem<DTOSach> t) -> {
                boolean flag = t.getValue().getTenSach().contains(newValue);
                return flag;
            });
        });

        searchkind.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            booktable.setPredicate((TreeItem<DTOSach> t) -> {
                boolean flag = t.getValue().getTheLoai().contains(newValue);
                return flag;
            });
        });

        searchauthor.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            booktable.setPredicate((TreeItem<DTOSach> t) -> {
                boolean flag = t.getValue().getTacGia().contains(newValue);
                return flag;
            });
        });

    }
//    tạo và hiển thị bảng thể loại

    public void Initkindtable() {
        JFXTreeTableColumn<DTOTheLoai, String> matl = new JFXTreeTableColumn<>("Mã thể loại");
        matl.setStyle("-fx-alignment: CENTER_LEFT;-fx-padding: 0 0 0 10;");
        matl.setPrefWidth(110);
        matl.setCellValueFactory((TreeTableColumn.CellDataFeatures<DTOTheLoai, String> param) -> new SimpleObjectProperty(param.getValue().getValue().getMaTL().trim()));

        JFXTreeTableColumn<DTOTheLoai, String> tentl = new JFXTreeTableColumn<>("Tên thể loại");
        tentl.setStyle("-fx-alignment: CENTER_LEFT;-fx-padding: 0 0 0 10;");
        tentl.setPrefWidth(170);
        tentl.setCellValueFactory((TreeTableColumn.CellDataFeatures<DTOTheLoai, String> param) -> new SimpleObjectProperty(param.getValue().getValue().getTenTL().trim()));

        JFXTreeTableColumn<DTOTheLoai, Integer> sl = new JFXTreeTableColumn<>("Sl");
        sl.setStyle("-fx-alignment: CENTER_RIGHT;-fx-padding: 0 10 0 0;");
        sl.setPrefWidth(50);
        sl.setCellValueFactory((TreeTableColumn.CellDataFeatures<DTOTheLoai, Integer> param) -> new SimpleObjectProperty(BUSTheLoai.CountKind(param.getValue().getValue().getMaTL())));

        ObservableList<DTOTheLoai> theloai;
        theloai = BUSTheLoai.KindALL();
        final TreeItem<DTOTheLoai> root = new RecursiveTreeItem<>(theloai, RecursiveTreeObject::getChildren);
        kindtable.getColumns().setAll(matl, tentl, sl);
        kindtable.setRoot(root);
        kindtable.setShowRoot(false);

        searchnamekind.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            kindtable.setPredicate((TreeItem<DTOTheLoai> t) -> {
                boolean flag = t.getValue().getTenTL().contains(newValue);
                return flag;
            });
        });
    }
//    tạo và hiển thị bảng tác giả

    public void Initauthortable() {
        JFXTreeTableColumn<DTOTacGia, String> matg = new JFXTreeTableColumn<>("Mã tác giả");
        matg.setStyle("-fx-alignment: CENTER_LEFT;-fx-padding: 0 0 0 10;");
        matg.setPrefWidth(110);
        matg.setCellValueFactory((TreeTableColumn.CellDataFeatures<DTOTacGia, String> param) -> new SimpleObjectProperty(param.getValue().getValue().getMaTG().trim()));

        JFXTreeTableColumn<DTOTacGia, String> tentg = new JFXTreeTableColumn<>("Tên tác giả");
        tentg.setStyle("-fx-alignment: CENTER_LEFT;-fx-padding: 0 0 0 10;");
        tentg.setPrefWidth(170);
        tentg.setCellValueFactory((TreeTableColumn.CellDataFeatures<DTOTacGia, String> param) -> new SimpleObjectProperty(param.getValue().getValue().getTenTG().trim()));

        JFXTreeTableColumn<DTOTacGia, Integer> sl = new JFXTreeTableColumn<>("Sl");
        sl.setPrefWidth(50);
        sl.setStyle("-fx-alignment: CENTER_RIGHT;-fx-padding: 0 10 0 0;");
        sl.setCellValueFactory((TreeTableColumn.CellDataFeatures<DTOTacGia, Integer> param) -> new SimpleObjectProperty(BUSTacGia.CountAuthor(param.getValue().getValue().getMaTG())));

        ObservableList<DTOTacGia> tacgia;
        tacgia = BUSTacGia.AuthorALL();
        final TreeItem<DTOTacGia> root = new RecursiveTreeItem<>(tacgia, RecursiveTreeObject::getChildren);
        authortable.getColumns().setAll(matg, tentg, sl);
        authortable.setRoot(root);
        authortable.setShowRoot(false);

        searchnameauthor.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            authortable.setPredicate((TreeItem<DTOTacGia> t) -> {
                boolean flag = t.getValue().getTenTG().contains(newValue);
                return flag;
            });
        });
    }
//    tạo và hiển thị bảng nhà xuất bản

    public void Initpublishtable() {
        JFXTreeTableColumn<DTONhaXB, String> manxb = new JFXTreeTableColumn<>("Mã nhà XB");
        manxb.setStyle("-fx-alignment: CENTER_LEFT;-fx-padding: 0 0 0 10;");
        manxb.setPrefWidth(110);
        manxb.setCellValueFactory((TreeTableColumn.CellDataFeatures<DTONhaXB, String> param) -> new SimpleObjectProperty(param.getValue().getValue().getMaNXB().trim()));

        JFXTreeTableColumn<DTONhaXB, String> tennxb = new JFXTreeTableColumn<>("Tên nhà XB");
        tennxb.setStyle("-fx-alignment: CENTER_LEFT;-fx-padding: 0 0 0 10;");
        tennxb.setPrefWidth(130);
        tennxb.setCellValueFactory((TreeTableColumn.CellDataFeatures<DTONhaXB, String> param) -> new SimpleObjectProperty(param.getValue().getValue().getTenNXB().trim()));

        JFXTreeTableColumn<DTONhaXB, String> dc = new JFXTreeTableColumn<>("Địa chỉ");
        dc.setStyle("-fx-alignment: CENTER_LEFT;-fx-padding: 0 0 0 10;");
        dc.setPrefWidth(200);
        dc.setCellValueFactory((TreeTableColumn.CellDataFeatures<DTONhaXB, String> param) -> new SimpleObjectProperty(param.getValue().getValue().getDiaChiNXB().trim()));

        JFXTreeTableColumn<DTONhaXB, String> email = new JFXTreeTableColumn<>("Email");
        email.setStyle("-fx-alignment: CENTER_LEFT;-fx-padding: 0 0 0 10;");
        email.setPrefWidth(200);
        email.setCellValueFactory((TreeTableColumn.CellDataFeatures<DTONhaXB, String> param) -> new SimpleObjectProperty(param.getValue().getValue().getEmail().trim()));

        JFXTreeTableColumn<DTONhaXB, String> dt = new JFXTreeTableColumn<>("Số điện thoại");
        dt.setStyle("-fx-alignment: CENTER_LEFT;-fx-padding: 0 0 0 10;");
        dt.setPrefWidth(110);
        dt.setCellValueFactory((TreeTableColumn.CellDataFeatures<DTONhaXB, String> param) -> new SimpleObjectProperty(param.getValue().getValue().getDT().trim()));

        JFXTreeTableColumn<DTONhaXB, Integer> sl = new JFXTreeTableColumn<>("Sl");
        sl.setStyle("-fx-alignment: CENTER_RIGHT;-fx-padding: 0 10 0 0;");
        sl.setPrefWidth(50);
        sl.setCellValueFactory((TreeTableColumn.CellDataFeatures<DTONhaXB, Integer> param) -> new SimpleObjectProperty(BUSNhaXB.CountPubish(param.getValue().getValue().getMaNXB())));

        ObservableList<DTONhaXB> nhaxuatban;
        nhaxuatban = BUSNhaXB.PublishALL();
        final TreeItem<DTONhaXB> root = new RecursiveTreeItem<>(nhaxuatban, RecursiveTreeObject::getChildren);
        publishtable.getColumns().setAll(manxb, tennxb, dc, email, dt, sl);
        publishtable.setRoot(root);
        publishtable.setShowRoot(false);

        searchnamepublish.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            publishtable.setPredicate((TreeItem<DTONhaXB> t) -> {
                boolean flag = t.getValue().getTenNXB().contains(newValue);
                return flag;
            });
        });

        searchaddress.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            publishtable.setPredicate((TreeItem<DTONhaXB> t) -> {
                boolean flag = t.getValue().getDiaChiNXB().contains(newValue);
                return flag;
            });
        });
        searchphone.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            publishtable.setPredicate((TreeItem<DTONhaXB> t) -> {
                boolean flag = t.getValue().getDT().contains(newValue);
                return flag;
            });
        });
    }
//    table click

    public void tablemousepress() {
        booktable.setOnMousePressed((MouseEvent event) -> {
            if (booktable.getSelectionModel().getSelectedItem() != null) {
                updatebook.setDisable(false);
                deletebook.setDisable(false);
                nametext.setText(booktable.getSelectionModel().getSelectedItem().getValue().getTenSach().trim());
                authortext.setText(booktable.getSelectionModel().getSelectedItem().getValue().getTacGia().trim());
                decriptext.setText(booktable.getSelectionModel().getSelectedItem().getValue().getMota().trim());
                if (!booktable.getSelectionModel().getSelectedItem().getValue().getAnh().trim().equals("null")) {
                    Image image = new Image(booktable.getSelectionModel().getSelectedItem().getValue().getAnh().trim());
                    bookimage.setImage(image);
                    bookimage.setFitWidth(160);
                    bookimage.setFitHeight(200);
                    bookimage.setPreserveRatio(true);
                } else {
                    bookimage.setImage(null);
                }
            }
        });
        kindtable.setOnMousePressed((MouseEvent event) -> {
            if (kindtable.getSelectionModel().getSelectedItem() != null) {
                updatekind.setDisable(false);
                deletekind.setDisable(false);
            }
        });
        authortable.setOnMousePressed((MouseEvent event) -> {
            if (authortable.getSelectionModel().getSelectedItem() != null) {
                updateauthor.setDisable(false);
                deleteauthor.setDisable(false);
            }
        });
        publishtable.setOnMousePressed((MouseEvent event) -> {
            if (publishtable.getSelectionModel().getSelectedItem() != null) {
                updatepublish.setDisable(false);
                deletepublish.setDisable(false);
            }
        });

    }

    public void refresh() {
        Initbooktable();
        Initkindtable();
        Initauthortable();
        Initpublishtable();
    }

    public void loadwindow(String s,String tilte) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(s));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
        stage.setTitle(tilte);
        stage.setOnHidden(e -> {
            refresh();
        });
    }

    @FXML
    private void change_click(MouseEvent event) {
        refresh();
    }

}
