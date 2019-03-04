package riskgame.model.Utils;

import riskgame.Main;
import riskgame.model.BasicClass.Continent;
import riskgame.model.BasicClass.Country;
import riskgame.model.BasicClass.GraphNode;

import java.io.*;
import java.util.Map;

/**
 * This class includes methods for initializing world map graph
 *
 * @author WW
 **/
public class InitMapGraph {
    /**
     * default string value for marking continent section in map file
     */
    static final String CONTINENT_HEADER_STRING = "[Continents]";

    /**
     * default string value for marking country section in map file
     */
    static final String COUNTRY_HEADER_STRING = "[Territories]";

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
     * @throws IOException map file not found
     */
    public static void buildWorldMapGraph(String path) throws IOException {
        System.out.println(new File(path).getAbsolutePath());

        BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
        String curLine;

        while ((curLine = bufferedReader.readLine()) != null) {
            if (curLine.contains(CONTINENT_HEADER_STRING)) {
                while ((curLine = bufferedReader.readLine()).length() != 0) {
                    String[] curLineSplitArray = curLine.split("=");
                    String curContinnentName = curLineSplitArray[0];
                    int curContinentBonusValue = Integer.parseInt(curLineSplitArray[1]);

                    //Initialize a new Continent object
                    Continent oneContinent = new Continent(curContinnentName, curContinentBonusValue);
                    Main.worldContinentMap.put(curContinnentName, oneContinent);
                }
            }

            if (curLine.contains(COUNTRY_HEADER_STRING)) {
                while ((curLine = bufferedReader.readLine()) != null) {
                    if (curLine.length() != 0) {
                        String[] curLineSplitArray = curLine.split(",");

                        String curCountryName = curLineSplitArray[0];
                        Country curCountry;
                        GraphNode curGraphNode;
                        if (!Main.graphSingleton.containsKey(curCountryName)) {
                            curCountry = new Country(curCountryName);
                            curGraphNode = new GraphNode(curCountry);
                        } else {
                            curGraphNode = Main.graphSingleton.get(curCountryName);
                            curCountry = curGraphNode.getCountry();
                        }

                        Main.graphSingleton.put(curCountryName, curGraphNode);

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

                            if (!Main.graphSingleton.containsKey(adjacentCountryName)) {
                                oneCountry = new Country(adjacentCountryName);
                                oneGraphNode = new GraphNode(oneCountry);
                            } else {
                                oneGraphNode = Main.graphSingleton.get(adjacentCountryName);
                                oneCountry = oneGraphNode.getCountry();
                            }

                            curGraphNode.addAdjacentCountry(oneCountry);
                            Main.graphSingleton.put(adjacentCountryName, oneGraphNode);
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
        for (Map.Entry<String, GraphNode> entry : Main.graphSingleton.entrySet()) {
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
}
