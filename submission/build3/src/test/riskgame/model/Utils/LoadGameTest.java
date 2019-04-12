package test.riskgame.model.Utils;

import javafx.scene.paint.Color;
import org.junit.Test;
import riskgame.Main;
import riskgame.controllers.StartViewController;
import riskgame.model.BasicClass.*;
import riskgame.model.BasicClass.StrategyPattern.*;;
import java.io.*;
import java.util.ArrayList;

import static riskgame.Main.*;
import static riskgame.Main.phaseViewObservable;
import static riskgame.Main.playersList;

public class LoadGameTest {

    @Test
    public void testLoadGame() throws IOException {
        String FilePath = "src\\test\\riskgame\\model\\Utils\\testcase.save";
        BufferedReader bufferedReader = new BufferedReader(new FileReader(FilePath));
        File writename = new File("./output.save");
        BufferedWriter out = new BufferedWriter(new FileWriter(writename));
        String curLine;
        while ((curLine = bufferedReader.readLine())!=null) {
            if (curLine.contains("[Players]")) {
                int playerNumber = Integer.parseInt(curLine = bufferedReader.readLine());
                for (int i = 0; i < playerNumber; i++) {
                    curLine = bufferedReader.readLine();
                    // Basic player info
                    String[] curLineSplitPlayerInfo = curLine.split(",");

                    int playerIndex = Integer.parseInt(curLineSplitPlayerInfo[0]);

                    boolean status = Boolean.parseBoolean(curLineSplitPlayerInfo[1]);

                    Color playerColor = Color.valueOf(curLineSplitPlayerInfo[3]);

                    int continentBonus = Integer.parseInt(curLineSplitPlayerInfo[4]);

                    int armyNbr = Integer.parseInt(curLineSplitPlayerInfo[5]);

                    ArrayList<Card> playerCards = new ArrayList<>();
                    if (curLineSplitPlayerInfo.length > 6) {
                        String cards = curLineSplitPlayerInfo[6];
                        String[] cardsArray = cards.split(";");
                        for (int j = 0; j < cardsArray.length; j++) {
                            if (cardsArray[j].equals("INFANTRY")) {
                                Card newCard = Card.INFANTRY;
                                playerCards.add(newCard);
                            } else if (cardsArray[j].equals("CAVALRY")) {
                                Card newCard = Card.CAVALRY;
                                playerCards.add(newCard);
                            } else if (cardsArray[j].equals("ARTILLERY")) {
                                Card newCard = Card.ARTILLERY;
                                playerCards.add(newCard);
                            }
                        }
                    }

                    // Owned countries
                    ArrayList<String> countryNameList = new ArrayList<>();
                    curLine = bufferedReader.readLine();
                    String[] curLineSplitCountryInfo = curLine.split(",");

                    for (int j = 0; j < curLineSplitCountryInfo.length; j++) {
                        countryNameList.add(curLineSplitCountryInfo[j]);
                    }
                    out.write(playerIndex+ " " + curLineSplitPlayerInfo[2] + " " + armyNbr +" " +playerCards.toString() +" "+
                            countryNameList.toString()+" "+playerColor.toString()+ "" + "\r\n");
                }
            }
        }
        out.flush();
        out.close();
    }
}
