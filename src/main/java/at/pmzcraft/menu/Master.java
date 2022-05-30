package at.pmzcraft.menu;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;


public class Master {
    public static void main(String args[]) {
        JFrame f = new JFrame("P*ZCraft");
        Musician musikant = null;
        try {
            musikant = new Musician();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        CardLayout layout = new Menu(f,"EN", musikant);
    }
}