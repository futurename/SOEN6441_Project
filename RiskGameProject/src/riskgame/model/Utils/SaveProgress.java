package riskgame.model.Utils;

import riskgame.Main;
import riskgame.controllers.StartViewController;
import riskgame.model.BasicClass.Card;
import riskgame.model.BasicClass.Country;
import riskgame.model.BasicClass.ObserverPattern.PhaseViewObservable;

import java.io.*;
import java.util.ArrayList;

/**
 * save game class
 *
 * @author YW
 */
public class SaveProgress {

    /**
     * save file method
     * @param phase current phase name
     * @param curPlayer current player
     * @param path file path
     * @param mapName name
     * @param AorF Attack and Fortification parameter if attack means whether current player get card or not,if fortification is whether player has done fortification or not
     * @param exchangetime count card exchange time
     * @param allocateArmyNumber allocateArmyNumber
     * @throws IOException file not found
     */
    public void SaveFile(String phase,int curPlayer,String path,String mapName,boolean AorF,int exchangetime,
                         int allocateArmyNumber) throws IOException {
        File writename = new File(path+"\\"+mapName+".save");
        System.out.println(writename);
        writename.createNewFile();
        BufferedWriter out = new BufferedWriter(new FileWriter(writename));
        out.write("[Save]\r\n");
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

        out.write("[Connection]\r\n");
        out.write(numberOfPlayers+"\r\n");
        for(int i=0;i<numberOfPlayers;i++){
            String playerOwnCountry = Main.playersList.get(i).getOwnedCountryNameList().toString();
            playerOwnCountry = playerOwnCountry.replaceAll("\\[","");
            playerOwnCountry = playerOwnCountry.replaceAll("\\]","");
            playerOwnCountry = playerOwnCountry.replaceAll(", ",",");
            out.write(Main.playersList.get(i).getPlayerIndex()+","+playerOwnCountry+"\r\n");
        }
        out.write("\r\n");

        out.write("[Phase]\r\n");
        out.write( StartViewController.reinforceInitCounter+","+StartViewController.firstRoundCounter+","+Main.curRoundPlayerIndex+","+exchangetime+","+phase+" Phase,"+curPlayer+",");


        if(phase.equals("Reinforcement")) {
            for (int k = 0; k < Main.playersList.size(); k++) {
                if (curPlayer==Main.playersList.get(k).getPlayerIndex()) {
                    int undeployedArmyMinusAllocateArmy = Main.playersList.get(k).getUndeployedArmy()-allocateArmyNumber;
                    out.write( undeployedArmyMinusAllocateArmy + "\r\n");
                    break;
                }
            }
        }
        else if(phase.equals("Attack")){
            out.write(AorF+"");
        }
        else if(phase.equals("Fortification")){
            out.write(AorF+"");
        }

    out.flush();
    out.close();
    }

    /**
     * check save progress
     * @param mapPath map path
     * @return
     */
    public boolean CheckSave(String mapPath){

        boolean checkresult;
        boolean checkresult1 = false;
        boolean checkresult2 = false;
        boolean checkresult3 = false;
        boolean checkresult4 = false;
        boolean checkresult5 = false;
        boolean checkresult6 = false;

        ArrayList<String> fileRead = new ArrayList<String>();
        try {
            File file=new File(mapPath);
            if(file.isFile()&&file.exists()){
                InputStreamReader read = new InputStreamReader(new FileInputStream(file));
                BufferedReader bufferedReader=new BufferedReader(read);
                String lineTxt;
                while((lineTxt=bufferedReader.readLine())!=null){
                    fileRead.add(lineTxt);
                }
                read.close();
            }else{
                System.out.println("cannot find file");
            }
        } catch (Exception e){
            System.out.println("wrong");
            e.printStackTrace();
        }
        for(int i = 0;i<fileRead.size();i++){
            if(fileRead.get(i).contains("[Save]")){
                checkresult1 = true;
            }
            if(fileRead.get(i).contains("[Continents]")){
                checkresult2 = true;
            }
            if(fileRead.get(i).contains("[Territories]")) {
                checkresult3 = true;
            }
            if(fileRead.get(i).contains("[Players]")) {
                checkresult4 = true;
            }
            if(fileRead.get(i).contains("[Connection]")) {
                checkresult5 = true;
            }
            if(fileRead.get(i).contains("[Phase]")) {
                checkresult6 = true;
            }


        }
        checkresult = checkresult1&&checkresult2&&checkresult3&&checkresult4&&checkresult5&&checkresult6;
        return checkresult;

    }
}
