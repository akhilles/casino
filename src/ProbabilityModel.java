import java.util.HashMap;
import java.util.Map;

/**
 * Created by akhilvelagapudi on 4/9/2017.
 */
public class ProbabilityModel {
    public Map<Integer,OptionVector> unknownCards;
    public Map<Integer,Integer> revealedCards;
    public long duration;

    public ProbabilityModel(){
        unknownCards = new HashMap<>();
        revealedCards = new HashMap<>();
        for (int i = 0; i < 52; i++){
            unknownCards.put(i, new OptionVector());
        }
    }

    public void revealCard(int index, int card){
        revealedCards.put(index, card);
        unknownCards.remove(index);
        for (int k: unknownCards.keySet()){
            unknownCards.get(k).removeOption(card);
        }
    }

    public void equalizeCardProbabilities(){
        for (int i = 0; i < 52; i++){
            int finalI = i;
            double sum = unknownCards.entrySet().stream().mapToDouble(e -> e.getValue().probabilities[finalI]).sum();
            if (sum != 0) unknownCards.forEach((k,v) -> v.adjustProbability(finalI, 1/sum));
        }
    }

    public double[] getAggregateModel(int[] cardIndexes){
        double[] sum = new double[52];
        for (int ci: cardIndexes){
            if (ci < 0) continue;
            double[] probabilities = unknownCards.get(ci).probabilities;
            for (int i = 0; i < 52; i++){
                sum[i] += probabilities[i];
            }
        }
        return sum;
    }

    public void buildProbabilityModel(){
        unknownCards.forEach((k,v) -> v.loadProbabilities());

        long startTime = System.nanoTime();
        for (int i = 0; i < 10000; i++){
            equalizeCardProbabilities();
            unknownCards.forEach((k,v) -> v.equalizeProbabilities());
        }
        long endTime = System.nanoTime();
        duration = (endTime - startTime)/1000000;
    }

    public static void main(String[] args){
        ProbabilityModel cd = new ProbabilityModel();
        cd.unknownCards.get(51).options = new int[]{
                1,0,0,0,0,0,0,0,0,0,0,0,0,
                0,0,0,0,0,0,0,0,0,0,0,0,0,
                0,0,0,0,0,0,0,0,0,0,0,0,0,
                0,0,0,0,0,0,0,0,0,0,0,0,0
        };
        cd.buildProbabilityModel();

        for (int k: cd.unknownCards.keySet()){
            OptionVector ov = cd.unknownCards.get(k);
            System.out.printf("%2d: ", k);
            System.out.println(ov);
        }
        System.out.println("duration: " + cd.duration/1000.0 + " seconds");
    }
}