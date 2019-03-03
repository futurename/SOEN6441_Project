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


public class MapEditorDeleteContinentController {

    @FXML
    private Button btn_deleteContinentOK;

    @FXML
    private ComboBox<String> cbb_deleteContinentName;

    @FXML
    private Label lab_deleteContinentName;

    @FXML
    private Button btn_deleteContinentApply;

    @FXML
    private Label lab_deleteContinentTitle;

    private String selectedContinentName = "";

    BooleanBinding booleanBinding ;

    public void initialize(){

        cbb_deleteContinentName.getItems().clear();
        for(int i = 0;i< MEMain.arrMEContinent.size();i++) {
            cbb_deleteContinentName.getItems().add(MEMain.arrMEContinent.get(i).getContinentName());
        }
        detectSelectionValidation();
    }

    /**
     * check the validation of input , if there is no input then the apply button cannot be pressed
     */
    @FXML
    public void detectSelectionValidation(){ ;
        booleanBinding = Bindings.createBooleanBinding(()->{
            if(cbb_deleteContinentName.getValue()==null){
                return false;
            }
            else{
                return true;
            }
        },cbb_deleteContinentName.itemsProperty());
        btn_deleteContinentApply.disableProperty().bind(booleanBinding.not());
    }

    /**
     * confirm the continent to be delete, p.s. if the continent is deleted ,then the country in this continent will be delete as well.
     * @param actionEvent
     * @throws Exception
     */
    @FXML
    public void clickToDeleteContinent(ActionEvent actionEvent) throws Exception{
        selectedContinentName = cbb_deleteContinentName.getValue();
        MEMain.deleteContinent(selectedContinentName);
    }
    @FXML

    /**
     * return to the edit page
     */
    public void clickToOk(ActionEvent actionEvent) throws Exception{

        Stage curStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        Pane mapEditorEditPagePane = new FXMLLoader(getClass().getResource("../views/MapEditorEditPageView.fxml")).load();
        Scene mapEditorEditPageScene = new Scene(mapEditorEditPagePane,1200,900);

        curStage.setScene(mapEditorEditPageScene);
        curStage.show();

    }
}
