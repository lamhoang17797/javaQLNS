package Controller;

import BUS.BUSTacGia;
import DTO.DTOTacGia;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

public class FXML_InUpAuthorController implements Initializable {

    @FXML
    private JFXButton close;
    @FXML
    private JFXButton save;

    private String idauthor;
    @FXML
    private JFXTextField nameauthor;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        idauthor = BUSTacGia.Tangma();
    }

//    Nhấn nút đóng
    @FXML
    private void close_click(ActionEvent event) {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
    }
//    Nhấn nút save

    @FXML
    private void save_click(ActionEvent event) {
        DTOTacGia tacgiaDTO = new DTOTacGia();
        tacgiaDTO.setMaTG(idauthor);
        tacgiaDTO.setTenTG(nameauthor.getText().trim());
        if (!nameauthor.getText().equals("")) {
            BUSTacGia.UIAuthor(tacgiaDTO);
        } else {
            JOptionPane.showMessageDialog(null, "Tên thể loại không được để trống");
        }
        close_click(event);
    }

//    Lấy dữ liệu
    public void getData(DTOTacGia tacgia) {
        idauthor = tacgia.getMaTG().trim();
        nameauthor.setText(tacgia.getTenTG().trim());
    }
}
