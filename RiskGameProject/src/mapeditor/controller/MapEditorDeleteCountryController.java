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
    private ComboBox<String> cbb_DeleteCountryName;

    @FXML
    private Label lab_DeleteCountryName;

    private String deleteCountryName;

    BooleanBinding booleanBinding ;

    public void initialize(){
        cbb_DeleteCountryName.getItems().clear();
        for(int i = 0; i< MEMain.arrMECountry.size(); i++){

            cbb_DeleteCountryName.getItems().add(MEMain.arrMECountry.get(i).getCountryName());
        }
        detectSelectionValidation();
    }

    @FXML
    public void detectSelectionValidation(){ ;
        booleanBinding = Bindings.createBooleanBinding(()->{
            if(cbb_DeleteCountryName.getValue()==null){
                return false;
            }
            else{
                return true;
            }
        },cbb_DeleteCountryName.itemsProperty());
        btn_DeleteCountryApply.disableProperty().bind(booleanBinding.not());
    }

    @FXML
    public void clickToApply(ActionEvent actionEvent) throws Exception{
        deleteCountryName = cbb_DeleteCountryName.getValue();
        MEMain.deleteCountry(deleteCountryName);
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
