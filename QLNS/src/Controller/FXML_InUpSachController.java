package Controller;

import BUS.BUSNhaXB;
import BUS.BUSSach;
import BUS.BUSTacGia;
import BUS.BUSTheLoai;
import DTO.DTONhaXB;
import DTO.DTOSach;
import DTO.DTOTacGia;
import DTO.DTOTheLoai;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

public class FXML_InUpSachController implements Initializable {

    @FXML
    private JFXTextField idbook, buy, sell, namebook;
    @FXML
    private JFXComboBox<String> kind, author, publish;
    @FXML
    private ImageView bookimage;
    @FXML
    private TextArea decripbook;

    String path = "null";
    @FXML
    private Label sellvalidation;
    @FXML
    private Label buyvalidation;
    @FXML
    private Button addkind;
    @FXML
    private Button addauthor;
    @FXML
    private Button addpublish;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        idbook.setText(BUSSach.Tangma().trim());
        loadcomboboxkind();
        loadcomboboxauthor();
        loadcomboboxpublish();
    }
//    Chọn ảnh bìa

    @FXML
    private void bookimage_click(MouseEvent event) throws MalformedURLException {
        chooseimage();
    }
//    nhấn nút chọn ảnh bìa

    @FXML
    private void choose_click(ActionEvent event) throws MalformedURLException {
        chooseimage();
    }
//    nhấn nút save

    @FXML
    private void save_click(ActionEvent event) throws IOException {
        if (Integer.parseInt(buy.getText().trim()) < Integer.parseInt(sell.getText().trim())) {
            if (sellvalidation.getText().trim().equals("") || buyvalidation.getText().trim().equals("")) {
                DTOSach sachDTO = new DTOSach();
                sachDTO.setMaSach(idbook.getText());
                sachDTO.setTenSach(namebook.getText());
                sachDTO.setTheLoai(BUSTheLoai.GetMaTL((String) kind.getSelectionModel().getSelectedItem().trim()));
                sachDTO.setTacGia(BUSTacGia.GetMaTG((String) author.getSelectionModel().getSelectedItem().trim()));
                sachDTO.setNXB(BUSNhaXB.GetMaNXB((String) publish.getSelectionModel().getSelectedItem().trim()));
                sachDTO.setSLT(0);
                sachDTO.setGB(Integer.parseInt(sell.getText().trim()));
                sachDTO.setGM(Integer.parseInt(buy.getText().trim()));
                sachDTO.setMota(decripbook.getText().trim());
                sachDTO.setAnh(path);
                BUSSach.IUBook(sachDTO);
                idbook.setText(BUSSach.Tangma());
                Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                stage.close();
            } else {
                JOptionPane.showMessageDialog(null, "Giá mua và giá bán phải là số");
            }
        } else {
            JOptionPane.showMessageDialog(null, "giá mua phải nhỏ hơn giá bán");
        }
    }
//    Nhấn nút xóa

    @FXML
    private void delete_click(ActionEvent event) {
        namebook.setText("");
        sell.setText("");
        buy.setText("");
        kind.setValue("");
        author.setValue("");
        publish.setValue("");
        decripbook.setText("");
        bookimage.setImage(null);
    }
//    Nhấn nút đóng

    @FXML
    private void close_click(ActionEvent event) {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
    }
//  Kiểm tra nhập số

    @FXML
    private void buy_keypress(KeyEvent event) {
        try {
            int i = Integer.parseInt(buy.getText().trim());
            buyvalidation.setText("");
        } catch (NumberFormatException e) {
            buyvalidation.setText("nhập số!");
        }
    }
//  Kiểm tra nhập số

    @FXML
    private void sell_keypress(KeyEvent event) {
        try {
            int i = Integer.parseInt(sell.getText().trim());
            sellvalidation.setText("");
        } catch (NumberFormatException e) {
            sellvalidation.setText("nhập số!");
        }
    }
//    Combobox thể loại

    public void loadcomboboxkind() {
        kind.getItems().clear();
        ObservableList<DTOTheLoai> arr = BUSTheLoai.KindALL();
        for (int i = 0; i < arr.size(); i++) {
            kind.getItems().add(arr.get(i).getTenTL().trim());
        }
    }
//    Combobox tác giả

    public void loadcomboboxauthor() {
        author.getItems().clear();
        ObservableList<DTOTacGia> arr = BUSTacGia.AuthorALL();
        for (int i = 0; i < arr.size(); i++) {
            author.getItems().add(arr.get(i).getTenTG().trim());
        }
    }
//    Combobox nhà xuất bản

    public void loadcomboboxpublish() {
        publish.getItems().clear();
        publish.getItems().clear();
        ObservableList<DTONhaXB> arr = BUSNhaXB.PublishALL();
        for (int i = 0; i < arr.size(); i++) {
            publish.getItems().add(arr.get(i).getTenNXB().trim());
        }
    }

    public void getdata(DTOSach sach) {
        idbook.setText(sach.getMaSach().trim());
        namebook.setText(sach.getTenSach().trim());
        sell.setText(Integer.toString(sach.getGB()));
        buy.setText(Integer.toString(sach.getGM()));
        kind.getSelectionModel().select(sach.getTheLoai().trim());
        author.getSelectionModel().select(sach.getTacGia().trim());
        publish.getSelectionModel().select(sach.getNXB().trim());
        decripbook.setText(sach.getMota().trim());
        if (!sach.getAnh().trim().equals("null")) {
            Image image = new Image(sach.getAnh().trim(), 160, 200, true, true);
            bookimage.setImage(image);
            bookimage.setFitWidth(160);
            bookimage.setFitHeight(200);
            bookimage.setPreserveRatio(true);
            path = sach.getAnh().trim();
        } else {
            bookimage.setImage(null);
        }
    }

    public void chooseimage() throws MalformedURLException {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Open File");
        File file = chooser.showOpenDialog(new Stage());
        if (file != null) {
            path = file.toURI().toURL().toString();
            Image image = new Image(path, bookimage.getFitWidth(), bookimage.getFitHeight(), true, true);
            bookimage.setImage(image);
        } else {
            JOptionPane.showMessageDialog(null, "Chưa chọn ảnh");
        }
    }

    @FXML
    private void addkind_click(ActionEvent event) throws IOException {
        loadwindow("/GUI/FXML_InUpKind.fxml","Nhập thể loại");
        
    }

    @FXML
    private void addauthor_click(ActionEvent event) throws IOException {
        loadwindow("/GUI/FXML_InUpAuthor.fxml","Thông tin tác giả");
    }

    @FXML
    private void addpublish_click(ActionEvent event) throws IOException {
        loadwindow("/GUI/FXML_InUpPuplish.fxml","Thông tin nhà xuất bản");
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
            loadcomboboxauthor();
            loadcomboboxkind();
            loadcomboboxpublish();
        });
    }

}
