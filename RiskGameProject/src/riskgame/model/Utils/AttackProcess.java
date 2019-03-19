package riskgame.model.Utils;

import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.TextArea;
import riskgame.Main;
import riskgame.model.BasicClass.Continent;
import riskgame.model.BasicClass.Country;
import riskgame.model.BasicClass.Dice;
import riskgame.model.BasicClass.Player;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static riskgame.controllers.AttackViewController.MAX_ATTACKING_ARMY_NUMBER;
import static riskgame.controllers.AttackViewController.MAX_DEFENDING_ARMY_NUMBER;

/**
 * This class processes the attack
 * checks that how many dice can be tron and how many dice user want to throw
 * checks if defender wins or attacker
 * checks if country,continent or whole map is conquered or not
 */
public class AttackProcess {

    public static int winnerPlayerIndex = -1;


    public static void alloutAttackSimulate(Country attackingCountry, Country defendingCountry, int attackArmyNbr, int defendArmyNbr,
                                            TextArea txa_attackInfoDisplay) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Attacker: [ ")
                .append(attackingCountry.getCountryName())
                .append(" ], Defender: [ ")
                .append(defendingCountry.getCountryName())
                .append(" ]\n\n");

        recursiveAttack(attackingCountry, defendingCountry, attackArmyNbr, defendArmyNbr, stringBuilder);

        if (isCountryConquered(defendingCountry)) {
            int nondeployedAttackerArmyNbr = attackingCountry.getCountryArmyNumber() - 1;
            Player attacker = Main.playersList.get(attackingCountry.getCountryOwnerIndex());
            Player defender = Main.playersList.get(defendingCountry.getCountryOwnerIndex());

            int attackerIndex = attacker.getPlayerIndex();
            int defenderIndex = defender.getPlayerIndex();

            String continentName = defendingCountry.getContinentName();
            Continent curContinent = Main.worldContinentMap.get(continentName);

            updateConqueredCountry(attackingCountry, defendingCountry, nondeployedAttackerArmyNbr, attacker, defender);
            updateContinentAndWorldStatus(attackerIndex, defenderIndex, curContinent);
        }
        txa_attackInfoDisplay.setText(stringBuilder.toString());

    }

    /**
     * this function defines that wheather the attacker or defender is available to attack
     * cheks if both have enough armies to attack.
     * @param attackingCountry
     * @param defendingCountry
     * @param attackArmyNbr
     * @param defendArmyNbr
     * @param stringBuilder
     */
    private static void recursiveAttack(Country attackingCountry, Country defendingCountry, int attackArmyNbr, int defendArmyNbr, StringBuilder stringBuilder) {
        if (attackArmyNbr == 0 || defendArmyNbr == 0) {
            return;
        }
        int avaliableForAttackNbr = attackArmyNbr > MAX_ATTACKING_ARMY_NUMBER ? MAX_ATTACKING_ARMY_NUMBER : attackArmyNbr;
        int avaliableForDefendNbr = defendArmyNbr > MAX_DEFENDING_ARMY_NUMBER ? MAX_DEFENDING_ARMY_NUMBER : defendArmyNbr;

        getOneAttackResult(attackingCountry, defendingCountry, avaliableForAttackNbr, avaliableForDefendNbr, stringBuilder);

        attackArmyNbr = attackingCountry.getCountryArmyNumber() - 1;
        defendArmyNbr = defendingCountry.getCountryArmyNumber();
        stringBuilder.append("\n");
        recursiveAttack(attackingCountry, defendingCountry, attackArmyNbr, defendArmyNbr, stringBuilder);
    }

    /**
     *this functions calculates that how many armies defender or attacker have after the attack
     * @param attackingCountry
     * @param defendingCountry
     * @param attackArmyNbr
     * @param defendArmyNbr
     * @param txa_attackInfoDisplay
     */
    public static void oneAttackSimulate(Country attackingCountry, Country defendingCountry, int attackArmyNbr, int defendArmyNbr,
                                         TextArea txa_attackInfoDisplay) {
        int avaliableForAttackNbr = attackArmyNbr > MAX_ATTACKING_ARMY_NUMBER ? MAX_ATTACKING_ARMY_NUMBER : attackArmyNbr;
        int avaliableForDefendNbr = defendArmyNbr > MAX_DEFENDING_ARMY_NUMBER ? MAX_DEFENDING_ARMY_NUMBER : defendArmyNbr;

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Attacker: [ ")
                .append(attackingCountry.getCountryName())
                .append(" ], Defender: [ ")
                .append(defendingCountry.getCountryName())
                .append(" ]\n\n");

        int attackerRemainArmyNbr = getOneAttackResult(attackingCountry, defendingCountry, avaliableForAttackNbr, avaliableForDefendNbr, stringBuilder);

        txa_attackInfoDisplay.setText(stringBuilder.toString());

        attackResultProcess(attackingCountry, defendingCountry, attackerRemainArmyNbr);

    }

    /**
     * this functions calculates the result for every single attack and returns the attacker's army number
     * @param attackingCountry
     * @param defendingCountry
     * @param attackableArmyNbr
     * @param defendableArmyNbr
     * @param stringBuilder
     * @return
     */
    private static int getOneAttackResult(Country attackingCountry, Country defendingCountry, int attackableArmyNbr, int defendableArmyNbr,
                                          StringBuilder stringBuilder) {
        int result = 0;

        ArrayList<Integer> attackerDiceResultList = getDiceResultList(attackableArmyNbr);
        ArrayList<Integer> defenderDiceResultList = getDiceResultList(defendableArmyNbr);

        stringBuilder
                .append("attacker dices: ")
                .append(attackerDiceResultList)
                .append(", defender dices: ")
                .append(defenderDiceResultList)
                .append("\n");

        System.out.println("\nattackerDiceList: " + attackerDiceResultList);
        System.out.println("defenderDiceResult:" + defenderDiceResultList + "\n");

        int attackArmyCount = attackerDiceResultList.size();
        int defendArmyCount = defenderDiceResultList.size();

        int compareTimes = defenderDiceResultList.size() < attackerDiceResultList.size() ? defenderDiceResultList.size() :
                attackerDiceResultList.size();

        for (int i = 0; i < compareTimes; i++) {

            System.out.println("\nattacker: " + attackerDiceResultList.size()
                    + ", defender: " + defenderDiceResultList.size());

            int bestAttackerDice = attackerDiceResultList.remove(0);
            int bestDefenderDice = defenderDiceResultList.remove(0);

            if (bestAttackerDice > bestDefenderDice) {
                defendingCountry.reduceFromCountryArmyNumber(1);

                System.out.println("Round: " + i + ", >>>Attacker win! attacker: " + attackingCountry.getCountryArmyNumber()
                        + ", defender: " + defendingCountry.getCountryArmyNumber() + "\n");

                defendArmyCount--;

                stringBuilder
                        .append("Round: ")
                        .append(i)
                        .append("\n>>>[ ")
                        .append(attackingCountry.getCountryName())
                        .append(" ]<<< WINS ")
                        .append(">>>[ ")
                        .append(defendingCountry.getCountryName())
                        .append(" ]<<<\nattacker remaining army num: ")
                        .append(attackingCountry.getCountryArmyNumber())
                        .append(", defender remaining army num: ")
                        .append(defendingCountry.getCountryArmyNumber())
                        .append("\n")
                        .append("defend army count after reduction: ")
                        .append(defendArmyCount)
                        .append(", attack army count: ")
                        .append(attackArmyCount)
                        .append("\n");
            } else {
                attackingCountry.reduceFromCountryArmyNumber(1);

                System.out.println("Round: " + i + ", >>>Defender win! attacker: " + attackingCountry.getCountryArmyNumber()
                        + ", defender: " + defendingCountry.getCountryArmyNumber() + "\n");

                attackArmyCount--;

                stringBuilder
                        .append("Round: ")
                        .append(i)
                        .append("\n>>>[ ")
                        .append(attackingCountry.getCountryName())
                        .append(" ]<<< LOSES to ")
                        .append(">>>[ ")
                        .append(defendingCountry.getCountryName())
                        .append(" ]<<<\nattacker remaining army num: ")
                        .append(attackingCountry.getCountryArmyNumber())
                        .append(", defender remaining army num: ")
                        .append(defendingCountry.getCountryArmyNumber())
                        .append("\n")
                        .append("attack army count after reduction: ")
                        .append(attackArmyCount)
                        .append(", defend army count: ")
                        .append(defendArmyCount)
                        .append("\n");
            }
        }
        result = attackArmyCount;

        return result;
    }

    /**
     * overall attack process result and it checks if country ,continent or whole map is conquered
     * @param attackingCountry
     * @param defendingCountry
     * @param remainingArmyNbr
     */
    private static void attackResultProcess(Country attackingCountry, Country defendingCountry, int remainingArmyNbr) {
        int defenderIndex = defendingCountry.getCountryOwnerIndex();
        int attackerIndex = attackingCountry.getCountryOwnerIndex();

        Player attackPlayer = Main.playersList.get(attackerIndex);
        Player defendPlayer = Main.playersList.get(defenderIndex);

        String continentName = defendingCountry.getContinentName();
        Continent curContinent = Main.worldContinentMap.get(continentName);

        if (isCountryConquered(defendingCountry)) {
            updateConqueredCountry(attackingCountry, defendingCountry, remainingArmyNbr, attackPlayer, defendPlayer);

            String defendCountryName = defendingCountry.getCountryName();
            int attackCountryArmyNbr = attackingCountry.getCountryArmyNumber();

            if (attackCountryArmyNbr > 1) {
                List<Integer> choices = IntStream.range(0, attackCountryArmyNbr).boxed().collect(Collectors.toList());

                System.out.println("attacking remaining army: " + attackCountryArmyNbr + ", remaining: " + remainingArmyNbr + ", choices: " + choices);

                ChoiceDialog<Integer> dialog = new ChoiceDialog<>(0, choices);
                dialog.setTitle("Army deployment");
                dialog.setHeaderText("Please input number of army for moving to \"" + defendCountryName + "\"");
                dialog.setContentText("Choose a number: ");

                Optional<Integer> dialogInput = dialog.showAndWait();
                if (dialogInput.isPresent()) {
                    int deployArmyNbr = dialogInput.get();

                    System.out.println("input deploy number: " + deployArmyNbr);

                    if (deployArmyNbr > 0) {
                        attackingCountry.reduceFromCountryArmyNumber(deployArmyNbr);
                        defendingCountry.addToCountryArmyNumber(deployArmyNbr);
                    }
                }
            }

            updateContinentAndWorldStatus(attackerIndex, defenderIndex, curContinent);

        }
    }

    private static void updateContinentAndWorldStatus(int attackerIndex, int defenderIndex, Continent curContinent) {
        String continentName = curContinent.getContinentName();
        if (isContinentConquered(defenderIndex, continentName)) {
            Player defenderPlayer = Main.playersList.get(defenderIndex);

            int continentBonus = curContinent.getContinentBonusValue();

            defenderPlayer.reduceContinentBonus(continentBonus);
            curContinent.setContinentOwnerIndex(-1);

            updateContinentOwner(attackerIndex, continentName);
        }
        if (isContinentConquered(attackerIndex, continentName)) {
            curContinent.setContinentOwnerIndex(attackerIndex);
            Player attackerPlayer = Main.playersList.get(attackerIndex);

            int continentBonus = curContinent.getContinentBonusValue();

            attackerPlayer.addContinentBonus(continentBonus);

            System.out.println("player: " + attackerIndex + " added continent bonus: " + continentBonus + ", total now: " + attackerPlayer.getContinentBonus());

            updateWorldOwner(attackerIndex);
        }
    }

    /**
     * If successfully conquered, defending country will notify players for changes
     * but attacking country would not, for its changes will not affect players.
     * To see specific changes: see Player.java where player acts as a observer.
     * @see Player#update(Observable, Object)
     * @param attackingCountry
     * @param defendingCountry
     * @param remainingArmyNbr
     * @param attackPlayer
     * @param defendPlayer
     */
    private static void updateConqueredCountry(Country attackingCountry, Country defendingCountry, int remainingArmyNbr, Player attackPlayer, Player defendPlayer) {
        String defendCountryName = defendingCountry.getCountryName();
        int attackerIndex = attackPlayer.getPlayerIndex();
        System.out.printf("Before battle: attacker owned %d countries\n", attackPlayer.getOwnedCountryNameList().size());

//        attackPlayer.getOwnedCountryNameList().add(defendCountryName);
//        defendPlayer.getOwnedCountryNameList().remove(defendCountryName);
        defendingCountry.setObservableOwner(attackerIndex);
        defendingCountry.setObservableArmy(remainingArmyNbr);
        defendingCountry.notifyObservers("Conquered a country");
        attackingCountry.reduceFromCountryArmyNumber(remainingArmyNbr);

        System.out.println("\n>>>>>>>>>>defender owned countries: " + defendPlayer.getOwnedCountryNameList() + "\n");

        if (!isPlayerHasCountry(defendPlayer)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Player [" + defendPlayer.getPlayerIndex() + "] has no country, QUIT!");
            alert.showAndWait();

            System.out.println("Player: " + defendPlayer.getPlayerIndex() + " fails, QUIT!");

            defendPlayer.setActiveStatus(false);
        }


//        Main.playerDomiViewObservable.updateObservable();
//        Main.playerDomiViewObservable.notifyObservers("Battle Report...");

        System.out.printf("After battle: attacker owned %d countries\n", attackPlayer.getOwnedCountryNameList().size());
    }

    private static boolean isPlayerHasCountry(Player player) {
        int ownedCountryNbr = player.getOwnedCountryNameList().size();

        System.out.println("\nPlayer: " + player.getPlayerIndex() + " has country num: " + ownedCountryNbr);

        return ownedCountryNbr > 0;
    }


    /**
     * get the random dice result list
     *
     * @param diceTimes number of dicing rolls
     * @return a new arrayList of Integer
     */
    public static ArrayList<Integer> getDiceResultList(int diceTimes) {
        ArrayList<Integer> result;
        Dice dice = new Dice();
        result = dice.rollNDice(diceTimes);

        return result;
    }


    public static boolean isCountryConquered(Country country) {
        boolean result = false;
        int remainingArmyNbr = country.getCountryArmyNumber();
        if (remainingArmyNbr == 0) {
            result = true;
        }

        System.out.println("country conquered: " + result);

        return result;
    }

    public static boolean isContinentConquered(int playerIndex, String continentName) {
        boolean result = true;
        Continent curContinent = Main.worldContinentMap.get(continentName);

        System.out.println("inside isContinentConquered, current owner: " + curContinent.getContinentOwnerIndex() + " - "
                + curContinent.getContinentCountryNameList());

        for (Map.Entry<String, Country> entry : curContinent.getContinentCountryGraph().entrySet()) {
            if (entry.getValue().getCountryOwnerIndex() != playerIndex) {
                result = false;
                break;
            }
        }

        if (result) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Contient conquered!");
            alert.setContentText("Contient [ " + continentName + " ] is conquered by Player [ " + playerIndex + " ]! "
                    + "  Bonus: [" + curContinent.getContinentBonusValue() + "]");
            alert.showAndWait();

            System.out.println("\n>>>>>>>>>>>>>>>Continent: [" + continentName + "] is conquered by player [" + playerIndex + "]\n "
                    + " current player continent bonus: " + Main.playersList.get(playerIndex).getContinentBonus());
        }

        return result;
    }

    private static void updateContinentOwner(int playerIndex, String continentName) {
        boolean result = true;
        Continent curContinent = Main.worldContinentMap.get(continentName);
        LinkedHashMap<String, Country> continentCountryGraph = curContinent.getContinentCountryGraph();

        for (Map.Entry<String, Country> entry : continentCountryGraph.entrySet()) {
            int curOwnerIndex = entry.getValue().getCountryOwnerIndex();
            if (curOwnerIndex != playerIndex) {
                result = false;
                break;
            }
        }

        if (result == true) {
            curContinent.setContinentOwnerIndex(playerIndex);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Contient conquered!");
            alert.setContentText("Contient [ " + continentName + " ] is conquered by Player [ " + playerIndex + " ]!  "
                    + " Bonus: [" + curContinent.getContinentBonusValue() + "]");
            alert.showAndWait();
        }

    }

    public static void updateWorldOwner(int playerIndex) {
        boolean result = true;

        for (Map.Entry<String, Continent> entry : Main.worldContinentMap.entrySet()) {
            int curOwnerIndex = entry.getValue().getContinentOwnerIndex();
            if (curOwnerIndex != playerIndex) {
                result = false;
                break;
            }
        }
        if (result == true) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Game Over!");
            alert.setContentText("Player [" + playerIndex + "] conquers the world! Game Over!");
            alert.showAndWait();

            winnerPlayerIndex = playerIndex;
        }
    }


}
