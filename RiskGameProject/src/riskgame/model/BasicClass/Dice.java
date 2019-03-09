package riskgame.model.BasicClass;

import java.util.Random;

public class Dice {
    private Random r;

    public Dice(int seed){
        r = new Random(seed);
    }
    public Dice(){
        int defaultSeed = 6;
        r = new Random(defaultSeed);
    }
    public int rollADice(){
        return r.nextInt(5)+1;
    }
    public int[] rollNDice(int n){
        int[] res = new int[n];
        for (int i=0;i<n;i++){
            res[i] = rollADice();
        }
        return res;
    }
}