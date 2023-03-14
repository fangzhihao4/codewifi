package codewifi.utils;

import java.util.*;

public class LotteryUtil {
    public static int lottery(Map<Integer,Double> prizeRatesMap){
        if (prizeRatesMap == null || prizeRatesMap.size() <= 0){
            return -1;
        }
        double totalRate = 0D; //总概率
        for (Map.Entry<Integer, Double> entry : prizeRatesMap.entrySet()){
            if (entry.getValue() <= 0D){
                //移除概率小于等于0的
                prizeRatesMap.remove(entry.getKey());
            }else{
                totalRate += entry.getValue();
            }
        }
        if (totalRate == 0D){
            return -1;
        }

        List<Double> sortRates = new ArrayList<>();
        Map<Double, Integer> rateDrawMap = new HashMap<>();
        Double drawSumRate = 0D;
        for (Map.Entry<Integer, Double> entry : prizeRatesMap.entrySet()){
            drawSumRate += entry.getValue();
            Double infoDouble =  drawSumRate/totalRate;
            rateDrawMap.put(infoDouble, entry.getKey());
            sortRates.add(infoDouble);
        }
        Double nextDouble = Math.random();
        sortRates.add(nextDouble);
        Collections.sort(sortRates);
        int i =  sortRates.indexOf(nextDouble);
        return rateDrawMap.get(sortRates.get(i+1));
    }
}
