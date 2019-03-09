package riskgame.model.BasicClass.ObserverPattern;

import java.util.Observable;
import java.util.Observer;

/**
 * created on 2019/03/08_15:11
 **/

public class PhaseViewObserver implements Observer {
    private String phaseName;
    private int playerIndex;
    private String actionString;

    public String getPhaseName() {
        return phaseName;
    }

    public void setPhaseName(String phaseName) {
        this.phaseName = phaseName;
    }

    public int getPlayerIndex() {
        return playerIndex;
    }

    public void setPlayerIndex(int playerIndex) {
        this.playerIndex = playerIndex;
    }

    public String getActionString() {
        return actionString;
    }

    public void setActionString(String actionString) {
        this.actionString = actionString;
    }

    @Override
    public void update(Observable o, Object arg) {
        this.phaseName = ((PhaseViewObservable)o).getPhaseName();
        this.playerIndex = ((PhaseViewObservable)o).getPlayerIndex();
        this.actionString = ((PhaseViewObservable)o).getActionString();

        System.out.println("observer updated: \n");
        System.out.println(phaseName + ", " + playerIndex + ", " + actionString + ", " + (String)arg);
    }
}
