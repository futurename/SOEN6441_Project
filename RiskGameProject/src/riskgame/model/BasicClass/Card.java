package riskgame.model.BasicClass;

import java.util.Random;

/**
 * Card class
 **/

public enum Card {
    INFANTRY("Infantry"),
    CAVALRY("Cavalry"),
    ARTILLERY("Artillery");

    private final String cardType;

    Card(String cardType) {
        this.cardType = cardType;
    }

    public static Card getCard(Class<Card> cardClass){
        return cardClass.getEnumConstants()[new Random().nextInt(cardClass.getEnumConstants().length)];
    }

}
