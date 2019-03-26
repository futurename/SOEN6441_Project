package mapeditor.controller;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import mapeditor.MEMain;

/**
 * @author YQW
 * @since build1
 **/
public class MapEditorDeleteCountryController {

    @FXML
    private Button btn_deleteCountryOK;

    @FXML
    private Button btn_deleteCountryApply;

    @FXML
    private Label lab_deleteCountryTitle;

    @FXML
    private ComboBox<String> cbb_deleteCountryName;

    @FXML
    private Label lab_deleteCountryName;

    /**
     * Delete country name input
     */
    private String deleteCountryName;

    BooleanBinding booleanBinding;

    public void initialize(){
        cbb_deleteCountryName.getItems().clear();
        for(int i = 0; i< MEMain.arrMECountry.size(); i++){

            cbb_deleteCountryName.getItems().add(MEMain.arrMECountry.get(i).getCountryName());
        }
        detectSelectionValidation();
    }

    /**
     * check the validation of selection, if there is no input the apply button cannot be pressed
     */
    @FXML
    public void detectSelectionValidation() {
        booleanBinding = Bindings.createBooleanBinding(()->{
            return cbb_deleteCountryName.getValue() != null;
        },cbb_deleteCountryName.itemsProperty());
        btn_deleteCountryApply.disableProperty().bind(booleanBinding.not());
    }

    /**
     * confirm the country to be delete
     * @param actionEvent click button
     */
    @FXML
    public void clickToDeleteCountry(ActionEvent actionEvent) {
        deleteCountryName = cbb_deleteCountryName.getValue();
        MEMain.deleteCountry(deleteCountryName);
        btn_deleteCountryApply.setVisible(false);
    }

    /**
     * return to the edit page
     * @param actionEvent click button
     * @throws Exception MapEditorEditPageView.fxml not found
     */
    @FXML
    public void clickToOk(ActionEvent actionEvent) throws Exception{
        Stage curStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        Pane mapEditorEditPagePane = new FXMLLoader(getClass().getResource("../views/MapEditorEditPageView.fxml")).load();
        Scene mapEditorEditPageScene = new Scene(mapEditorEditPagePane,1200,900);

        curStage.setScene(mapEditorEditPageScene);
        curStage.show();
    }
}
