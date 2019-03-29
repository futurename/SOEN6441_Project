package riskgame.controllers;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author Wei Wang
 * @version 1.0
 * @since 2019/03/29
 **/

public class TournamentModeViewController {

    @FXML
    private ComboBox cbb_gamesCount;
    @FXML
    private ComboBox cbb_gameMaxRounds;
    @FXML
    private Button btn_confirmSetting;
    @FXML
    private TableColumn<String, String> tbc_playerTypeColumn;
    @FXML
    private TableColumn<File, ObservableList> tbc_mapSlectionColumn;
    @FXML
    private TableView tbv_mapSelectionView;

    private final String DEFAULT_MAPS_FOLDER_PATH = "maps/TournamentModeMaps/";

    public void initialize(){
        ArrayList<File> mapFileList = getMapFiles(DEFAULT_MAPS_FOLDER_PATH);
        setMapSelectionTableView(mapFileList);

    }

    private void setMapSelectionTableView(ArrayList<File> mapFileList) {
        ObservableList<File> mapFileObservableList = FXCollections.observableList(mapFileList);

        ComboBox<File> comboBox = new ComboBox<>();
        comboBox.getItems().addAll(mapFileObservableList);

    }

    private ArrayList<File> getMapFiles(String default_maps_folder_path) {

        File folder = new File(default_maps_folder_path);
        File[] files = folder.listFiles();

        ArrayList<File> result = new ArrayList<File>(Arrays.asList(files));

        for(File file: result ){
            System.out.println(file.toString());
        }

        return result;
    }


    public void clickConfirmSetting(ActionEvent actionEvent) {
        Stage curStage = (Stage)btn_confirmSetting.getScene().getWindow();
        curStage.close();
    }

    public void clickGamesCount(MouseEvent mouseEvent) {
    }

    public void clickGameMaxRounds(MouseEvent mouseEvent) {
    }
}
