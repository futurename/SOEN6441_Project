package riskgame.model.Utils;

import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceDialog;
import riskgame.Main;
import riskgame.model.BasicClass.Continent;
import riskgame.model.BasicClass.Country;
import riskgame.model.BasicClass.Dice;
import riskgame.model.BasicClass.Player;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * This class processes the attack
 * checks that how many dice can be tron and how many dice user want to throw
 * checks if defender wins or attacker
 * checks if country,continent or whole map is conquered or not
 * @author Karamveer
 * @since build2
 */
public class AttackProcess {

    public static int winnerPlayerIndex = -1;


    /**
     * overall attack process result and it checks if country ,continent or whole map is conquered
     * @param attackingCountry attacking country
     * @param defendingCountry defending country
     * @param remainingArmyNbr army number ramained after attacking
     */
    public static void attackResultProcess(Country attackingCountry, Country defendingCountry, int remainingArmyNbr) {
        int defenderIndex = defendingCountry.getCountryOwnerIndex();
        int attackerIndex = attackingCountry.getCountryOwnerIndex();

        Player attackPlayer = Main.playersList.get(attackerIndex);
        Player defendPlayer = Main.playersList.get(defenderIndex);

        String continentName = defendingCountry.getContinentName();
        Continent curContinent = Main.worldContinentMap.get(continentName);

        if (isCountryConquered(defendingCountry)) {
            updateConqueredCountry(attackingCountry, defendingCountry, remainingArmyNbr, attackPlayer, defendPlayer, true);

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

            updateContinentAndWorldStatus(attackPlayer, defendPlayer, curContinent, true);

        }
    }

    /**
     * Same function as attackResultProcess but eliminate UI.
     * @see AttackProcess#attackResultProcess
     */
    public static void autoResultProcess(Country attackingCountry, Country defendingCountry, int remainingArmyNbr){
        int defenderIndex = defendingCountry.getCountryOwnerIndex();
        int attackerIndex = attackingCountry.getCountryOwnerIndex();

        Player attackPlayer = Main.playersList.get(attackerIndex);
        Player defendPlayer = Main.playersList.get(defenderIndex);

        String continentName = defendingCountry.getContinentName();
        Continent curContinent = Main.worldContinentMap.get(continentName);

        if (isCountryConquered(defendingCountry)) {
            updateConqueredCountry(attackingCountry, defendingCountry, remainingArmyNbr, attackPlayer, defendPlayer, false);

            int attackCountryArmyNbr = attackingCountry.getCountryArmyNumber();

            if (attackCountryArmyNbr > 1) {
                attackingCountry.reduceFromCountryArmyNumber(attackCountryArmyNbr-1);
                defendingCountry.addToCountryArmyNumber(attackCountryArmyNbr-1);
            }
            updateContinentAndWorldStatus(attackPlayer, defendPlayer, curContinent, false);
        }
    }

    /**
     * this method updates the status of country and world if these are conqured and changes the owner
     * @param attacker attacking player
     * @param defender defending player
     * @param curContinent current continent the country locates
     */
    public static void updateContinentAndWorldStatus(Player attacker, Player defender, Continent curContinent, boolean UIOption) {
        int attackerIndex = attacker.getPlayerIndex();
        int defenderIndex = defender.getPlayerIndex();
        String continentName = curContinent.getContinentName();

        System.out.println("\n\nContinent owner: " + curContinent.getContinentOwnerIndex() + " before evaluating!\n\n");

        if (curContinent.getContinentOwnerIndex() == defenderIndex) {
            int continentBonus = curContinent.getContinentBonusValue();

            defender.reduceContinentBonus(continentBonus);
            curContinent.setContinentOwnerIndex(-1);

            updateContinentOwner(attacker, curContinent, UIOption);
        }
        if (isContinentConquered(attacker, curContinent)) {
            curContinent.setContinentOwnerIndex(attackerIndex);
            int continentBonus = curContinent.getContinentBonusValue();

            attacker.addContinentBonus(continentBonus);

            System.out.println("player: " + attackerIndex + " added continent bonus: " + continentBonus + ", total now: " + attacker.getContinentBonus());

            if (UIOption){
                popupContinentConqueredAlert(attackerIndex, continentName, curContinent);
                updateWorldOwner(attackerIndex);
            }else {
                if (isWorldConquered(attackerIndex)){
                    winnerPlayerIndex = attackerIndex;
                }
            }
        }
    }

    /**
     * If successfully conquered, defending country will notify players for changes
     * but attacking country would not, for its changes will not affect players.
     * To see specific changes: see Player.java where player acts as a observer.
     * @see Player#update(Observable, Object)
     * @param attackingCountry attacking country
     * @param defendingCountry defending country
     * @param remainingArmyNbr remaining army number of the attacker after attack
     * @param attackPlayer attacker
     * @param defendPlayer defender
     */
    public static void updateConqueredCountry(Country attackingCountry, Country defendingCountry, int remainingArmyNbr, Player attackPlayer, Player defendPlayer, boolean UIOption) {
        String defendCountryName = defendingCountry.getCountryName();
        int attackerIndex = attackPlayer.getPlayerIndex();
        System.out.printf("Before battle: attacker owned %d countries\n", attackPlayer.getOwnedCountryNameList().size());

        defendingCountry.setObservableOwner(attackerIndex);
        defendingCountry.setObservableArmy(remainingArmyNbr);
        defendingCountry.notifyObservers("Conquered a country");
        attackingCountry.reduceFromCountryArmyNumber(remainingArmyNbr);

        System.out.println("\n>>>>>>>>>>defender owned countries: " + defendPlayer.getOwnedCountryNameList() + "\n");

        if (!isPlayerHasCountry(defendPlayer)) {
            if (UIOption){
                try {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("Player [" + defendPlayer.getPlayerIndex() + "] has no country, QUIT!");
                    alert.showAndWait();
                }catch (Error e){
                    System.out.println("Mute Alert");
                }
            }
            System.out.println("Player: " + defendPlayer.getPlayerIndex() + " fails, QUIT!");
            defendPlayer.setActiveStatus(false);
        }

        System.out.printf("After battle: attacker owned %d countries\n", attackPlayer.getOwnedCountryNameList().size());
    }


    public static boolean isPlayerHasCountry(Player player) {
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

    public static boolean isContinentConquered(Player player, Continent curContinent) {
        boolean result = true;
        int playerIndex = player.getPlayerIndex();

        System.out.println("inside isContinentConquered, current owner: " + curContinent.getContinentOwnerIndex() + " - "
                + curContinent.getContinentCountryNameList());

        for (Map.Entry<String, Country> entry : curContinent.getContinentCountryGraph().entrySet()) {
            if (entry.getValue().getCountryOwnerIndex() != playerIndex) {
                result = false;
                break;
            }
        }
        System.out.println("continent conquered? result: " + result);

        return result;
    }

    private static void popupContinentConqueredAlert(int playerIndex, String continentName, Continent curContinent) {
        try{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Continent conquered!");
            alert.setContentText("Continent [ " + continentName + " ] is conquered by Player [ " + playerIndex + " ]! "
                    + "  Bonus: [" + curContinent.getContinentBonusValue() + "]");
            alert.showAndWait();
        }catch (Error e){
            System.out.println("Mute alter in popupContinentConqueredAlert");
        }
        System.out.println("\n>>>>>>>>>>>>>>>Continent: [" + continentName + "] is conquered by player [" + playerIndex + "]\n "
                + " current player continent bonus: " + Main.playersList.get(playerIndex).getContinentBonus());
    }

    private static void updateContinentOwner(Player player, Continent curContinent, boolean UIOption) {
        boolean result = true;
        int playerIndex = player.getPlayerIndex();
        LinkedHashMap<String, Country> continentCountryGraph = curContinent.getContinentCountryGraph();

        for (Map.Entry<String, Country> entry : continentCountryGraph.entrySet()) {
            int curOwnerIndex = entry.getValue().getCountryOwnerIndex();
            if (curOwnerIndex != playerIndex) {
                result = false;
                break;
            }
        }

        if (result) {
            curContinent.setContinentOwnerIndex(playerIndex);
            if (UIOption){
                try {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText("Contient conquered!");
                    alert.setContentText("Contient [ " + curContinent.getContinentName() + " ] is conquered by Player [ " + playerIndex + " ]!  "
                            + " Bonus: [" + curContinent.getContinentBonusValue() + "]");
                    alert.showAndWait();
                }catch (Error e){
                    System.out.println("Mute alter updateContinentOwner");
                }
            }
        }else{
            curContinent.setContinentOwnerIndex(-1);
        }
    }

    private static void updateWorldOwner(int playerIndex) {
        boolean result = isWorldConquered(playerIndex);
        if (result) {
            try {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Game Over!");
                alert.setContentText("Player [" + playerIndex + "] conquers the world! Game Over!");
                alert.showAndWait();
            }catch (Error e){
                System.out.println("Mute alter in updateWorldOwner");
            }
            winnerPlayerIndex = playerIndex;
        }
    }

    public static boolean isWorldConquered(int playerIndex) {
        boolean result = true;
        for (Map.Entry<String, Continent> entry : Main.worldContinentMap.entrySet()) {
            int curOwnerIndex = entry.getValue().getContinentOwnerIndex();
            if (curOwnerIndex != playerIndex) {
                result = false;
                break;
            }
        }
        return result;
    }
}
