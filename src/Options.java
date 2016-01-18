import java.awt.*;

/**
 * Created by Gregory on 1/18/2016.
 */
public class Options {
    public static Color bgColor = Color.BLACK;
    public static Color lineColor = Color.ORANGE;
    public static Color player1Color = Color.CYAN;
    public static Color player2Color = Color.WHITE;

    public static Color getBgColor() {
        return bgColor;
    }

    public static void setBgColor(Color bgColor) {
        Options.bgColor = bgColor;
    }

    public static Color getLineColor() {
        return lineColor;
    }

    public static void setLineColor(Color lineColor) {
        Options.lineColor = lineColor;
    }

    public static Color getPlayer1Color() {
        return player1Color;
    }

    public static void setPlayer1Color(Color player1Color) {
        Options.player1Color = player1Color;
    }

    public static Color getPlayer2Color() {
        return player2Color;
    }

    public static void setPlayer2Color(Color player2Color) {
        Options.player2Color = player2Color;
    }
}
