package entities;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.net.URL;

public class Sound {
    Clip clip;
    URL soundURL[] = new URL[10];

    public Sound() {
        soundURL[0] = getClass().getResource("/Sounds/epicBGM.wav");
        soundURL[1] = getClass().getResource("/Sounds/EXPLOSION!!!.wav");
        soundURL[2] = getClass().getResource("/Sounds/PDie.wav");
        soundURL[3] = getClass().getResource("/Sounds/PowerUp.wav");
        soundURL[4] = getClass().getResource("/Sounds/OnealDie.wav");

    }
    public void setFile(int i) {
        try {
            AudioInputStream audioIS = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(audioIS);
        } catch (Exception e) {
            System.out.println("audio jam");
        }
    }

    public void start() {
        clip.start();
    }

    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop() {
        clip.stop();
    }
}
