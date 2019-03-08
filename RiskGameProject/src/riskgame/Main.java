package riskgame;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import riskgame.controllers.StartViewController;
import riskgame.model.BasicClass.Continent;
import riskgame.model.BasicClass.GraphNode;
import riskgame.model.BasicClass.GraphSingleton;
import riskgame.model.BasicClass.ObserverPattern.PhaseViewObservable;
import riskgame.model.BasicClass.Player;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * Game program entry
 */
public class Main extends Application {
    /**
     * Static arraylist of players for storing all player objects
     */
    public static ArrayList<Player> playersList = new ArrayList<>();

    /**
     * Indicating the index of current player in gaming
     */
    public static int curRoundPlayerIndex = 0;

    /**
     * Indicating the total amount of players
     */
    public static int totalNumOfPlayers = -1;

    /**
     * Continent objects map
     */
    public static LinkedHashMap<String, Continent> worldContinentMap = new LinkedHashMap<>();

    /**
     * Singleton world map organized in graph data structure
     */
    public static LinkedHashMap<String, GraphNode> graphSingleton = GraphSingleton.INSTANCE.getInstance();

    private PhaseViewObservable startPhaseViewObservable;

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

        startPhaseViewObservable = new PhaseViewObservable();
        startPhaseViewObservable.addObserver(StartViewController.getStartPhaseViewObserver());

    }

    /**
     * @param args args
     * @throws IOException launch fails
     */
    public static void main(String[] args) throws IOException {
        launch(args);
    }
}
