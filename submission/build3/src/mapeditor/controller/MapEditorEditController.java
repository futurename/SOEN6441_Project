package mapeditor.controller;

import javafx.beans.binding.BooleanBinding;
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
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import mapeditor.MEMain;
import mapeditor.model.MapObject;

import java.io.File;
import java.io.IOException;

/**
 * this class is to select old map from folder
 * @author YW
 * @since build1
 **/
public class MapEditorEditController {

    @FXML
    private Button btn_mapSelect;

    @FXML
    private Button btn_returnToHomePage;

    @FXML
    private Label lab_mapPath;

    @FXML
    private TextField txf_defaultMapPath;

    @FXML
    private Text txt_OMEtitle;

    @FXML
    private Button btn_confirmMapSelection;

    BooleanBinding booleanBinding;

    private static final String DEFAULT_PATH = "maps/World.map";

    public void initialize() {
        txf_defaultMapPath.setText(DEFAULT_PATH);
    }


    /**
     * select map file path
     *
     * @param actionEvent click button
     * @throws Exception path not found
     */
    @FXML
    public void selectPath(ActionEvent actionEvent) throws Exception {
        Stage fileStage = null;

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select map file");

        fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Map files(*.map)", "*.map");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(fileStage);
        if(file.getAbsolutePath()!=null) {
            txf_defaultMapPath.setText(file.getAbsolutePath());
        }
    }

    /**
     * return to homepage
     *
     * @param actionEvent click return button
     * @throws Exception MapEditorHomePageView.fxml not found
     */
    @FXML
    public void clickToReturn(ActionEvent actionEvent) throws Exception {
        Stage curStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        Pane mapEditorHomePagePane = new FXMLLoader(getClass().getResource("../views/MapEditorHomePageView.fxml")).load();
        Scene mapEditorHomePageScene = new Scene(mapEditorHomePagePane, 1200, 900);

        curStage.setScene(mapEditorHomePageScene);
        curStage.show();
    }

    /**
     * confirm map file path check map format and jump to edit page
     *
     * @param actionEvent onClick event for confirming map file selection
     * @throws IOException MapEditorEditPageView.fxml not found
     */
    public void onClickConfirmMapSelection(ActionEvent actionEvent) throws IOException {
        MEMain.OLDMAPPATH = txf_defaultMapPath.getText();
        MapObject mapObject = new MapObject();
        boolean checkmapformatresult = mapObject.mapFormatCheck(MEMain.OLDMAPPATH);
        boolean checkmapcontentresult = mapObject.mapContentCheck(MEMain.OLDMAPPATH);

        if(checkmapformatresult == false) {
            System.out.println(checkmapformatresult);
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("format error");
            alert.showAndWait();
            return;
        }
        if(checkmapcontentresult == false) {
            System.out.println(checkmapformatresult);
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("content error");
            alert.showAndWait();
            return;
        }
        Stage curStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        Pane mapEditorEditPagePane = new FXMLLoader(getClass().getResource("../views/MapEditorEditPageView.fxml")).load();
        Scene mapEditorEditPageScene = new Scene(mapEditorEditPagePane, 1200, 900);

        curStage.setScene(mapEditorEditPageScene);
        curStage.show();
    }
}
