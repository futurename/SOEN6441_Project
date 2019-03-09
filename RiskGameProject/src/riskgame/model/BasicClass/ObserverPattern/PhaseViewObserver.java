package riskgame.model.BasicClass.ObserverPattern;

import java.util.Observable;
import java.util.Observer;

/**
 * created on 2019/03/08_15:11
 **/

public class PhaseViewObserver implements Observer {
    private String phaseName;
    private String playerName;
    private String actionString;

    public PhaseViewObserver(String phaseName, int playerIndex, String actionString) {
        this.phaseName = phaseName;
        this.playerName = "Player_" + playerIndex;
        this.actionString = actionString;
    }

    @Override
    public void update(Observable o, Object arg) {

    }
}
