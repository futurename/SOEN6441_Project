package riskgame.model.BasicClass.ObserverPattern;

import java.util.Observable;

/**
 * Phase view observable objects include current phase,
 * in-turn player, action to be executed and and global exchange time.
 * It is observed by PhaseViewObserver and cardExchangeViewObserver who only look at exchange time.
 * @see PhaseViewObserver
 * @see CardExchangeViewObserver
 * @author zhanfan
 * @since build2
 **/
public class PhaseViewObservable extends Observable {
    private final String attackActionStr = "Action:\n" +
            "\n1. Select one attacking country" +
            "\n2. Select an adjacent empty country" +
            "\n3_1. Click \"All-Out\" button to use all army for attacking" +
            "\n3_2. Select certain number of army for both attacker and defender" +
            "\n  4. Click \"Accept\" button for confirming army number selection" +
            "\n  5. Click \"Attack\" button to use selected army number";
    private final String fortificationActionStr = "Action:\n" +
            "\nselect one owned country from left and another country of the right as target";
    private final String reinforcementActionStr = "Action:\n" +
            "\nBegin reinforce phase, need deploy armies to your countries";

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

    private void setPhaseName(String phaseName) {
        this.phaseName = phaseName;
    }

    public int getPlayerIndex() {
        return playerIndex;
    }

    private void setPlayerIndex(int playerIndex) {
        this.playerIndex = playerIndex;
    }

    public String getActionString() {
        return actionString;
    }

    private void setActionString(String actionType) {
        switch (actionType){
            case "Attack Action":
                this.actionString = this.attackActionStr;
                break;
            case "Fortification Action":
                this.actionString = this.fortificationActionStr;
                break;
            case "Reinforcement Action":
                this.actionString = this.reinforcementActionStr;
                break;
            default:
                this.actionString = actionType;
        }

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

    public void setAllParam(String phaseName, int playerIndex, String actionType){
        setPhaseName(phaseName);
        setPlayerIndex(playerIndex);
        setActionString(actionType);
        setChanged();
    }

    public void resetObservable(){
        this.phaseName = "";
        this.playerIndex = -1;
        this.actionString = "";
    }
}
