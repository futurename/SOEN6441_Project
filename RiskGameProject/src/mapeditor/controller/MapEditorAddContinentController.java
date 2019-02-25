package mapeditor.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import mapeditor.MEMain;

public class MapEditorAddContinentController {

    @FXML
    private TextField txf_ContinentBonus;

    @FXML
    private Button btn_AddContinentOK;

    @FXML
    private Label lab_AddContinenttitle;

    @FXML
    private Label lab_AddContinentName;

    @FXML
    private TextField txf_AddContinentName;

    @FXML
    private Label lab_ContinentBonus;

    @FXML
    private Button btn_AddContinentApply;

    private String newContinentName;

    private int newContinentBonus;

    public void initialize()throws Exception{
        newContinentName = txf_AddContinentName.getText();
        newContinentBonus = Integer.parseInt(txf_ContinentBonus.getText());
    }

    @FXML
    public void clickToApply(ActionEvent actionEvent) throws Exception{
        for(int i=0;i<MEMain.arrMEContinent.size();i++) {
            if (newContinentName.equals(MEMain.arrMEContinent.get(i).getContinentName())) {
                throw new Exception("already exist this continent");
            }
        }
        MEMain.createContinent(newContinentName,newContinentBonus);
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
