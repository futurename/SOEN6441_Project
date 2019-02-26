package mapeditor.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import mapeditor.MEMain;

public class MapEditorDeleteCountryController {

    @FXML
    private Button btn_DeleteCountryOK;

    @FXML
    private Button btn_DeleteCountryApply;

    @FXML
    private Label lab_DeleteCountrytitle;

    @FXML
    private ComboBox<String> cbb__DeleteCountryName;

    @FXML
    private Label lab_DeleteCountryName;

    private String deleteCountryName;

    public void initialize(){
        cbb__DeleteCountryName.getItems().clear();
        for(int i = 0; i< MEMain.arrMECountry.size(); i++){
            cbb__DeleteCountryName.getItems().add(MEMain.arrMECountry.get(i).getCountryName());
        }
    }
    @FXML
    public void clickToApply(ActionEvent actionEvent) throws Exception{
        deleteCountryName = cbb__DeleteCountryName.getValue();
        MEMain.deleteCountry(deleteCountryName);
    }

    @FXML
    public void clickToOk(ActionEvent actionEvent) throws Exception{
        Stage curStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        Pane reinforcePane = new FXMLLoader(getClass().getResource("../views/MapEditorEditPageView.fxml")).load();
        Scene reinforceScene = new Scene(reinforcePane,1200,900);

        curStage.setScene(reinforceScene);

        curStage.show();
    }
}
