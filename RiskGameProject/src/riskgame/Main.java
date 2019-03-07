package riskgame;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import riskgame.model.BasicClass.Continent;
import riskgame.model.BasicClass.GraphNode;
import riskgame.model.BasicClass.GraphSingleton;
import riskgame.model.BasicClass.Player;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * @author WW
 */
public class Main extends Application {
    public static ArrayList<Player> playersList = new ArrayList<>();
    public static LinkedHashMap<String, Continent> worldContinentMap = new LinkedHashMap<>();
    public static int curRoundPlayerIndex = 0;
    public static int totalNumOfPlayers = -1;
    public static LinkedHashMap<String, GraphNode> graphSingleton = GraphSingleton.INSTANCE.getInstance();


    /**
     * @param primaryStage default start page
     * @throws Exception startview.fxml not found
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        Pane root = FXMLLoader.load(getClass().getResource("view/StartView.fxml"));

        primaryStage.setTitle("Risk Game");
        primaryStage.setScene(new Scene(root, 1200, 900));

        primaryStage.setResizable(false);
        primaryStage.show();

    }

    /**
     * @param args args
     * @throws IOException launch fails
     */
    public static void main(String[] args) throws IOException {
        launch(args);
    }
}
