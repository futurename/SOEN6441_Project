package mapeditor.model;

import javafx.beans.property.*;
import javafx.beans.value.ObservableValue;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MEContinent{
    private String continentName;
    private int bonus;
    private int countryNumber = 0;
    public LinkedList<String> countryList = new LinkedList<String>();

    private StringProperty name;
    private StringProperty bns;
    private StringProperty countries;

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

    public StringProperty bonusProperty() {
        if (bns == null) {
            bns = new SimpleStringProperty(this, "name");
        }
        return bns;
    }


    public StringProperty countryProperty() {
        if (countries == null) {
            countries = new SimpleStringProperty(this, "country");
        }
        return countries;
    }


    public boolean addCountry(String countryName){
        if(!countryList.contains(countryName)){
            countryNumber++;
            boolean sign = countryList.add(countryName);
            String replaceSymbol = countryList.toString();
            replaceSymbol = replaceSymbol.replaceAll( "\\[","");
            replaceSymbol = replaceSymbol.replaceAll( "\\]","");
            countryProperty().set(replaceSymbol);
            return sign;
        }
        return false;
    }

    public void deleteCountry(String countryName){
        if(countryList.contains(countryName)){
            countryList.remove(countryName);
            String replaceSymbol = countryList.toString();
            replaceSymbol = replaceSymbol.replaceAll( "\\[","");
            replaceSymbol = replaceSymbol.replaceAll( "\\]","");
            countryProperty().set(replaceSymbol);
            countryNumber--;
        }
    }

    public String getContinentName(){
        return continentName;
    }

    public int getBonus(){
        return this.bonus;
    }

    public String getCountryList(){
        return countryList.toString();
    }

    public LinkedList<String> getCountryListName(){
        return countryList;
    }

    public int getCountryNumber(){ return countryNumber;  }

}