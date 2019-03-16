package riskgame.model.Utils;

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

public class AttackProcess {

    public static void alloutAttackSimulate(Country attackingCountry, Country defendingCountry, int attackArmyNbr, int defendArmyNbr,
                                            TextArea txa_attackInfoDisplay){

    }

    public static void attackSimulate(Country attackingCountry, Country defendingCountry, int attackArmyNbr, int defendArmyNbr,
                                      TextArea txa_attackInfoDisplay) {
        StringBuilder stringBuilder = new StringBuilder();

        ArrayList<Integer> attackerDiceResultList = getDiceResultList(attackArmyNbr);
        ArrayList<Integer> defenderDiceResultList = getDiceResultList(defendArmyNbr);

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
                        .append(", >>>[ ")
                        .append(attackingCountry.getCountryName())
                        .append(" ]<<< wins ")
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
                        .append(", >>>[ ")
                        .append(attackingCountry.getCountryName())
                        .append(" ]<<< loses to ")
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

        txa_attackInfoDisplay.setText(stringBuilder.toString());

        if (defendArmyCount == 0) {
            attackResultProcess(attackingCountry, defendingCountry, attackArmyCount);
        }

    }

    private static void attackResultProcess(Country attackingCountry, Country defendingCountry, int remainingArmyNbr) {
        int defenderIndex = defendingCountry.getCountryOwnerIndex();
        int attackerIndex = attackingCountry.getCountryOwnerIndex();

        Player attackPlayer = Main.playersList.get(attackerIndex);
        Player defendPlayer = Main.playersList.get(defenderIndex);

        String continentName = defendingCountry.getContinentName();
        Continent curContinent = Main.worldContinentMap.get(continentName);

        if (AttackProcess.isCountryConquered(defendingCountry)) {
            defendingCountry.setCountryOwnerIndex(attackingCountry.getCountryOwnerIndex());

            String defendCountryName = defendingCountry.getCountryName();
            attackPlayer.getOwnedCountryNameList().add(defendCountryName);
            defendPlayer.getOwnedCountryNameList().remove(defendCountryName);
            defendingCountry.setCountryOwnerIndex(attackerIndex);

            defendingCountry.setCountryArmyNumber(remainingArmyNbr);
            attackingCountry.reduceFromCountryArmyNumber(remainingArmyNbr);

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

            if (AttackProcess.isContinentConquered(defenderIndex, continentName)) {
                Player defenderPlayer = Main.playersList.get(defenderIndex);

                int continentBonus = curContinent.getContinentBonusValue();

                defenderPlayer.reduceContinentBonus(continentBonus);
                curContinent.setContinentOwnerIndex(-1);

                AttackProcess.updateContinentOwner(attackerIndex, continentName);

            }
            if (AttackProcess.isContinentConquered(attackerIndex, continentName)) {
                curContinent.setContinentOwnerIndex(attackerIndex);
                Player attackerPlayer = Main.playersList.get(attackerIndex);

                int continentBonus = curContinent.getContinentBonusValue();

                attackerPlayer.addContinentBonus(continentBonus);

                AttackProcess.updateWorldOwner(attackerIndex);
            }

        }
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
        LinkedHashMap<String, Country> continentCountryGraph = curContinent.getContinentCountryGraph();

        for (Map.Entry<String, Country> entry : continentCountryGraph.entrySet()) {
            int curOwnerIndex = entry.getValue().getCountryOwnerIndex();
            if (curOwnerIndex != playerIndex) {
                result = false;
                break;
            }
        }

        System.out.println("continent conquered: " + result);

        return result;
    }

    public static void updateContinentOwner(int attackerIndex, String continentName) {
        boolean result = true;
        Continent curContinent = Main.worldContinentMap.get(continentName);
        LinkedHashMap<String, Country> continentCountryGraph = curContinent.getContinentCountryGraph();

        for (Map.Entry<String, Country> entry : continentCountryGraph.entrySet()) {
            int curOwnerIndex = entry.getValue().getCountryOwnerIndex();
            if (curOwnerIndex != attackerIndex) {
                result = false;
                break;
            }
        }
        if (result == true) {
            curContinent.setContinentOwnerIndex(attackerIndex);
        }

    }

    public static void updateWorldOwner(int attackerIndex) {
        boolean result = true;

        for (Map.Entry<String, Continent> entry : Main.worldContinentMap.entrySet()) {
            int curOwnerIndex = entry.getValue().getContinentOwnerIndex();
            if (curOwnerIndex != attackerIndex) {
                result = false;
                break;
            }
        }
        if (result == true) {
            System.out.println(attackerIndex + "wins");
        }

    }
}
