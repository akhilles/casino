/**
 * Created by akhil on 4/9/2017.
 */
public class CardDeck {
    public SchrodingerCard[] cards;

    public CardDeck(){
        cards = new SchrodingerCard[52];
        for (int i = 0; i < 52; i++){
            cards[i] = new SchrodingerCard();
        }
    }

    public void buildProbabilityModel(){
        int[] possibilityAggregate = new int[52];

        for (int i = 0; i < 52; i++){
            for (SchrodingerCard card: cards){
                if (card.possibilityModel[i]) possibilityAggregate[i]++;
            }
            double probability = 1.0/possibilityAggregate[i];
            for (SchrodingerCard card: cards){
                if (card.possibilityModel[i]) card.probabilityModel[i] = probability;
            }
        }

        //equalize
        for (int iter = 0; iter < 5000; iter++) {
            for (SchrodingerCard card : cards) {
                double sum = 0;
                for (int i = 0; i < 52; i++) {
                    sum += card.probabilityModel[i];
                }
                for (int i = 0; i < 52; i++) {
                    card.probabilityModel[i] /= sum;
                }
                //System.out.println(sum * 100);
            }

            for (int i = 0; i < 52; i++) {
                double probabilitySum = 0;
                for (SchrodingerCard card : cards) {
                    probabilitySum += card.probabilityModel[i];
                }
                for (SchrodingerCard card : cards) {
                    card.probabilityModel[i] /= probabilitySum;
                }
                //System.out.println(SchrodingerCard.cardToString(i) + ": " + probabilitySum * 100);
            }
        }

    }

    public static void main(String[] args){
        CardDeck cd = new CardDeck();
        cd.cards[0].possibilityModel = new boolean[]{
                false, false, false, false, false, false, false, false, false, false, false, true, false,
                false, false, false, false, false, false, false, false, false, false, false, true, false,
                false, false, false, false, false, false, false, false, false, false, false, true, false,
                false, false, false, false, false, false, false, false, false, false, false, true, false};
        cd.cards[1].possibilityModel = new boolean[]{
                false, false, false, false, false, false, false, false, false, false, false, true, false,
                false, false, false, false, false, false, false, false, false, false, false, true, false,
                false, false, false, false, false, false, false, false, false, false, false, true, false,
                false, false, false, false, false, false, false, false, false, false, false, true, false};
        cd.cards[2].possibilityModel = new boolean[]{
                false, false, false, false, false, false, false, false, false, false, false, true, false,
                false, false, false, false, false, false, false, false, false, false, false, true, false,
                false, false, false, false, false, false, false, false, false, false, false, true, false,
                false, false, false, false, false, false, false, false, false, false, false, true, false};
        cd.cards[4].possibilityModel = new boolean[]{
                false, false, false, false, false, false, false, false, false, false, false, false, true,
                false, false, false, false, false, false, false, false, false, false, false, false, true,
                false, false, false, false, false, false, false, false, false, false, false, false, false,
                false, false, false, false, false, false, false, false, false, false, false, false, false};
        cd.buildProbabilityModel();
        for (SchrodingerCard card: cd.cards){
            System.out.println(card);
        }
    }
}