package GUI_Test.Model;

import GUI_Test.Class.Continent;
import GUI_Test.Class.Country;
import GUI_Test.Main;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public class InitMap {
    public static final String STR_CONTINENT = "Continents";
    public static final String STR_COUNTRY_INFO = "Territories";
    //public static final String LINE_SPLIT = "\n";

    public static void buildMap(String path) throws IOException {

        BufferedReader bfr = new BufferedReader(new FileReader(path));
        String curLine;
        boolean isReadFinish = false;

        while (!isReadFinish) {
            curLine = bfr.readLine();

            if (curLine.contains(STR_CONTINENT)) {

                //System.out.println(curLine);

                while ((curLine = bfr.readLine()).length() != 0) {

                    //System.out.println(curLine.length());

                    String[] curLineSplitArray = curLine.split("=");

                   //System.out.println("continent name: " + curLineSplitArray[0] + ", bonus: " + curLineSplitArray[1]);

                    Continent oneContinent = new Continent(curLineSplitArray[0]);
                    oneContinent.setBonusValue(Integer.parseInt(curLineSplitArray[1]));
                    Main.continentList.add(oneContinent);

                    continue;
                }
                continue;
            }
            if (curLine.contains(STR_COUNTRY_INFO)) {
                //curLine = bfr.readLine();

                //System.out.println(curLine);

                int continentCounter = 0, continentCountriesCounter = 0;
                while ((curLine = bfr.readLine()) != null) {

                    //System.out.println(curLine);

                    if (curLine.length() != 0) {
                        String[] countriesSplitArray = curLine.split(",");
                        Country oneCountry = new Country(countriesSplitArray[0]);
                        oneCountry.setContinentname(countriesSplitArray[3]);


                        Main.continentList.get(continentCounter).getCountryList().add(oneCountry.getCountryname());
                        continentCountriesCounter++;
                        for (int i = 4; i < countriesSplitArray.length; i++) {
                            oneCountry.getAdjacentCountries().add(countriesSplitArray[i]);
                            continentCountriesCounter++;
                        }

                        Main.worldMap.put(oneCountry.getCountryname(), oneCountry);

                        continue;
                    } else {
                        if(continentCounter < Main.continentList.size()) {
                            Main.continentList.get(continentCounter).setNumOfCountries(continentCountriesCounter);
                            continentCountriesCounter = 0;
                            continentCounter++;
                        }

                        continue;
                    }
                }
                isReadFinish = true;
                break;
            }
            continue;
        }

        printContinents();
        printCountriesInContinent();
        printCountryInfo();
    }

    private static void printCountryInfo() {

        for(Map.Entry<String, Country> entry: Main.worldMap.entrySet()){
            System.out.println("---" + entry.getValue().getCountryname() + " -> " + entry.getValue().getContinentname() + " : " + entry.getValue().getArmies() +
                    "---");
            System.out.println(entry.getValue().getAdjacentCountries());
        }

        System.out.println("\n");
    }

    private static void printCountriesInContinent() {
        for(int i = 0; i < Main.continentList.size(); i++){
            Continent curContinent = Main.continentList.get(i);
            ArrayList<String> countries = curContinent.getCountryList();

            System.out.println(curContinent.getContinentname() + ": " + curContinent.getBonusValue());

            System.out.println(countries);

            System.out.println("\n");
        }
    }

    private static void printContinents() {
        for(int i = 0; i < Main.continentList.size(); i++) {
            System.out.printf("%s, ", Main.continentList.get(i).getContinentname());
        }
        System.out.println("\n");
    }
}
