package mapeditor.controller;

import javafx.beans.property.IntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import mapeditor.MEMain;
import mapeditor.model.MEContinent;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;

import static mapeditor.model.MECheckMapCorrectness.isCorrect;

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

    private String path;

    public MapEditorEditPageController() {
    }

    public void initialize()throws Exception{
        path = MEMain.OLDMAPPATH;
        if(MEMain.EDITPAGEFLAG==false) {
            readMap(path);
            MEMain.EDITPAGEFLAG=true;
        }



        ContinentNameCol.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        ContinentBonusCol.setCellValueFactory(cellData -> cellData.getValue().bonusProperty());
        CountryCol.setCellValueFactory(cellData -> cellData.getValue().countryProperty());

        ContinentTable.setItems(continentData);

        for(Iterator<MEContinent> iterator = MEMain.arrMEContinent.iterator(); iterator.hasNext();){
            continentData.add(iterator.next());
        }

    }

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
                int continentNumber = 0;
                for(int k=i+1;k<fileRead.size();k++){
                    if(!fileRead.get(k).equals("")){
                        String[] countrydata = fileRead.get(k).split(",");
                        MEMain.createCountry(countrydata[0],countrydata);
                        MEMain.arrMEContinent.get(continentNumber).addCountry(countrydata[0]);
                    }
                    else{
                        continentNumber++;
                    }
                }
            }
        }
    }

    @FXML
    public void loadTable() throws Exception{


    }


    @FXML
    public void clickToAddContinent(ActionEvent actionEvent) throws Exception{
        Stage curStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        Pane mapEditorAddContinentPane = new FXMLLoader(getClass().getResource("../views/MapEditorAddContinentView.fxml")).load();
        Scene mapEditorAddContinentScene = new Scene(mapEditorAddContinentPane,1200,900);

        curStage.setScene(mapEditorAddContinentScene);
        curStage.show();
    }
    @FXML
    public void clickToDeleteContinent(ActionEvent actionEvent) throws Exception{
        Stage curStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        Pane mapEditorDeleteContinentPane = new FXMLLoader(getClass().getResource("../views/MapEditorDeleteContinentView.fxml")).load();
        Scene mapEditorDeleteContinentScene = new Scene(mapEditorDeleteContinentPane,1200,900);

        curStage.setScene(mapEditorDeleteContinentScene);
        curStage.show();
    }

    @FXML
    public void clickToAddCountry(ActionEvent actionEvent) throws Exception{
        Stage curStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        Pane mapEditorAddCountryPane = new FXMLLoader(getClass().getResource("../views/MapEditorAddCountryView.fxml")).load();
        Scene mapEditorAddCountryScene = new Scene(mapEditorAddCountryPane,1200,900);

        curStage.setScene(mapEditorAddCountryScene);
        curStage.show();
    }

    @FXML
    public void clickToDeleteCountry(ActionEvent actionEvent) throws Exception{
        Stage curStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        Pane mapEditorDeleteCountryPane = new FXMLLoader(getClass().getResource("../views/MapEditorDeleteCountryView.fxml")).load();
        Scene mapEditorDeleteCountryScene = new Scene(mapEditorDeleteCountryPane,1200,900);

        curStage.setScene(mapEditorDeleteCountryScene);
        curStage.show();
    }

    @FXML
    public void clickToSave(ActionEvent actionEvent) throws Exception{
        Stage curStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        Pane reinforcePane = new FXMLLoader(getClass().getResource("../views/MapEditorSaveFileView.fxml")).load();
        Scene reinforceScene = new Scene(reinforcePane,1200,900);

        curStage.setScene(reinforceScene);
        curStage.show();
    }


    @FXML
    public void clickToCheck(ActionEvent actionEvent) throws Exception{
        txt_check.setText(isCorrect(MEMain.arrMECountry, MEMain.arrMEContinent));
    }
}
