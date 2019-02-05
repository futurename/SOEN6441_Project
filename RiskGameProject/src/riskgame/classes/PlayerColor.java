package riskgame.classes;

import javafx.scene.paint.Color;

/**
 * @author WW
 */

public enum PlayerColor {
    GREEN(Color.GREEN),
    YELLOW(Color.YELLOW),
    RED(Color.RED),
    BLUE(Color.BLUE),
    PINK(Color.PINK),
    ORANGE(Color.ORANGE),
    GREY(Color.GREY),
    BROWN(Color.BROWN);

    public final Color colorValue;


    PlayerColor(Color colorValue) {
        this.colorValue = colorValue;
    }

}
