package Controller;

import BUS.BUSBaoCao;
import BUS.BUSBaoCaoTon;
import BUS.BUSCTHoaDon;
import BUS.BUSCTPhieuNhap;
import BUS.BUSSach;
import DTO.DTOBaoCao;
import DTO.DTOBaoCaoTon;
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

public class FXML_BaoCaoTonController implements Initializable {

    @FXML
    private JFXTreeTableView<DTOBaoCaoTon> reporttable;

    @FXML
    private TextField year;

    @FXML
    private ComboBox<String> month;

    private NumberAxis yAxis;
    private CategoryAxis xAxis;
    private BarChart<String, Number> barChart;

    SimpleDateFormat sdf = new SimpleDateFormat("mm");
    DecimalFormat df = new DecimalFormat("###,###,###");
    String ngay;
    @FXML
    private BorderPane pane;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        loadcombo();

    }

    @FXML
    private void create(ActionEvent event) {
        LocalDate today = LocalDate.now();
        if (year.getText().trim().equals("")) {
            createreport(monthformat(today.getMonthValue()), today.getYear());
        } else {
            createreport(month.getValue().trim(), Integer.parseInt(year.getText().trim()));
        }
    }

    public void loadreporttable(String ma) {
        JFXTreeTableColumn<DTOBaoCaoTon, String> mabct = new JFXTreeTableColumn<>("STT");
        mabct.setStyle("-fx-alignment: CENTER;");
        mabct.setPrefWidth(90);
        mabct.setCellValueFactory((TreeTableColumn.CellDataFeatures<DTOBaoCaoTon, String> param) -> new SimpleObjectProperty(BUSBaoCaoTon.STT(param.getValue().getValue().getMaBC().trim(), BUSSach.GetMaSach(param.getValue().getValue().getMaSach().trim()))));

        JFXTreeTableColumn<DTOBaoCaoTon, String> mabc = new JFXTreeTableColumn<>("Mã báo cáo");
        mabc.setPrefWidth(90);
        mabc.setCellValueFactory((TreeTableColumn.CellDataFeatures<DTOBaoCaoTon, String> param) -> new SimpleObjectProperty(param.getValue().getValue().getMaBC().trim()));

        JFXTreeTableColumn<DTOBaoCaoTon, String> tensach = new JFXTreeTableColumn<>("Tên sách");
        tensach.setPrefWidth(111);
        tensach.setCellValueFactory((TreeTableColumn.CellDataFeatures<DTOBaoCaoTon, String> param) -> new SimpleObjectProperty(param.getValue().getValue().getMaSach().trim()));

        JFXTreeTableColumn<DTOBaoCaoTon, Integer> tondau = new JFXTreeTableColumn<>("Tồn đầu");
        tondau.setStyle("-fx-alignment: CENTER;");
        tondau.setPrefWidth(90);
        tondau.setCellValueFactory((TreeTableColumn.CellDataFeatures<DTOBaoCaoTon, Integer> param) -> new SimpleObjectProperty(df.format(param.getValue().getValue().getTonDau())));

        JFXTreeTableColumn<DTOBaoCaoTon, Integer> tonnhap = new JFXTreeTableColumn<>("SL Nhập");
        tonnhap.setStyle("-fx-alignment: CENTER;");
        tonnhap.setPrefWidth(90);
        tonnhap.setCellValueFactory((TreeTableColumn.CellDataFeatures<DTOBaoCaoTon, Integer> param) -> new SimpleObjectProperty(df.format(param.getValue().getValue().getTonNhap())));

        JFXTreeTableColumn<DTOBaoCaoTon, Integer> tonban = new JFXTreeTableColumn<>("Sl Bán");
        tonban.setStyle("-fx-alignment: CENTER;");
        tonban.setPrefWidth(90);
        tonban.setCellValueFactory((TreeTableColumn.CellDataFeatures<DTOBaoCaoTon, Integer> param) -> new SimpleObjectProperty(df.format(param.getValue().getValue().getTonBan())));

        JFXTreeTableColumn<DTOBaoCaoTon, Integer> toncuoi = new JFXTreeTableColumn<>("Tồn cuối");
        toncuoi.setStyle("-fx-alignment: CENTER;");
        toncuoi.setPrefWidth(90);
        toncuoi.setCellValueFactory((TreeTableColumn.CellDataFeatures<DTOBaoCaoTon, Integer> param) -> new SimpleObjectProperty(df.format(param.getValue().getValue().getTonCuoi())));

        ObservableList<DTOBaoCaoTon> baocaoton;
        baocaoton = BUSBaoCaoTon.ReportAlL(ma);
        final TreeItem<DTOBaoCaoTon> root = new RecursiveTreeItem<>(baocaoton, RecursiveTreeObject::getChildren);
        reporttable.getColumns().setAll(mabct, mabc, tensach, tondau, tonnhap, tonban, toncuoi);
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
        xAxis.setLabel("Sách");
        yAxis.setLabel("Số lượng");
        barChart.setTitle("Báo cáo tồn");
        XYChart.Series<String, Number> series1 = new XYChart.Series<>();
        XYChart.Series tondau = new XYChart.Series<>();
        XYChart.Series tonnhap = new XYChart.Series<>();
        XYChart.Series tonban = new XYChart.Series<>();
        XYChart.Series toncuoi = new XYChart.Series<>();
        tondau.setName("Tồn đầu");
        tonnhap.setName("SL Nhập");
        tonban.setName("SL Bán");
        toncuoi.setName("Tồn cuối");
        for (int i = 0; i < reporttable.getCurrentItemsCount(); i++) {
            tondau.getData().add(new XYChart.Data(reporttable.getColumns().get(2).getCellData(i).toString(), Integer.parseInt(reporttable.getColumns().get(3).getCellData(i).toString())));
            tonnhap.getData().add(new XYChart.Data(reporttable.getColumns().get(2).getCellData(i).toString(), Integer.parseInt(reporttable.getColumns().get(4).getCellData(i).toString())));
            tonban.getData().add(new XYChart.Data(reporttable.getColumns().get(2).getCellData(i).toString(), Integer.parseInt(reporttable.getColumns().get(5).getCellData(i).toString())));
            toncuoi.getData().add(new XYChart.Data(reporttable.getColumns().get(2).getCellData(i).toString(), Integer.parseInt(reporttable.getColumns().get(6).getCellData(i).toString())));
        }
        barChart.getData().addAll(tondau, tonnhap, tonban, toncuoi);
        barChart.setPrefSize(reporttable.getCurrentItemsCount() * 100, 490);
        ScrollPane scrollpane = new ScrollPane(barChart);
        pane.setCenter(scrollpane);
    }

    public void createreport(String thang, int nam) {
        boolean check = BUSBaoCao.checkMaBC(Integer.parseInt(thang), nam, 0);
        if (check == false) {
            ngay = nam + "-" + thang;
            DTOBaoCao baocao = new DTOBaoCao(BUSBaoCao.Tangma(), Integer.parseInt(thang), nam, 0);
            BUSBaoCao.InsertBC(baocao);
            ArrayList<String> masach = BUSSach.IdBook();
            for (int i = 0; i < masach.size(); i++) {
                DTOBaoCaoTon baocaoton = new DTOBaoCaoTon();
                baocaoton.setMaBCT(BUSBaoCaoTon.Tangma());
                baocaoton.setMaBC(BUSBaoCao.GetMaBC(Integer.parseInt(thang), nam, 0));
                baocaoton.setMaSach(masach.get(i));
                baocaoton.setTonCuoi(BUSSach.GetSLT("1", masach.get(i)));
                baocaoton.setTonNhap(BUSCTPhieuNhap.TongSLN(ngay, masach.get(i)));
                baocaoton.setTonBan(BUSCTHoaDon.TongSLB(ngay, masach.get(i)));
                baocaoton.setTonDau(BUSSach.GetSLT("1", masach.get(i)) - (BUSCTPhieuNhap.TongSLN(ngay, masach.get(i)) - BUSCTHoaDon.TongSLB(ngay, masach.get(i))));
                BUSBaoCaoTon.InsertReport(baocaoton);
            }
            loadreporttable(BUSBaoCao.GetMaBC(Integer.parseInt(thang), nam, 0));
            getChart();
        } else {
            String ma = BUSBaoCao.GetMaBC(Integer.parseInt(thang), nam, 0);
            loadreporttable(ma);
            getChart();
        }
    }
}
