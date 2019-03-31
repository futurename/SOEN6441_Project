package riskgame.model.Utils;

import riskgame.model.BasicClass.StrategyPattern.Strategy;
import riskgame.model.BasicClass.TournamentGame;

import java.util.ArrayList;

/**
 * Created on 2019-03-30 030
 */
public class RobotGamingProcess {

    public static void initRobotGaming(ArrayList<String> mapFileList, ArrayList<Strategy> robotPlayerList, int gamesValue, int gameRoundValue) {
        System.out.println(mapFileList);

        for (String fileName : mapFileList) {
            for (int gameIndex = 0; gameIndex < gamesValue; gameIndex++) {
                TournamentGame oneTournamentGame = new TournamentGame(fileName, robotPlayerList, gameRoundValue);
                oneTournamentGame.run();
            }
        }
    }

    private static void startAllGames() {

    }

}
