package mapeditor.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import mapeditor.MEMain;


public class MapEditorEditController {

    @FXML
    private Button btn_MapSelect;

    @FXML
    private Label lab_MapPath;

    @FXML
    private TextField txf_defaultMapPath;

    @FXML
    private Text txt_OMEtitle;

    private static final String DEFAULT_PATH = "../World.map";

    public void initialize(){
        txf_defaultMapPath.setText(DEFAULT_PATH);
    }

    @FXML
    public void selectPath(ActionEvent actionEvent) throws Exception{
        MEMain.OLDMAPPATH = txf_defaultMapPath.getText();
        //need to add map path check
        Stage curStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        Pane reinforcePane = new FXMLLoader(getClass().getResource("../views/MapEditorEditPageView.fxml")).load();
        Scene reinforceScene = new Scene(reinforcePane,1200,900);

        curStage.setScene(reinforceScene);

        curStage.show();
    }
}
