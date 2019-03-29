package riskgame.model.BasicClass;

import riskgame.model.BasicClass.StrategyPattern.Strategy;

import java.io.File;
import java.util.ArrayList;

/**
 * @author Wei Wang
 * @version 1.0
 * @since 2019/03/29
 **/

public class TournamentGame {

    private File mapFile;
    private int games;
    private ArrayList<Strategy> playerStrategyList;
    private int maxRound;

    public TournamentGame(File mapFile, int games, ArrayList<Strategy> playerStrategyList, int maxRound) {
        this.mapFile = mapFile;
        this.games = games;
        this.playerStrategyList = playerStrategyList;
        this.maxRound = maxRound;
    }
}
