package riskgame.model.BasicClass.StrategyPattern;

import javafx.scene.Node;
import javafx.stage.Stage;
import riskgame.Main;
import riskgame.controllers.StartViewController;
import riskgame.model.BasicClass.Card;
import riskgame.model.BasicClass.Country;
import riskgame.model.BasicClass.Player;
import riskgame.model.Utils.InfoRetriver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import static riskgame.Main.curRoundPlayerIndex;
import static riskgame.Main.phaseViewObservable;

public class StrategyRandom implements Strategy {
    private Random r = new Random();

    @Override
    public int doReinforcement(Player player) {
        ArrayList<Card> cards = player.getCardsList();
        if (cards.size()>=5){
            int code = availableCombo(cards);
            exchangeCard(player, code);

        }else if (cards.size()>=3){
            int code = availableCombo(cards);
            if (code != -2) {
                //50% probability
                if (r.nextInt(2) == 0){
                    exchangeCard(player, code);
                }
            }
        }
        int availableArmy = player.getUndeployedArmy();
        ArrayList<Country> countries = InfoRetriver.getCountryList(player);
        while (availableArmy > 0){
            int randomArmy = r.nextInt(availableArmy)+1;
            int randomIndex = r.nextInt(countries.size());
            countries.get(randomIndex).addToCountryArmyNumber(randomArmy);
            availableArmy -= randomArmy;
        }
        player.addUndeployedArmy(-player.getUndeployedArmy());
        //TODO go to next step
        return 0;
    }

    /**
     * making exchange
     * @param player player
     * @param code combo type
     */
    private void exchangeCard(Player player, int code) {
        if (code == -1){
            ArrayList<Card> removes = new ArrayList<>();
            removes.add(Card.ARTILLERY);
            removes.add(Card.CAVALRY);
            removes.add(Card.INFANTRY);
            player.removeObservableCards(removes);
        }else if(code == Card.INFANTRY.ordinal()){
            player.removeObservableCards(new ArrayList<>(Collections.nCopies(3,Card.INFANTRY)));
        }else if (code == Card.CAVALRY.ordinal()){
            player.removeObservableCards(new ArrayList<>(Collections.nCopies(3,Card.CAVALRY)));
        }else if (code == Card.ARTILLERY.ordinal()){
            player.removeObservableCards(new ArrayList<>(Collections.nCopies(3,Card.ARTILLERY)));
        }
    }

    /**
     * looking  for available combo to exchange
     * @param cards cards list that size > 5
     * @return an integer that stands for a possible combo
     */
    private int availableCombo(ArrayList<Card> cards){
        int cavalry = 0;
        int infantry = 0;
        int artillery = 0;
        for (Card card: cards){
            switch (card){
                case CAVALRY:
                    cavalry += 1;
                    break;
                case INFANTRY:
                    infantry += 1;
                    break;
                case ARTILLERY:
                    artillery += 1;
            }
        }
        if (cavalry >= 3){
            return Card.CAVALRY.ordinal();
        }else if (infantry >= 3){
            return Card.INFANTRY.ordinal();
        }else if (artillery >= 3){
            return Card.ARTILLERY.ordinal();
        }else if (cavalry>0 && infantry>0 && artillery>0){
            return -1;
        }else return -2;
    }

    public void gotoNextStep(Player player){
        if (StartViewController.reinforceInitCounter > 1) {
            checkNextViewNeedChange(false, player);
            StartViewController.reinforceInitCounter--;

        } else {
            checkNextViewNeedChange(true, player);
        }
    }

    /**
     * notify all phase view observer that game stage changed.
     * Changes can be next player's reinforcement or going to attack phase.
     *
     * @param isAttackPhase true for going to attack phase otherwise, next player's turn
     */
    private void checkNextViewNeedChange(boolean isAttackPhase, Player player){
        if (!isAttackPhase) {
            int nextPlayerIndex = (player.getPlayerIndex() + 1) % Main.totalNumOfPlayers;
            phaseViewObservable.setAllParam("Reinforcement Phase", nextPlayerIndex, "Reinforcement Action");
            phaseViewObservable.notifyObservers("continue reinforce");

        } else {
            phaseViewObservable.setAllParam("Attack Phase", curRoundPlayerIndex, "Attack Action");
            phaseViewObservable.notifyObservers("From ReinforceView to AttackView");
        }
    }

    @Override
    public void doAttack(Player player) {

    }

    @Override
    public void doFortification(Player player) {

    }
}
