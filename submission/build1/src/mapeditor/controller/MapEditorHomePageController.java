package mapeditor.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import mapeditor.MEMain;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * home page class
 */
public class MapEditorHomePageController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btn_CreateNewMap"
    private Button btn_createNewMap; // Value injected by FXMLLoader

    @FXML // fx:id="txt_Title"
    private Text txt_title; // Value injected by FXMLLoader

    @FXML // fx:id="btn_EditOldMap"
    private Button btn_editOldMap; // Value injected by FXMLLoader

    @FXML
    private Button btn_playGame;

    /**
     * This method is called by the FXMLLoader when initialization is complete
     */
    @FXML
    void initialize() {
        assert btn_createNewMap != null : "fx:id=\"btn_CreateNewMap\" was not injected: check your FXML file 'MapEditorHomePageView.fxml'.";
        assert txt_title != null : "fx:id=\"txt_Title\" was not injected: check your FXML file 'MapEditorHomePageView.fxml'.";
        assert btn_editOldMap != null : "fx:id=\"btn_EditOldMap\" was not injected: check your FXML file 'MapEditorHomePageView.fxml'.";

    }

    /**
     * this method control the edit button to the select map path page
     *
     * @param actionEvent click button
     * @throws Exception MapEditorEditView.fxml not found
     */
    @FXML
    public void clickToEdit(ActionEvent actionEvent) throws Exception {
        Stage curStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        Pane mapEditorEditPane = new FXMLLoader(getClass().getResource("../views/MapEditorEditView.fxml")).load();
        Scene mapEditorEditScene = new Scene(mapEditorEditPane, 1200, 900);

        curStage.setScene(mapEditorEditScene);
        curStage.show();
    }

    /**
     * this method control the create button to the edit page
     *
     * @param actionEvent click button
     * @throws Exception MapEditorEditPageView.fxml not found
     */
    @FXML
    public void clickToCreate(ActionEvent actionEvent) throws Exception {
        MEMain.EDITPAGEFLAG = true;
        Stage curStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        Pane mapEditorEditPagePane = new FXMLLoader(getClass().getResource("../views/MapEditorEditPageView.fxml")).load();
        Scene mapEditorEditPageScene = new Scene(mapEditorEditPagePane, 1200, 900);

        curStage.setScene(mapEditorEditPageScene);
        curStage.show();
    }

    /**
     * This method switch to game page
     *
     * @param actionEvent click button
     * @throws IOException StartView.fxml not found
     */
    public void clickPlayGame(ActionEvent actionEvent) throws IOException {
        Stage curStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        Pane startViewPane = FXMLLoader.load(getClass().getResource("../../riskGame/view/StartView.fxml"));
        Scene startViewScene = new Scene(startViewPane, 1200, 900);

        curStage.setScene(startViewScene);
        curStage.show();
    }
}





