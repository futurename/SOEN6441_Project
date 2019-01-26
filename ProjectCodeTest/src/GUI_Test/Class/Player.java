package GUI_Test.Class;

import java.util.ArrayList;

public class Player {
    String playername;
    int armies;
    ArrayList<String> countries;

    public Player(int seq){
        this.playername = "Player: " + seq;
        this.countries = new ArrayList<>();
        this.armies = 0;
    }

    public String getPlayername() {
        return playername;
    }

    public ArrayList<String> getCountries() {
        return countries;
    }

    public int getArmies() {
        return armies;
    }

    public void setPlayername(String playername) {
        this.playername = playername;
    }

    public void setArmies(int armies) {
        this.armies = armies;
    }

    public void setCountries(ArrayList<String> countries) {
        this.countries = countries;
    }
}
