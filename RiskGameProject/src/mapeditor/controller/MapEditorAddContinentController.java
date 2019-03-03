package mapeditor.controller;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
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
    private Text txt_addContinentTitle;

    @FXML
    private Text txt_continentName;

    @FXML
    private Text txt_continentBonus;

    @FXML
    private TextField txf_continentName;

    @FXML
    private TextField txf_continentBonus;

    @FXML
    private Button btn_addContinentApply;

    @FXML
    private Button btn_ok;

    private String DEFAULTCONTINENTNAME = "";

    private String newContinentName;

    private int newContinentBonus = -1;

    BooleanBinding booleanBindingName ;

    BooleanBinding booleanBindingBonus ;

    public void initialize(){
        newContinentName = DEFAULTCONTINENTNAME;
        detectInputValidation();
    }

    /**
     * press apply button to confirm changes
     * @param actionEvent
     * @throws Exception
     */
    public void clickToAddContinent(ActionEvent actionEvent)throws Exception{
        newContinentName = txf_continentName.getText();
        newContinentBonus = Integer.parseInt(txf_continentBonus.getText());
        for(int i = 0; i< MEMain.arrMEContinent.size(); i++) {
            if (newContinentName.equals(MEMain.arrMEContinent.get(i).getContinentName())) {
                throw new Exception("already exist this continent");
            }
        }
        MEMain.createContinent(newContinentName,newContinentBonus);
    }

    /**
     * check whether the input continent name has already exist in the map
     * @param newContinentName
     * @return
     */
    private boolean checkNameInput(String newContinentName){
        for(int i=0;i<MEMain.arrMEContinent.size();i++){
            if(MEMain.arrMEContinent.get(i).getContinentName().equals(newContinentName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * if the input is invalid then the apply button cannot be pressed
     */
    @FXML
    public void detectInputValidation(){

        booleanBindingName = Bindings.createBooleanBinding(()->{
            if(txf_continentName.getText().equals("")||checkNameInput(txf_continentName.getText())){
                return false;
            }
            else{
                return true;
            }
        },txf_continentName.textProperty());

        booleanBindingBonus = Bindings.createBooleanBinding(()->{
            if(txf_continentBonus.getText().equals("")){
                return false;
            }
            else{
                return true;
            }
        },txf_continentBonus.textProperty());

        btn_addContinentApply.disableProperty().bind(booleanBindingBonus.not().or(booleanBindingName.not()));
    }

    /**
     * return to the edit page
     * @param actionEvent
     * @throws Exception
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
