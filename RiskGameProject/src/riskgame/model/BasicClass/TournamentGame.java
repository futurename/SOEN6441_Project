package riskgame.model.BasicClass;

import riskgame.model.BasicClass.StrategyPattern.Strategy;
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

    private static LinkedHashMap<String, GraphNode> worldMapSingleton = GraphSingleton.INSTANCE.getInstance();

    public TournamentGame(String mapFile, ArrayList<Strategy> playerStrategyList, int gameVale, int gameRoundVale) {
        this.mapFile = mapFile;
        this.playerStrategyList = playerStrategyList;
        this.gameVale = gameVale;
        this.gameRoundVale = gameRoundVale;
    }

    public void gameStart() throws IOException {
        initMapAndPlayers();
    }

    private void initMapAndPlayers() throws IOException {
        InitWorldMap.buildWorldMapGraph(mapFile, worldMapSingleton);

    }
}
