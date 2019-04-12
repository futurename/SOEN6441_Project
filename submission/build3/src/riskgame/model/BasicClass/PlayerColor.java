package riskgame.model.BasicClass;

import javafx.scene.paint.Color;

/**
 * This class enumates colors used for assigning to each player
 * @author WW
 * @since build1
 **/
public enum PlayerColor {
    GREEN(Color.GREEN),
    YELLOW(Color.RED),
    RED(Color.BLUE),
    BLUE(Color.BROWN),
    PINK(Color.TURQUOISE),
    ORANGE(Color.ORANGE),
    GREY(Color.SLATEBLUE),
    BROWN(Color.CRIMSON),
    DRAWPLAYER(Color.BLACK);

    public final Color colorValue;

    PlayerColor(Color colorValue) {
        this.colorValue = colorValue;
    }
}
