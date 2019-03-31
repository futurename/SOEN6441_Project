package riskgame.model.BasicClass;

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
    private ArrayList<Player> playerArrayList;

    public TournamentGame(String mapFile, ArrayList<Strategy> playerStrategyList, int gameRoundValue) {
        this.mapFile = mapFile;
        this.playerStrategyList = playerStrategyList;
        this.gameRoundValue = gameRoundValue;
        this.worldMapInstance = new LinkedHashMap<>();
        this.continentLinkedHashMap = new LinkedHashMap<>();
        this.playerArrayList = new ArrayList<>();
    }

    private void initMapAndPlayers() throws IOException {
        GraphNormal worldGraphNormal = new GraphNormal();
        worldMapInstance = worldGraphNormal.getWorldHashMap();
        continentLinkedHashMap = new LinkedHashMap<>();

        InitWorldMap.buildWorldMapGraph(mapFile, worldMapInstance, continentLinkedHashMap);
        int numOfplayers = playerStrategyList.size();

        InitPlayers.initPlayers(numOfplayers, worldMapInstance,playerStrategyList, playerArrayList);

        System.out.println("\n\n\n" + playerArrayList + "\n\n\n");

        //InitWorldMap.printGraph(worldMapInstance, playerArrayList);


    }

    @Override
    public void run() {
        try {
            System.out.println("\n\n!!!!!!!!!!!!!!!tournamentGame instance start!!!");
            System.out.println("map: " + mapFile);
            System.out.println("gameRoundValue: " + gameRoundValue);
            System.out.println("playerlist: " + playerArrayList + "\n\n");

            initMapAndPlayers();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
