import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;
public class Track {
    private Clip clip;

    public void main (){
        try {
            File track = new File("resources/Tracks/Track.wav");
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(track);
            clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
            ex.printStackTrace();
        }
    }

    public void opening (){
        try {
            File track = new File("resources/Tracks/Opening.wav");
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(track);
            clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
            ex.printStackTrace();
        }
    }
    public void stop(){
        if (clip.isRunning())
            clip.stop();
    }

    public void win (){
        try {
            File track = new File("resources/Tracks/Win.wav");
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(track);
            clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
            ex.printStackTrace();
        }
    }
    public void fail (){
        try {
            File track = new File("resources/Tracks/Fail.wav");
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(track);
            clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
            ex.printStackTrace();
        }
    }

}
