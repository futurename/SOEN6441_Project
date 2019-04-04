package riskgame.model.BasicClass.StrategyPattern;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import riskgame.Main;
import riskgame.controllers.StartViewController;
import riskgame.model.BasicClass.Card;
import riskgame.model.BasicClass.ObserverPattern.CardExchangeViewObserver;
import riskgame.model.BasicClass.ObserverPattern.PhaseViewObservable;
import riskgame.model.BasicClass.Player;
import riskgame.model.Utils.AttackProcess;
import riskgame.model.Utils.InfoRetriver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import static riskgame.Main.*;
import static riskgame.controllers.StartViewController.firstRoundCounter;

public class UtilMethods {

    /**
     * init a cardObserver for this player to get "exchange time" and player card list
     * Although card list can be get directly from player, doing this is to unify the usage
     * Observer will be deleted before next phase.
     *
     * @param player player who will attach to the observer/ongoing player
     * @see CardExchangeViewObserver
     * @see UtilMethods#deregisterCardObserver(Player, PhaseViewObservable, CardExchangeViewObserver)
     */
    public static CardExchangeViewObserver initCardObserver(Player player, PhaseViewObservable observable) {
        CardExchangeViewObserver cardObserver = new CardExchangeViewObserver();
        player.addObserver(cardObserver);
        //init cards if player already had some
        player.initObservableCard();
        player.notifyObservers("Get players cards from observer.");
        //get current exchange time.
        observable.addObserver(cardObserver);
        observable.initObservableExchangeTime();
        //Although it will notify other phase view observer, but it won't change the its value.
        observable.notifyObservers("keeping exchange time up to date.");
        return cardObserver;
    }

    /**
     * cardObserver deleted from two observable
     *
     * @param player       current player
     * @param cardObserver observer been removed
     */
    public static void deregisterCardObserver(Player player, PhaseViewObservable observable, CardExchangeViewObserver cardObserver) {
        observable.deleteObserver(cardObserver);
        player.deleteObserver(cardObserver);
    }

    /**
     * Making exchange in "code" way.
     * Then notify the observable to update exchange time.
     * The armies obtained through exchanging is set to player.undeployedArmy first.
     * Then added to army only when they are deployed.
     *
     * @param player player
     * @param code   combo type
     */
    public static void exchangeCard(Player player, int code, int exchangeTime) {
        int newArmy = getExchangedArmy(exchangeTime);
        if (code == -1) {
            ArrayList<Card> removes = new ArrayList<>();
            removes.add(Card.ARTILLERY);
            removes.add(Card.CAVALRY);
            removes.add(Card.INFANTRY);
            player.removeObservableCards(removes);
        } else if (code == Card.INFANTRY.ordinal()) {
            player.removeObservableCards(new ArrayList<>(Collections.nCopies(3, Card.INFANTRY)));
        } else if (code == Card.CAVALRY.ordinal()) {
            player.removeObservableCards(new ArrayList<>(Collections.nCopies(3, Card.CAVALRY)));
        } else if (code == Card.ARTILLERY.ordinal()) {
            player.removeObservableCards(new ArrayList<>(Collections.nCopies(3, Card.ARTILLERY)));
        }
        UtilMethods.addUndeployedArmyAfterExchanging(player, newArmy);
        phaseViewObservable.addOneExchangeTime();
        phaseViewObservable.notifyObservers("Add Exchange Time");
    }

    /**
     * if the selected cards list satisfies the game rule, this method it will return the exact nbr of exchanged army.
     *
     * @return exchanged army number
     */
    public static int getExchangedArmy(int curExchangeTime) {
        //get exchange time from card observer
        return 5 * curExchangeTime;
    }

    /**
     * Automatically looking for available combo to exchange
     *
     * @param cards cards list with size that > 2 (do not required)
     * @return an integer that stands for a possible combo
     */
    public static int availableCombo(ArrayList<Card> cards) {
        int cavalry = 0;
        int infantry = 0;
        int artillery = 0;
        for (Card card : cards) {
            switch (card) {
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
        if (cavalry >= 3) {
            return Card.CAVALRY.ordinal();
        } else if (infantry >= 3) {
            return Card.INFANTRY.ordinal();
        } else if (artillery >= 3) {
            return Card.ARTILLERY.ordinal();
        } else if (cavalry > 0 && infantry > 0 && artillery > 0) {
            return -1;
        } else return -2;
    }

    /**
     * New army will be added to player's undeployed army.
     * Same logic as exchanging card for new Army.
     * Never add new army directly.
     *
     * @param player current player
     */
    public static void getNewArmyPerRound(Player player) {
        int ownedCountryNum = player.getOwnedCountryNameList().size();
        int newArmyPerRound =
                InfoRetriver.getStandardReinforceArmyNum(ownedCountryNum) + player.getContinentBonus();
        player.addUndeployedArmy(newArmyPerRound);
    }

    /**
     * The armies obtained through exchanging is set to player.undeployedArmy first.
     * Same logic as getting new army per round.
     * Never add new army directly.
     *
     * @param exchangedArmyNbr army number exchanged to be added to the player
     */
    public static int addUndeployedArmyAfterExchanging(Player player, int exchangedArmyNbr) {
        player.addUndeployedArmy(exchangedArmyNbr);
        return player.getUndeployedArmy();
    }

    /**
     * is called in cheater attack logic which does not actually attack.
     */
    public static void checkDefenderAlive(Player defender) {
        if (!AttackProcess.isPlayerHasCountry(defender)) {
            defender.setActiveStatus(false);
            System.out.println("Player: " + defender.getPlayerIndex() + " LOSE, QUIT! cheater did it");
        }
    }

    /**
     * Method does nothing unless next player is robot
     */
    public static void callNextRobotPhase() {
        Player nextPlayer = playersList.get(Main.phaseViewObserver.getPlayerIndex());
        String nextPhase = Main.phaseViewObserver.getPhaseName();
        if (!nextPlayer.getStrategy().toString().equals("Human")) {
            switch (nextPhase) {
                case "Reinforcement Phase":
                    nextPlayer.executeReinforcement(phaseViewObservable);
                    break;
                case "Attack Phase":
                    nextPlayer.executeAttack();
                    break;
                case "Fortification Phase":
                    nextPlayer.executeFortification();
                    break;
            }
        }
    }

    /**
     * Must be Called at end of reinforcement phase.
     * Work out a proper phase to go.
     * Notify observer and auto call robots move until a human's turn.
     *
     * @param player current player
     */
    public static void endReinforcement(Player player) {
        //If single game mode
        if (!playersList.isEmpty()) {
            if (StartViewController.reinforceInitCounter > 1) {
                notifyReinforcementEnd(false, player);
                StartViewController.reinforceInitCounter--;
            } else {
                notifyReinforcementEnd(true, player);
            }
            //if not robot phase, method does nothing
            callNextRobotPhase();
        }
    }

    /**
     * Notify all phase view observer that game stage changed.
     * Changes can be next player's reinforcement or going to attack phase.
     *
     * @param isAttackPhase true for going to attack phase otherwise, next player's turn
     */
    private static void notifyReinforcementEnd(boolean isAttackPhase, Player player) {
        if (isAttackPhase) {
            phaseViewObservable.setAllParam("Attack Phase", curRoundPlayerIndex, "Attack Action");
            phaseViewObservable.notifyObservers("From ReinforceView to AttackView");
            System.out.printf("%s player finished reinforcement, player %s's turn.\n", player.getPlayerIndex(), curRoundPlayerIndex);
        } else {
            int nextPlayerIndex = (player.getPlayerIndex() + 1) % totalNumOfPlayers;
            phaseViewObservable.setAllParam("Reinforcement Phase", nextPlayerIndex, "Reinforcement Action");
            phaseViewObservable.notifyObservers("Continue reinforcement");
            System.out.printf("%s player finished reinforcement, player %s's turn.\n", player.getPlayerIndex(), nextPlayerIndex);
        }
    }

    /**
     * Must be Called at end of fortification phase.
     * Work out a proper phase to go.
     * Notify observer and auto call robots move until a human's turn.
     *
     * @param player current player
     */
    public static void endFortification(Player player) {
        //If single game mode
        if (!playersList.isEmpty()) {
            if (firstRoundCounter > 0) {
                firstRoundCounter--;
                if (firstRoundCounter == 0) {
                    curRoundPlayerIndex = -1;
                }
                notifyFortificationEnd(true, player);
            } else {
                notifyFortificationEnd(false, player);
            }
            //if not robot phase, method does nothing
            callNextRobotPhase();
        }
    }

    /**
     * Call phase view observable notify its observers.
     *
     * @param player current player
     */
    private static void notifyFortificationEnd(boolean isAttackView, Player player) {
        if (isAttackView) {
//            int nextPlayerIndex = (player.getPlayerIndex() + 1) % totalNumOfPlayers;
            int nextPlayerIndex = InfoRetriver.getNextActivePlayer(player.getPlayerIndex());
            phaseViewObservable.setAllParam("Attack Phase", nextPlayerIndex, "Attack Action");
            phaseViewObservable.notifyObservers("From fortification to attack");
            System.out.printf("%s player finished fortification, player %s's turn.\n", player.getPlayerIndex(), nextPlayerIndex);
        } else {
            curRoundPlayerIndex = InfoRetriver.getNextActivePlayer(player.getPlayerIndex());
            phaseViewObservable.setAllParam("Reinforcement Phase", curRoundPlayerIndex, "Reinforcement Action");
            phaseViewObservable.notifyObservers("From fortification to reinforcement");
            System.out.printf("%s player finished fortification, player %s's turn.\n", player.getPlayerIndex(), curRoundPlayerIndex);
        }
    }

    /**
     * Must be Called at end of attack phase.
     * Work out a proper phase to go
     * Notify observer and auto call robots move until a human's turn.
     *
     * @param player current player
     */
    public static void endAttack(Player player) {
        //If single game mode
        if (!playersList.isEmpty()) {
            if (player.isFinalWinner()) {
                notifyAttackEnd(true, player);


            } else {
                notifyAttackEnd(false, player);
                //if not robot phase, method does nothing
                callNextRobotPhase();
            }
        }
    }

    /**
     * notify phase view observers
     */
    private static void notifyAttackEnd(boolean isFinalView, Player player) {
        if (isFinalView) {
            Main.phaseViewObservable.setAllParam("Final Phase", player.getPlayerIndex(), "Game Over");
            Main.phaseViewObservable.notifyObservers("From attack to final");
            System.out.printf("player %s wins.\n", player.getPlayerName());
        } else {
            int curPlayerIndex = player.getPlayerIndex();
            Main.phaseViewObservable.setAllParam("Fortification Phase", curPlayerIndex, "Fortification Action");
            Main.phaseViewObservable.notifyObservers("From attack to fortification");
            System.out.printf("player %s finished attack, player %s's turn.\n", curPlayerIndex, curPlayerIndex);
        }
    }

    public static <T extends Initializable> Scene startView(String phase, T controller) {
        String resourceLocation;
        switch (phase) {
            case "Reinforcement Phase":
                resourceLocation = "../view/ReinforceView.fxml";
                break;
            case "Attack Phase":
                resourceLocation = "../view/AttackView.fxml";
                break;
            case "Fortification Phase":
                resourceLocation = "../view/FortificationView.fxml";
                break;
            case "Final Phase":
                resourceLocation = "../view/FinalView.fxml";
                break;
            default:
                resourceLocation = "";
        }
        try {
            System.out.println("LOADING......" + phase);
            FXMLLoader loader = new FXMLLoader(controller.getClass().getResource(resourceLocation));

        /*    if (phase.equals("Final Phase")) {
                FinalViewController finalViewController = new FinalViewController();
                loader.setController(finalViewController);

                Field playerField = controller.getClass().getDeclaredField("curPlayer");
                playerField.setAccessible(true);
                Object playerVlaue = playerField.get(controller);

                finalViewController.setWinner((Player) playerVlaue);
            }*/
            return new Scene(loader.load(), 1200, 900);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
