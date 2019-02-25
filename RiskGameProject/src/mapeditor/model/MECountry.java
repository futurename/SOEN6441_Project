package mapeditor.model;


import java.util.LinkedList;

public class MECountry{

    private String countryName;
    private LinkedList<String> neighbor = new LinkedList<String>();


    public void setCountryName(String newCountryName){
        this.countryName = newCountryName;
    }

    public void setNeighbor(String newNeighbor){
        neighbor.offer(newNeighbor);
    }

    public void deleteNeighbor(String oldNeighbor){
        if(neighbor.indexOf(oldNeighbor)!=-1){
            neighbor.remove(oldNeighbor);
        }
    }
    public String getCountryName(){return countryName;}

    public String getNeighbor(){return neighbor.toString();}

}