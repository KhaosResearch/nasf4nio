package util;

import java.util.Arrays;
import java.util.List;

public class Util {

    public static List<Integer> fill( int n,int value){
        List<Integer> list;
        Integer[] data = new Integer[n];
        Arrays.fill(data, new Integer(value));
        list = Arrays.asList(data);
        return list;
    }
    public static List<Double> fillDouble(int n,int value){
        List<Double> list;
        Double[] data = new Double[n];
        Arrays.fill(data, new Double(value));
        list = Arrays.asList(data);
        return list;
    }

    public static int getBinomial(int n, double p) {
        double logQ = Math.log(1.0 - p);
        int x = 0;
        double sum = 0;
        while(sum<logQ) {
            sum += Math.log(Math.random()) / (n - x);
            x++;
        }
        return x;
    }

}
