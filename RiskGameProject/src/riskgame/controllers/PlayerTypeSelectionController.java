package riskgame.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import riskgame.model.BasicClass.StrategyPattern.*;

import java.util.ArrayList;


/**
 * @author Wei Wang
 * @version 1.0
 * @since 2019/03/30
 **/

public class PlayerTypeSelectionController {

    @FXML
    private GridPane grp_playerTypeSelection;
    @FXML
    private Button btn_confirmSelection;
    @FXML
    private Button btn_resetSelection;
    @FXML
    private Label lbl_numberOfPlayers;

    private int numOfPlayers;
    private ArrayList<Strategy> strategySelectionList;

    public void initialize() {
        strategySelectionList = new ArrayList<>();
    }

    public void InitViewSettings() {
        lbl_numberOfPlayers.setText(String.valueOf(getNumOfPlayers()));

        ObservableList<Strategy> strategyArrayList = getStrategyPlayerList();

        for (int index = 0; index < numOfPlayers; index++) {
            ComboBox comboBox = new ComboBox();
            comboBox.setItems(strategyArrayList);
            comboBox.setMinWidth(240);
            comboBox.getStylesheets().add(getClass().getResource("../view/css/combobox.css").toExternalForm());
            comboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
                @Override
                public void changed(ObservableValue observable, Object oldValue, Object newValue) {

                    System.out.println(newValue);

                    strategySelectionList.add((Strategy) newValue);
                }
            });

            grp_playerTypeSelection.add(comboBox, 0, index);
        }
    }

    private ObservableList<Strategy> getStrategyPlayerList() {
        Strategy aggressive = new StrategyAggressive();
        Strategy benevolent = new StrategyBenevolent();
        Strategy cheater = new StrategyCheater();
        Strategy random = new StrategyRandom();
        Strategy human = new StrategyHuman();

        ArrayList<Strategy> strategyArrayList = new ArrayList<>();
        strategyArrayList.add(human);
        strategyArrayList.add(aggressive);
        strategyArrayList.add(benevolent);
        strategyArrayList.add(cheater);
        strategyArrayList.add(random);

        ObservableList<Strategy> result = FXCollections.observableArrayList(strategyArrayList);
        return result;
    }

    public int getNumOfPlayers() {
        return this.numOfPlayers;
    }

    public void setNumOfPlayers(int numOfPlayers) {
        this.numOfPlayers = numOfPlayers;
    }

    public void clickConfirmSelection(ActionEvent actionEvent) {
        if (isAllComboboxSelected()) {
            StartViewController.setStrategyTypeList(strategySelectionList);

            Stage curStage = (Stage) btn_confirmSelection.getScene().getWindow();
            curStage.close();

            System.out.println(strategySelectionList);
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("All comboboxes need be selected!");
            alert.showAndWait();
        }
    }

    private boolean isAllComboboxSelected() {
        boolean result = true;

        int comboboxCount = grp_playerTypeSelection.getChildren().size();
        for (int index = 0; index < comboboxCount; index++) {
            if (((ComboBox) grp_playerTypeSelection.getChildren().get(index)).getSelectionModel().isEmpty()) {
                result = false;
                break;
            }
        }
        return result;
    }

    public void clickResetSelection(ActionEvent actionEvent) {
        for (Node node : grp_playerTypeSelection.getChildren()) {
            ((ComboBox) node).getSelectionModel().select(-1);
        }
        strategySelectionList.clear();
    }



}
