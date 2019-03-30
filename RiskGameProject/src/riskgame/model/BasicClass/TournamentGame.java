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

    private static LinkedHashMap<String, GraphNode> worldMapInstance;

    public TournamentGame(String mapFile, ArrayList<Strategy> playerStrategyList, int gameValue, int gameRoundVale) {
        this.mapFile = mapFile;
        this.playerStrategyList = playerStrategyList;
        this.gameVale = gameValue;
        this.gameRoundVale = gameRoundVale;
    }

    public void gameStart() throws IOException {
        initMapAndPlayers();
    }

    private void initMapAndPlayers() throws IOException {
        GraphNormal worldGraphNormal = new GraphNormal();
        worldMapInstance = worldGraphNormal.getWorldHashMap();

        InitWorldMap.buildWorldMapGraph(mapFile, worldMapInstance);
        int numOfplayers = playerStrategyList.size();

        InitPlayers.initPlayers(numOfplayers, worldMapInstance,playerStrategyList);

        InitWorldMap.printGraph();
    }
}
