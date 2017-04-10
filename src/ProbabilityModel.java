/**
 * Created by akhil on 4/9/2017.
 */
public class ProbabilityModel {
    public int[] cards;
    public int[] cardRevealed;
    public int[] valueRevealed;
    public int[][] canExist;
    public double[][] model;

    public ProbabilityModel(){
        cards = new int[52];
        cardRevealed = new int[52];
        valueRevealed = new int[52];
        canExist = new int[52][52];
        model = new double[52][52];
        for (int i = 0; i < 52; i++){
            cards[i] = -1;
            cardRevealed[i] = i;
            valueRevealed[i] = i;
            for (int j = 0; j < 52; j++){
                canExist[i][j] = 1;
            }
        }
    }

    public void equalizeHands(){
        for (int i = 0; i < model.length; i++){
            double sum = 0;
            for (int j = 0; j < model[0].length; j++){
                sum += model[i][j];
            }
            for (int j = 0; j < model[0].length; j++){
                model[i][j] /= sum;
            }
        }
    }

    public void equalizeCards(){
        for (int j = 0; j < model[0].length; j++){
            double sum = 0;
            for (int i = 0; i < model.length; i++){
                sum += model[i][j];
            }
            for (int i = 0; i < model.length; i++){
                model[i][j] /= sum;
            }
        }
    }

    public void buildProbabilityModel(){
        for (int i = 0; i < model.length; i++){
            for (int j = 0; j < model[0].length; j++){
                model[i][j] = canExist[i][j];
            }
        }

        for (int i = 0; i < 5000; i++) {
            equalizeHands();
            equalizeCards();
        }
    }

    public void revealCard(int cardIndex, int cardValue){
        int prevRow = cardRevealed[cardIndex];
        int prevColumn = valueRevealed[cardValue];

        cards[cardIndex] = cardValue;

        cardRevealed[cardIndex] = -1;
        for (int i = cardIndex; i < 52; i++){
            cardRevealed[i]--;
        }
        valueRevealed[cardValue] = -1;
        for (int i = cardValue; i < 52; i++){
            valueRevealed[i]--;
        }

        int[][] canExistReplacement = new int[canExist.length - 1][canExist[0].length - 1];
        double[][] modelReplacement = new double[canExist.length - 1][canExist[0].length - 1];
        for (int i = 0; i < canExist.length; i++){
            int i_adjusted = i;
            if (i > prevRow) i_adjusted--;
            for (int j = 0; j < canExist[0].length; j++){
                int j_adjusted = j;
                if (j > prevColumn) j_adjusted--;

                canExistReplacement[i_adjusted][j_adjusted] = canExist[i][j];
                modelReplacement[i_adjusted][j_adjusted] = model[i][j];
            }
        }
        canExist = canExistReplacement;
        model = modelReplacement;
    }

    public static void main(String[] args){
        ProbabilityModel cd = new ProbabilityModel();
        cd.canExist[51] = new int[]{
                0,0,0,0,0,0,0,0,0,0,0,1,0,
                0,0,0,0,0,0,0,0,0,0,0,1,0,
                0,0,0,0,0,0,0,0,0,0,0,1,0,
                0,0,0,0,0,0,0,0,0,0,0,1,0
        };
        cd.revealCard(0, 11);
        cd.revealCard(1, 24);
        cd.revealCard(2, 37);
        cd.buildProbabilityModel();
        for (int i = 0; i < cd.model.length; i++){
            for (int j = 0; j < cd.model[0].length; j++){
                System.out.printf("%5.2f ", cd.model[i][j] * 100);
            }
            System.out.println();
        }
    }
}