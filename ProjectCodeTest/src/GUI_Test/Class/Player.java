package GUI_Test.Class;

import java.util.ArrayList;

public class Player {
    String playername;
    int armies;
    ArrayList<Country> countries;

    public Player(int seq){
        this.playername = "Player: " + seq;

        this.countries = new ArrayList<>();
        this.countries.add(new Country("China"));
        this.countries.add(new Country("India"));
        this.countries.add(new Country("USA"));
        this.countries.add(new Country("France"));
    }

    public String getPlayername() {
        return playername;
    }

    public ArrayList<Country> getCountries() {
        return countries;
    }
}
