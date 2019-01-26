package GUI_Test.Controller;

import java.io.IOException;
import java.util.ArrayList;

import GUI_Test.Class.Country;
import GUI_Test.Class.Player;
import GUI_Test.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;


public class AttackViewController {

    @FXML
    public Label lb_PlayerNum;
    public TextArea ta_PlayerInfo;

    public AttackViewController() {
        System.out.println("player seq: " + Main.playerSeqCounter);

    }

    public void initialize() {
        lb_PlayerNum.setText("Player: " + Main.playerSeqCounter);

        Player thisPlayer = Main.playerList.get(Main.playerSeqCounter);
        String outputText = thisPlayer.getPlayername() + "\n"
                + "Rounds left: " + Main.rounds + "\n"
                + "Occupied countries:" + "\n";

        ArrayList<String> countryList = thisPlayer.getCountries();
        for (int i = 0; i < countryList.size(); i++) {
            System.out.println("get country name: " + Main.worldMap.get(countryList.get(i)).getCountryname() + ", armies: " + Main.worldMap.get(countryList.get(i)).getArmies());
            outputText += Main.worldMap.get(countryList.get(i)).getCountryname() + " (" + Main.worldMap.get(countryList.get(i)).getArmies() + ")  ";
        }

        System.out.println(outputText);

        ta_PlayerInfo.setText(outputText);

    }

    // Event Listener on Button.onMouseClicked
    @FXML
    public void ClickConfirmAtttack(ActionEvent event) throws IOException {

        Parent fortificationViewParent = FXMLLoader.load(getClass().getResource("/GUI_Test/View/FortificationView.fxml"));
        Scene fortificationScene = new Scene(fortificationViewParent, 800, 400);

        Stage windowStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        windowStage.setScene(fortificationScene);
        windowStage.setTitle("Player: " + Main.playerSeqCounter);
        //lb_PlayerNum.setText("Player: " + String.valueOf(Main.playerSeqCounter));
        windowStage.show();

    }

}
