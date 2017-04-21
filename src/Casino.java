import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by akhilvelagapudi on 4/16/2017.
 */
public class Casino {
    private int cardsDealt;
    public ProbabilityModel probModel;
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

    public void unlikelyValues(int player, int[] values){
        for (int c: players[player]){
            if (c >= 0){
                probModel.unknownCards.get(c).addUnlikelyValues(values);
            }
        }
    }

    public void revealValue(int player, int value){
        for (int c: players[player]){
            if (c >= 0 && !probModel.unknownCards.get(c).locked){
                probModel.unknownCards.get(c).valueRevealed(value);
            }
        }
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

    public double pAggVal(double[] aggregateModel, int card){
        if (card < 0 || card > 12) return 0;
        return aggregateModel[card] + aggregateModel[card + 13] + aggregateModel[card + 26] + aggregateModel[card + 39];
    }

    public double pModCap(double[] aggregateModel, int value){
        double prob = 0;
        int temp = value - 2;
        if (temp == -1) temp = 12;
        prob += pAggVal(aggregateModel, temp);
        prob += pAggVal(aggregateModel, 12) * pAggVal(aggregateModel, temp + 1);
        for (int i = 2; i <= 14 - value; i++){
            prob += pAggVal(aggregateModel, i - 2) * pAggVal(aggregateModel, value - 2 + i);
        }
        return prob;
    }

    public void printModifyCapturePredictions(){
        double[][] aggregateModels = new double[][]{
                probModel.getAggregateModel(players[1]),
                probModel.getAggregateModel(players[2]),
                probModel.getAggregateModel(players[3])
        };

        System.out.println("chance of modifying/capturing:");
        System.out.println("\t\tPlayer 2:\tPlayer 3:\tPlayer 4:\tEnemy Team:");
        for (int i = 14; i >= 1; i--){
            System.out.print(i + ":\t");
            for (int j = 0; j < 3; j++){
                System.out.printf("\t%5.3f\t", pModCap(aggregateModels[j], i));
            }
            System.out.printf("\t%5.3f\t\n", pModCap(aggregateModels[0], i) + pModCap(aggregateModels[2], i));
        }
    }

    public void printCardPredictions(){
        double[][] aggregateModels = new double[][]{
                probModel.getAggregateModel(players[1]),
                probModel.getAggregateModel(players[2]),
                probModel.getAggregateModel(players[3])
        };

        System.out.println("card probabilities:");
        System.out.println("\t\tPlayer 2:\tPlayer 3:\tPlayer 4:\tEnemy Team:");
        System.out.print("10-D:  ");
        for (int i = 0; i < 3; i++){
            System.out.printf("\t%5.3f\t", aggregateModels[i][21]);
        }
        System.out.print("\n2-S:   ");
        for (int i = 0; i < 3; i++){
            System.out.printf("\t%5.3f\t", aggregateModels[i][26]);
        }
        System.out.println();
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
                System.out.printf("\t%5.3f\t", pAggVal(aggregateModels[i], j));
            }
            System.out.printf("\t%5.3f\t\n", pAggVal(aggregateModels[0], j) + pAggVal(aggregateModels[2], j));
        }
    }

    public void displayInfo(){
        if (probModel.unknownCards.isEmpty()) return;
        probModel.buildProbabilityModel();
        printCardPredictions();
        printModifyCapturePredictions();
    }

    public boolean playLockedCard(int card, int player){
        for (int i = 0; i < 4; i++){
            if (players[player][i] < 0) continue;
            OptionVector ov = probModel.unknownCards.get(players[player][i]);
            if (ov.locked && ov.options[card] == 1){
                probModel.revealCard(players[player][i], card);
                players[player][i] = -1;
                return true;
            }
        }
        return false;
    }

    public void playUnlockedCard(int card, int player){
        for (int i = 0; i < 4; i++){
            if (players[player][i] < 0) continue;
            OptionVector ov = probModel.unknownCards.get(players[player][i]);
            if (!ov.locked){
                probModel.revealCard(players[player][i], card);
                players[player][i] = -1;
                return;
            }
        }
    }

    public static void main(String[] args) throws IOException {
        int firstPlayer;
        Casino casino = new Casino();

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("first player to go: ");
        firstPlayer = Integer.parseInt(br.readLine()) - 1;
        System.out.print("cards on field: ");
        int[] fieldCards = Arrays.stream(br.readLine().split(",")).mapToInt(e -> CardUtilities.stringToCard(e)).toArray();
        casino.dealField(fieldCards);

        for (int x = 0; x < 4; x++){
            System.out.print("cards in your hand: ");
            int[] handCards = Arrays.stream(br.readLine().split(",")).mapToInt(e -> CardUtilities.stringToCard(e)).toArray();
            casino.dealHands(handCards);
            casino.displayInfo();
            for (int i = 0; i < 4; i++){
                for (int j = 0; j < 4; j++){
                    if (firstPlayer == 0){
                        firstPlayer = (firstPlayer + 1) % 4;
                        continue;
                    }
                    System.out.print("played by player " + (firstPlayer + 1) + ": ");
                    int cardVal = CardUtilities.stringToCard(br.readLine());
                    if (!casino.playLockedCard(cardVal, firstPlayer)) casino.playUnlockedCard(cardVal, firstPlayer);

                    System.out.print("+ ");
                    String plus = br.readLine();
                    if (!plus.equals("null") && !plus.isEmpty()) casino.revealValue(firstPlayer, Integer.parseInt(plus));
                    System.out.print("- ");
                    String minus = br.readLine();
                    if (!minus.equals("null") && !minus.isEmpty()){
                        casino.unlikelyValues(firstPlayer, Arrays.stream(minus.split(",")).mapToInt(Integer::parseInt).toArray());
                    }

                    casino.displayInfo();
                    firstPlayer = (firstPlayer + 1) % 4;
                }
            }
        }
    }
}
