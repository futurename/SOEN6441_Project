package mapeditor.Test;


import mapeditor.MEMain;
import mapeditor.model.MECheckMapCorrectness;
import mapeditor.model.MEContinent;
import mapeditor.model.MECountry;
import mapeditor.model.MapObject;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * @author TX
 * @since build1
 **/
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MapEditorTest {

    public static MEMain testMEMain;
    public String thirdmap = "maps/ErrorMap/World-thirderror.map";

    @BeforeClass
    public static void before(){
        MEMain.createContinent("testFirstContinent",1);
        MEMain.createsSoloCountry("testFirstCountry");
        MEMain.arrMEContinent.get(0).addCountry("testFirstCountry");
        MEMain.createsSoloCountry("testSecondCountry");
        System.out.println("Test map editor");
    }

    /**
     * Check add continent function
     */
    @Test public void testA(){
        MEContinent newContinent =  MEMain.arrMEContinent.get(0);
        assertEquals("testFirstContinent",newContinent.getContinentName());
        assertEquals(1,newContinent.getBonus());
    }

    /**
     * Check add country function
     */
    @Test public void testB(){
        MECountry newCountry =  MEMain.arrMECountry.get(0);
        assertEquals("testFirstCountry",newCountry.getCountryName());
    }

    /**
     * Check delete continent function
     */
    @Test public void testC() {
        MEMain.deleteContinent("testFirstContinent");
        assertEquals(0, MEMain.arrMEContinent.size());
        assertEquals(1, MEMain.arrMECountry.size());
    }

    /**
     * Check delete country function
     */
    @Test public void testD() {
        MEMain.deleteCountry("testSecondCountry");
        assertEquals(0, MEMain.arrMECountry.size());
    }

    /**
     * Test for checking whether all countries in map are connected
     */
    @Test public void testE() {
        MEMain.arrMEContinent.clear();
        MEMain.arrMECountry.clear();
        readMap("maps/ErrorMap/World-firsterror.map");
        MECheckMapCorrectness mapCheck = new  MECheckMapCorrectness();
        assertEquals( "Unconnected Graph",mapCheck.isCorrect(MEMain.arrMECountry, MEMain.arrMEContinent));
    }

    /**
     * Test for checking whether all countries in one continent are placed together
     */
    @Test public void testF() {
        MEMain.arrMEContinent.clear();
        MEMain.arrMECountry.clear();
        readMap("maps/ErrorMap/World-seconderror.map");
        MECheckMapCorrectness mapCheck = new  MECheckMapCorrectness();
        assertEquals( "Country doesn't connect with any country of its continent",mapCheck.isCorrect(MEMain.arrMECountry, MEMain.arrMEContinent));
    }

    /**
     * Test for checking whether a country only belongs to one continent
     */
    @Test public void testG() {
        MEMain.arrMEContinent.clear();
        MEMain.arrMECountry.clear();
        readMap("maps/ErrorMap/World-thirderror.map");
        MECheckMapCorrectness mapCheck = new  MECheckMapCorrectness();
        assertEquals( "Country belongs to mutiple continent",mapCheck.isCorrect(MEMain.arrMECountry, MEMain.arrMEContinent));
    }

    /**
     *Test for invalid map format
     */
    @Test public void testH() {
        String readPath = "maps/ErrorMap/World-fourtherror.map";
        MapObject mapCheck = new  MapObject();
        assertEquals( false ,mapCheck.mapFormatCheck(readPath));
    }

    /**
     *Test for invalid map content
     */
    @Test public void testI() {
        String readPath = "maps/ErrorMap/World-seventherror.map";
        MapObject mapCheck = new  MapObject();
        assertEquals( false, mapCheck.mapContentCheck(readPath));
    }

    private void readMap(String path){
        ArrayList<String> fileRead = new ArrayList<String>();
        try {
            File file=new File(path);
            if(file.isFile()&&file.exists()){
                InputStreamReader read = new InputStreamReader(new FileInputStream(file));
                BufferedReader bufferedReader=new BufferedReader(read);
                String lineTxt=null;
                while((lineTxt=bufferedReader.readLine())!=null){
                    fileRead.add(lineTxt);
                }
                read.close();
            }else{
                System.out.println("cannot find file");
            }
        } catch (Exception e){
            System.out.println("wrong");
            e.printStackTrace();
        }
        for(int i = 0;i<fileRead.size();i++){
            if(fileRead.get(i).equals("[Continents]")){
                for(int j=i+1;j<fileRead.size();j++){
                    if(!fileRead.get(j).equals("")){
                        MEMain.createContinent(fileRead.get(j).split("=")[0],Integer.parseInt(fileRead.get(j).split("=")[1]));
                    }
                    else{
                        break;
                    }
                }
            }
            else if(fileRead.get(i).equals("[Territories]")){
                for(int k=i+1;k<fileRead.size();k++){
                    if(!fileRead.get(k).equals("")){
                        String[] countrydata = fileRead.get(k).split(",");
                        MEMain.createCountry(countrydata[0],countrydata);
                        for(int z = 0; z< MEMain.arrMEContinent.size(); z++) {
                            if(MEMain.arrMEContinent.get(z).getContinentName().equals(countrydata[3]))
                                MEMain.arrMEContinent.get(z).addCountry(countrydata[0]);
                        }
                    }
                }
            }
        }
    }
}
