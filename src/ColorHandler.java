import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;

/**
 * Created by Gregory on 2/4/2016.
 */
public class ColorHandler {
    public final static String toHexString(Color colour) throws NullPointerException {
        String hexColour = Integer.toHexString(colour.getRGB() & 0xffffff);
        if (hexColour.length() < 6) {
            hexColour = "000000".substring(0, 6 - hexColour.length()) + hexColour;
        }
        return "#" + hexColour;
    }

    public static void saveColorData() {
        try {
            WriteFile data = new WriteFile(ColorHandler.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath() + "colorDataOthello.txt");
            data.writeToFile(toHexString(Fields.bgColor), false);
            data.writeToFile(toHexString(Fields.lineColor), true);
            data.writeToFile(toHexString(Fields.whiteColor), true);
            data.writeToFile(toHexString(Fields.blackColor), true);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public static void readColorData() {
        try {
            ReadFile file = new ReadFile(ColorHandler.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath() + "colorDataOthello.txt");
            String[] data = file.OpenFile(4);

            Fields.bgColor = Color.decode(data[0]);
            Fields.lineColor = Color.decode(data[1]);
            Fields.whiteColor = Color.decode(data[2]);
            Fields.blackColor = Color.decode(data[3]);
        } catch (IOException e) {
            System.out.println("File not found - using defaults");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

    }
}
