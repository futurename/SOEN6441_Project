package riskgame.model.Utils;

import riskgame.Main;
import riskgame.model.BasicClass.Continent;
import riskgame.model.BasicClass.Country;
import riskgame.model.BasicClass.GraphNode;
import java.io.*;
import java.util.Map;

/**
 * This class initialize the graph from a valid map file
 */
public class InitMapGraph {
    protected static final String CONTINENT_HEADER_STRING = "[Continents]";
    protected static final String COUNTRY_HEADER_STRING = "[Territories]";
    private static final int COORDINATE_X_POSITION = 1;
    private static final int COORDINATE_Y_POSITION = 2;
    private static final int CONTINENT_POSITION = 3;

    public static void buildWorldMapGraph(String path) throws IOException {
        System.out.println(new File(path).getAbsolutePath());

        BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
        String curLine;

        while ((curLine = bufferedReader.readLine()) != null) {

            //System.out.println("cur line: " + curLine);

            if (curLine.contains(CONTINENT_HEADER_STRING)) {
                while ((curLine = bufferedReader.readLine()).length() != 0) {
                    String[] curLineSplitArray = curLine.split("=");
                    String curContinnentName = curLineSplitArray[0];
                    int curContinentBonusValue = Integer.parseInt(curLineSplitArray[1]);

                    //Initialize a new Continent object
                    Continent oneContinent = new Continent(curContinnentName, curContinentBonusValue);
                    Main.worldContinentMap.put(curContinnentName, oneContinent);
                    continue;
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
                        Main.worldContinentMap.get(curContinentName).addToContinentCountryNameList(curCountryName);

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
    }

    private static void printContinent() {
        for(Map.Entry<String, Continent> entry: Main.worldContinentMap.entrySet()){
            Continent curContinent = entry.getValue();
            String curContinentName = entry.getKey();

            System.out.println("\n---[" + curContinentName + "]---");
            System.out.println(curContinent.getContinentCountryNameList());
        }
    }

    public static void printGraph() {
        for(Map.Entry<String, GraphNode> entry: Main.graphSingleton.entrySet()){
            String countryName = entry.getKey();
            GraphNode node = entry.getValue();
            System.out.println(">>>>>>>>>>>> country: " + countryName + ", continent: " + node.getCountry().getContinentName() + " <<<<<<<<<<<<<<<");
            printGraphNode(node);
        }
    }

    private static void printGraphNode(GraphNode node) {
        for(Country country: node.getAdjacentCountryList()){
            String countryName = country.getCountryName();
            System.out.printf("%s, ", countryName);
        }
        System.out.println("\n");
    }
}
