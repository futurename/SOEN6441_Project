package riskgame.classes;

import javafx.scene.paint.Color;

/**
 * @author WW
 */

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
