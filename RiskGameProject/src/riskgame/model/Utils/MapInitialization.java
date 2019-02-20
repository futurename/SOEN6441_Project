package riskgame.model.Utils;

import riskgame.Main;
import riskgame.model.BasicClass.Continent;
import riskgame.model.BasicClass.Country;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

/**
 * model class that includes methods for world map initialization
 *
 * @author WW
 */
public class MapInitialization {
    private static final String CONTINENT_ID_LOWER_CASE_STRING = "continents";
    private static final String COUNTRY_ID_LOWER_CASE_STRING = "territories";
    private static final int FIRST_POS_OF_ADJACENT_LIST = 4;

    /**
     * @param mapPath path of the map file
     * @throws IOException map file not found
     */
    public static void buildWorldMap(String mapPath) throws IOException {
        System.out.println(new File(mapPath).getAbsolutePath());

        BufferedReader bufferedReader = new BufferedReader(new FileReader(mapPath));

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
                    Main.worldContinentMap.put(curContinnentName, oneContinent);
                }
            }

            //read country section
            if (curLine.toLowerCase().contains(COUNTRY_ID_LOWER_CASE_STRING)) {
                while ((curLine = bufferedReader.readLine()) != null) {
                    if (curLine.length() != 0) {
                        String[] curLineSplitArray = curLine.split(",");
                        String curCountryName = curLineSplitArray[0];
                        String curContinentName = curLineSplitArray[3];

                        Main.worldContinentMap.get(curContinentName).addToContinentCountryNameList(curCountryName);

                        Country oneCountry = new Country(curCountryName, curContinentName);
                        Main.worldCountriesMap.put(curCountryName, oneCountry);

                        for (int i = FIRST_POS_OF_ADJACENT_LIST; i < curLineSplitArray.length; i++) {
                            String nextAdjacentCountryName = curLineSplitArray[i];
                            oneCountry.addToAdjacentCountryNameList(nextAdjacentCountryName);
                        }
                    }
                }
            }
        }
        printMapInformation();
    }

    /**
     * print formatted world map
     */
    private static void printMapInformation() {
        for (Map.Entry<String, Continent> entry : Main.worldContinentMap.entrySet()) {
            Continent curContinent = entry.getValue();
            String curContinentName = entry.getKey();
            ArrayList<String> countryNamesInContinent = curContinent.getContinentCountryNameList();

            System.out.println("size: " + Main.worldContinentMap.size() + " >>>>>>>>>>>>>>>>>>>\n");
            System.out.println("[" + curContinentName + "]");
            System.out.println(countryNamesInContinent + "\n");
        }
    }
}
