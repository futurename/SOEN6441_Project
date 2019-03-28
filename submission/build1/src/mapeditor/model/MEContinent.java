package mapeditor.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.LinkedList;

/**
 * class continent
 */
public class MEContinent {
    /**
     * Continent name attribute
     */
    private String continentName;

    /**
     * Continent bonus attribute
     */
    private int bonus;

    /**
     * Number of countries in this continent
     */
    private int countryNumber = 0;

    /**
     * Name list of countries in this continent
     */
    public LinkedList<String> countryList = new LinkedList<String>();

    private StringProperty name;
    private StringProperty bns;
    private StringProperty countries;

    /**
     * set continent name
     *
     * @param newContinentName new continent name
     */
    public void setContinentName(String newContinentName) {

        this.continentName = newContinentName;
        nameProperty().set(newContinentName);
    }

    /**
     * Bind continent name to table view
     *
     * @return StringProperty of name
     */
    public StringProperty nameProperty() {
        if (name == null) {
            name = new SimpleStringProperty(this, "name");
        }
        return name;
    }

    /**
     * set bonus
     *
     * @param bonus set the bonus of this continent
     */
    public void setBonus(int bonus) {

        this.bonus = bonus;
        String bonusToString = String.valueOf(bonus);
        bonusProperty().set(bonusToString);
    }

    /**
     * Bind continent bonus infomation to table view.
     *
     * @return StringPropety of bonus
     */
    public StringProperty bonusProperty() {
        if (bns == null) {
            bns = new SimpleStringProperty(this, "name");
        }
        return bns;
    }

    /**
     * Bind country infomation to table view.
     *
     * @return StringProperty of country
     */
    public StringProperty countryProperty() {
        if (countries == null) {
            countries = new SimpleStringProperty(this, "country");
        }
        return countries;
    }

    /**
     * add a new country in this continent
     *
     * @param countryName the name of country you want to add
     * @return whether it is success or not
     */
    public boolean addCountry(String countryName) {
        if (!countryList.contains(countryName)) {
            countryNumber++;
            boolean sign = countryList.add(countryName);
            String replaceSymbol = countryList.toString();
            replaceSymbol = replaceSymbol.replaceAll("\\[", "");
            replaceSymbol = replaceSymbol.replaceAll("\\]", "");
            countryProperty().set(replaceSymbol);
            return sign;
        }
        return false;
    }

    /**
     * delete a country from this continent
     *
     * @param countryName the name of the country you want to delete
     */
    public void deleteCountry(String countryName) {
        if (countryList.contains(countryName)) {
            countryList.remove(countryName);
            String replaceSymbol = countryList.toString();
            replaceSymbol = replaceSymbol.replaceAll("\\[", "");
            replaceSymbol = replaceSymbol.replaceAll("\\]", "");
            countryProperty().set(replaceSymbol);
            countryNumber--;
        }
    }

    /**
     * get continent name
     *
     * @return the name of this continent
     */
    public String getContinentName() {
        return continentName;
    }

    /**
     * get bonus
     *
     * @return the bonus of this continent
     */
    public int getBonus() {
        return this.bonus;
    }

    /**
     * get country list, same as get country list name but in String ,for example"[A, B, C]"
     *
     * @return country list
     */
    public String getCountryList() {
        return countryList.toString();
    }

    /**
     * get country list name
     *
     * @return the countries name in this continent
     */
    public LinkedList<String> getCountryListName() {
        return countryList;
    }

    /**
     * get country number
     *
     * @return the number of countries in this continent
     */
    public int getCountryNumber() {
        return countryNumber;
    }

}