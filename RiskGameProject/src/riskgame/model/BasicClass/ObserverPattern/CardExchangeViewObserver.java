package riskgame.model.BasicClass.ObserverPattern;

import riskgame.model.BasicClass.Card;
import riskgame.model.BasicClass.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

/**
 * This class is not a static observer.
 * It will instantiate in ReinforceViewController.java as a private observer.
 * The observable object is Player and phaseViewObservable.
 * @author zhanfan
 * @since build2
 */
public class CardExchangeViewObserver implements Observer {
    private HashMap<String, ArrayList<Card>> playersCards = new HashMap<>();
    private ArrayList<Card> playerCards;
    private int exchangeTime = 1;

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof PhaseViewObservable){
            exchangeTime = ((PhaseViewObservable)o).getExchangeTime();
        }else {
            String key = String.valueOf(((Player)o).getPlayerIndex());
            playersCards.put(key, ((Player)o).getCardsList());
            playerCards = ((Player)o).getCardsList();
        }
//        System.out.printf("card observer updated: %s\n", arg);
    }

    public ArrayList<Card> getCardsByPlayerIndex(int playerIndex){
        if (playersCards.get(String.valueOf(playerIndex)) != null){
            return playersCards.get(String.valueOf(playerIndex));
        }else return new ArrayList<Card>();
    }

    public HashMap<String, ArrayList<Card>> getPlayersCards() {
        return playersCards;
    }

    public ArrayList<Card> getPlayerCards() {
        return playerCards;
    }

    public int getExchangeTime() {
        return exchangeTime;
    }
}
