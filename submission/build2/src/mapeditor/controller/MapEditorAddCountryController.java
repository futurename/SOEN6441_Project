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
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import mapeditor.MEMain;

/**
 * @author YW
 * @since build1
 **/
public class MapEditorAddCountryController {


    @FXML
    private Text txt_setContinent;

    @FXML
    private Label lab_addCountryName;

    @FXML
    private Label lab_addCountrySelectNeighbor;

    @FXML
    private TextField txf_addCountryName;

    @FXML
    private Label lab_addCountryTitle;

    @FXML
    private Button btn_addCountryOK;

    @FXML
    private ComboBox<String> cbb_neighborCountry;

    @FXML
    private Button btn_addCountryApply;

    @FXML
    private ComboBox<String> cbb_setContinent;

    /**
     * Country name input
     */
    private String newCountryName;

    /**
     * Neighbor name input
     */
    private String newNeighborName;

    /**
     * Set continent input
     */
    private String setContinent;

    private boolean ISNOTANEWCOUNTRY = false;

    //booleanBindingCountryName is used to monitor the input of txf_AddCountryName.
    BooleanBinding booleanBindingCountryName;
    //booleanBindingNeighbor is used to monitor thw selection of cbb_NeighborCountry.
    BooleanBinding booleanBindingNeighbor;
    //booleanBindingBelongingContinent is used to monitor thw selection of cbb_setContinent.
    BooleanBinding booleanBindingContinent;


    public void initialize() {

        CheckAddCountryPageInput();
        cbb_neighborCountry.getItems().clear();
        for (int i = 0; i < MEMain.arrMECountry.size(); i++) {
            cbb_neighborCountry.getItems().add(MEMain.arrMECountry.get(i).getCountryName());
        }
        cbb_setContinent.getItems().clear();
        for (int j = 0; j < MEMain.arrMEContinent.size(); j++) {
            cbb_setContinent.getItems().add(MEMain.arrMEContinent.get(j).getContinentName());
        }
    }

    /**
     * Check the validation of country name.
     */
    public void CountryNameValidation() {

        booleanBindingCountryName = Bindings.createBooleanBinding(() -> {
            return !txf_addCountryName.getText().equals("");
        }, txf_addCountryName.textProperty());
    }

    /**
     * Check the validation of neighbor selection.
     */
    public void neighborSelectValidation(){

        booleanBindingNeighbor = Bindings.createBooleanBinding(()->{
            return cbb_neighborCountry.getValue() != null && !cbb_neighborCountry.getValue().equals(txf_addCountryName.getText());
        },cbb_neighborCountry.itemsProperty());
    }

    /**
     * Check the validation of continent selection.
     */
    public void continentSelectValidation(){

        booleanBindingContinent = Bindings.createBooleanBinding(()->{
            return cbb_setContinent.getValue() != null;
        },cbb_setContinent.itemsProperty());
    }

    /**
     * This method is for ensure the validation of user's input.
     */
    @FXML
    public void CheckAddCountryPageInput(){

        CountryNameValidation();
        neighborSelectValidation();
        continentSelectValidation();

        if(!MEMain.arrMECountry.isEmpty()){

            String checkIfSecondTime = MEMain.arrMECountry.toString();
            checkIfSecondTime.replaceAll("\\[", "");
            checkIfSecondTime.replaceAll("\\]", "");

            //If it is the first time to add this country.
            if (!checkIfSecondTime.contains(txf_addCountryName.getText())) {
                //Second or more than second time.
                btn_addCountryApply.disableProperty().bind(booleanBindingCountryName.not().or(booleanBindingNeighbor.not()));
            }

        }else {
            //The First time.
            btn_addCountryApply.disableProperty().bind(booleanBindingCountryName.not().or(booleanBindingContinent.not()));
        }
    }

    /**
     * clickToAddCountry receive action event and add a new country instance to the program.
     * @param actionEvent click button
     * @throws Exception file not found
     */
    @FXML
    public void clickToAddCountry(ActionEvent actionEvent) throws Exception{
        newCountryName = txf_addCountryName.getText();
        newNeighborName = cbb_neighborCountry.getValue();
        setContinent = cbb_setContinent.getValue();

        if(newCountryName.equals(newNeighborName)){
            throw new Exception("a country's neighbor should not be itself ");
        }
        if(newCountryName.equals("")){
            throw new Exception("a country must have a name");
        }

        for(int i = 0;i<MEMain.arrMECountry.size();i++){
            if(MEMain.arrMECountry.get(i).getCountryName().equals(newNeighborName)){
                boolean alreadyHaveRelationship1 = false;
                for(int j=0;j<MEMain.arrMECountry.get(i).getNeighborName().size();j++){
                    if(MEMain.arrMECountry.get(i).getNeighborName().get(j).equals(newCountryName)){
                        alreadyHaveRelationship1=true;
                        break;
                    }
                }
                if(alreadyHaveRelationship1==false) {
                    MEMain.arrMECountry.get(i).setNeighbor(newCountryName);
                }
            }
            if(newCountryName.equals(MEMain.arrMECountry.get(i).getCountryName())) {

                boolean alreadyHaveRelationship2 = false;
                for(int k = 0;k<MEMain.arrMECountry.get(i).getNeighborName().size();k++){
                    if(MEMain.arrMECountry.get(i).getNeighborName().get(k).equals(newNeighborName)){
                        alreadyHaveRelationship2 = true;
                    }
                }
                if(alreadyHaveRelationship2==false){
                    MEMain.arrMECountry.get(i).setNeighbor(newNeighborName);
                }
                ISNOTANEWCOUNTRY = true;
            }
        }
        if(ISNOTANEWCOUNTRY==false){
            for(int k = 0;k<MEMain.arrMEContinent.size();k++){
                if(setContinent.equals(MEMain.arrMEContinent.get(k).getContinentName())){
                    if(MEMain.arrMECountry.size()>=1){
                        String[] tempneighbor = new String[5];
                        tempneighbor[4] = newNeighborName;
                        MEMain.createCountry(newCountryName,tempneighbor);
                    }
                    else{
                        MEMain.createsSoloCountry(newCountryName);
                    }
                    MEMain.arrMEContinent.get(k).addCountry(newCountryName);
                    break;
                }
            }
        }
        btn_addCountryApply.setVisible(false);
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

