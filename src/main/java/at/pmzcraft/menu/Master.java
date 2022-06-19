package at.pmzcraft.menu;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.io.IOException;


public class Master {
    public static void main(String args[]) {
        JFrame f = new JFrame("P*ZCraft");
        Musician musikant = null;
        try {
            musikant = Musician.getInstance();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        new Menu(f,"EN", musikant);
    }

    public static void stopProgram() {
        Musician.aufhoeren();
        System.exit(0);
    }
}