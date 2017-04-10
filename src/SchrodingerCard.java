public class SchrodingerCard {
    public boolean[] possibilityModel;
    public double[] probabilityModel;
    private boolean locked;

    public SchrodingerCard(){
        possibilityModel = new boolean[52];
        probabilityModel = new double[52];
        for (int i = 0; i < probabilityModel.length; i++){
            possibilityModel[i] = true;
        }
    }

    public static String cardToString(int cardIndex){
        int suit = cardIndex / 13;
        int value = cardIndex % 13;
        String card;

        switch(value){
            case 0: card = "Two of "; break;
            case 1: card = "Three of "; break;
            case 2: card = "Four of "; break;
            case 3: card = "Five of "; break;
            case 4: card = "Six of "; break;
            case 5: card = "Seven of "; break;
            case 6: card = "Eight of "; break;
            case 7: card = "Nine of "; break;
            case 8: card = "Ten of "; break;
            case 9: card = "Jack of "; break;
            case 10: card = "Queen of "; break;
            case 11: card = "King of "; break;
            case 12: card = "Ace of "; break;
            default: return "INVALID CARD";
        }

        switch(suit){
            case 0: card += "Diamonds"; break;
            case 1: card += "Hearts"; break;
            case 2: card += "Spades"; break;
            case 3: card += "Clubs"; break;
            default: return "INVALID CARD";
        }
        return card;
    }

    public String toString(){
        String out = "";
        double maxProbability = 0;
        for (double prob: probabilityModel){
            if (prob > maxProbability) maxProbability = prob;
        }
        for (int i = 0; i < probabilityModel.length; i++){
            double prob = probabilityModel[i];
            if (prob == maxProbability){
                if (out.length() != 0) out += ", ";
                out += cardToString(i) + " (" + (Math.round(prob * 100000))/1000.0 + "%)";
            }
        }

        return out;
    }
}