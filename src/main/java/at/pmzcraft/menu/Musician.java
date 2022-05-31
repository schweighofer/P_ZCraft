package at.pmzcraft.menu;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class Musician {
    private Clip clip = null;
    private FloatControl gainControl;
    public Musician() throws LineUnavailableException, UnsupportedAudioFileException, IOException {
        URL url = null;
        url = new File("src/main/resources/menu/SoundHelix-Song-1.wav").toURI().toURL();

        clip = AudioSystem.getClip();
        AudioInputStream ais = null;
        ais = AudioSystem.getAudioInputStream( url );

        clip.open(ais);
        gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(-37);
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void changeVolume(float volume){
        gainControl.setValue(volume);
    }
    public void aufhoeren(){
        clip.stop();
    }

}
