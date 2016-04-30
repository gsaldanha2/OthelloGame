package com.game.othello;

import java.awt.*;

/**
 * Created by Gregory on 1/18/2016.
 */
//this class contains static fields used globally
public class Fields {
    public static final int WHITE = 1;
    public static final int BLACK = 2;
    public static final int EMPTY = -1;
    public static int playType = 1;
    public static int cornerBiasPoints = 5;
    public static int edgeBiasPoints = 3;
    public static int cornerRiskPoints = -3;
    public static boolean minimax = false, corners = false, random = false, casual = false, minimaxOpp = false, cornersOpp = false, randomOpp = false, casualOpp = false;
    public static int difficulty = 1, difficultyOpp = 1;
    public static int player = 1, ai = 2, currPlayer = 1;
    public static int player1score = 2, player2score = 2;
    public static Color bgColor = new Color(0x2B7254);
    public static Color lineColor = new Color(0x153A2E);
    public static Color whiteColor = new Color(0x03041B);
    public static Color blackColor = new Color(0xC8C9B1);

    public static boolean useMinimax() {
        if(playType == 1) {
            return minimax;
        }
        if(currPlayer == 1) {
            return minimax;
        }
        return minimaxOpp;
    }

    public static boolean useCorners() {
        if(playType == 1) {
            return corners;
        }
        if(currPlayer == 1) {
            return corners;
        }
        return cornersOpp;
    }

    public static boolean useRandom() {
        if(playType == 1) {
            return random;
        }
        if(currPlayer == 1) {
            return random;
        }
        return randomOpp;
    }
    public static boolean useCasual() {
        if(playType == 1) {
            return casual;
        }
        if(currPlayer == 1) {
            return casual;
        }
        return casualOpp;
    }

}
