package Controller;

import BUS.BUSBaoCao;
import BUS.BUSBaoCaoDoanhThu;
import BUS.BUSHoaDon;
import BUS.BUSPhieuNhap;
import DTO.DTOBaoCao;
import DTO.DTOBaoCaoDoanhThu;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
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
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.layout.BorderPane;

public class FXML_BaoCaoDoanhThuController implements Initializable {

    @FXML
    private JFXTreeTableView<DTOBaoCaoDoanhThu> reporttable;

    @FXML
    private TextField year;

    @FXML
    private BorderPane pane;

    private NumberAxis yAxis;
    private CategoryAxis xAxis;
    private BarChart<String, Number> barChart;

    SimpleDateFormat sdf = new SimpleDateFormat("mm");
    DecimalFormat df = new DecimalFormat("###,###,###");

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void create(ActionEvent event) {
        LocalDate today = LocalDate.now();
        if (year.getText().trim().equals("")) {
            createreport(today.getYear());
        } else {
            createreport(Integer.parseInt(year.getText().trim()));
        }
    }

    public void loadreporttable(String ma) {
        JFXTreeTableColumn<DTOBaoCaoDoanhThu, String> mabcdt = new JFXTreeTableColumn<>("STT");
        mabcdt.setStyle("-fx-alignment: CENTER;");
        mabcdt.setPrefWidth(90);
        mabcdt.setCellValueFactory((TreeTableColumn.CellDataFeatures<DTOBaoCaoDoanhThu, String> param) -> new SimpleObjectProperty(BUSBaoCaoDoanhThu.STT(param.getValue().getValue().getMaBC().trim(), param.getValue().getValue().getThang())));

        JFXTreeTableColumn<DTOBaoCaoDoanhThu, String> mabc = new JFXTreeTableColumn<>("Mã báo cáo");
        mabc.setPrefWidth(90);
        mabc.setCellValueFactory((TreeTableColumn.CellDataFeatures<DTOBaoCaoDoanhThu, String> param) -> new SimpleObjectProperty(param.getValue().getValue().getMaBC().trim()));

        JFXTreeTableColumn<DTOBaoCaoDoanhThu, String> thang = new JFXTreeTableColumn<>("Tháng");
        thang.setPrefWidth(111);
        thang.setCellValueFactory((TreeTableColumn.CellDataFeatures<DTOBaoCaoDoanhThu, String> param) -> new SimpleObjectProperty(param.getValue().getValue().getThang()));

        JFXTreeTableColumn<DTOBaoCaoDoanhThu, Integer> nhap = new JFXTreeTableColumn<>("Tiền nhập");
        nhap.setStyle("-fx-alignment: CENTER;");
        nhap.setPrefWidth(90);
        nhap.setCellValueFactory((TreeTableColumn.CellDataFeatures<DTOBaoCaoDoanhThu, Integer> param) -> new SimpleObjectProperty(param.getValue().getValue().getNhap()));

        JFXTreeTableColumn<DTOBaoCaoDoanhThu, Integer> xuat = new JFXTreeTableColumn<>("Tiền bán");
        xuat.setStyle("-fx-alignment: CENTER;");
        xuat.setPrefWidth(90);
        xuat.setCellValueFactory((TreeTableColumn.CellDataFeatures<DTOBaoCaoDoanhThu, Integer> param) -> new SimpleObjectProperty(param.getValue().getValue().getXuat()));

        ObservableList<DTOBaoCaoDoanhThu> baocaodoanhthu;
        baocaodoanhthu = BUSBaoCaoDoanhThu.ReportAlL(ma);
        final TreeItem<DTOBaoCaoDoanhThu> root = new RecursiveTreeItem<>(baocaodoanhthu, RecursiveTreeObject::getChildren);
        reporttable.getColumns().setAll(mabcdt, mabc, thang, nhap, xuat);
        reporttable.setRoot(root);
        reporttable.setShowRoot(false);
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
        xAxis.setLabel("Tháng");
        yAxis.setLabel("Số lượng");
        barChart.setTitle("Báo cáo doanh thu");
        XYChart.Series<String, Number> series1 = new XYChart.Series<>();
        XYChart.Series nhap = new XYChart.Series<>();
        XYChart.Series xuat = new XYChart.Series<>();
        XYChart.Series tonban = new XYChart.Series<>();
        XYChart.Series toncuoi = new XYChart.Series<>();
        nhap.setName("Tiền nhập");
        xuat.setName("Tiền xuất");
        for (int i = 0; i < reporttable.getCurrentItemsCount(); i++) {
            nhap.getData().add(new XYChart.Data(reporttable.getColumns().get(2).getCellData(i).toString(), Integer.parseInt(reporttable.getColumns().get(3).getCellData(i).toString())));
            xuat.getData().add(new XYChart.Data(reporttable.getColumns().get(2).getCellData(i).toString(), Integer.parseInt(reporttable.getColumns().get(4).getCellData(i).toString())));
        }
        barChart.getData().addAll(nhap, xuat);
        barChart.setPrefSize(reporttable.getCurrentItemsCount() * 100, 490);
        ScrollPane scrollpane = new ScrollPane(barChart);
        pane.setCenter(scrollpane);
    }

    public void createreport(int nam) {
        boolean check = BUSBaoCao.checkMaBC(0, nam, 1);
        if (check == false) {
            DTOBaoCao baocao = new DTOBaoCao(BUSBaoCao.Tangma(), 0, nam, 1);
            BUSBaoCao.InsertBC(baocao);
            for (int i = 1; i <= 12; i++) {
                DTOBaoCaoDoanhThu baocaodoanhthu = new DTOBaoCaoDoanhThu();
                baocaodoanhthu.setMaBCDT(BUSBaoCaoDoanhThu.Tangma());
                baocaodoanhthu.setMaBC(BUSBaoCao.GetMaBC(0, nam, 1));
                baocaodoanhthu.setThang(i);
                baocaodoanhthu.setNhap(BUSPhieuNhap.GetTT(monthformat(i), nam));
                baocaodoanhthu.setXuat(BUSHoaDon.GetTT(monthformat(i),nam));
                BUSBaoCaoDoanhThu.InsertReport(baocaodoanhthu);
            }
            loadreporttable(BUSBaoCao.GetMaBC(0, nam, 1));
            getChart();
        } else {
            String ma = BUSBaoCao.GetMaBC(0, nam, 1);
            for (int i = 1; i <= 12; i++) {
                DTOBaoCaoDoanhThu baocaodoanhthu = new DTOBaoCaoDoanhThu();
                baocaodoanhthu.setMaBCDT(BUSBaoCaoDoanhThu.GetMaBCDT(BUSBaoCao.GetMaBC(0, nam, 1), i));
                baocaodoanhthu.setMaBC(BUSBaoCao.GetMaBC(0, nam, 1));
                baocaodoanhthu.setThang(i);
                baocaodoanhthu.setNhap(BUSPhieuNhap.GetTT(monthformat(i), nam));
                baocaodoanhthu.setXuat(BUSHoaDon.GetTT(monthformat(i), nam));
                BUSBaoCaoDoanhThu.UpdateReport(baocaodoanhthu);
            }
            loadreporttable(BUSBaoCao.GetMaBC(0, nam, 1));
            getChart();
        }
    }
}
