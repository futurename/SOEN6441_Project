package riskgame.model.BasicClass;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import riskgame.Main;
import riskgame.model.BasicClass.ObserverPattern.PhaseViewObservable;
import riskgame.model.BasicClass.StrategyPattern.Strategy;
import riskgame.model.Utils.InitPlayers;
import riskgame.model.Utils.InitWorldMap;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;


/**
 * @author Wei Wang
 * @version 1.0
 * @since 2019/03/29
 **/

public class TournamentGame implements Runnable {

    private String mapFile;
    private ArrayList<Strategy> playerStrategyList;
    private int gameRoundValue;

    private LinkedHashMap<String, GraphNode> worldMapInstance;
    private LinkedHashMap<String, Continent> continentLinkedHashMap;
    private ArrayList<Player> robotPlayerList;
    private int gameWinner;
    private PhaseViewObservable tournamentObservable;
    private final int MAX_GAME_ROUND = 9999;

    public TournamentGame(String mapFile, ArrayList<Strategy> playerStrategyList, int gameRoundValue) {
        this.mapFile = mapFile;
        this.playerStrategyList = playerStrategyList;
        this.gameRoundValue = gameRoundValue;
        this.worldMapInstance = new LinkedHashMap<>();
        this.continentLinkedHashMap = new LinkedHashMap<>();
        this.gameWinner = -1;
        this.robotPlayerList = new ArrayList<>();
        this.tournamentObservable = new PhaseViewObservable();
    }

    public TournamentGame(String mapFile, ArrayList<Strategy> playerStrategyList) {
        this.mapFile = mapFile;
        this.playerStrategyList = playerStrategyList;
        this.gameRoundValue = MAX_GAME_ROUND;
        this.robotPlayerList = new ArrayList<>();
        this.continentLinkedHashMap = new LinkedHashMap<>();
        this.gameWinner = -1;
        this.tournamentObservable = new PhaseViewObservable();
        this.worldMapInstance = new LinkedHashMap<>();
    }

    private void mainGamingLogic() throws IOException {
        initMapAndPlayers();

        doAllPlayerReinforcement(robotPlayerList, tournamentObservable);

        doAllPlayerAttackAndFortification(robotPlayerList);

        int gameRoundLeft = gameRoundValue - 1;

        doRegularGaming(gameRoundLeft);

        System.out.println("\n\n\nOne Tournament END:\n");

        InitWorldMap.printGraph(worldMapInstance, robotPlayerList);

        System.out.println("\n>>>>> Final winner: " + gameWinner + ", map: " + mapFile + "\n\n");
    }

    private void initMapAndPlayers() throws IOException {
        GraphNormal worldGraphNormal = new GraphNormal();
        worldMapInstance = worldGraphNormal.getWorldHashMap();
        continentLinkedHashMap = new LinkedHashMap<>();

        InitWorldMap.buildWorldMapGraph(mapFile, worldMapInstance, continentLinkedHashMap);
        int numOfplayers = playerStrategyList.size();
        InitPlayers.initPlayers(numOfplayers, worldMapInstance, continentLinkedHashMap, playerStrategyList, robotPlayerList);

        System.out.println("map: " + mapFile);
        System.out.println("gameRoundValue: " + gameRoundValue);
        System.out.println("playerlist: " + robotPlayerList + "\n\n");

        InitWorldMap.printGraph(worldMapInstance, robotPlayerList);

    }


    private void doRegularGaming(int gameRoundLeft) {
        while (gameRoundLeft > 0 && gameWinner == -1) {
            for (int playerIndex = 0; playerIndex < robotPlayerList.size(); playerIndex++) {
                Player curRobot = robotPlayerList.get(playerIndex);
                if (curRobot.getActiveStatus()) {
                    curRobot.executeReinforcement(tournamentObservable);
                    curRobot.executeAttack();

                    if (curRobot.isFinalWinner()) {
                        gameWinner = curRobot.getPlayerIndex();

                        System.out.println("Tournament game winner: " + gameWinner);

                    } else {
                        curRobot.executeFortification();
                    }

                    System.out.println("robot " + playerIndex + ": regular gaming!  Round left: " + gameRoundLeft);
                }
            }
            gameRoundLeft--;
        }
    }

    private void doAllPlayerAttackAndFortification(ArrayList<Player> robotPlayerList) {
        for (int playerIndex = 0; playerIndex < robotPlayerList.size(); playerIndex++) {
            Player curRobot = robotPlayerList.get(playerIndex);
            if (curRobot.getActiveStatus()) {
                curRobot.executeAttack();
                System.out.println("robot " + playerIndex + ": doAllPlayerAttackAndFortification");

                if (curRobot.isFinalWinner()) {
                    gameWinner = curRobot.getPlayerIndex();

                    System.out.println("Tournament game winner: " + gameWinner);

                } else {
                    curRobot.executeFortification();
                }

            }
        }
    }

    private void doAllPlayerReinforcement(ArrayList<Player> robotPlayerList, PhaseViewObservable tournamentObservable) {
        for (int playerIndex = 0; playerIndex < robotPlayerList.size(); playerIndex++) {
            Player curRobot = robotPlayerList.get(playerIndex);
            curRobot.executeReinforcement(tournamentObservable);

            System.out.println("robot " + playerIndex + ": doAllPlayerReinforcement");
        }
    }

    @Override
    public void run() {
        try {

            System.out.println("\n\n!!!!!!!!tournamentGame instance start!!! map: " + mapFile + "\n\n");

            mainGamingLogic();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getGameWinner() {
        String result = "Draw";

        if (gameWinner != -1) {
            result = robotPlayerList.get(gameWinner).getPlayerName();
        }
        return result;
    }

    public void singleModeRun() throws IOException {
        mainGamingLogic();
        Player player;
        if(gameWinner == -1){
            int lastIndex = Main.playersList.size();
            player = new Player(lastIndex);
            player.setPlayerName("NOBODY");
            player.setPlayerIndex(-1);
            gameWinner = lastIndex;
        }
        Main.phaseViewObserver.setPlayerIndex(gameWinner);
        Stage stage = new Stage();
        Pane pane = new FXMLLoader(getClass().getResource("../../view/FinalView.fxml")).load();
        Scene scene = new Scene(pane,1200,900);
        stage.setScene(scene);
        stage.show();

    }
}
