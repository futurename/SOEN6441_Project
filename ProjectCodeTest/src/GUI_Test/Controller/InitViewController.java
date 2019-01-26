package GUI_Test.Controller;//package Controller;


import java.io.IOException;

import GUI_Test.Main;
import GUI_Test.Model.InitPlayers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;



public class InitViewController {

	@FXML
	public TextField tf_NumOfPlayersInput;
	public TextArea ta_GameInfo;

	public void initialize(){
		String gameInfoString = "Defalut round: 3" + "\nInput number of players below, no input check!" + "\nGaming functions not implemented";
		ta_GameInfo.setText(gameInfoString);
	}


	// Event Listener on Button.onMouseClicked
	@FXML
	public void ClickSwitchScene(ActionEvent event) throws IOException {

		Main.totalNumOfPlayers = Integer.parseInt(tf_NumOfPlayersInput.getText());

		Main.playerList = InitPlayers.GenPlayers(Main.totalNumOfPlayers);

		Parent attackViewParent = FXMLLoader.load(getClass().getResource("/GUI_Test/View/AttackView.fxml"));
		Scene attackScene = new Scene(attackViewParent,800,400);
		
		Stage windowStage = (Stage)((Node)event.getSource()).getScene().getWindow();


		windowStage.setTitle("Player: " + Main.playerSeqCounter);


		windowStage.setScene(attackScene);
		//lb_PlayerNum.setText(String.valueOf(Main.playerSeqCounter));
		windowStage.show();
		
	}


}
