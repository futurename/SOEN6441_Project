package riskgame.model.Utils;

import riskgame.Main;
import riskgame.model.BasicClass.Continent;
import riskgame.model.BasicClass.Country;
import riskgame.model.BasicClass.GraphNode;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import static riskgame.Main.graphSingleton;

/**
 * @author WW
 * @since build1
 */
public class InitWorldMap {
    /**
     * default string value for marking continent section in map file
     */
    private static final String CONTINENT_HEADER_STRING = "[Continents]";

    /**
     * default string value for marking country section in map file
     */
    private static final String COUNTRY_HEADER_STRING = "[Territories]";

    /**
     * default position index for coordinate x in map file
     */
    private static final int COORDINATE_X_POSITION = 1;

    /**
     * default position index for coordinate y in map file
     */
    private static final int COORDINATE_Y_POSITION = 2;

    /**
     * default position index for indicating continent in map file
     */
    private static final int CONTINENT_POSITION = 3;

    /**
     * this method read and initialize world map
     *
     * @param path path of map file
     * @param graphSingleton world map graph singleton
     * @throws IOException map file not found
     */
    public static void buildWorldMapGraph(String path, LinkedHashMap<String, GraphNode> graphSingleton) throws IOException {
        System.out.println(new File(path).getAbsolutePath());

        BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
        String curLine;

        while ((curLine = bufferedReader.readLine()) != null) {
            if (curLine.contains(CONTINENT_HEADER_STRING)) {
                while ((curLine = bufferedReader.readLine()).length() != 0) {
                    String[] curLineSplitArray = curLine.split("=");
                    String curContinentName = curLineSplitArray[0];
                    int curContinentBonusValue = Integer.parseInt(curLineSplitArray[1]);

                    //Initialize a new Continent object
                    Continent oneContinent = new Continent(curContinentName, curContinentBonusValue);
                    Main.worldContinentMap.put(curContinentName, oneContinent);
                }
            }

            if (curLine.contains(COUNTRY_HEADER_STRING)) {
                while ((curLine = bufferedReader.readLine()) != null) {
                    if (curLine.length() != 0) {
                        String[] curLineSplitArray = curLine.split(",");

                        String curCountryName = curLineSplitArray[0];
                        Country curCountry;
                        GraphNode curGraphNode;
                        if (!graphSingleton.containsKey(curCountryName)) {
                            curCountry = new Country(curCountryName);
                            curGraphNode = new GraphNode(curCountry);
                        } else {
                            curGraphNode = graphSingleton.get(curCountryName);
                            curCountry = curGraphNode.getCountry();
                        }

                        graphSingleton.put(curCountryName, curGraphNode);

                        String curCoordinateX = curLineSplitArray[COORDINATE_X_POSITION];
                        String curCoordinateY = curLineSplitArray[COORDINATE_Y_POSITION];
                        curCountry.setCoordinateX(curCoordinateX);
                        curCountry.setCoordinateY(curCoordinateY);

                        String curContinentName = curLineSplitArray[CONTINENT_POSITION];
                        curCountry.setContinentName(curContinentName);
                        Main.worldContinentMap.get(curContinentName).getContinentCountryGraph().put(curCountryName, curCountry);

                        for (int i = CONTINENT_POSITION + 1; i < curLineSplitArray.length; i++) {
                            Country oneCountry;
                            GraphNode oneGraphNode;
                            String adjacentCountryName = curLineSplitArray[i];

                            if (!graphSingleton.containsKey(adjacentCountryName)) {
                                oneCountry = new Country(adjacentCountryName);
                                oneGraphNode = new GraphNode(oneCountry);
                            } else {
                                oneGraphNode = graphSingleton.get(adjacentCountryName);
                                oneCountry = oneGraphNode.getCountry();
                            }

                            curGraphNode.addAdjacentCountry(oneCountry);
                            graphSingleton.put(adjacentCountryName, oneGraphNode);
                        }
                    }
                }
            }
        }
        printGraph();
        printContinent();
        bufferedReader.close();

    }

    /**
     * this method prints continents and their countries in console
     */
    private static void printContinent() {
        for (Map.Entry<String, Continent> entry : Main.worldContinentMap.entrySet()) {
            Continent curContinent = entry.getValue();
            String curContinentName = entry.getKey();

            System.out.println("\n---[" + curContinentName + "]---");
            System.out.println(curContinent);
        }
    }

    /**
     * this method prints world map graph in console
     */
    private static void printGraph() {
        for (Map.Entry<String, GraphNode> entry : graphSingleton.entrySet()) {
            String countryName = entry.getKey();
            GraphNode node = entry.getValue();
            System.out.println(">>>>>>>>>>>> country: " + countryName + ", continent: " + node.getCountry().getContinentName() + " <<<<<<<<<<<<<<<");
            printGraphNode(node);
        }
    }

    /**
     * this method prints informaton of selected graph node
     *
     * @param node selected graph node
     */
    private static void printGraphNode(GraphNode node) {
        for (Country country : node.getAdjacentCountryList()) {
            String countryName = country.getCountryName();
            System.out.printf("%s, ", countryName);
        }
        System.out.println("\n");
    }

    /**
     * getter
     *
     * @return default continent header string
     */
    public static String getContinentHeaderString() {
        return CONTINENT_HEADER_STRING;
    }

    /**
     * getter
     *
     * @return default country header string
     */
    public static String getCountryHeaderString() {
        return COUNTRY_HEADER_STRING;
    }
}
