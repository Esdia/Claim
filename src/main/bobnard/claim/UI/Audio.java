package bobnard.claim.UI;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.Random;

public class Audio  {

    private static File p1bf = new File("src/main/bobnard/claim/UI/resources/audio/phase1/bgm");
    private static final File[] p1blf = p1bf.listFiles();
    private static File p2bf = new File("src/main/bobnard/claim/UI/resources/audio/phase2/bgm");
    private static final File[] p2blf = p2bf.listFiles();

    private static Clip bgm;

    private static Integer NB_SONG_P1 = p1blf.length;
    private static Integer NB_SONG_P2 = p2blf.length;

    public static void playBGM(int phase){
        File song;
        Random rand = new Random();
        song = switch (phase) {
            case 0 -> new File("src/main/bobnard/claim/UI/resources/audio/menu/bgm/happiness_of_marionette_omake.wav");
            case 1 -> p1blf[rand.nextInt(NB_SONG_P1)];
            case 2 -> p2blf[rand.nextInt(NB_SONG_P2)];
            default -> throw new IllegalStateException("Unexpected value: " + phase);
        };

        play(song, true);

    }

    public static void playSE(int phase){
        File song = switch (phase) {
            case 0 -> new File("src/main/bobnard/claim/UI/resources/audio/menu/se/zyosys1.wav");
            case 1 -> null;
            default -> throw new IllegalStateException("Unexpected value: " + phase);
        };

        play(song, false);

    }

    private static void play(File song, boolean loop){
        Clip clip;
        try{
            clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(song));

            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);

            double gain = 0.15;
            float dB = (float) (Math.log(gain) / Math.log(10.0) * 20.0);
            gainControl.setValue(dB);
            if(loop){
                clip.loop(Clip.LOOP_CONTINUOUSLY);
                bgm = clip;
            }
            else{
                clip.start();
            }

        } catch (Exception e){
            e.printStackTrace();
        }

    }

    public static Clip getBGM(){
        return bgm;
    }
}
