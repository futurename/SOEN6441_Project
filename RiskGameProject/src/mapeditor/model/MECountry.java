package mapeditor.model;


import java.util.LinkedList;

public class MECountry{

    private String countryName;
    private LinkedList<MECountry> neighbor = new LinkedList<MECountry>();


    public void setCountryName(String newCountryName){
        this.countryName = newCountryName;
    }

    public void setNeighbor(MECountry newNeighbor){
        neighbor.offer(newNeighbor);
    }

    public void deleteNeighbor(MECountry oldNeighbor){
        if(neighbor.indexOf(oldNeighbor)!=-1){
            neighbor.remove(oldNeighbor);
        }
    }
    public String getCountryName(){return countryName;}

    public String getNeighbor(){return neighbor.toString();}

}