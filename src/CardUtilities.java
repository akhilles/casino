/**
 * Created by akhilvelagapudi on 4/16/2017.
 */
public class CardUtilities {
    public static String cardToString(int cardIndex){
        int suit = cardIndex / 4;
        int value = cardIndex % 4;
        String card;
        switch (value){
            case 8:  card = "10-"; break;
            case 9:  card = " J-"; break;
            case 10: card = " Q-"; break;
            case 11: card = " K-"; break;
            case 12: card = " A-"; break;
            default: card = " " + (value + 2) + "-"; break;
        }
        switch (suit){
            case 0: card += "H"; break;
            case 1: card += "D"; break;
            case 2: card += "S"; break;
            case 3: card += "C"; break;
        }
        return card;
    }

    public static int stringToCard(String cardString){
        int value, suit;
        String valueString = cardString.substring(0,1);
        String suitString = cardString.substring(1,2);
        if (cardString.length() == 3){
            valueString = cardString.substring(0,2);
            suitString = cardString.substring(2,3);
        }
        switch (valueString){
            case "J": value = 9; break;
            case "Q": value = 10; break;
            case "K": value = 11; break;
            case "A": value = 12; break;
            default: value = Integer.parseInt(valueString) - 2; break;
        }
        switch (suitString){
            case "H": suit = 0; break;
            case "D": suit = 1; break;
            case "S": suit = 2; break;
            case "C": suit = 3; break;
            default: suit = -1; break;
        }
        return 13 * suit + value;
    }
}
