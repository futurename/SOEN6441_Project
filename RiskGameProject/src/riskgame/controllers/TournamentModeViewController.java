package riskgame.controllers;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
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

    private static ObservableList<String> mapFileNameList;
    private final String DEFAULT_MAPS_FOLDER_PATH = "maps/TournamentModeMaps/";
    @FXML
    private ComboBox cbb_gamesCount;
    @FXML
    private ComboBox cbb_gameMaxRounds;
    @FXML
    private Button btn_confirmSetting;
    @FXML
    private ComboBox cbx_mapFileOne;
    @FXML
    private ComboBox cbx_mapFileTwo;
    @FXML
    private ComboBox cbx_mapFileThree;
    @FXML
    private ComboBox cbx_mapFileFour;
    @FXML
    private ComboBox cbx_mapFileFive;
    @FXML
    private CheckBox ckb_aggressivePlayer;
    @FXML
    private CheckBox ckb_benevolentPlayer;
    @FXML
    private CheckBox ckb_randomPlayer;
    @FXML
    private CheckBox ckb_cheaterPlayer;

    public void initialize() {
        ArrayList<File> mapFileList = getMapFiles(DEFAULT_MAPS_FOLDER_PATH);
        mapFileNameList = getShortFileNameList(mapFileList);
        setOnlyFirstComboxVisible(mapFileNameList);

    }

    private ObservableList<String> getShortFileNameList(ArrayList<File> mapFileList) {
        ArrayList<String> shortNameList = new ArrayList<>();
        for (File file : mapFileList) {
            String[] splitStrings = file.toString().split("\\\\");
            String shortFileName = splitStrings[splitStrings.length - 1];
            shortNameList.add(shortFileName);
        }
        return FXCollections.observableArrayList(shortNameList);
    }

    private void setOnlyFirstComboxVisible(ObservableList<String> mapFileNameList) {

        ObservableList<String> comboboxOneList = FXCollections.observableArrayList(mapFileNameList);
        cbx_mapFileOne.setItems(comboboxOneList);
        cbx_mapFileTwo.setVisible(false);
        cbx_mapFileThree.setVisible(false);
        cbx_mapFileFour.setVisible(false);
        cbx_mapFileFive.setVisible(false);
    }


    private ArrayList<File> getMapFiles(String default_maps_folder_path) {

        File folder = new File(default_maps_folder_path);
        File[] files = folder.listFiles();

        ArrayList<File> result = new ArrayList<File>(Arrays.asList(files));

        for (File file : result) {
            System.out.println(file.toString());
        }

        return result;
    }


    public void clickConfirmSetting(ActionEvent actionEvent) {
        Stage curStage = (Stage) btn_confirmSetting.getScene().getWindow();
        curStage.close();
    }

    public void clickGamesCount(MouseEvent mouseEvent) {
    }

    public void clickGameMaxRounds(MouseEvent mouseEvent) {
    }

    public void selectMapFileOne(ActionEvent actionEvent) {
        String selectedFileName = (String) cbx_mapFileOne.getSelectionModel().getSelectedItem();
        ObservableList<String> comboboxOneList = cbx_mapFileOne.getItems();
        if (comboboxOneList.size() > 1) {
            ObservableList<String> comboboxTwoList = FXCollections.observableArrayList(comboboxOneList);
            comboboxTwoList.remove(selectedFileName);
            cbx_mapFileOne.setMouseTransparent(true);
            cbx_mapFileTwo.setItems(comboboxTwoList);
            cbx_mapFileTwo.setVisible(true);
        }
    }

    public void selectMapFileTwo(ActionEvent actionEvent) {
        String selectedFileName = (String) cbx_mapFileTwo.getSelectionModel().getSelectedItem();
        ObservableList<String> comboboxTwoList = cbx_mapFileTwo.getItems();
        if (comboboxTwoList.size() > 1) {
            ObservableList<String> comboboxThreeList = FXCollections.observableArrayList(comboboxTwoList);
            comboboxThreeList.remove(selectedFileName);
            cbx_mapFileTwo.setMouseTransparent(true);
            cbx_mapFileThree.setItems(comboboxThreeList);
            cbx_mapFileThree.setVisible(true);
        }
    }

    public void selectMapFileThree(ActionEvent actionEvent) {
        String selectedFileName = (String) cbx_mapFileThree.getSelectionModel().getSelectedItem();
        ObservableList<String> comboboxThreeList = cbx_mapFileThree.getItems();
        if (comboboxThreeList.size() > 1) {
            ObservableList<String> comboboxFourList = FXCollections.observableArrayList(comboboxThreeList);
            comboboxFourList.remove(selectedFileName);
            cbx_mapFileThree.setMouseTransparent(true);
            cbx_mapFileFour.setItems(comboboxFourList);
            cbx_mapFileFour.setVisible(true);
        }
    }

    public void selectMapFileFour(ActionEvent actionEvent) {
        String selectedFileName = (String) cbx_mapFileFour.getSelectionModel().getSelectedItem();
        ObservableList<String> comboboxFourList = cbx_mapFileFour.getItems();
        if (comboboxFourList.size() > 1) {
            ObservableList<String> comboboxFiveList = FXCollections.observableArrayList(comboboxFourList);
            comboboxFiveList.remove(selectedFileName);
            cbx_mapFileFour.setMouseTransparent(true);
            cbx_mapFileFive.setItems(comboboxFourList);
            cbx_mapFileFive.setVisible(true);
        }
    }

    public void selectMapFileFive(ActionEvent actionEvent) {
        cbx_mapFileFive.setMouseTransparent(true);
    }

    public void selectAggressivePlayer(ActionEvent actionEvent) {
    }

    public void selectbenevolent(ActionEvent actionEvent) {
    }

    public void selectRandomPlayer(ActionEvent actionEvent) {
    }

    public void selectCheaterPlayer(ActionEvent actionEvent) {
    }
}
