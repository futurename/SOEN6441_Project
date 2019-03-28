package riskgame.model.BasicClass;

import javafx.scene.paint.Color;

/**
 * This class enumates colors used for assigning to each player
 **/
public enum PlayerColor {
    GREEN(Color.GREEN),
    YELLOW(Color.RED),
    RED(Color.BLUE),
    BLUE(Color.BROWN),
    PINK(Color.TURQUOISE),
    ORANGE(Color.ORANGE),
    GREY(Color.SLATEBLUE),
    BROWN(Color.CRIMSON);

    public final Color colorValue;

    PlayerColor(Color colorValue) {
        this.colorValue = colorValue;
    }
}
