package mapeditor.controller;

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

    private String newContryName;

    private String newNeighborName;

    private String setContinent;

    private boolean ISNOTANEWCOUNTRY = false;

    public void initialize(){
        cbb_NeighborCountry.getItems().clear();
        for(int i=0;i< MEMain.arrMECountry.size();i++){
            cbb_NeighborCountry.getItems().add(MEMain.arrMECountry.get(i).getCountryName());
        }
        cbb_setContinent.getItems().clear();
        for(int j=0;j<MEMain.arrMEContinent.size();j++){
            cbb_setContinent.getItems().add(MEMain.arrMEContinent.get(j).getContinentName());
        }
    }

    @FXML
    public void clickToApply(ActionEvent actionEvent)throws Exception{
        newContryName = txf_AddCountryName.getText();
        newNeighborName = cbb_NeighborCountry.getValue();
        setContinent = cbb_setContinent.getValue();

        if(newNeighborName.equals(newContryName)){
            throw new Exception("a country's neighbor should not be itself ");
        }
        if(newContryName.equals("")){
            throw new Exception("a country must have a name");
        }

        for(int i = 0;i<MEMain.arrMECountry.size();i++){
            if(newNeighborName.equals(MEMain.arrMECountry.get(i).getCountryName())){
                MEMain.arrMECountry.get(i).setNeighbor(newContryName);
            }
            if(newContryName.equals(MEMain.arrMECountry.get(i).getCountryName())) {
                ISNOTANEWCOUNTRY = true;
                MEMain.arrMECountry.get(i).setNeighbor(newNeighborName);
                break;
            }
        }
        if(ISNOTANEWCOUNTRY==false){
            for(int k = 0;k<MEMain.arrMEContinent.size();k++){
                if(setContinent.equals(MEMain.arrMEContinent.get(k).getContinentName())){
                    MEMain.arrMEContinent.get(k).addCountry(newContryName);
                    break;
                }
            }
            String[] tempneighbor = new String[4];
            tempneighbor[3] = newNeighborName;
            MEMain.createCountry(newContryName,tempneighbor);
        }
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

