package mapeditor.controller;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import mapeditor.model.MECheckMapCorrectness;
import mapeditor.model.MEContinent;
import mapeditor.model.MapObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * edit page class
 */
public class MapEditorEditPageController {


    @FXML
    private Button btn_AddContinent;

    @FXML
    private Label lab_EditPagetitle;

    @FXML
    private Button btn_AddCountry;

    @FXML
    private Button btn_Save;

    @FXML
    private Button btn_DeleteCountry;

    @FXML
    private Button btn_DeleteContinent;

    @FXML
    private Button btn_check;

    @FXML
    private Button btn_returnToHomePage;

    @FXML
    private Text txt_check;

    @FXML
    private TableView<MEContinent> ContinentTable;

    @FXML
    private TableColumn<MEContinent, String> ContinentNameCol;

    @FXML
    private TableColumn<MEContinent, String> ContinentBonusCol;

    @FXML
    private TableColumn<MEContinent, String> CountryCol;


    private ObservableList<MEContinent> continentData = FXCollections.observableArrayList();

    /**
     * Old map path
     */
    public String path;

    public MapEditorEditPageController() {
    }

    public void initialize()throws Exception{
        path = MEMain.OLDMAPPATH;
        if(MEMain.EDITPAGEFLAG==false) {
            readMap(path);
            MEMain.EDITPAGEFLAG=true;
        }


        //Pass value to the corresponding table column.
        ContinentNameCol.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        ContinentBonusCol.setCellValueFactory(cellData -> cellData.getValue().bonusProperty());
        CountryCol.setCellValueFactory(cellData -> cellData.getValue().countryProperty());
        ContinentTable.setItems(continentData);

        for(Iterator<MEContinent> iterator = MEMain.arrMEContinent.iterator(); iterator.hasNext();){
            continentData.add(iterator.next());
        }

    }

    /**
     * read map from the given valid map path
     * @param path map file path
     * @throws Exception map file not found
     */
    private void readMap(String path)throws Exception{
        ArrayList<String> fileRead = new ArrayList<String>();
        try {
            File file=new File(path);
            if(file.isFile()&&file.exists()){
                InputStreamReader read = new InputStreamReader(new FileInputStream(file));
                BufferedReader bufferedReader=new BufferedReader(read);
                String lineTxt=null;
                while((lineTxt=bufferedReader.readLine())!=null){
                    fileRead.add(lineTxt);
                }
                read.close();
            }else{
                System.out.println("cannot find file");
            }
        } catch (Exception e){
            System.out.println("wrong");
            e.printStackTrace();
        }
        for(int i = 0;i<fileRead.size();i++){
            if(fileRead.get(i).equals("[Continents]")){
                for(int j=i+1;j<fileRead.size();j++){
                    if(!fileRead.get(j).equals("")){
                        MEMain.createContinent(fileRead.get(j).split("=")[0],Integer.parseInt(fileRead.get(j).split("=")[1]));
                    }
                    else{
                        break;
                    }
                }
            }
            else if(fileRead.get(i).equals("[Territories]")){
                for(int k=i+1;k<fileRead.size();k++){
                    if(!fileRead.get(k).equals("")){
                        String[] countrydata = fileRead.get(k).split(",");
                        MEMain.createCountry(countrydata[0],countrydata);
                        for(int z=0; z<MEMain.arrMEContinent.size();z++) {
                            if(MEMain.arrMEContinent.get(z).getContinentName().equals(countrydata[3]))
                                MEMain.arrMEContinent.get(z).addCountry(countrydata[0]);
                        }
                    }

                }
            }
        }
    }


    /**
     * jump to the add continent page
     * @param actionEvent click button
     * @throws Exception apEditorAddContinentView.fxml not found
     */
    @FXML
    public void clickToAddContinent(ActionEvent actionEvent) throws Exception{
        Stage curStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        Pane mapEditorAddContinentPane = new FXMLLoader(getClass().getResource("../views/MapEditorAddContinentView.fxml")).load();
        Scene mapEditorAddContinentScene = new Scene(mapEditorAddContinentPane,1200,900);

        curStage.setScene(mapEditorAddContinentScene);
        curStage.show();
    }

    /**
     * jump to the delete continent page
     * @param actionEvent click button
     * @throws Exception MapEditorDeleteContinentView.fxml not found
     */
    @FXML
    public void clickToDeleteContinent(ActionEvent actionEvent) throws Exception{
        Stage curStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        Pane mapEditorDeleteContinentPane = new FXMLLoader(getClass().getResource("../views/MapEditorDeleteContinentView.fxml")).load();
        Scene mapEditorDeleteContinentScene = new Scene(mapEditorDeleteContinentPane,1200,900);

        curStage.setScene(mapEditorDeleteContinentScene);
        curStage.show();
    }

    /**
     * jump to the add country page
     * @param actionEvent click button
     * @throws Exception MapEditorAddCountryView.fxml not found
     */
    @FXML
    public void clickToAddCountry(ActionEvent actionEvent) throws Exception{
        Stage curStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        Pane mapEditorAddCountryPane = new FXMLLoader(getClass().getResource("../views/MapEditorAddCountryView.fxml")).load();
        Scene mapEditorAddCountryScene = new Scene(mapEditorAddCountryPane,1200,900);

        curStage.setScene(mapEditorAddCountryScene);
        curStage.show();
    }

    /**
     * jump to the delete country page
     * @param actionEvent click button
     * @throws Exception apEditorDeleteCountryView.fxml not found
     */
    @FXML
    public void clickToDeleteCountry(ActionEvent actionEvent) throws Exception{
        Stage curStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        Pane mapEditorDeleteCountryPane = new FXMLLoader(getClass().getResource("../views/MapEditorDeleteCountryView.fxml")).load();
        Scene mapEditorDeleteCountryScene = new Scene(mapEditorDeleteCountryPane,1200,900);

        curStage.setScene(mapEditorDeleteCountryScene);
        curStage.show();
    }

    /**
     * jump to the save file page
     * @param actionEvent click button
     * @throws Exception MapEditorSaveFileView.fxml not found
     */
    @FXML
    public void clickToSave(ActionEvent actionEvent) throws Exception{
        Stage curStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        Pane SaveFilePane = new FXMLLoader(getClass().getResource("../views/MapEditorSaveFileView.fxml")).load();
        Scene SaveFileScene = new Scene(SaveFilePane,1200,900);

        curStage.setScene(SaveFileScene);
        curStage.show();
    }

    /**
     * return to homepage
     * @param actionEvent click button
     * @throws Exception apEditorHomePageView.fxml not found
     */
    @FXML
    public void clickToReturn(ActionEvent actionEvent) throws Exception{

        MEMain.arrMEContinent.clear();
        MEMain.arrMECountry.clear();
        MEMain.OLDMAPPATH = "";
        MEMain.EDITPAGEFLAG = false;
        Stage curStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        Pane returnToHomePagePane = new FXMLLoader(getClass().getResource("../views/MapEditorHomePageView.fxml")).load();
        Scene returnToHomePageScene = new Scene(returnToHomePagePane,1200,900);

        curStage.setScene(returnToHomePageScene);
        curStage.show();
    }

    /**
     * check whether the loading map is correct or not
     * @param actionEvent click button
     * @throws Exception file not found
     */
    @FXML
    public void clickToCheck(ActionEvent actionEvent) throws Exception{
        MapObject check = new MapObject();
        check.checkCorrectness(path);
        txt_check.setText(check.errorMsg.toString());
    }
}
