import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by akhilvelagapudi on 4/16/2017.
 */
public class Casino {
    private int cardsDealt;
    private ProbabilityModel probModel;
    private ArrayList<Integer> field;
    private int[][] players;

    public Casino(){
        cardsDealt = 0;
        probModel = new ProbabilityModel();
        field = new ArrayList<>(10);
        players = new int[][]{
            {-1,-1,-1,-1},
            {-1,-1,-1,-1},
            {-1,-1,-1,-1},
            {-1,-1,-1,-1}
        };
    }

    public void dealField(int[] fieldCards){
        for (int i = 0; i < 4; i++){
            field.add(cardsDealt);
            probModel.revealCard(cardsDealt++, fieldCards[i]);
        }
    }

    public void dealHands(int[] myCards){
        for (int i = 0; i < 4; i++){
            for (int j = 0; j < 4; j++){
                if (i == 0) probModel.revealCard(cardsDealt, myCards[j]);
                players[i][j] = cardsDealt++;
            }
        }
    }

    public void printPredictions(){
        probModel.buildProbabilityModel();
        double[][] aggregateModels = new double[][]{
                probModel.getAggregateModel(players[1]),
                probModel.getAggregateModel(players[2]),
                probModel.getAggregateModel(players[3])
        };

        System.out.println("\t\tPlayer 2:\tPlayer 3:\tPlayer 4:");
        for (int j = 12; j >= 0; j--){
            switch (j){
                case 12: System.out.print("Ace:   "); break;
                case 11: System.out.print("King:  "); break;
                case 10: System.out.print("Queen: "); break;
                case 9: System.out.print("Jack:  "); break;
                case 8: System.out.print("Ten:   "); break;
                case 7: System.out.print("Nine:  "); break;
                case 6: System.out.print("Eight: "); break;
                case 5: System.out.print("Seven: "); break;
                case 4: System.out.print("Six:   "); break;
                case 3: System.out.print("Five:  "); break;
                case 2: System.out.print("Four:  "); break;
                case 1: System.out.print("Three: "); break;
                case 0: System.out.print("Two:   "); break;
            }
            for (int i = 0; i < 3; i++){
                System.out.printf("\t%6.2f\t", (aggregateModels[i][j] + aggregateModels[i][j+13] + aggregateModels[i][j+26] + aggregateModels[i][j+39]) * 100);
            }
            System.out.println();
        }
    }

    public static void main(String[] args){
        Casino casino = new Casino();
        casino.dealField(new int[]{ 0, 1, 2, 3});
        casino.dealHands(new int[]{17,14,15,16});
        casino.printPredictions();
    }
}
