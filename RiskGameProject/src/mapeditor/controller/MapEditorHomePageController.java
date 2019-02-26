package mapeditor.controller;

    /**
     * Sample Skeleton for 'MapEditorHomePageView.fxml' Controller Class
     */

import javafx.event.ActionEvent;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MapEditorHomePageController {

        @FXML // ResourceBundle that was given to the FXMLLoader
        private ResourceBundle resources;

        @FXML // URL location of the FXML file that was given to the FXMLLoader
        private URL location;

        @FXML // fx:id="btn_CreateNewMap"
        private Button btn_CreateNewMap; // Value injected by FXMLLoader

        @FXML // fx:id="txt_Title"
        private Text txt_Title; // Value injected by FXMLLoader

        @FXML // fx:id="btn_EditOldMap"
        private Button btn_EditOldMap; // Value injected by FXMLLoader

        @FXML // This method is called by the FXMLLoader when initialization is complete
        void initialize() {
            assert btn_CreateNewMap != null : "fx:id=\"btn_CreateNewMap\" was not injected: check your FXML file 'MapEditorHomePageView.fxml'.";
            assert txt_Title != null : "fx:id=\"txt_Title\" was not injected: check your FXML file 'MapEditorHomePageView.fxml'.";
            assert btn_EditOldMap != null : "fx:id=\"btn_EditOldMap\" was not injected: check your FXML file 'MapEditorHomePageView.fxml'.";

        }
        @FXML
        public void clickToEdit(ActionEvent actionEvent) throws Exception{
            Stage curStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

            Pane reinforcePane = new FXMLLoader(getClass().getResource("../views/MapEditorEditView.fxml")).load();
            Scene reinforceScene = new Scene(reinforcePane,1200,900);

            curStage.setScene(reinforceScene);

            curStage.show();
        }
        @FXML
        public void clickToCreate(ActionEvent actionEvent) throws Exception{
            Stage curStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

            Pane reinforcePane = new FXMLLoader(getClass().getResource("../views/MapEditorCreatePageView.fxml")).load();
            Scene reinforceScene = new Scene(reinforcePane,1200,900);

            curStage.setScene(reinforceScene);

            curStage.show();
        }
    }





