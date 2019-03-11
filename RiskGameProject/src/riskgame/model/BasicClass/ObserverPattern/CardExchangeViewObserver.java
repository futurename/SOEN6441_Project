package riskgame.model.BasicClass.ObserverPattern;

import riskgame.Main;
import riskgame.model.BasicClass.Card;
import riskgame.model.BasicClass.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

public class CardExchangeViewObserver implements Observer {
    private HashMap<String, ArrayList<Card>> playersCards = new HashMap<>();
    private ArrayList<Card> playerCards;

//    public CardExchangeViewObserver() {
//        playersCards = new HashMap<>();
//        for (int i=0; i< Main.totalNumOfPlayers; i++){
//            playersCards.put(String.valueOf(i), new ArrayList<>());
//        }
//        System.out.println(playersCards.get("0").toString());
//    }

    @Override
    public void update(Observable o, Object arg) {
        String key = String.valueOf(((Player)o).getPlayerIndex());
        playersCards.put(key, ((Player)o).getCardsList());
        playerCards = ((Player)o).getCardsList();

        System.out.printf("card observer updated: %s!\n", arg);
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
}
