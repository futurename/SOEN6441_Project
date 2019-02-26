package mapeditor.model;

import javafx.beans.property.*;
import javafx.beans.value.ObservableValue;

import java.util.LinkedList;

public class MEContinent{
    private String continentName;
    private int bonus;
    private int countryNumber;
    private LinkedList<String> countryList = new LinkedList<String>();

    private StringProperty name;
    private StringProperty bns;

    public void setContinentName(String newContinentName){

        this.continentName = newContinentName;
        nameProperty().set(newContinentName);
    }

    public StringProperty nameProperty(){
        if(name == null){
            name = new SimpleStringProperty(this, "name");
        }
        return name;
    }


    public void setBonus(int bonus){

        this.bonus = bonus;
        String bonusToString = String.valueOf(bonus);
        bonusProperty().set(bonusToString);
    }

    public StringProperty bonusProperty(){
        if(bns == null){
            bns = new SimpleStringProperty(this, "name");
        }
        return bns;
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