/**
 * Created by akhil on 4/9/2017.
 */
public class CardDeck {
    public SchrodingerCard[] cards;

    public CardDeck(){
        cards = new SchrodingerCard[52];
        for (int i = 0; i < 52; i++){
            cards[i] = new SchrodingerCard(this);
        }
    }

    public static void main(String[] args){
        CardDeck cd = new CardDeck();
        for (SchrodingerCard card: cd.cards){
            System.out.println(card);
        }
    }
}
