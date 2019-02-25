package mapeditor.model;

import java.util.LinkedList;

public class MEContinent{
    private String continentName;
    private int bonus;
    private int countryNumber;
    private LinkedList<String> countryList = new LinkedList<String>();

    public void setContinentName(String newContinentName){

        this.continentName = newContinentName;
    }

    public void setBonus(int bonus){

        this.bonus = bonus;
    }

    public void initCountryNumber(){
        this.countryNumber = 0;
    }

    public boolean addCountry(String countryName){
        if(!countryList.contains(countryName)){
            countryNumber++;
            return countryList.add(countryName);
        }
        return false;
    }

    public void deleteCountry(String countryName){
        if(countryList.contains(countryName)){
            countryList.remove(countryName);
            countryNumber--;
        }
    }

    public String getContinentName(){
        return continentName;
    }

    public String getcountryList(){
        return countryList.toString();
    }

    public int getCountryNumber(){ return countryNumber;  }

}