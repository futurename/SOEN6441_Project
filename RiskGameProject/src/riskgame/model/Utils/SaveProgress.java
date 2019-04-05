package riskgame.model.Utils;

import riskgame.Main;
import riskgame.model.BasicClass.Card;
import riskgame.model.BasicClass.Country;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class SaveProgress {

    public void SaveFile(String phase,int curPlayer,String path,String mapName,boolean AorF,boolean AOC) throws IOException {
        File writename = new File(path+"\\"+mapName+".map");
        System.out.println(writename);
        writename.createNewFile();
        BufferedWriter out = new BufferedWriter(new FileWriter(writename));
        out.write("[Map]\r\n");
        out.write("author=author\r\n");
        out.write("image=world.bmp\r\n");
        out.write("wrap=no\r\n");
        out.write("scroll=horizontal\r\n");
        out.write("warn=yes\r\n");
        out.write("\r\n");



        out.write("[Continents]\r\n");
        for(String continentName:Main.worldContinentMap.keySet()){
            out.write(Main.worldContinentMap.get(continentName).getContinentName()+"="+Main.worldContinentMap.get(continentName).getContinentBonusValue()+"\r\n");
        }
        out.write("\r\n");

        out.write("[Territories]\r\n");
        for(String continentName:Main.worldContinentMap.keySet()){
            for(String countryName:Main.worldContinentMap.get(continentName).getContinentCountryGraph().keySet()){
                String curCountryName = Main.worldContinentMap.get(continentName).getContinentCountryGraph().get(countryName).getCountryName();
                String curContinentName = Main.worldContinentMap.get(continentName).getContinentCountryGraph().get(countryName).getContinentName();
                int curPlayerIndex = Main.worldContinentMap.get(continentName).getContinentCountryGraph().get(countryName).getOwnerIndex();
                int curArmyNumber = Main.worldContinentMap.get(continentName).getContinentCountryGraph().get(countryName).getCountryArmyNumber();
                String neighbor = "";

                for(Country neighborCountry : Main.graphSingleton.get(countryName).getAdjacentCountryList()){
                    neighbor = neighbor+","+neighborCountry.getCountryName();
                }

                out.write(curCountryName+",0,0,"+curContinentName+","+curPlayerIndex+","+curArmyNumber+neighbor+"\r\n");
            }
            out.write("\r\n");
        }

        out.write("[Players]\r\n");
        int numberOfPlayers = Main.totalNumOfPlayers;
        out.write(numberOfPlayers+"\r\n");
        for(int i=0;i<numberOfPlayers;i++){
            String cardInfor = "";
            for(Card card: Main.playersList.get(i).getCardsList()){
                cardInfor = cardInfor+card+";";
            }
            out.write(Main.playersList.get(i).getPlayerIndex()+","+Main.playersList.get(i).getActiveStatus()+","+Main.playersList.get(i).getPlayerName()+","+Main.playersList.get(i).getPlayerColor()+","+Main.playersList.get(i).getContinentBonus()+","+Main.playersList.get(i).getArmyNbr()+","+cardInfor+"\r\n");
            String playerOwnCountry = Main.playersList.get(i).getOwnedCountryNameList().toString();
            playerOwnCountry = playerOwnCountry.replaceAll("\\[","");
            playerOwnCountry = playerOwnCountry.replaceAll("\\]","");
            playerOwnCountry = playerOwnCountry.replaceAll(", ",",");
            out.write(playerOwnCountry+"\r\n");
        }
        out.write("\r\n");

        out.write("[Phase]\r\n");
        out.write(phase+","+curPlayer+",");

        if(phase.equals("Reinforcement")) {
            for (int k = 0; k < Main.playersList.size(); k++) {
                if (curPlayer==Main.playersList.get(k).getPlayerIndex()) {
                    out.write(Main.playersList.get(k).getUndeployedArmy() + "\r\n");
                    break;
                }
            }
        }
        else if(phase.equals("Attack")){
            out.write(AorF+","+AOC);
        }
        else if(phase.equals("Fortification")){
            out.write(AorF+"");
        }

    out.flush();
    out.close();
    }

}
