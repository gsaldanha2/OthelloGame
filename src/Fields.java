import java.awt.*;

/**
 * Created by Gregory on 1/18/2016.
 */
//this class contains static fields used globally
public class Fields {
    public static final int WHITE = 1;
    public static final int BLACK = 2;
    public static final int EMPTY = -1;
    public static int player = 1;
    public static Color bgColor = Color.BLACK;
    public static Color lineColor = Color.ORANGE;
    public static Color whiteColor = Color.CYAN;
    public static Color blackColor = Color.WHITE;
    public static boolean AiMode = true;

    public static Color getBgColor() {
        return bgColor;
    }

    public static void nextMove() {
        player = (player == 1) ? 2 : 1;
    }

    public static void setBgColor(Color bgColor) {
        Fields.bgColor = bgColor;
    }

    public static Color getLineColor() {
        return lineColor;
    }

    public static void setLineColor(Color lineColor) {
        Fields.lineColor = lineColor;
    }

    public static Color getwhiteColor() {
        return whiteColor;
    }

    public static void setwhiteColor(Color whiteColor) {
        Fields.whiteColor = whiteColor;
    }

    public static Color getblackColor() {
        return blackColor;
    }

    public static void setblackColor(Color blackColor) {
        Fields.blackColor = blackColor;
    }
}
