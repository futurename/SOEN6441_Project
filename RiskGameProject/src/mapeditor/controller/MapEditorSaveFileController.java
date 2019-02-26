package mapeditor.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import mapeditor.MEMain;
import mapeditor.model.MEContinent;
import mapeditor.model.MECountry;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import static mapeditor.model.MECheckMapCorrectness.isCorrect;

public class MapEditorSaveFileController {

    @FXML
    private Button btn_check;

    @FXML
    private Text txt_checkResult;

    @FXML
    private AnchorPane txt_saveFiletitle;

    @FXML
    private Text txt_fileName;

    @FXML
    private TextField txf_fileName;

    @FXML
    private TextField txf_mapPath;

    @FXML
    private Button btn_save;

    @FXML
    private Text txt_mapPath;

    @FXML
    private Button btn_return;

    @FXML
    public void clickToReturn(ActionEvent actionEvent) throws Exception{
        MEMain.EDITPAGEFLAG = true;
        Stage curStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        Pane mapEditorEditPagePane = new FXMLLoader(getClass().getResource("../views/MapEditorEditPageView.fxml")).load();
        Scene mapEditorEditPageScene = new Scene(mapEditorEditPagePane,1200,900);

        curStage.setScene(mapEditorEditPageScene);
        curStage.show();
    }

    @FXML
    public void clickToCheck(ActionEvent actionEvent) throws Exception{


        txt_checkResult.setText(isCorrect(MEMain.arrMECountry, MEMain.arrMEContinent));

    }


    public void generateMap(String authorName) throws Exception{
        File writename = new File(".\\newMap.map");
        writename.createNewFile();
        BufferedWriter out = new BufferedWriter(new FileWriter(writename));
        out.write("author="+authorName+"\r\n");
        out.write("image=world.bmp\r\n");
        out.write("wrap=\r\n");
        out.write("scroll=horizontal\r\n");
        out.write("warn=yes\r\n");
        out.write("\r\n");

        out.write("[Continents]\r\n");
        for(int i=0;i<MEMain.arrMEContinent.size();i++) {
            MEContinent printContinent = MEMain.arrMEContinent.get(i);
            out.write(printContinent.getContinentName()+"="+printContinent.getBonus());
        }
        out.write("\r\n");

        out.write("[Territories]\r\n");
        for(int i=0;i<MEMain.arrMECountry.size();i++) {
            MECountry printCountry = MEMain.arrMECountry.get(i);
            String neighbor =  printCountry.getNeighbor();
            for(int j=0;j<MEMain.arrMECountry.size();j++){

                neighbor = neighbor.replaceAll("\\[", "");
                neighbor = neighbor.replaceAll("\\]", "");
                neighbor = neighbor.replaceAll(" ", "");
            }
            out.write(printCountry.getCountryName()+",0,0,"+ neighbor);
        }
        out.flush();
        out.close();

    }
}