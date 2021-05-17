package bobnard.claim.UI;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

public class Audio  {

    private static String path = "src/main/bobnard/claim/UI/resources/audio/";

    public static void play(String name, boolean loop){
        File song = new File(path+name);
        Clip clip;

        try{
            clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(song));

            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);

            double gain = 0.15;
            float dB = (float) (Math.log(gain) / Math.log(10.0) * 20.0);
            gainControl.setValue(dB);
            if(loop) clip.loop(Clip.LOOP_CONTINUOUSLY);
            else clip.start();

        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
