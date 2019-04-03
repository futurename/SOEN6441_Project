package riskgame.model.BasicClass;

/**
 * @author Wei Wang
 * @version 1.0
 * @since 2019/04/02
 **/

public class GameRunningResult {
    private String mapFileName;
    private int mapIndex;
    private String gameName;
    private int gameValue;
    private String winnerName;

    public GameRunningResult(String mapFileName, int mapIndex, String gameName, int gameValue, String winnerName) {
        this.mapFileName = mapFileName;
        this.mapIndex = mapIndex;
        this.gameName = gameName;
        this.gameValue = gameValue;
        this.winnerName = winnerName;
    }

    public String getMapFileName() {
        return mapFileName;
    }

    public int getMapIndex() {
        return mapIndex;
    }

    public String getGameName() {
        return gameName;
    }

    public int getGameValue() {
        return gameValue;
    }

    public String getWinnerName() {
        return winnerName;
    }

    @Override
    public String toString() {
        return "GameRunningResult{" +
                "mapFileName='" + mapFileName + '\'' +
                ", mapIndex=" + mapIndex +
                ", gameName='" + gameName + '\'' +
                ", gameValue=" + gameValue +
                ", winnerName='" + winnerName + '\'' +
                '}';
    }
}
