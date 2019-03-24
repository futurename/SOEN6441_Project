package mapeditor.Test;


import mapeditor.MEMain;
import mapeditor.model.MECheckMapCorrectness;
import mapeditor.model.MEContinent;
import mapeditor.model.MECountry;
import org.junit.*;
import org.junit.runners.MethodSorters;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MapEditorTest {

    public static MEMain testMEMain;
    public String thirdmap = "maps/ErrorMap/World-thirderror.map";

    @BeforeClass
    public static void before(){
        testMEMain.createContinent("testFirstContinent",1);
        testMEMain.createsSoloCountry("testFirstCountry");
        testMEMain.arrMEContinent.get(0).addCountry("testFirstCountry");
        testMEMain.createsSoloCountry("testSecondCountry");
        System.out.println("Test map editor");
    }

    /**
     * Check add continent function
     */
    @Test public void testA(){
        MEContinent newContinent =  testMEMain.arrMEContinent.get(0);
        assertEquals("testFirstContinent",newContinent.getContinentName());
        assertEquals(1,newContinent.getBonus());
    }

    /**
     * Check add country function
     */
    @Test public void testB(){
        MECountry newCountry =  testMEMain.arrMECountry.get(0);
        assertEquals("testFirstCountry",newCountry.getCountryName());
    }

    /**
     * Check delete continent function
     */
    @Test public void testC() {
        testMEMain.deleteContinent("testFirstContinent");
        assertEquals(0, testMEMain.arrMEContinent.size());
        assertEquals(1, testMEMain.arrMECountry.size());
    }

    /**
     * Check delete country function
     */
    @Test public void testD() {
        testMEMain.deleteCountry("testSecondCountry");
        assertEquals(0, testMEMain.arrMECountry.size());
    }

    /**
     * Test for checking whether all countries in map are connected
     */
    @Test public void testE() throws Exception{
        testMEMain.arrMEContinent.clear();
        testMEMain.arrMECountry.clear();
        readMap("maps/ErrorMap/World-firsterror.map");
        MECheckMapCorrectness mapCheck = new  MECheckMapCorrectness();
        assertEquals( "Unconnected Graph",mapCheck.isCorrect(testMEMain.arrMECountry, testMEMain.arrMEContinent));
    }

    /**
     * Test for checking whether all countries in one continent are placed together
     */
    @Test public void testF() throws Exception{
        testMEMain.arrMEContinent.clear();
        testMEMain.arrMECountry.clear();
        readMap("maps/ErrorMap/World-seconderror.map");
        MECheckMapCorrectness mapCheck = new  MECheckMapCorrectness();
        assertEquals( "Country doesn't connect with any country of its continent",mapCheck.isCorrect(testMEMain.arrMECountry, testMEMain.arrMEContinent));
    }

    /**
     * Test for checking whether a country only belongs to one continent
     */
    @Test public void testG() throws Exception{
        testMEMain.arrMEContinent.clear();
        testMEMain.arrMECountry.clear();
        readMap("maps/ErrorMap/World-thirderror.map");
        MECheckMapCorrectness mapCheck = new  MECheckMapCorrectness();
        assertEquals( "Country belongs to mutiple continent",mapCheck.isCorrect(testMEMain.arrMECountry, testMEMain.arrMEContinent));
    }

    private void readMap(String path)throws Exception{
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
                        testMEMain.createContinent(fileRead.get(j).split("=")[0],Integer.parseInt(fileRead.get(j).split("=")[1]));
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
                        testMEMain.createCountry(countrydata[0],countrydata);
                        for(int z=0; z<testMEMain.arrMEContinent.size();z++) {
                            if(testMEMain.arrMEContinent.get(z).getContinentName().equals(countrydata[3]))
                                testMEMain.arrMEContinent.get(z).addCountry(countrydata[0]);
                        }
                    }

                }
            }
        }
    }
}
