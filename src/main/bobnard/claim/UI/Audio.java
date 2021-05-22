package bobnard.claim.UI;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.Random;

public class Audio  {

    private static File p1bf;
    private static File[] p1blf;
    private static File p2bf;
    private static File[] p2blf;

    private static File menuBGM;
    private static File menuSE1;
    private static File menuSE2;

    private static Clip bgm;

    private static Integer NB_SONG_P1;
    private static Integer NB_SONG_P2;


    public static void setFiles(){
            System.out.println(Menu.skin);
            p1bf = new File("src/main/bobnard/claim/UI/resources/"+Menu.skin+"/audio/phase1/bgm");
            p2bf = new File("src/main/bobnard/claim/UI/resources/"+Menu.skin+"/audio/phase2/bgm");
            p1blf = p1bf.listFiles();
            p2blf = p2bf.listFiles();
            NB_SONG_P1 = p1blf.length;
            NB_SONG_P2 = p2blf.length;
            menuBGM = new File("src/main/bobnard/claim/UI/resources/"+Menu.skin+"/audio/menu/bgm/happiness_of_marionette_omake.wav");
            menuSE1 =  new File("src/main/bobnard/claim/UI/resources/"+Menu.skin+"/audio/menu/se/zyosys1.wav");
            menuSE2 = new File("src/main/bobnard/claim/UI/resources/"+Menu.skin+"/audio/menu/se/ZS1.WAV");


    }

    public static void playBGM(int phase){
        File song;
        Random rand = new Random();
        song = switch (phase) {
            case 0 -> menuBGM;
            case 1 -> p1blf[rand.nextInt(NB_SONG_P1)];
            case 2 -> p2blf[rand.nextInt(NB_SONG_P2)];
            default -> throw new IllegalStateException("Unexpected value: " + phase);
        };

        play(song, true);

    }

    public static void playSE(int number){
        File song = switch (number) {
            case 0 -> menuSE1;
            case 1 -> menuSE2;
            default -> throw new IllegalStateException("Unexpected value: " + number);
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
