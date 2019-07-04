package Controller;

import BUS.BUSCTPhieuNhap;
import BUS.BUSNhaCungCap;
import BUS.BUSPhieuNhap;
import BUS.BUSSach;
import BUS.BUSThamSo;
import DAL.DALCTPhieuNhap;
import DAL.DALNhaCungCap;
import DTO.DTOCTPhieuNhap;
import DTO.DTONhaCungCap;
import DTO.DTOPhieuNhap;
import DTO.DTOSach;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.GrayColor;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import java.awt.HeadlessException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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

public class FXML_PhieuNhapSachController implements Initializable {

    @FXML
    private JFXButton updatesupply, deletesupply, createimport, savedetail, export, addbook, updatedetail, deletedetail;

    @FXML
    private ImageView supplyimage;

    @FXML
    private Label nametext, addresstext, emailtext, phonetext, sum;

    @FXML
    private JFXTreeTableView<DTONhaCungCap> supplytable;
    @FXML
    private JFXTextField searchname, searchaddress, searchemail, ncc;

    @FXML
    private JFXTreeTableView<DTOPhieuNhap> importtable;

    @FXML
    private JFXComboBox<String> supply;

    @FXML
    private JFXDatePicker day;

    @FXML
    private JFXTreeTableView<DTOCTPhieuNhap> detailimporttable;

    @FXML
    private TextField amountimport, price;

    @FXML
    private JFXTreeTableView<DTOSach> booktable;

    ObservableList<DTOCTPhieuNhap> ctphieunhap;
    JFXTreeTableColumn<DTOCTPhieuNhap, String> mactpn;
    JFXTreeTableColumn<DTOCTPhieuNhap, String> tsach;
    JFXTreeTableColumn<DTOCTPhieuNhap, String> sln;
    JFXTreeTableColumn<DTOCTPhieuNhap, String> dongia;
    int stt, tongtt = 0;
    String mapn, tens;
    DecimalFormat df = new DecimalFormat("###,###,###");

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        Initsupplytable();
        Initimporttable();
        Initbooktable();
        loadcomobox();
    }

//    Thêm nhà cung cấp
    @FXML
    void addsupply_click(ActionEvent event) throws IOException {
        updatesupply.setDisable(true);
        deletesupply.setDisable(true);
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/GUI/FXML_InUpNhaCungCap.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Thông tin nhà cung cấp");
        stage.centerOnScreen();
        stage.show();
        stage.setOnHidden(e -> {
            Initsupplytable();
        });
    }
//    Cập nhật nhà cung cấp

    @FXML
    void updatesupply_click(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/GUI/FXML_InUpNhaCungCap.fxml"));
        Parent root1 = loader.load();
        Scene scene = new Scene(root1);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Thông tin nhà cung cấp");
        stage.centerOnScreen();
        stage.show();
        stage.setOnHidden(e -> {
            Initsupplytable();
        });
        FXML_InUpNhaCungCapController data = loader.getController();
        data.getData(supplytable.getSelectionModel().getSelectedItem().getValue());
    }
//    Xóa nhà cung cấp

    @FXML
    void deletesupply_click(ActionEvent event) {
        int p = JOptionPane.showConfirmDialog(null, "Bạn thật sự muốn xóa?", "Xóa", JOptionPane.YES_NO_OPTION);
        if (p == 0) {
            BUSNhaCungCap.DeleteSupply(supplytable.getSelectionModel().getSelectedItem().getValue().getMaNCC());
            Initsupplytable();
        }
        updatesupply.setDisable(true);
        deletesupply.setDisable(true);
    }
//    Nhập danh sách nhà cung cấp

    @FXML
    void importsupply_click(ActionEvent event) throws BiffException {
        FileChooser chooser = new FileChooser();
        chooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Excel (*.xls)", "*.xls"));
        chooser.setTitle("Open File");
        DTONhaCungCap nhacungcap = new DTONhaCungCap();
        File file = chooser.showOpenDialog(new Stage());
        if (file != null) {
            String path = file.getPath();
            Workbook workbook;
            try {
                workbook = Workbook.getWorkbook(new File(path));
                Sheet sheet = workbook.getSheet(0);
                for (int row = 0; row < sheet.getRows(); row++) {

                    nhacungcap.setMaNCC(sheet.getCell(0, row).getContents());
                    nhacungcap.setTenNCC(sheet.getCell(1, row).getContents());
                    nhacungcap.setDiaChiNCC(sheet.getCell(2, row).getContents());
                    nhacungcap.setDienThoai(sheet.getCell(3, row).getContents());
                    nhacungcap.setEmail(sheet.getCell(4, row).getContents());
                    nhacungcap.setDienThoai(sheet.getCell(5, row).getContents());
                    nhacungcap.setHinhAnh("null");
                    BUSNhaCungCap.IUSupply(nhacungcap);
                }
                Initsupplytable();
                workbook.close();
            } catch (IOException | BiffException ex) {
                JOptionPane.showMessageDialog(null, "Tải dữ liệu thất bại");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Chưa chọn đường dẫn");
        }
    }
//    Xuất danh sách nhà cung cấp

    @FXML
    void exportsupply_click(ActionEvent event) {
        FileChooser chooser = new FileChooser();
        chooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Excel (*.xls)", "*.xls"));
        chooser.setTitle("Save File");
        File file = chooser.showSaveDialog(new Stage());
        if (file != null) {
            String path = file.getPath();
            try {
                WritableWorkbook workbook1 = Workbook.createWorkbook(new File(path));
                WritableSheet sheet1 = workbook1.createSheet("First Sheet", 0);

                for (int i = 0; i < supplytable.getCurrentItemsCount(); i++) {
                    for (int j = 0; j < supplytable.getColumns().size(); j++) {
                        jxl.write.Label row = new jxl.write.Label(j, i, supplytable.getColumns().get(j).getCellData(i).toString());
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

//    phím tắt 
    @FXML
    void supplytable_keypress(KeyEvent event) throws IOException {
        if (event.getCode() == KeyCode.DELETE) {
            int p = JOptionPane.showConfirmDialog(null, "Bạn thật sự muốn xóa?", "Xóa", JOptionPane.YES_NO_OPTION);
            if (p == 0) {
                BUSNhaCungCap.DeleteSupply(supplytable.getSelectionModel().getSelectedItem().getValue().getMaNCC());
                Initsupplytable();
            }
        } else {
            if (event.getCode() == KeyCode.INSERT) {
                updatesupply.setDisable(true);
                deletesupply.setDisable(true);
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/GUI/FXML_InUpNhaCungCap.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setTitle("Thông tin nhà cung cấp");
                stage.setScene(scene);
                stage.centerOnScreen();
                stage.show();
            }

        }
    }

//    chọn dữ liệu nhà cung cấp
    @FXML
    private void supplytable_click(MouseEvent event) {
        if (supplytable.getSelectionModel().getSelectedItem() != null) {
            updatesupply.setDisable(false);
            deletesupply.setDisable(false);
            nametext.setText(supplytable.getSelectionModel().getSelectedItem().getValue().getTenNCC().trim());
            addresstext.setText(supplytable.getSelectionModel().getSelectedItem().getValue().getDiaChiNCC().trim());
            emailtext.setText(supplytable.getSelectionModel().getSelectedItem().getValue().getEmail().trim());
            phonetext.setText(supplytable.getSelectionModel().getSelectedItem().getValue().getDienThoai().trim());
            if (!supplytable.getSelectionModel().getSelectedItem().getValue().getHinhAnh().trim().equals("null")) {
                Image image = new Image(supplytable.getSelectionModel().getSelectedItem().getValue().getHinhAnh().trim());
                supplyimage.setImage(image);
                supplyimage.setFitWidth(160);
                supplyimage.setFitHeight(200);
                supplyimage.setPreserveRatio(true);
            } else {
                supplyimage.setImage(null);
            }
        }
    }

//  Thay đổi tab 
    @FXML
    private void change_click(MouseEvent event) {
        loadcomobox();
    }
//    Thêm sách vào chi tiết phiếu nhập

    @FXML
    void addbook_click(ActionEvent event) {
        stt++;
        DTOCTPhieuNhap dtm = new DTOCTPhieuNhap();
        dtm.setMaCTPN(Integer.toString(stt));
        dtm.setMaPN(mapn);
        dtm.setMaSach(tens);
        if (Integer.parseInt(amountimport.getText().trim()) < BUSThamSo.GetSLNToiThieu(1)) {
            if ((BUSSach.GetSLT(tens, "1") + Integer.parseInt(amountimport.getText().trim())) < BUSThamSo.GetSLTToida(1)) {
                dtm.setSLN(Integer.parseInt(amountimport.getText().trim()));
                dtm.setGiaMua(Integer.parseInt(price.getText().trim()));
                mactpn = new JFXTreeTableColumn<>("Số thứ tự");
                mactpn.setStyle("-fx-alignment: CENTER;");
                mactpn.setPrefWidth(194);
                mactpn.setCellValueFactory((TreeTableColumn.CellDataFeatures<DTOCTPhieuNhap, String> param) -> new SimpleObjectProperty(param.getValue().getValue().getMaCTPN().trim()));

                ctphieunhap.add(dtm);
                final TreeItem<DTOCTPhieuNhap> root = new RecursiveTreeItem<>(ctphieunhap, RecursiveTreeObject::getChildren);
                detailimporttable.getColumns().setAll(mactpn, tsach, sln, dongia);
                detailimporttable.setRoot(root);
                detailimporttable.setShowRoot(false);
                TinhTT();
                Initbooktable();
                
            } else {
                JOptionPane.showMessageDialog(null, "Số lượng tồn tối đa : " + BUSThamSo.GetSLTToida(1));
            }
        } else {
            JOptionPane.showMessageDialog(null, "Số lượng nhập tối đa : " + BUSThamSo.GetSLNToiThieu(1));
        }
    }
//    Tạo phiếu nhập

    @FXML
    void createimport_click(ActionEvent event) {
        DTOPhieuNhap phieunhapDTO = new DTOPhieuNhap();
        phieunhapDTO.setMaPN(BUSPhieuNhap.Tangma());
        phieunhapDTO.setMaNCC(BUSNhaCungCap.GetMaNCC((String) supply.getValue()));
        phieunhapDTO.setTongTT(0);
        day.setValue(LocalDate.now());
        phieunhapDTO.setNgayNhap(day.getValue().toString());
        BUSPhieuNhap.IUImport(phieunhapDTO);
        Initimporttable();
    }
//    Xóa chi tiết phiếu nhập

    @FXML
    void deletedetail_click(ActionEvent event) {
        BUSSach.UpdateSLN(Integer.parseInt(amountimport.getText().trim()), BUSSach.GetMaSach(detailimporttable.getSelectionModel().getSelectedItem().getValue().getMaSach().trim()), 2);
        BUSCTPhieuNhap.DeleteDetailImport(BUSCTPhieuNhap.GetMaCTPN(detailimporttable.getSelectionModel().getSelectedItem().getValue().getMaPN().trim(), BUSSach.GetMaSach(detailimporttable.getSelectionModel().getSelectedItem().getValue().getMaSach())));
        initdetail(mapn);
        TinhTT();
        BUSPhieuNhap.UpdateTT(Integer.parseInt(sum.getText().trim()), mapn);
        Initimporttable();
    }
//    Xuất phiếu nhập

    @FXML
    void export_click(ActionEvent event) throws IOException {
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
                addpararaph(document, "PHIẾU NHẬP SÁCH", 24, 1);
                addpararaph(document, "NHÀ SÁCH APOLLO", 12, 1);
                addpararaph(document, "----------------------------------------------------------------------------------------------------------------------", 12, 1);
                Date date = new Date();
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                addpararaph(document, "Mã Phiếu nhập: " + mapn + "                   Ngày nhập: " + formatter.format(date), 12, 0);
                addpararaph(document, "Nhà cung cấp: " + importtable.getSelectionModel().getSelectedItem().getValue().getMaNCC().trim() + "                   Điện thoại: " + DALNhaCungCap.GetDT(importtable.getSelectionModel().getSelectedItem().getValue().getMaNCC().trim()), 12, 0);
                addpararaph(document, "----------------------------------------------------------------------------------------------------------------------", 12, 1);
                float[] columnWidths = {10, 10, 30, 10, 10, 10};
                PdfPTable table = new PdfPTable(columnWidths);
                table.setWidthPercentage(100);
                table.setTotalWidth(10);
                BaseFont unicode_font = BaseFont.createFont("times.ttf",
                        BaseFont.IDENTITY_H, BaseFont.EMBEDDED);

                Font f = new Font(unicode_font, 15, Font.NORMAL, GrayColor.GRAYWHITE);
                PdfPCell cell = new PdfPCell(new Phrase("Danh sách chi tiết phiếu nhập", f));
                cell.setBackgroundColor(GrayColor.GRAYBLACK);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setColspan(6);
                table.addCell(cell);
                Font f1 = new Font(unicode_font, 15, Font.NORMAL, GrayColor.BLACK);
                PdfPCell cell1 = new PdfPCell(new Phrase("Mã sách", f1));
                table.addCell("STT");
                table.addCell(cell1);
                cell1 = new PdfPCell(new Phrase("Tên sách", f1));
                table.addCell(cell1);
                cell1 = new PdfPCell(new Phrase("Số lượng", f1));
                table.addCell(cell1);
                cell1 = new PdfPCell(new Phrase("Đơn giá", f1));
                table.addCell(cell1);
                cell1 = new PdfPCell(new Phrase("Thành tiền", f1));
                table.addCell(cell1);
                int tongsl = 0;
                for (int i = 0; i < detailimporttable.getCurrentItemsCount(); i++) {
                    table.addCell(detailimporttable.getColumns().get(0).getCellData(i).toString());
                    table.addCell(BUSSach.GetMaSach(detailimporttable.getColumns().get(1).getCellData(i).toString()));
                    cell1 = new PdfPCell(new Phrase(detailimporttable.getColumns().get(1).getCellData(i).toString(), f1));
                    table.addCell(cell1);
                    table.addCell(detailimporttable.getColumns().get(2).getCellData(i).toString());
                    table.addCell(detailimporttable.getColumns().get(3).getCellData(i).toString());
                    int tt = Integer.parseInt(detailimporttable.getColumns().get(2).getCellData(i).toString()) * DALCTPhieuNhap.GetDG(mapn, BUSSach.GetMaSach(detailimporttable.getColumns().get(1).getCellData(i).toString()));
                    tongsl = tongsl + Integer.parseInt(detailimporttable.getColumns().get(2).getCellData(i).toString());
                    table.addCell(df.format(tt));

                }
                cell = new PdfPCell(new Phrase("Tổng: ", f));
                cell.setBackgroundColor(GrayColor.GRAY);
                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                cell.setColspan(3);
                cell.setFixedHeight(30f);
                table.addCell(cell);
                table.addCell(Integer.toString(tongsl));
                table.addCell("");
                table.addCell(sum.getText().trim());
                document.add(table);
                addpararaph(document, "", 12, 0);
                addpararaph(document, "", 12, 0);
                addpararaph(document, "", 12, 0);
                // đóng file
                document.close();

            } catch (DocumentException | FileNotFoundException e) {
            }
        }
    }
//    Lưu chi tiết phiếu nhập

    @FXML
    void savedetail_click(ActionEvent event) {
        for (int i = 0; i < detailimporttable.getCurrentItemsCount(); i++) {
            DTOCTPhieuNhap ctphieunhapDTO = new DTOCTPhieuNhap();
            ctphieunhapDTO.setMaCTPN(BUSCTPhieuNhap.Tangma());
            ctphieunhapDTO.setMaPN(mapn);
            ctphieunhapDTO.setMaSach(BUSSach.GetMaSach(detailimporttable.getColumns().get(1).getCellData(i).toString()));
            ctphieunhapDTO.setSLN(Integer.parseInt(detailimporttable.getColumns().get(2).getCellData(i).toString()));
            ctphieunhapDTO.setGiaMua(BUSSach.GetGM(detailimporttable.getColumns().get(1).getCellData(i).toString()));
            BUSSach.UpdateSLN(Integer.parseInt(detailimporttable.getColumns().get(2).getCellData(i).toString()), BUSSach.GetMaSach(detailimporttable.getColumns().get(1).getCellData(i).toString()), 1);
            BUSCTPhieuNhap.IUDetailImport(ctphieunhapDTO);
        }
        JOptionPane.showMessageDialog(null, "Lưu chi tiết thành công");
        BUSPhieuNhap.UpdateTT(Integer.parseInt(sum.getText().trim()), mapn);
        Initimporttable();
    }
//    Cập nhật số lượng nhập

    @FXML
    void updatedetail_click(ActionEvent event) {
        if (DALCTPhieuNhap.Checkma(detailimporttable.getSelectionModel().getSelectedItem().getValue().getMaPN(), detailimporttable.getSelectionModel().getSelectedItem().getValue().getMaSach()) == true) {
            int slt = detailimporttable.getSelectionModel().getSelectedItem().getValue().getSLN();
            if ((BUSSach.GetSLT(detailimporttable.getSelectionModel().getSelectedItem().getValue().getMaSach(), "1") + (Integer.parseInt(amountimport.getText().trim()) - slt)) < BUSThamSo.GetSLTToida(1)) {
                DTOCTPhieuNhap dtm = new DTOCTPhieuNhap();
                dtm.setMaCTPN(BUSCTPhieuNhap.GetMaCTPN(detailimporttable.getSelectionModel().getSelectedItem().getValue().getMaPN(), BUSSach.GetMaSach(detailimporttable.getSelectionModel().getSelectedItem().getValue().getMaSach())));
                dtm.setMaPN(detailimporttable.getSelectionModel().getSelectedItem().getValue().getMaPN());
                dtm.setMaSach(detailimporttable.getSelectionModel().getSelectedItem().getValue().getMaSach());
                dtm.setSLN(Integer.parseInt(amountimport.getText().trim()));
                dtm.setGiaMua(Integer.parseInt(price.getText().trim()));

                if (Integer.parseInt(amountimport.getText().trim()) != slt) {
                    BUSSach.UpdateSLN(Integer.parseInt(amountimport.getText().trim()) - slt, BUSSach.GetMaSach(detailimporttable.getSelectionModel().getSelectedItem().getValue().getMaSach()), 1);
                }
                detailimporttable.getSelectionModel().getSelectedItem().setValue(dtm);

                DTOCTPhieuNhap ctphieunhapDTO = new DTOCTPhieuNhap();
                ctphieunhapDTO.setMaCTPN(BUSCTPhieuNhap.GetMaCTPN(detailimporttable.getSelectionModel().getSelectedItem().getValue().getMaPN(), detailimporttable.getSelectionModel().getSelectedItem().getValue().getMaSach()));
                ctphieunhapDTO.setMaPN(detailimporttable.getSelectionModel().getSelectedItem().getValue().getMaPN());
                ctphieunhapDTO.setMaSach(BUSSach.GetMaSach(detailimporttable.getSelectionModel().getSelectedItem().getValue().getMaSach()));
                ctphieunhapDTO.setSLN(Integer.parseInt(amountimport.getText().trim()));
                ctphieunhapDTO.setGiaMua(Integer.parseInt(price.getText().trim()));
                BUSCTPhieuNhap.IUDetailImport(ctphieunhapDTO);
                TinhTT();
                BUSPhieuNhap.UpdateTT(Integer.parseInt(sum.getText().trim()), mapn);
                Initimporttable();
            } else {
                JOptionPane.showMessageDialog(null, "Số lượng tồn tối đa: " + BUSThamSo.GetSLTToida(1));
            }
        } else {
            int slt = detailimporttable.getSelectionModel().getSelectedItem().getValue().getSLN();
            if ((BUSSach.GetSLT(detailimporttable.getSelectionModel().getSelectedItem().getValue().getMaSach(), "1") + (Integer.parseInt(amountimport.getText().trim()) - slt)) < BUSThamSo.GetSLTToida(1)) {
                DTOCTPhieuNhap dtm = new DTOCTPhieuNhap();
                dtm.setMaCTPN(detailimporttable.getSelectionModel().getSelectedItem().getValue().getMaCTPN());
                dtm.setMaPN(detailimporttable.getSelectionModel().getSelectedItem().getValue().getMaPN());
                dtm.setMaSach(detailimporttable.getSelectionModel().getSelectedItem().getValue().getMaSach());
                dtm.setSLN(Integer.parseInt(amountimport.getText().trim()));
                dtm.setGiaMua(Integer.parseInt(price.getText().trim()));
                detailimporttable.getSelectionModel().getSelectedItem().setValue(dtm);
                TinhTT();
                Initimporttable();
            } else {
                JOptionPane.showMessageDialog(null, "Số lượng tồn tối đa: " + BUSThamSo.GetSLTToida(1));
            }
        }

    }
//    Chọn dữ liệu phiếu nhập

    @FXML
    private void importtable_click(MouseEvent event) {
        if (importtable.getSelectionModel().getSelectedItem() != null) {
            detailimporttable.setDisable(false);
            mapn = importtable.getSelectionModel().getSelectedItem().getValue().getMaPN().trim();
            if (importtable.getSelectionModel().getSelectedItem().getValue().getTongTT() != 0) {
                booktable.setDisable(true);
            } else {
                booktable.setDisable(false);
            }
            tongtt = 0;
            sum.setText(df.format(importtable.getSelectionModel().getSelectedItem().getValue().getTongTT()));
            initdetail(mapn);
        }
    }
//    Chọn dữ liệu chi tiết phiếu nhập

    @FXML
    private void detailimporttable_click(MouseEvent event) {
        if (detailimporttable.getSelectionModel().getSelectedItem() != null) {
            updatedetail.setDisable(false);
            deletedetail.setDisable(false);
            amountimport.setDisable(false);
            price.setDisable(false);
            amountimport.setText(Integer.toString(detailimporttable.getSelectionModel().getSelectedItem().getValue().getSLN()));
            price.setText(Integer.toString(detailimporttable.getSelectionModel().getSelectedItem().getValue().getGiaMua()));
        }
    }
//    Chọn dữ liệu sách

    @FXML
    private void booktable_click(MouseEvent event) {
        if (booktable.getSelectionModel().getSelectedItem() != null) {
            amountimport.setDisable(false);
            price.setDisable(false);
            addbook.setDisable(false);
            detailimporttable.setDisable(false);
            price.setText(Integer.toString(booktable.getSelectionModel().getSelectedItem().getValue().getGM()));
            tens = booktable.getSelectionModel().getSelectedItem().getValue().getTenSach().trim();
        }
    }

//    Danh sách nhà cung cấp
    public void loadcomobox() {
        supply.getItems().clear();
        ObservableList<DTONhaCungCap> arr = BUSNhaCungCap.SupplyALL();
        for (int i = 0; i < arr.size(); i++) {
            supply.getItems().add(arr.get(i).getTenNCC().trim());
        }
    }

    //    Tạo bảng nhà cung cấp
    public void Initsupplytable() {
        JFXTreeTableColumn<DTONhaCungCap, String> mancc = new JFXTreeTableColumn<>("Mã nhà cung cấp");
        mancc.setStyle("-fx-alignment: CENTER;");
        mancc.setPrefWidth(150);
        mancc.setCellValueFactory((TreeTableColumn.CellDataFeatures<DTONhaCungCap, String> param) -> new SimpleObjectProperty(param.getValue().getValue().getMaNCC().trim()));

        JFXTreeTableColumn<DTONhaCungCap, String> tenncc = new JFXTreeTableColumn<>("Họ tên khách hàng");
        tenncc.setPrefWidth(260);
        tenncc.setCellValueFactory((TreeTableColumn.CellDataFeatures<DTONhaCungCap, String> param) -> new SimpleObjectProperty(param.getValue().getValue().getTenNCC().trim()));

        JFXTreeTableColumn<DTONhaCungCap, String> dc = new JFXTreeTableColumn<>("Địa chỉ");
        dc.setPrefWidth(332);
        dc.setCellValueFactory((TreeTableColumn.CellDataFeatures<DTONhaCungCap, String> param) -> new SimpleObjectProperty(param.getValue().getValue().getDiaChiNCC().trim()));

        JFXTreeTableColumn<DTONhaCungCap, String> email = new JFXTreeTableColumn<>("Email");
        email.setPrefWidth(200);
        email.setCellValueFactory((TreeTableColumn.CellDataFeatures<DTONhaCungCap, String> param) -> new SimpleObjectProperty(param.getValue().getValue().getEmail().trim()));

        JFXTreeTableColumn<DTONhaCungCap, String> dt = new JFXTreeTableColumn<>("Số điện thoại");
        dt.setStyle("-fx-alignment: CENTER;");
        dt.setPrefWidth(150);
        dt.setCellValueFactory((TreeTableColumn.CellDataFeatures<DTONhaCungCap, String> param) -> new SimpleObjectProperty(param.getValue().getValue().getDienThoai().trim()));

        JFXTreeTableColumn<DTONhaCungCap, String> hinhanh = new JFXTreeTableColumn<>("Hình ảnh");
        hinhanh.setVisible(false);
        hinhanh.setCellValueFactory((TreeTableColumn.CellDataFeatures<DTONhaCungCap, String> param) -> new SimpleObjectProperty(param.getValue().getValue().getHinhAnh().trim()));

        ObservableList<DTONhaCungCap> nhacungcap;
        nhacungcap = BUSNhaCungCap.SupplyALL();
        final TreeItem<DTONhaCungCap> root = new RecursiveTreeItem<>(nhacungcap, RecursiveTreeObject::getChildren);
        supplytable.getColumns().setAll(mancc, tenncc, dc, email, dt, hinhanh);
        supplytable.setRoot(root);
        supplytable.setShowRoot(false);

        searchname.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            supplytable.setPredicate((TreeItem<DTONhaCungCap> t) -> {
                boolean flag = t.getValue().getTenNCC().contains(newValue);
                return flag;
            });
        });

        searchaddress.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            supplytable.setPredicate((TreeItem<DTONhaCungCap> t) -> {
                boolean flag = t.getValue().getDiaChiNCC().contains(newValue);
                return flag;
            });
        });

        searchemail.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            supplytable.setPredicate((TreeItem<DTONhaCungCap> t) -> {
                boolean flag = t.getValue().getEmail().contains(newValue);
                return flag;
            });
        });

    }

//    Tạo bảng phiếu nhập
    public void Initimporttable() {
        JFXTreeTableColumn<DTOPhieuNhap, String> maPn = new JFXTreeTableColumn<>("Mã phiếu nhập");
        maPn.setStyle("-fx-alignment: CENTER;");
        maPn.setPrefWidth(124);
        maPn.setCellValueFactory((TreeTableColumn.CellDataFeatures<DTOPhieuNhap, String> param) -> new SimpleObjectProperty(param.getValue().getValue().getMaPN().trim()));

        JFXTreeTableColumn<DTOPhieuNhap, String> tenncc = new JFXTreeTableColumn<>("Nhà cung cấp");
        tenncc.setPrefWidth(124);
        tenncc.setCellValueFactory((TreeTableColumn.CellDataFeatures<DTOPhieuNhap, String> param) -> new SimpleObjectProperty(param.getValue().getValue().getMaNCC().trim()));

        JFXTreeTableColumn<DTOPhieuNhap, String> ngaynhap = new JFXTreeTableColumn<>("Ngày nhập");
        ngaynhap.setPrefWidth(124);
        ngaynhap.setCellValueFactory((TreeTableColumn.CellDataFeatures<DTOPhieuNhap, String> param) -> new SimpleObjectProperty(param.getValue().getValue().getNgayNhap().trim()));

        JFXTreeTableColumn<DTOPhieuNhap, String> tongtien = new JFXTreeTableColumn<>("Tổng tiền");
        tongtien.setStyle("-fx-alignment: CENTER;");
        tongtien.setPrefWidth(120);
        tongtien.setCellValueFactory((TreeTableColumn.CellDataFeatures<DTOPhieuNhap, String> param) -> new SimpleObjectProperty(df.format(param.getValue().getValue().getTongTT())));

        ObservableList<DTOPhieuNhap> phieunhap;
        phieunhap = BUSPhieuNhap.ImportALL();
        final TreeItem<DTOPhieuNhap> root = new RecursiveTreeItem<>(phieunhap, RecursiveTreeObject::getChildren);
        importtable.getColumns().setAll(maPn, tenncc, ngaynhap, tongtien);
        importtable.setRoot(root);
        importtable.setShowRoot(false);
        ncc.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            importtable.setPredicate((TreeItem<DTOPhieuNhap> t) -> {
                boolean flag = t.getValue().getMaNCC().contains(newValue);
                return flag;
            });
        });

    }

//    tạo bảng chi tiết phiếu nhập
    public void Initidetailmporttable() {

        tsach = new JFXTreeTableColumn<>("Sách");
        tsach.setPrefWidth(194);
        tsach.setCellValueFactory((TreeTableColumn.CellDataFeatures<DTOCTPhieuNhap, String> param) -> new SimpleObjectProperty(param.getValue().getValue().getMaSach().trim()));

        sln = new JFXTreeTableColumn<>("Số lượng nhập");

        sln.setStyle("-fx-alignment: CENTER;");
        sln.setPrefWidth(190);
        sln.setCellValueFactory((TreeTableColumn.CellDataFeatures<DTOCTPhieuNhap, String> param) -> new SimpleObjectProperty(param.getValue().getValue().getSLN()));
        dongia = new JFXTreeTableColumn<>("Đơn giá");
        dongia.setStyle("-fx-alignment: CENTER;");
        dongia.setPrefWidth(194);
        dongia.setCellValueFactory((TreeTableColumn.CellDataFeatures<DTOCTPhieuNhap, String> param) -> new SimpleObjectProperty(df.format(param.getValue().getValue().getGiaMua())));
    }

    public void initdetail(String ma) {
        Initidetailmporttable();
        mactpn = new JFXTreeTableColumn<>("Số thứ tự");
        mactpn.setStyle("-fx-alignment: CENTER;");
        mactpn.setPrefWidth(194);
        mactpn.setCellValueFactory((TreeTableColumn.CellDataFeatures<DTOCTPhieuNhap, String> param) -> new SimpleObjectProperty(BUSCTPhieuNhap.STT(param.getValue().getValue().getMaPN().trim(), BUSSach.GetMaSach(param.getValue().getValue().getMaSach().trim()))));

        ctphieunhap = BUSCTPhieuNhap.DetailImportAll(ma);
        final TreeItem<DTOCTPhieuNhap> root = new RecursiveTreeItem<>(ctphieunhap, RecursiveTreeObject::getChildren);
        detailimporttable.getColumns().setAll(mactpn, tsach, sln, dongia);
        detailimporttable.setRoot(root);
        detailimporttable.setShowRoot(false);
    }

    //    Tạo bảng sách
    public void Initbooktable() {
        JFXTreeTableColumn<DTOSach, String> tensach = new JFXTreeTableColumn<>("Tên sách");
        tensach.setStyle("-fx-alignment: CENTER;");
        tensach.setPrefWidth(158);
        tensach.setCellValueFactory((TreeTableColumn.CellDataFeatures<DTOSach, String> param) -> new SimpleObjectProperty(param.getValue().getValue().getTenSach().trim()));

        JFXTreeTableColumn<DTOSach, String> giamua = new JFXTreeTableColumn<>("Giá mua");
        giamua.setStyle("-fx-alignment: CENTER;");
        giamua.setPrefWidth(158);
        giamua.setCellValueFactory((TreeTableColumn.CellDataFeatures<DTOSach, String> param) -> new SimpleObjectProperty(df.format(param.getValue().getValue().getGM())));

        ObservableList<DTOSach> sach;
        sach = BUSSach.BookALL();
        final TreeItem<DTOSach> root = new RecursiveTreeItem<>(sach, RecursiveTreeObject::getChildren);
        booktable.getColumns().setAll(tensach, giamua);
        booktable.setRoot(root);
        booktable.setShowRoot(false);

        searchname.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            booktable.setPredicate((TreeItem<DTOSach> t) -> {
                boolean flag = t.getValue().getTenSach().contains(newValue);
                return flag;
            });
        });
    }

    @FXML
    private void supply_click(ActionEvent event) {
        ncc.setText(supply.getValue());
    }

    //    Tính tổng tiền
    public void TinhTT() {
        tongtt = 0;
        for (int i = 0; i < detailimporttable.getCurrentItemsCount(); i++) {
            tongtt = tongtt + Integer.parseInt(detailimporttable.getColumns().get(2).getCellData(i).toString()) * BUSSach.GetGM(detailimporttable.getColumns().get(1).getCellData(i).toString());
        }
        sum.setText(Integer.toString(tongtt));
    }

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
