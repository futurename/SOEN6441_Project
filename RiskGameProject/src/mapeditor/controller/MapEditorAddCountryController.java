package mapeditor.controller;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import mapeditor.MEMain;

public class MapEditorAddCountryController {


    @FXML
    private Text txt_setCountinent;

    @FXML
    private Label lab_AddCountryName;

    @FXML
    private Label lab_AddCountrySelectNeighbor;

    @FXML
    private TextField txf_AddCountryName;

    @FXML
    private Label lab_AddCountrytitle;

    @FXML
    private Button btn_AddCountryOK;

    @FXML
    private ComboBox<String> cbb_NeighborCountry;

    @FXML
    private Button btn_AddCountryApply;

    @FXML
    private ComboBox<String> cbb_setContinent;

    private String newCountryName;

    private String newNeighborName;

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
        cbb_NeighborCountry.getItems().clear();
        for (int i = 0; i < MEMain.arrMECountry.size(); i++) {
            cbb_NeighborCountry.getItems().add(MEMain.arrMECountry.get(i).getCountryName());
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
            if (txf_AddCountryName.getText().equals("")) {
                return false;
            } else {
                return true;
            }
        }, txf_AddCountryName.textProperty());
    }

    /**
     * Check the validation of neighbor selection.
     */
    public void neighborSelectValidation(){

        booleanBindingNeighbor = Bindings.createBooleanBinding(()->{
            if(cbb_NeighborCountry.getValue() == null||cbb_NeighborCountry.getValue().equals(txf_AddCountryName.getText())){
                return false;
            }
            else{
                return true;
            }
        },cbb_NeighborCountry.itemsProperty());
    }

    /**
     * Check the validation of continent selection.
     */
    public void continentSelectValidation(){

        booleanBindingContinent = Bindings.createBooleanBinding(()->{
            if(cbb_setContinent.getValue() == null){
                return false;
            }
            else{
                return true;
            }
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
            if (!checkIfSecondTime.contains(txf_AddCountryName.getText())) {
                //Second or more than second time.
                btn_AddCountryApply.disableProperty().bind(booleanBindingCountryName.not().or(booleanBindingNeighbor.not()));
            }

        }else {
            //The First time.
            btn_AddCountryApply.disableProperty().bind(booleanBindingCountryName.not().or(booleanBindingContinent.not()));
        }
    }

    /**
     * clickToAddCountry receive action event and add a new country instance to the program.
     * @param actionEvent
     * @throws Exception
     */
    @FXML
    public void clickToAddCountry(ActionEvent actionEvent) throws Exception{
        newCountryName = txf_AddCountryName.getText();
        newNeighborName = cbb_NeighborCountry.getValue();
        setContinent = cbb_setContinent.getValue();

        if(newCountryName.equals(newNeighborName)){
            throw new Exception("a country's neighbor should not be itself ");
        }
        if(newCountryName.equals("")){
            throw new Exception("a country must have a name");
        }

        for(int i = 0;i<MEMain.arrMECountry.size();i++){
            if(newNeighborName.equals(MEMain.arrMECountry.get(i).getCountryName())){
                MEMain.arrMECountry.get(i).setNeighbor(newCountryName);
            }
            if(newCountryName.equals(MEMain.arrMECountry.get(i).getCountryName())) {
                ISNOTANEWCOUNTRY = true;
                MEMain.arrMECountry.get(i).setNeighbor(newNeighborName);
                break;
            }
        }
        if(ISNOTANEWCOUNTRY==false){
            for(int k = 0;k<MEMain.arrMEContinent.size();k++){
                if(setContinent.equals(MEMain.arrMEContinent.get(k).getContinentName())){
                    MEMain.arrMEContinent.get(k).addCountry(newCountryName);
                    if(MEMain.arrMEContinent.get(k).getCountryNumber()>1){
                        String[] tempneighbor = new String[4];
                        tempneighbor[3] = newNeighborName;
                        MEMain.createCountry(newCountryName,tempneighbor);
                    }
                    else{
                        MEMain.createsSoloCountry(newCountryName);
                    }
                    break;
                }
            }
        }
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

