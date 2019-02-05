package classes;

import java.util.LinkedList;

public class MEContinent{
    private String continentName;
    private int bonus;
    private LinkedList<String> countryList = new LinkedList<String>();

    public void setContinentName(String newContinentName){

        this.continentName = newContinentName;
    }

    public void setBonus(int bonus){

        this.bonus = bonus;
    }

    public boolean addCountry(String countryName){
        if(!countryList.contains(countryName)){
            return countryList.add(countryName);
        }
        return false;
    }

    public String getContinentName(){
        return continentName;
    }

    public String getcountryList(){
        return countryList.toString();
    }


}