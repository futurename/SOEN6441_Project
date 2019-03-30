package riskgame.controllers;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author Wei Wang
 * @version 1.0
 * @since 2019/03/29
 **/

public class TournamentModeViewController {

    private static ObservableList<String> mapFileNameList;
    private final String DEFAULT_MAPS_FOLDER_PATH = "maps/TournamentModeMaps/";
    private final int MAX_GAMES_TO_BE_PLAYED = 5;
    private final int MIN_GAME_ROUND = 10;
    private final int MAX_GAME_ROUND = 50;
    private ArrayList<String> robotPlayerList = new ArrayList<>();

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
        initGamesCombobox(MAX_GAMES_TO_BE_PLAYED);
        initMaxGameRound(MIN_GAME_ROUND, MAX_GAME_ROUND);

    }

    private void initMaxGameRound(int min_game_round, int max_game_round) {
        ArrayList<Integer> gameRoundSequenceList = (ArrayList<Integer>) IntStream.rangeClosed(min_game_round, max_game_round)
                .boxed().collect(Collectors.toList());
        ObservableList<Integer> observableGameRoundSeqList = FXCollections.observableArrayList(gameRoundSequenceList);
        cbb_gameMaxRounds.setItems(observableGameRoundSeqList);
    }

    private void initGamesCombobox(int max_games_to_be_played) {
        ArrayList<Integer> sequenceNumberList = (ArrayList<Integer>) IntStream.rangeClosed(1, max_games_to_be_played).boxed().collect(Collectors.toList());
        ObservableList<Integer> observableSequenceList = FXCollections.observableArrayList(sequenceNumberList);
        cbb_gamesCount.setItems(observableSequenceList);
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


        if (isEnoughPlayerTypes() && isMapSelected() && isGamesSelected() && isRoundNumberSelected()) {
            Stage curStage = (Stage) btn_confirmSetting.getScene().getWindow();
            curStage.close();

        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Error, please check selections!");
            alert.showAndWait();
        }
    }

    private boolean isRoundNumberSelected() {
        return cbb_gameMaxRounds.getSelectionModel().getSelectedIndex() != -1;
    }

    private boolean isGamesSelected() {
        return cbb_gamesCount.getSelectionModel().getSelectedIndex() != -1;
    }

    private boolean isMapSelected() {
        return cbx_mapFileOne.getSelectionModel().getSelectedIndex() != -1;
    }

    private boolean isEnoughPlayerTypes() {
        int counter = 0;
        if (ckb_cheaterPlayer.isSelected()) {
            counter++;
        }
        if (ckb_randomPlayer.isSelected()) {
            counter++;
        }
        if (ckb_benevolentPlayer.isSelected()) {
            counter++;
        }
        if (ckb_aggressivePlayer.isSelected()) {
            counter++;
        }
        return counter >= 2;
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
        robotPlayerList.add("aggressive");

    }

    public void selectbenevolent(ActionEvent actionEvent) {
        robotPlayerList.add("benevolent");

    }

    public void selectRandomPlayer(ActionEvent actionEvent) {
        robotPlayerList.add("random");

    }

    public void selectCheaterPlayer(ActionEvent actionEvent) {
        robotPlayerList.add("cheater");

    }

    public void clickResetSetting(ActionEvent actionEvent) {
        cbx_mapFileOne.getSelectionModel().clearSelection();
        cbx_mapFileTwo.getSelectionModel().clearSelection();
        cbx_mapFileThree.getSelectionModel().clearSelection();
        cbx_mapFileFour.getSelectionModel().clearSelection();
        cbx_mapFileFive.getSelectionModel().clearSelection();
        cbx_mapFileOne.setMouseTransparent(false);
        cbx_mapFileTwo.setMouseTransparent(false);
        cbx_mapFileThree.setMouseTransparent(false);
        cbx_mapFileFour.setMouseTransparent(false);
        cbx_mapFileFive.setMouseTransparent(false);
        cbx_mapFileTwo.setVisible(false);
        cbx_mapFileThree.setVisible(false);
        cbx_mapFileFour.setVisible(false);
        cbx_mapFileFive.setVisible(false);

        ckb_cheaterPlayer.setSelected(false);
        ckb_randomPlayer.setSelected(false);
        ckb_benevolentPlayer.setSelected(false);
        ckb_aggressivePlayer.setSelected(false);

        cbb_gameMaxRounds.getSelectionModel().clearSelection();
        cbb_gamesCount.getSelectionModel().clearSelection();

    }
}
