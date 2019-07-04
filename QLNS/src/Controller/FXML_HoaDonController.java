package Controller;

import BUS.BUSCTHoaDon;
import BUS.BUSHoaDon;
import BUS.BUSKhachHang;
import BUS.BUSSach;
import BUS.BUSThamSo;
import DTO.DTOCTHoaDon;
import DTO.DTOHoaDon;
import DTO.DTOKhachHang;
import DTO.DTOSach;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.VerticalPositionMark;
import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

public class FXML_HoaDonController implements Initializable {

    @FXML
    private JFXHamburger hamber;

    @FXML
    private JFXTreeTableView<DTOCTHoaDon> detailbilltable;

    @FXML
    private JFXTreeTableView<DTOHoaDon> billtable;

    @FXML
    private JFXComboBox<String> customer;

    @FXML
    private JFXDatePicker day;

    @FXML
    private Label changedue, searchbill, searchbillday, total;

    @FXML
    private JFXDrawer drawer;

    @FXML
    private TextField searchname, price, amount;

    @FXML
    private Pane pane;

    @FXML
    private JFXButton addbook;

    @FXML
    private JFXTreeTableView<DTOSach> booktable;

    String mahd, tens, makh;
    int stt, tongtt = 0;
    ObservableList<DTOCTHoaDon> cthoadon;
    JFXTreeTableColumn<DTOCTHoaDon, String> macthd;
    JFXTreeTableColumn<DTOCTHoaDon, String> tsach;
    JFXTreeTableColumn<DTOCTHoaDon, Integer> slb;
    JFXTreeTableColumn<DTOCTHoaDon, Integer> dongia;
    JFXTreeTableColumn<DTOCTHoaDon, Integer> km;
    DecimalFormat df = new DecimalFormat("###,###,###");
    @FXML
    private TextField payment;
    @FXML
    private TextField selloff;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        bookdrawer();
        loadcomobox();
        initbooktable();
        initbilltable();
        BUSCTHoaDon.DeleteAllDetailBillTemp();
    }
//    Nhấn Enter tạo hóa đơn

    @FXML
    void createbill_press(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            if (BUSKhachHang.GetSTN(customer.getValue().trim(), "p") < BUSThamSo.GetSTNToiDa(1)) {
                DTOHoaDon hoadonDTO = new DTOHoaDon();
                hoadonDTO.setMaHD(BUSHoaDon.Tangma());
                hoadonDTO.setMaKH(BUSKhachHang.GetMaKH((String) customer.getValue()));
                hoadonDTO.setTongTT(0);
                day.setValue(LocalDate.now());
                hoadonDTO.setNgaylap(day.getValue().toString());
                BUSHoaDon.IUBill(hoadonDTO);
                initbilltable();
            } else {
                JOptionPane.showMessageDialog(null, "Số tiền nợ tối đa:" + BUSThamSo.GetSTNToiDa(1));
            }
        }
    }

    @FXML
    private void hamber_click(MouseEvent event) {
        if (drawer.isVisible() == true) {
            drawer.setVisible(false);
        } else {
            drawer.setVisible(true);
        }
    }
//    Thêm sách vào chi tiết hóa đơn

    @FXML
    void addbook_click(ActionEvent event) {
        if (Integer.parseInt(amount.getText().trim()) <= booktable.getSelectionModel().getSelectedItem().getValue().getSLT()) {
            stt = detailbilltable.getCurrentItemsCount();
            stt = stt + 1;
            DTOCTHoaDon dtm = new DTOCTHoaDon();
            dtm.setMaCTHD(Integer.toString(stt));
            dtm.setMaHD(mahd);
            dtm.setMaSach(BUSSach.GetMaSach(tens));
            dtm.setSLB(Integer.parseInt(amount.getText().trim()));
            dtm.setGiaBan(Integer.parseInt(price.getText().trim()));
            dtm.setKM(Integer.parseInt(selloff.getText().trim()));
            BUSCTHoaDon.IUDetailBillTemp(dtm);

            macthd = new JFXTreeTableColumn<>("Số thứ tự");
            macthd.setStyle("-fx-alignment: CENTER;");
            macthd.setPrefWidth(124);
            macthd.setCellValueFactory((TreeTableColumn.CellDataFeatures<DTOCTHoaDon, String> param) -> new SimpleObjectProperty(param.getValue().getValue().getMaCTHD().trim()));
            cthoadon = BUSCTHoaDon.DetailBillAllTemp();
            final TreeItem<DTOCTHoaDon> root = new RecursiveTreeItem<>(cthoadon, RecursiveTreeObject::getChildren);
            detailbilltable.getColumns().setAll(macthd, tsach, slb, dongia, km);
            detailbilltable.setRoot(root);
            detailbilltable.setShowRoot(false);
            TinhTT();
            initbooktable();
        } else {
            JOptionPane.showMessageDialog(null, "Số lượng không đủ");
        }
    }

//    Chọn thông tin sách
    @FXML
    void booktable_click(MouseEvent event) {
        if (booktable.getSelectionModel().getSelectedItem() != null) {
            addbook.setDisable(false);
            price.setText(Integer.toString(booktable.getSelectionModel().getSelectedItem().getValue().getGB()));
            tens = booktable.getSelectionModel().getSelectedItem().getValue().getTenSach().trim();
        }
    }

//    Chọn thông tin hóa đơn
    @FXML
    void billtable_click(MouseEvent event) {
        if (billtable.getSelectionModel().getSelectedItem() != null) {
            BUSCTHoaDon.DeleteAllDetailBillTemp();
            mahd = billtable.getSelectionModel().getSelectedItem().getValue().getMaHD().trim();
            makh = BUSKhachHang.GetMaKH(billtable.getSelectionModel().getSelectedItem().getValue().getMaKH().trim());
            if (billtable.getSelectionModel().getSelectedItem().getValue().getTongTT() != 0) {
                booktable.setDisable(true);
            } else {
                booktable.setDisable(false);
            }
            tongtt = 0;
            payment.setText(Integer.toString(billtable.getSelectionModel().getSelectedItem().getValue().getTienThu()));
            changedue.setText(df.format(Math.abs(billtable.getSelectionModel().getSelectedItem().getValue().getTienThu() - billtable.getSelectionModel().getSelectedItem().getValue().getTongTT())));
            total.setText(df.format(billtable.getSelectionModel().getSelectedItem().getValue().getTongTT()));
            initdetail(mahd);
        }
    }

//    Tạo bảng sách
    public void initbooktable() {
        JFXTreeTableColumn<DTOSach, String> tensach = new JFXTreeTableColumn<>("Tên sách");
        tensach.setStyle("-fx-alignment: CENTER;");
        tensach.setPrefWidth(100);
        tensach.setCellValueFactory((TreeTableColumn.CellDataFeatures<DTOSach, String> param) -> new SimpleObjectProperty(param.getValue().getValue().getTenSach().trim()));

        JFXTreeTableColumn<DTOSach, Integer> giamua = new JFXTreeTableColumn<>("Giá bán");
        giamua.setStyle("-fx-alignment: CENTER;");
        giamua.setPrefWidth(70);
        giamua.setCellValueFactory((TreeTableColumn.CellDataFeatures<DTOSach, Integer> param) -> new SimpleObjectProperty(param.getValue().getValue().getGB()));

        JFXTreeTableColumn<DTOSach, Integer> slt = new JFXTreeTableColumn<>("SL");
        slt.setStyle("-fx-alignment: CENTER;");
        slt.setPrefWidth(70);
        slt.setCellValueFactory((TreeTableColumn.CellDataFeatures<DTOSach, Integer> param) -> new SimpleObjectProperty(param.getValue().getValue().getSLT()));

        ObservableList<DTOSach> sach;
        sach = BUSSach.BookALL();
        final TreeItem<DTOSach> root = new RecursiveTreeItem<>(sach, RecursiveTreeObject::getChildren);
        booktable.getColumns().setAll(tensach, giamua, slt);
        booktable.setRoot(root);
        booktable.setShowRoot(false);

        searchname.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            booktable.setPredicate((TreeItem<DTOSach> t) -> {
                boolean flag = t.getValue().getTenSach().contains(newValue);
                return flag;
            });
        });
    }
//    Tạo bảng chi tiết hóa đơn

    public void Initidetaibilltable() {

        tsach = new JFXTreeTableColumn<>("Sách");
        tsach.setStyle("-fx-alignment: CENTER;");
        tsach.setPrefWidth(205);
        tsach.setCellValueFactory((TreeTableColumn.CellDataFeatures<DTOCTHoaDon, String> param) -> new SimpleObjectProperty(param.getValue().getValue().getMaSach().trim()));

        slb = new JFXTreeTableColumn<>("Số lượng bán");
        slb.setStyle("-fx-alignment: CENTER;");
        slb.setPrefWidth(180);
        slb.setCellValueFactory((TreeTableColumn.CellDataFeatures<DTOCTHoaDon, Integer> param) -> new SimpleObjectProperty(param.getValue().getValue().getSLB()));

        dongia = new JFXTreeTableColumn<>("Đơn giá");
        dongia.setStyle("-fx-alignment: CENTER;");
        dongia.setPrefWidth(180);
        dongia.setCellValueFactory((TreeTableColumn.CellDataFeatures<DTOCTHoaDon, Integer> param) -> new SimpleObjectProperty(df.format(param.getValue().getValue().getGiaBan())));

        km = new JFXTreeTableColumn<>("Giảm giá");
        km.setStyle("-fx-alignment: CENTER;");
        km.setPrefWidth(180);
        km.setCellValueFactory((TreeTableColumn.CellDataFeatures<DTOCTHoaDon, Integer> param) -> new SimpleObjectProperty(param.getValue().getValue().getKM()));

    }
//    Hiển thị chi tiết hóa đơn

    public void initdetail(String ma) {
        Initidetaibilltable();
        macthd = new JFXTreeTableColumn<>("Số thứ tự");
        macthd.setStyle("-fx-alignment: CENTER;");
        macthd.setPrefWidth(187);
        macthd.setCellValueFactory((TreeTableColumn.CellDataFeatures<DTOCTHoaDon, String> param) -> new SimpleObjectProperty(BUSCTHoaDon.STT(param.getValue().getValue().getMaHD().trim(), BUSSach.GetMaSach(param.getValue().getValue().getMaSach().trim()))));

        cthoadon = BUSCTHoaDon.DetailBillAll(ma);
        final TreeItem<DTOCTHoaDon> root = new RecursiveTreeItem<>(cthoadon, RecursiveTreeObject::getChildren);
        detailbilltable.getColumns().setAll(macthd, tsach, slb, dongia, km);
        detailbilltable.setRoot(root);
        detailbilltable.setShowRoot(false);
    }
//    Tạo bảng hóa đơn

    public void initbilltable() {
        JFXTreeTableColumn<DTOHoaDon, String> maHđ = new JFXTreeTableColumn<>("Mã hóa đơn");
        maHđ.setStyle("-fx-alignment: CENTER;");
        maHđ.setPrefWidth(120);
        maHđ.setCellValueFactory((TreeTableColumn.CellDataFeatures<DTOHoaDon, String> param) -> new SimpleObjectProperty(param.getValue().getValue().getMaHD().trim()));

        JFXTreeTableColumn<DTOHoaDon, String> tenkh = new JFXTreeTableColumn<>("Khách hàng");
        tenkh.setPrefWidth(124);
        tenkh.setCellValueFactory((TreeTableColumn.CellDataFeatures<DTOHoaDon, String> param) -> new SimpleObjectProperty(param.getValue().getValue().getMaKH().trim()));

        JFXTreeTableColumn<DTOHoaDon, String> ngaylap = new JFXTreeTableColumn<>("Ngày lập");
        ngaylap.setPrefWidth(124);
        ngaylap.setCellValueFactory((TreeTableColumn.CellDataFeatures<DTOHoaDon, String> param) -> new SimpleObjectProperty(param.getValue().getValue().getNgaylap().trim()));

        JFXTreeTableColumn<DTOHoaDon, Integer> tongtien = new JFXTreeTableColumn<>("Tổng tiền");
        tongtien.setStyle("-fx-alignment: CENTER;");
        tongtien.setPrefWidth(124);
        tongtien.setCellValueFactory((TreeTableColumn.CellDataFeatures<DTOHoaDon, Integer> param) -> new SimpleObjectProperty(df.format(param.getValue().getValue().getTongTT())));

        JFXTreeTableColumn<DTOHoaDon, Integer> tienthu = new JFXTreeTableColumn<>("Tiền thu");
        tienthu.setStyle("-fx-alignment: CENTER;");
        tienthu.setVisible(true);
        tienthu.setCellValueFactory((TreeTableColumn.CellDataFeatures<DTOHoaDon, Integer> param) -> new SimpleObjectProperty(param.getValue().getValue().getTienThu()));

        ObservableList<DTOHoaDon> phieunhap;
        phieunhap = BUSHoaDon.BillALL();
        final TreeItem<DTOHoaDon> root = new RecursiveTreeItem<>(phieunhap, RecursiveTreeObject::getChildren);
        billtable.getColumns().setAll(maHđ, tenkh, ngaylap, tongtien, tienthu);
        billtable.setRoot(root);
        billtable.setShowRoot(false);
        searchbill.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            billtable.setPredicate((TreeItem<DTOHoaDon> t) -> {
                boolean flag = t.getValue().getMaKH().contains(newValue);
                return flag;
            });
        });
    }

    //    Danh sách nhà cung cấp
    public void loadcomobox() {
        customer.getItems().clear();
        ObservableList<DTOKhachHang> arr = BUSKhachHang.CustomerALL();
        for (int i = 0; i < arr.size(); i++) {
            customer.getItems().add(arr.get(i).getTenKH().trim());
        }
    }

    public void bookdrawer() {
        try {
            drawer.setSidePane(pane);
            HamburgerBackArrowBasicTransition burgertask = new HamburgerBackArrowBasicTransition(hamber);
            burgertask.setRate(-1);
            hamber.addEventHandler(MouseEvent.MOUSE_PRESSED, (e) -> {

                burgertask.setRate(burgertask.getRate() * -1);
                burgertask.play();
                if (drawer.isOpened()) {
                    drawer.close();
                } else {
                    drawer.open();
                }
            });
        } catch (Exception e) {
        }
    }

//    Tìm kiếm hóa đơn
    @FXML
    private void customer_click(ActionEvent event) {
        searchbill.setText(customer.getValue());
    }

    //    Tính tổng tiền
    public void TinhTT() {
        tongtt = 0;
        for (int i = 0; i < detailbilltable.getCurrentItemsCount(); i++) {
            double se = (Integer.parseInt(detailbilltable.getColumns().get(2).getCellData(i).toString()) * BUSSach.GetGB(detailbilltable.getColumns().get(1).getCellData(i).toString())) * (Integer.parseInt(detailbilltable.getColumns().get(4).getCellData(i).toString()) * 1.0 / 100);
            tongtt = tongtt + Integer.parseInt(detailbilltable.getColumns().get(2).getCellData(i).toString()) * BUSSach.GetGB(detailbilltable.getColumns().get(1).getCellData(i).toString())
                    - (int) se;
        }
        total.setText(df.format(tongtt));
    }

    public int TT() {
        tongtt = 0;
        for (int i = 0; i < detailbilltable.getCurrentItemsCount(); i++) {
            double se = (Integer.parseInt(detailbilltable.getColumns().get(2).getCellData(i).toString()) * BUSSach.GetGB(detailbilltable.getColumns().get(1).getCellData(i).toString())) * (Integer.parseInt(detailbilltable.getColumns().get(4).getCellData(i).toString()) * 1.0 / 100);
            tongtt = tongtt + Integer.parseInt(detailbilltable.getColumns().get(2).getCellData(i).toString()) * BUSSach.GetGB(detailbilltable.getColumns().get(1).getCellData(i).toString())
                    - (int) se;
        }
        return tongtt;
    }
//    Lưu và xuất hóa đơn

    @FXML
    private void keypress(KeyEvent event) throws FileNotFoundException, IOException {
        if (event.getCode() == KeyCode.F12) {
            if (changedue.getText().trim().equals("")) {
                changedue.setText(df.format(Math.abs(TT() - Integer.parseInt(payment.getText().trim()))));
            }
            exportbill();
        }
        if (event.getCode() == KeyCode.F8) {
            for (int i = 0; i < detailbilltable.getCurrentItemsCount(); i++) {
                DTOCTHoaDon cthoadonDTO = new DTOCTHoaDon();
                cthoadonDTO.setMaCTHD(BUSCTHoaDon.Tangma());
                cthoadonDTO.setMaHD(mahd);
                cthoadonDTO.setMaSach(BUSSach.GetMaSach(detailbilltable.getColumns().get(1).getCellData(i).toString()));
                cthoadonDTO.setSLB(Integer.parseInt(detailbilltable.getColumns().get(2).getCellData(i).toString()));
                cthoadonDTO.setGiaBan(BUSSach.GetGB(detailbilltable.getColumns().get(1).getCellData(i).toString()));
                cthoadonDTO.setKM(Integer.parseInt(detailbilltable.getColumns().get(4).getCellData(i).toString()));
                BUSSach.UpdateSLN(Integer.parseInt(detailbilltable.getColumns().get(2).getCellData(i).toString()), BUSSach.GetMaSach(detailbilltable.getColumns().get(1).getCellData(i).toString()), 2);
                BUSCTHoaDon.IUDetailBill(cthoadonDTO);
            }
            changedue.setText(df.format(Math.abs(TT() - Integer.parseInt(payment.getText().trim()))));
            if (TT() > Integer.parseInt(payment.getText().trim())) {
                BUSKhachHang.UpdateSTN(Math.abs(TT() - Integer.parseInt(payment.getText().trim())), makh, 1);
            }
            BUSCTHoaDon.DeleteAllDetailBillTemp();
            JOptionPane.showMessageDialog(null, "Lưu thành công");
            BUSHoaDon.UpdateTT(TT(), Integer.parseInt(payment.getText().trim()), mahd);
            initbilltable();
            initbooktable();
        }
    }

//    Xử lý nhấn phím trên bảng chi tiết hóa đơn
    @FXML
    private void detailbill_keypress(KeyEvent event) {
        if (event.getCode() == KeyCode.DELETE) {
            BUSCTHoaDon.DeleteDetailBillTemp(detailbilltable.getSelectionModel().getSelectedItem().getValue().getMaCTHD());
            cthoadon = BUSCTHoaDon.DetailBillAllTemp();
            final TreeItem<DTOCTHoaDon> root = new RecursiveTreeItem<>(cthoadon, RecursiveTreeObject::getChildren);
            detailbilltable.getColumns().setAll(macthd, tsach, slb, dongia, km);
            detailbilltable.setRoot(root);
            detailbilltable.setShowRoot(false);
            TinhTT();
        }

    }

    public void exportbill() throws FileNotFoundException, IOException {
        Document document = new Document();
        Rectangle one = new Rectangle(400, 600);
        document.setPageSize(one);
        FileChooser chooser = new FileChooser();
        chooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PDF (*.pdf)", "*.pdf"));
        chooser.setTitle("Save File");
        File file = chooser.showSaveDialog(new Stage());
        if (file != null) {
            String path = file.getPath();
            try {
//                // khởi tạo một PdfWriter truyền vào document và FileOutputStream
                PdfWriter.getInstance(document, new FileOutputStream(path));
//                // mở file để thực hiện viết
                document.open();
//                // thêm nội dung sử dụng add function
                addpararaph(document, "HÓA ĐƠN", 24, 1);
                addpararaph(document, "NHÀ SÁCH APOLLO", 12, 1);
                addpararaph(document, "----------------------------------------------------------------------------------------------------------------------", 12, 1);
                Date date = new Date();
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                addpararaph(document, "Mã hóa đơn:   " + mahd, 20, 1);
                addpararaph(document, "Khách hàng:   " + billtable.getSelectionModel().getSelectedItem().getValue().getMaKH().trim(), 15, 1);
                addpararaph(document, "Ngày lập:   " + billtable.getSelectionModel().getSelectedItem().getValue().getNgaylap().trim(), 15, 1);
                addpararaph(document, "----------------------------------------------------------------------------------------------------------------------", 12, 1);
                for (int i = 0; i < detailbilltable.getCurrentItemsCount(); i++) {
                    Chunk glue = new Chunk(new VerticalPositionMark());
                    Paragraph p = new Paragraph(detailbilltable.getColumns().get(0).getCellData(i).toString() + "              " + detailbilltable.getColumns().get(1).getCellData(i).toString());
                    p.setIndentationLeft(30f);
                    p.setIndentationRight(30f);
                    p.add(new Chunk(glue));
                    int tt = Integer.parseInt(detailbilltable.getColumns().get(2).getCellData(i).toString()) * BUSSach.GetGB(detailbilltable.getColumns().get(1).getCellData(i).toString())
                            - ((Integer.parseInt(detailbilltable.getColumns().get(2).getCellData(i).toString()) * BUSSach.GetGB(detailbilltable.getColumns().get(1).getCellData(i).toString())) * (Integer.parseInt(detailbilltable.getColumns().get(4).getCellData(i).toString()) / 100));
                    p.add(tt + "");
                    document.add(p);
                }
                addpararaph(document, "", 12, 0);
                Chunk glue = new Chunk(new VerticalPositionMark());
                Paragraph p = new Paragraph("Total: ");
                p.setIndentationLeft(30f);
                p.setIndentationRight(30f);
                p.add(new Chunk(glue));
                p.add(total.getText().trim());
                document.add(p);
                addpararaph(document, "", 12, 0);
                p = new Paragraph("Payment: ");
                p.setIndentationLeft(30f);
                p.setIndentationRight(30f);
                p.add(new Chunk(glue));
                p.add(df.format(Integer.parseInt(payment.getText().trim())));
                document.add(p);
                addpararaph(document, "", 12, 0);
                p = new Paragraph("Changedue: ");
                p.setIndentationLeft(30f);
                p.setIndentationRight(30f);
                p.add(new Chunk(glue));
                p.add(changedue.getText().trim());
                document.add(p);
//                // đóng file
                document.close();

            } catch (DocumentException | FileNotFoundException e) {
            }
        }
    }

//    Thêm Paragraph vào pdf
    public void addpararaph(Document document, String s, int size, int ali) throws DocumentException, IOException {
        Paragraph p = new Paragraph();
        p.setAlignment(ali);
        p.setSpacingAfter(15);
        BaseFont unicode_font = BaseFont.createFont("times.ttf",
                BaseFont.IDENTITY_H, BaseFont.EMBEDDED);

        Font f = new Font(unicode_font, size, Font.NORMAL);
        Phrase phrase = new Phrase(s, f);
        p.add(phrase);

        document.add(p);
    }

}
