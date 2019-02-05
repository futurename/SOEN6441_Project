package classes;

import java.util.LinkedList;

public class MECountry{
    private String countryName;
    private LinkedList<String> adjacentCountry = new LinkedList<String>();

    public void setCountryName(String newCountryName){
        this.countryName = newCountryName;
    }

    public boolean setAdjacentCountry(String AdjacentCountryName){

        if(!adjacentCountry.contains(AdjacentCountryName)){
            return adjacentCountry.add(AdjacentCountryName);
        }
        return false;
    }

    public String getCountryName(){
        return countryName;
    }

    public String getAdjacentCountry(){
        return adjacentCountry.toString();
    }
}