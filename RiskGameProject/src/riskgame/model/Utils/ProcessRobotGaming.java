package riskgame.model.Utils;

import riskgame.model.BasicClass.StrategyPattern.Strategy;
import riskgame.model.BasicClass.TournamentGame;

import java.util.ArrayList;

/**
 * Created on 2019-03-30 030
 */
public class ProcessRobotGaming {
    private static ArrayList<TournamentGame> tournamentGameArrayList = new ArrayList<>();


    public static void initRobotGaming(ArrayList<String> mapFileList, ArrayList<Strategy> robotPlayerList, int gamesValue, int gameRoundValue){
        for (String fileName : mapFileList) {
            TournamentGame oneTournamentGame = new TournamentGame(fileName, robotPlayerList, gamesValue,gameRoundValue);
            tournamentGameArrayList.add(oneTournamentGame);
        }

        startAllGames();
    }

    private static void startAllGames() {

    }

}
