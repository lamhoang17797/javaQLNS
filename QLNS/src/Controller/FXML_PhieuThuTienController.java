package Controller;

import BUS.BUSKhachHang;
import BUS.BUSPhieuThuTien;
import BUS.BUSThamSo;
import DTO.DTOKhachHang;
import DTO.DTOPhieuThuTien;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

public class FXML_PhieuThuTienController implements Initializable {

    @FXML
    private JFXTreeTableView<DTOPhieuThuTien> receipttable;

    @FXML
    private JFXTextField searchname;

    @FXML
    private JFXTreeTableView<DTOKhachHang> customertable;

    @FXML
    private JFXDatePicker day;

    @FXML
    private TextField money;

    @FXML
    private JFXButton addreceipt;

    private JFXButton updatereceipt;

    private JFXButton deletereceipt;

    @FXML
    private JFXButton exportceipt;

    DecimalFormat df = new DecimalFormat("###,###,###");

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        Initreceipttable();
        Initcustomertable();
    }

    @FXML
    void addrecipt_click(ActionEvent event) {
        if (!money.getText().trim().equals("")) {
            if (BUSThamSo.GetQuyDinh(1) == 1) {
                if (Integer.parseInt(money.getText().trim()) == customertable.getSelectionModel().getSelectedItem().getValue().getSTN()) {
                    DTOPhieuThuTien phieuthutien = new DTOPhieuThuTien();
                    phieuthutien.setMaPT(BUSPhieuThuTien.Tangma());
                    phieuthutien.setMaKH(customertable.getSelectionModel().getSelectedItem().getValue().getMaKH().trim());
                    day.setValue(LocalDate.now());
                    phieuthutien.setNgayThu(day.getValue().toString());
                    phieuthutien.setTienThu(Integer.parseInt(money.getText().trim()));
                    BUSPhieuThuTien.InsertReceipt(phieuthutien);
                    BUSKhachHang.UpdateSTN(Integer.parseInt(money.getText().trim()), BUSKhachHang.GetMaKH(customertable.getSelectionModel().getSelectedItem().getValue().getTenKH()), 2);
                    Initreceipttable();
                    Initcustomertable();

                } else {
                    JOptionPane.showMessageDialog(null, "Tiền thu phải bằng tiền nợ");
                }
            } else {
                DTOPhieuThuTien phieuthutien = new DTOPhieuThuTien();
                phieuthutien.setMaPT(BUSPhieuThuTien.Tangma());
                phieuthutien.setMaKH(customertable.getSelectionModel().getSelectedItem().getValue().getMaKH().trim());
                day.setValue(LocalDate.now());
                phieuthutien.setNgayThu(day.getValue().toString());
                phieuthutien.setTienThu(Integer.parseInt(money.getText().trim()));
                BUSPhieuThuTien.InsertReceipt(phieuthutien);
                BUSKhachHang.UpdateSTN(Integer.parseInt(money.getText().trim()), BUSKhachHang.GetMaKH(customertable.getSelectionModel().getSelectedItem().getValue().getTenKH()), 2);
                Initreceipttable();
            }

        } else {
            JOptionPane.showMessageDialog(null, "Tiền thu không được để trống");
        }
        Initcustomertable();
    }

    @FXML
    void customertale_click(MouseEvent event) {
        addreceipt.setDisable(false);
    }

    @FXML
    void exportceipt_click(ActionEvent event) {
        Document document = new Document();
        Rectangle one = new Rectangle(600, 500);
        document.setPageSize(one);
        FileChooser chooser = new FileChooser();
        chooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PDF (*.pdf)", "*.pdf"));
        chooser.setTitle("Save File");
        File file = chooser.showSaveDialog(new Stage());
        if (file != null) {
            String path = file.getPath();
            try {
                // khởi tạo một PdfWriter truyền vào document và FileOutputStream
                PdfWriter.getInstance(document, new FileOutputStream(path));
                // mở file để thực hiện viết
                document.open();
                // thêm nội dung sử dụng add function
                addpararaph(document, "PHIEU THU TIEN", 24, 1);
                addpararaph(document, "NHA SACH APOLLO", 12, 1);
                addpararaph(document, "----------------------------------------------------------------------------------------------------------------------", 12, 1);
                Date date = new Date();
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                addpararaph(document, "----------------------------------------------------------------------------------------------------------------------", 12, 1);
                float[] columnWidths = {10, 10, 30, 10};
                PdfPTable table = new PdfPTable(columnWidths);
                table.setWidthPercentage(100);
                table.setTotalWidth(10);
                table.addCell("STT");
                table.addCell("Ma phieu thu");
                table.addCell("Ten khach hang");
                table.addCell("Thanh tien");
                table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell("1");
                table.addCell(receipttable.getSelectionModel().getSelectedItem().getValue().getMaPT());
                table.addCell(receipttable.getSelectionModel().getSelectedItem().getValue().getMaKH());
                table.addCell(Integer.toString(receipttable.getSelectionModel().getSelectedItem().getValue().getTienThu()));
                document.add(table);
                addpararaph(document, "", 12, 0);
                addpararaph(document, "", 12, 0);
                addpararaph(document, "", 12, 0);
                addpararaph(document, "Khach Hang", 12, 0);
                // đóng file
                document.close();

            } catch (DocumentException | FileNotFoundException e) {
            }
        }
    }

    @FXML
    void receipttable_click(MouseEvent event) {
        if (receipttable.getSelectionModel().getSelectedItem() != null) {
            exportceipt.setDisable(false);
            money.setText(Integer.toString(BUSPhieuThuTien.GetTienThu(receipttable.getSelectionModel().getSelectedItem().getValue().getMaPT())));
        }
    }

    public void Initcustomertable() {
        JFXTreeTableColumn<DTOKhachHang, String> tenkh = new JFXTreeTableColumn<>("Tên khách hàng");
        tenkh.setStyle("-fx-alignment: CENTER;");
        tenkh.setPrefWidth(159);
        tenkh.setCellValueFactory((TreeTableColumn.CellDataFeatures<DTOKhachHang, String> param) -> new SimpleObjectProperty(param.getValue().getValue().getTenKH().trim()));

        JFXTreeTableColumn<DTOKhachHang, String> stn = new JFXTreeTableColumn<>("Số tiền nợ");
        stn.setStyle("-fx-alignment: CENTER;");
        stn.setPrefWidth(155);
        stn.setCellValueFactory((TreeTableColumn.CellDataFeatures<DTOKhachHang, String> param) -> new SimpleObjectProperty(df.format(param.getValue().getValue().getSTN())));

        ObservableList<DTOKhachHang> sach;
        sach = BUSKhachHang.CustomerALL();
        final TreeItem<DTOKhachHang> root = new RecursiveTreeItem<>(sach, RecursiveTreeObject::getChildren);
        customertable.getColumns().setAll(tenkh, stn);
        customertable.setRoot(root);
        customertable.setShowRoot(false);

        searchname.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            customertable.setPredicate((TreeItem<DTOKhachHang> t) -> {
                boolean flag = t.getValue().getTenKH().contains(newValue);
                return flag;
            });
        });
    }

    public void Initreceipttable() {
        JFXTreeTableColumn<DTOPhieuThuTien, String> mapt = new JFXTreeTableColumn<>("Mã Phiếu thu");
        mapt.setStyle("-fx-alignment: CENTER;");
        mapt.setPrefWidth(200);
        mapt.setCellValueFactory((TreeTableColumn.CellDataFeatures<DTOPhieuThuTien, String> param) -> new SimpleObjectProperty(param.getValue().getValue().getMaPT().trim()));

        JFXTreeTableColumn<DTOPhieuThuTien, String> makh = new JFXTreeTableColumn<>("Tên khách hàng");
        makh.setStyle("-fx-alignment: CENTER;");
        makh.setPrefWidth(205);
        makh.setCellValueFactory((TreeTableColumn.CellDataFeatures<DTOPhieuThuTien, String> param) -> new SimpleObjectProperty(param.getValue().getValue().getMaKH().trim()));

        JFXTreeTableColumn<DTOPhieuThuTien, String> ngaythu = new JFXTreeTableColumn<>("Ngày thu");
        ngaythu.setStyle("-fx-alignment: CENTER;");
        ngaythu.setPrefWidth(205);
        ngaythu.setCellValueFactory((TreeTableColumn.CellDataFeatures<DTOPhieuThuTien, String> param) -> new SimpleObjectProperty(param.getValue().getValue().getNgayThu().trim()));

        JFXTreeTableColumn<DTOPhieuThuTien, Integer> tienthu = new JFXTreeTableColumn<>("Số tiền thu");
        tienthu.setStyle("-fx-alignment: CENTER;");
        tienthu.setPrefWidth(205);
        tienthu.setCellValueFactory((TreeTableColumn.CellDataFeatures<DTOPhieuThuTien, Integer> param) -> new SimpleObjectProperty(df.format(param.getValue().getValue().getTienThu())));

        ObservableList<DTOPhieuThuTien> phieuthutien;
        phieuthutien = BUSPhieuThuTien.ReceiptsAll();
        final TreeItem<DTOPhieuThuTien> root = new RecursiveTreeItem<>(phieuthutien, RecursiveTreeObject::getChildren);
        receipttable.getColumns().setAll(mapt, makh, ngaythu, tienthu);
        receipttable.setRoot(root);
        receipttable.setShowRoot(false);

        searchname.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            receipttable.setPredicate((TreeItem<DTOPhieuThuTien> t) -> {
                boolean flag = t.getValue().getMaKH().contains(newValue);
                return flag;
            });
        });
    }

    public void addpararaph(Document document, String s, int size, int ali) throws DocumentException {
        Paragraph p = new Paragraph();
        p.setAlignment(ali);
        p.setSpacingAfter(15);
        Font f = new Font(Font.FontFamily.TIMES_ROMAN, size, Font.NORMAL);
        Phrase phrase = new Phrase();
        phrase.add(s);
        p.setFont(f);
        p.add(phrase);

        document.add(p);
    }
}
