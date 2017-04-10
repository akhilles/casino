public class SchrodingerCard {
    public enum Suit{
        DIAMONDS, HEARTS, SPADES, CLUBS, UNKNOWN
    }
    public int value;
    public Suit suit;

    public SchrodingerCard(){
        this(-1, Suit.UNKNOWN);
    }

    public SchrodingerCard(int value, Suit suit){
        this.value = value;
        this.suit = suit;
    }

    public String toString(){
        String card;

        switch(value){
            case 2: card = "Two of "; break;
            case 3: card = "Three of "; break;
            case 4: card = "Four of "; break;
            case 5: card = "Five of "; break;
            case 6: card = "Six of "; break;
            case 7: card = "Seven of "; break;
            case 8: card = "Eight of "; break;
            case 9: card = "Nine of "; break;
            case 10: card = "Ten of "; break;
            case 11: card = "Jack of "; break;
            case 12: card = "Queen of "; break;
            case 13: card = "King of "; break;
            case 14: card = "Ace of "; break;
            case -1: card = "??? of "; break;
            default: return "INVALID CARD";
        }

        switch(suit){
            case DIAMONDS: card += "Diamonds"; break;
            case HEARTS: card += "Hearts"; break;
            case SPADES: card += "Spades"; break;
            case CLUBS: card += "Clubs"; break;
            case UNKNOWN: card += "???"; break;
            default: return "INVALID CARD";
        }
        return card;
    }
}