package mapeditor.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import mapeditor.MEMain;

public class MapEditorSaveFileController {

    @FXML
    private Button btn_check;

    @FXML
    private Text txt_checkResult;

    @FXML
    private AnchorPane txt_saveFiletitle;

    @FXML
    private Text txt_fileName;

    @FXML
    private TextField txf_fileName;

    @FXML
    private TextField txf_mapPath;

    @FXML
    private Button btn_save;

    @FXML
    private Text txt_mapPath;

    @FXML
    public void clickToCheck(ActionEvent actionEvent) throws Exception{


        txt_checkResult.setText(String.valueOf(MEMain.CHECKRESULT));

    }

}