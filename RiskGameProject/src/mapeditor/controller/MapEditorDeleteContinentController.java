package mapeditor.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import mapeditor.MEMain;

import java.util.Collection;

public class MapEditorDeleteContinentController {

    @FXML
    private Button btn_DeleteContinentOK;

    @FXML
    private ComboBox<String> cbb_deleteContinentName;

    @FXML
    private Label lab_DeleteContinentName;

    @FXML
    private Button btn_DeleteContinentApply;

    @FXML
    private Label lab_DeleteContinenttitle;

    private String selectedContinentName;

    public void initialize(){

        cbb_deleteContinentName.getItems().clear();
        for(int i = 0;i< MEMain.arrMEContinent.size();i++) {
            cbb_deleteContinentName.getItems().add(MEMain.arrMEContinent.get(i).getContinentName());
        }

    }

    @FXML
    public void clickToDeleteContinent(ActionEvent actionEvent) throws Exception{
        selectedContinentName = cbb_deleteContinentName.getValue();
        MEMain.deleteContinent(selectedContinentName);
    }
    @FXML

    public void clickToOk(ActionEvent actionEvent) throws Exception{

        Stage curStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        Pane mapEditorEditPagePane = new FXMLLoader(getClass().getResource("../views/MapEditorEditPageView.fxml")).load();
        Scene mapEditorEditPageScene = new Scene(mapEditorEditPagePane,1200,900);

        curStage.setScene(mapEditorEditPageScene);
        curStage.show();

    }
}
