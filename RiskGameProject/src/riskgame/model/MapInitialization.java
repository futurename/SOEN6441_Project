package riskgame.model;

import riskgame.Main;
import riskgame.classes.Continent;
import riskgame.classes.Country;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * This class reads map file and initialzie countries, continents, players etc.
 *
 * @author : Wei Wang
 */

public class MapInitialization {
    private static final String CONTINENT_ID_LOWER_CASE_STRING = "continents";
    private static final String COUNTRY_ID_LOWER_CASE_STRING = "territories";
    private static final int FIRST_POS_OF_ADJACENT_LIST = 4;

    public static void buildWorldMap(String mapPah) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(mapPah));

        String curLine;

        while ((curLine = bufferedReader.readLine()) != null) {

            //read continent section
            if (curLine.toLowerCase().contains(CONTINENT_ID_LOWER_CASE_STRING)) {

                //this condition is tested with "World.map", could be modified for other map files.
                while ((curLine = bufferedReader.readLine()).length() != 0) {
                    String[] curLineSplitArray = curLine.split("=");
                    String curContinnentName = curLineSplitArray[0];
                    int curContinentBonusValue = Integer.parseInt(curLineSplitArray[1]);

                    //Initialize a new Continent object
                    Continent oneContinent = new Continent(curContinnentName, curContinentBonusValue);
                    Main.worldContinentsList.add(oneContinent);
                    continue;
                }
            }

            //read country section
            if (curLine.toLowerCase().contains(COUNTRY_ID_LOWER_CASE_STRING)) {
                int continentSeqIndx = 0;


                while ((curLine = bufferedReader.readLine()) != null) {

                    if (curLine.length() != 0) {
                        String[] curLineSplitArray = curLine.split(",");
                        String curCountryName = curLineSplitArray[0];

                        Main.worldContinentsList.get(continentSeqIndx).addToContinentCountryNameList(curCountryName);
                        String curContinentName = Main.worldContinentsList.get(continentSeqIndx).getContinentName();
                        Country oneCountry = new Country(curCountryName, curContinentName);
                        Main.worldCountriesMap.put(curCountryName, oneCountry);

                        for (int i = FIRST_POS_OF_ADJACENT_LIST; i < curLineSplitArray.length; i++) {
                            String nextAdjacentCountryName = curLineSplitArray[i];
                            oneCountry.addToAdjacentCountryNameList(nextAdjacentCountryName);
                           /* if (!isFirstLetterNumber(nextAdjacentCountryName.charAt(0))) {
                                oneCountry.addToAdjacentCountryNameList(nextAdjacentCountryName);
                            }*/

                        }

                    } else {
                        continentSeqIndx++;
                    }
                }
            }
        }

        printMapInformation();
    }

    private static void printMapInformation() {
        for(int i = 0; i < Main.worldContinentsList.size(); i++){
            Continent curContinent = Main.worldContinentsList.get(i);
            System.out.println("["+curContinent.getContinentName() +"]");
            ArrayList<String> countryNamesInContinent = curContinent.getContinentCountryNameList();
            System.out.println(countryNamesInContinent + "\n");
        }
    }

    private static boolean isFirstLetterNumber(char ch) {
        return (ch >= '0' && ch <= '9') ? true : false;
    }
}