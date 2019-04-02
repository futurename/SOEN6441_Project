package riskgame.model.BasicClass;

/**
 * @author Wei Wang
 * @version 1.0
 * @since 2019/04/02
 **/

public class GameRunningResult {
    private String mapFileName;
    private String gameName;
    private String winnerName;

    public GameRunningResult(String mapFileName, String gameName, String winnerName) {
        this.mapFileName = mapFileName;
        this.gameName = gameName;
        this.winnerName = winnerName;
    }

    public String getMapFileName() {
        return mapFileName;
    }

    public String getGameName() {
        return gameName;
    }

    public String getWinnerName() {
        return winnerName;
    }

    @Override
    public String toString() {
        return "GameRunningResult{" +
                "mapFileName='" + mapFileName + '\'' +
                ", gameName='" + gameName + '\'' +
                ", winnerName='" + winnerName + '\'' +
                '}';
    }
}
