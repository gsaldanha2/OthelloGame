package com.game.othello;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * Created by Gregory on 2/13/2016.
 */
public class DebugHandler {

    public static void writeGameData(int p1, int p2) {
        try {
            WriteFile data = new WriteFile(DebugHandler.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath() + "gameInfo.txt");
            data.writeToFile(p1 + ", " + p2, true);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        Game.restart();
    }
}
