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

public class TournamentGame {

    private String mapFile;
    private ArrayList<Strategy> playerStrategyList;
    private int gameVale;
    private int gameRoundVale;

    private LinkedHashMap<String, GraphNode> worldMapInstance;
    private LinkedHashMap<String, Continent> continentLinkedHashMap;
    private ArrayList<Player> playerArrayList;

    public TournamentGame(String mapFile, ArrayList<Strategy> playerStrategyList, int gameValue, int gameRoundVale) {
        this.mapFile = mapFile;
        this.playerStrategyList = playerStrategyList;
        this.gameVale = gameValue;
        this.gameRoundVale = gameRoundVale;
        this.worldMapInstance = new LinkedHashMap<>();
        this.continentLinkedHashMap = new LinkedHashMap<>();
        this.playerArrayList = new ArrayList<>();
    }

    public void gameStart() throws IOException {
        initMapAndPlayers();
    }

    private void initMapAndPlayers() throws IOException {
        GraphNormal worldGraphNormal = new GraphNormal();
        worldMapInstance = worldGraphNormal.getWorldHashMap();
        continentLinkedHashMap = new LinkedHashMap<>();

        InitWorldMap.buildWorldMapGraph(mapFile, worldMapInstance, continentLinkedHashMap, playerArrayList);
        int numOfplayers = playerStrategyList.size();

        InitPlayers.initPlayers(numOfplayers, worldMapInstance,playerStrategyList, playerArrayList);

        InitWorldMap.printGraph(worldMapInstance, playerArrayList);
    }
}
