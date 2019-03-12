package riskgame.model.BasicClass.ObserverPattern;

import java.util.Observable;

/**
 * Phase view observable objects include current phase,
 * in-turn player, action to be executed and and global exchange time.
 * It is observed by PhaseViewObserver and cardExchangeViewObserver who only look at exchange time.
 * @see PhaseViewObserver
 * @see CardExchangeViewObserver
 **/
public class PhaseViewObservable extends Observable {
    private String phaseName;
    private int playerIndex;
    private String actionString;
    private int exchangeTime;

    public PhaseViewObservable(){
        this.phaseName = "";
        this.playerIndex = -1;
        this.actionString = "";
        this.exchangeTime = 1;
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

    public void addOneExchangeTime() {
        this.exchangeTime++;
        setChanged();
    }

    public int getExchangeTime() {
        return exchangeTime;
    }

    /**
     * since cardViewObserver is not static, it has to
     * initialize exchangeTime before using it, although no changes happened.
     */
    public void initObservableExchangeTime(){
        setChanged();
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
