package mapeditor.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import mapeditor.MEMain;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

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

    private String path;

    public void initialize()throws Exception{
        path = MEMain.OLDMAPPATH;
        if(MEMain.EDITPAGEFLAG==false) {
            readMap(path);
            MEMain.EDITPAGEFLAG=true;
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
    public void clickToAddContinent(ActionEvent actionEvent) throws Exception{
        Stage curStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        Pane reinforcePane = new FXMLLoader(getClass().getResource("../views/MapEditorAddContinentView.fxml")).load();
        Scene reinforceScene = new Scene(reinforcePane,1200,900);

        curStage.setScene(reinforceScene);

        curStage.show();
    }
    @FXML
    public void clickToDeleteContinent(ActionEvent actionEvent) throws Exception{
        Stage curStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        Pane reinforcePane = new FXMLLoader(getClass().getResource("../views/MapEditorDeleteContinentView.fxml")).load();
        Scene reinforceScene = new Scene(reinforcePane,1200,900);

        curStage.setScene(reinforceScene);

        curStage.show();
    }

    @FXML
    public void clickToAddCountry(ActionEvent actionEvent) throws Exception{
        Stage curStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        Pane reinforcePane = new FXMLLoader(getClass().getResource("../views/MapEditorAddCountryView.fxml")).load();
        Scene reinforceScene = new Scene(reinforcePane,1200,900);

        curStage.setScene(reinforceScene);

        curStage.show();
    }

    @FXML
    public void clickToDeleteCountry(ActionEvent actionEvent) throws Exception{
        Stage curStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        Pane reinforcePane = new FXMLLoader(getClass().getResource("../views/MapEditorDeleteCountryView.fxml")).load();
        Scene reinforceScene = new Scene(reinforcePane,1200,900);

        curStage.setScene(reinforceScene);

        curStage.show();
    }

    @FXML
    public void clickToSave(ActionEvent actionEvent) throws Exception{
        Stage curStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        Pane reinforcePane = new FXMLLoader(getClass().getResource("../views/MapEditorSaveView.fxml")).load();
        Scene reinforceScene = new Scene(reinforcePane,1200,900);

        curStage.setScene(reinforceScene);

        curStage.show();
    }
}
