package riskgame.model.BasicClass;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Dice {
    private Random r;
    private final int NUMBER_RANGE = 6;

    public Dice(){
        r = new Random();
    }

    public int rollADice(){
        return r.nextInt(NUMBER_RANGE)+1;
    }

    public ArrayList<Integer> rollNDice(int n){
        ArrayList<Integer> result=new ArrayList<>();
        for (int i=0;i<n;i++){
            result.add(rollADice());
        }
        Collections.sort(result,Collections.reverseOrder());
        return result;
    }
}