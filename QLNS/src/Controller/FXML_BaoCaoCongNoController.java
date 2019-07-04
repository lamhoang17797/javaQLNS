package Controller;

import BUS.BUSBaoCao;
import BUS.BUSBaoCaoCongNo;
import BUS.BUSHoaDon;
import BUS.BUSKhachHang;
import BUS.BUSPhieuThuTien;
import DTO.DTOBaoCao;
import DTO.DTOBaoCaoCongNo;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.layout.BorderPane;

public class FXML_BaoCaoCongNoController implements Initializable {

    @FXML
    private JFXTreeTableView<DTOBaoCaoCongNo> reporttable;

    @FXML
    private TextField year;

    @FXML
    private ComboBox<String> month;

    @FXML
    private BorderPane pane;

    private NumberAxis yAxis;
    private CategoryAxis xAxis;
    private BarChart<String, Number> barChart;

    SimpleDateFormat sdf = new SimpleDateFormat("mm");

    String ngay;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        loadcombo();
    }

    @FXML
    void create(ActionEvent event) {
        LocalDate today = LocalDate.now();
        if (year.getText().trim().equals("")) {
            createreport(monthformat(today.getMonthValue()), today.getYear());
        } else {
            createreport(month.getValue().trim(), Integer.parseInt(year.getText().trim()));
        }
    }

    public void loadreporttable(String ma) {
        JFXTreeTableColumn<DTOBaoCaoCongNo, String> mabccn = new JFXTreeTableColumn<>("STT");
        mabccn.setStyle("-fx-alignment: CENTER;");
        mabccn.setPrefWidth(90);
        mabccn.setCellValueFactory((TreeTableColumn.CellDataFeatures<DTOBaoCaoCongNo, String> param) -> new SimpleObjectProperty(BUSBaoCaoCongNo.STT(param.getValue().getValue().getMaBC().trim(), BUSKhachHang.GetMaKH(param.getValue().getValue().getMaKH().trim()))));

        JFXTreeTableColumn<DTOBaoCaoCongNo, String> mabc = new JFXTreeTableColumn<>("Mã báo cáo");
        mabc.setPrefWidth(90);
        mabc.setCellValueFactory((TreeTableColumn.CellDataFeatures<DTOBaoCaoCongNo, String> param) -> new SimpleObjectProperty(param.getValue().getValue().getMaBC().trim()));

        JFXTreeTableColumn<DTOBaoCaoCongNo, String> tenkh = new JFXTreeTableColumn<>("Tên khách hàng");
        tenkh.setPrefWidth(111);
        tenkh.setCellValueFactory((TreeTableColumn.CellDataFeatures<DTOBaoCaoCongNo, String> param) -> new SimpleObjectProperty(param.getValue().getValue().getMaKH().trim()));

        JFXTreeTableColumn<DTOBaoCaoCongNo, Integer> nodau = new JFXTreeTableColumn<>("Nợ đầu");
        nodau.setStyle("-fx-alignment: CENTER;");
        nodau.setPrefWidth(120);
        nodau.setCellValueFactory((TreeTableColumn.CellDataFeatures<DTOBaoCaoCongNo, Integer> param) -> new SimpleObjectProperty(param.getValue().getValue().getNoDau()));

        JFXTreeTableColumn<DTOBaoCaoCongNo, Integer> nophatsinh = new JFXTreeTableColumn<>("Nợ phát sinh");
        nophatsinh.setStyle("-fx-alignment: CENTER;");
        nophatsinh.setPrefWidth(120);
        nophatsinh.setCellValueFactory((TreeTableColumn.CellDataFeatures<DTOBaoCaoCongNo, Integer> param) -> new SimpleObjectProperty(param.getValue().getValue().getNoPhatSinh()));

        JFXTreeTableColumn<DTOBaoCaoCongNo, Integer> nocuoi = new JFXTreeTableColumn<>("Nợ cuối");
        nocuoi.setStyle("-fx-alignment: CENTER;");
        nocuoi.setPrefWidth(120);
        nocuoi.setCellValueFactory((TreeTableColumn.CellDataFeatures<DTOBaoCaoCongNo, Integer> param) -> new SimpleObjectProperty(param.getValue().getValue().getNoCuoi()));

        ObservableList<DTOBaoCaoCongNo> baocaocongno;
        baocaocongno = BUSBaoCaoCongNo.ReportAlL(ma);
        final TreeItem<DTOBaoCaoCongNo> root = new RecursiveTreeItem<>(baocaocongno, RecursiveTreeObject::getChildren);
        reporttable.getColumns().setAll(mabccn, mabc, tenkh, nodau, nophatsinh, nocuoi);
        reporttable.setRoot(root);
        reporttable.setShowRoot(false);
    }

    public void loadcombo() {
        month.getItems().add("01");
        month.getItems().add("02");
        month.getItems().add("03");
        month.getItems().add("04");
        month.getItems().add("05");
        month.getItems().add("06");
        month.getItems().add("07");
        month.getItems().add("08");
        month.getItems().add("09");
        month.getItems().add("10");
        month.getItems().add("11");
        month.getItems().add("12");
    }

    public String monthformat(int m) {
        String s;
        if (m < 10) {
            s = "0" + (m);
        } else {
            s = Integer.toString(m);
        }
        return s;
    }

    public void getChart() {
        xAxis = new CategoryAxis();
        yAxis = new NumberAxis();
        barChart = new BarChart<>(xAxis, yAxis);
        xAxis.setLabel("Khách hàng");
        yAxis.setLabel("Số lượng");
        barChart.setTitle("Báo cáo công nợ");
        XYChart.Series<String, Number> series1 = new XYChart.Series<>();
        XYChart.Series tondau = new XYChart.Series<>();
        XYChart.Series tonphatsinh = new XYChart.Series<>();
        XYChart.Series toncuoi = new XYChart.Series<>();
        tondau.setName("Nợ đầu");
        tonphatsinh.setName("Nợ phát sinh");
        toncuoi.setName("Nợ cuối");
        for (int i = 0; i < reporttable.getCurrentItemsCount(); i++) {
            tondau.getData().add(new XYChart.Data(reporttable.getColumns().get(2).getCellData(i).toString(), Integer.parseInt(reporttable.getColumns().get(3).getCellData(i).toString())));
            tonphatsinh.getData().add(new XYChart.Data(reporttable.getColumns().get(2).getCellData(i).toString(), Integer.parseInt(reporttable.getColumns().get(4).getCellData(i).toString())));
            toncuoi.getData().add(new XYChart.Data(reporttable.getColumns().get(2).getCellData(i).toString(), Integer.parseInt(reporttable.getColumns().get(5).getCellData(i).toString())));
        }
        barChart.getData().addAll(tondau, tonphatsinh, toncuoi);
        barChart.setPrefSize(reporttable.getCurrentItemsCount() * 150, 490);
        ScrollPane scrollpane = new ScrollPane(barChart);
        pane.setCenter(scrollpane);
    }

    public void createreport(String thang, int nam) {
        boolean check = BUSBaoCao.checkMaBC(Integer.parseInt(thang), nam, 2);
        if (check == false) {
            ngay = nam + "-" + thang;
            DTOBaoCao baocao = new DTOBaoCao(BUSBaoCao.Tangma(), Integer.parseInt(thang), nam, 2);
            BUSBaoCao.InsertBC(baocao);
            ArrayList<String> khachhang = BUSKhachHang.IdCustomer();
            for (int i = 0; i < khachhang.size(); i++) {
                DTOBaoCaoCongNo baocaocongno = new DTOBaoCaoCongNo();
                baocaocongno.setMaBCCN(BUSBaoCaoCongNo.Tangma());
                baocaocongno.setMaBC(BUSBaoCao.GetMaBC(Integer.parseInt(thang), nam, 2));
                baocaocongno.setMaKH(khachhang.get(i));
                baocaocongno.setNoCuoi(BUSKhachHang.GetSTN("1", khachhang.get(i)));
                int nophatsinh;
                System.out.println(BUSHoaDon.GetTNo(ngay, thang));
                System.out.println(BUSPhieuThuTien.GetTienThu(ngay, thang));
                if (BUSHoaDon.GetTNo(ngay, khachhang.get(i)) >= BUSPhieuThuTien.GetTienThu(ngay, khachhang.get(i))) {
                    nophatsinh = BUSHoaDon.GetTNo(ngay, khachhang.get(i)) - BUSPhieuThuTien.GetTienThu(ngay, khachhang.get(i));
                } else {
                    nophatsinh = 0;
                }
                baocaocongno.setNoPhatSinh(nophatsinh);
                baocaocongno.setNoDau(BUSKhachHang.GetSTN("1", khachhang.get(i)) - nophatsinh);
                System.out.println(baocaocongno.getMaBCCN());
                System.out.println(baocaocongno.getMaBC());
                System.out.println(baocaocongno.getMaKH());
                System.out.println(baocaocongno.getNoDau());
                System.out.println(baocaocongno.getNoPhatSinh());
                System.out.println(baocaocongno.getNoCuoi());
                BUSBaoCaoCongNo.InsertReport(baocaocongno);
            }
            loadreporttable(BUSBaoCao.GetMaBC(Integer.parseInt(thang), nam, 2));
            getChart();
        } else {
            String ma = BUSBaoCao.GetMaBC(Integer.parseInt(thang), nam, 2);
            loadreporttable(ma);
            getChart();
        }
    }
}
