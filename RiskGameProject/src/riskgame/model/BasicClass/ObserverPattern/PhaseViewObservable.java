package riskgame.model.BasicClass.ObserverPattern;

import java.util.Observable;

/**
 * created on 2019/03/08_15:11
 **/

public class PhaseViewObservable extends Observable {
    private String phaseName;
    private int playerIndex;
    private String actionString;

    public PhaseViewObservable(){
        this.phaseName = "";
        this.playerIndex = -1;
        this.actionString = "";
    }

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

    public void setAllParam(String phaseName, int playerIndex, String actionString){
        setPhaseName(phaseName);
        setPlayerIndex(playerIndex);
        setActionString(actionString);
        setChanged();
    }

    public void resetObservable(){
        this.phaseName = "";
        this.playerIndex = -1;
        this.actionString = "";
    }
}