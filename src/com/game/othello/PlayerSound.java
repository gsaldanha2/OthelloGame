package com.game.othello;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

/**
 * Created by Gregory on 4/29/2016.
 */
public class PlayerSound {
    public PlayerSound() {
        if(Game.playerSound.isSelected()) {
            try {
                URL url = this.getClass().getClassLoader().getResource("player_turn.wav");
                AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
                Clip clip = AudioSystem.getClip();
                clip.open(audioIn);
                clip.start();
            } catch (UnsupportedAudioFileException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (LineUnavailableException e) {
                e.printStackTrace();
            }
        }
    }
}
