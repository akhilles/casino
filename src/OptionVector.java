import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by akhilvelagapudi on 4/13/2017.
 */
public class OptionVector {
    public int options[];
    public double probabilities[];
    public double probabilitySum;
    public boolean locked;
    public ArrayList<Integer> unlikelyValues;

    public OptionVector(){
        unlikelyValues = new ArrayList<>();
        locked = false;
        options = new int[52];
        probabilities = new double[52];
        probabilitySum = 0;
        Arrays.fill(options, 1);
    }

    public void addUnlikelyValues(int[] values){
        for (int v: values){
            if (!unlikelyValues.contains(v - 2)) unlikelyValues.add(v - 2);
        }
    }

    public void valueRevealed(int value){
        locked = true;
        for (int i = 0; i < 52; i++){
            if (i % 13 != value) options[i] = 0;
        }
    }

    public void removeOption(int index){
        options[index] = 0;
    }

    public void loadProbabilities(){
        probabilitySum = 0;
        for (int i = 0; i < 52; i++){
            if (unlikelyValues.contains(i % 13)) probabilities[i] = options[i];
            else probabilities[i] = 10 * options[i];
            probabilitySum += probabilities[i];
        }
    }

    public void equalizeProbabilities(){
        for (int i = 0; i < 52; i++){
            probabilities[i] /= probabilitySum;
        }
        probabilitySum = 1;
    }

    public void adjustProbability(int index, double scalingFactor){
        double difference = (probabilities[index] * scalingFactor) - probabilities[index];
        probabilities[index] += difference;
        probabilitySum += difference;
    }

    @Override
    public boolean equals(Object other){
        if (other == null || other.getClass() != getClass()) return false;
        return Arrays.equals(options, ((OptionVector) other).options);
    }

    @Override
    public String toString(){
        String out = "";
        for (int i = 0; i < 52; i++){
            out += String.format("%6.2f ", probabilities[i] * 100);
        }
        return out;
    }
}