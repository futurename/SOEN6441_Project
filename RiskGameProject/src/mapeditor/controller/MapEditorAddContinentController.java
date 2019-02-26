package mapeditor.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import mapeditor.MEMain;


public class MapEditorAddContinentController {

    @FXML
    private Text txt_addContinenttitle;

    @FXML
    private Text txt_continentName;

    @FXML
    private Text txt_continentBonus;

    @FXML
    private TextField txf_continentName;

    @FXML
    private TextField txf_continentBonus;

    @FXML
    private Button btn_apply;

    @FXML
    private Button btn_ok;

    private String newContinentName;

    private int newContinentBonus;

    public void clickToApply(ActionEvent actionEvent)throws Exception{
        newContinentName = txf_continentName.getText();
        newContinentBonus = Integer.parseInt(txf_continentBonus.getText());
        for(int i = 0; i< MEMain.arrMEContinent.size(); i++) {
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
