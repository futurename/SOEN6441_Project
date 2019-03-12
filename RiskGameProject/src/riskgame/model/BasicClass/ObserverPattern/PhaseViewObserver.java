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

    public int getPlayerIndex() {
        return playerIndex;
    }

    public String getActionString() {
        return actionString;
    }

    @Override
    public void update(Observable o, Object arg) {
        this.phaseName = ((PhaseViewObservable)o).getPhaseName();
        this.playerIndex = ((PhaseViewObservable)o).getPlayerIndex();
        this.actionString = ((PhaseViewObservable)o).getActionString();

        System.out.println("phase observer updated to:");
        System.out.printf("%s, player %s, %s. \nExecuting: %s\n",phaseName, playerIndex, actionString, arg);
    }
}
