package mapeditor.model;

import java.util.ArrayList;
import java.util.LinkedList;

public class MECountry{

    private String countryName;
    private LinkedList<String> neighbor = new LinkedList<>();


    public void setCountryName(String newCountryName){
        this.countryName = newCountryName;
    }
    public void addNeighbor(String newNeighbor){
        neighbor.offer(newNeighbor);
    }
    public void deletNeighbor(String oldNeighbor){
        if(neighbor.indexOf(oldNeighbor)!=-1){
            neighbor.remove(oldNeighbor);
        }
    }
    public String getCountryName(){return countryName;}
}