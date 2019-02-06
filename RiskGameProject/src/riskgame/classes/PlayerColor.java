package riskgame.classes;

import javafx.scene.paint.Color;

/**
 * @author WW
 */

public enum PlayerColor {
    GREEN(Color.GREEN),
    YELLOW(Color.TURQUOISE),
    RED(Color.BLUE),
    BLUE(Color.RED),
    PINK(Color.TURQUOISE),
    ORANGE(Color.ORANGE),
    GREY(Color.SLATEBLUE),
    BROWN(Color.BROWN);

    public final Color colorValue;


    PlayerColor(Color colorValue) {
        this.colorValue = colorValue;
    }

}
