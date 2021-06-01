package bobnard.claim.UI;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.Control;
import javax.sound.sampled.FloatControl;
import java.io.File;
import java.util.Random;
import java.util.ResourceBundle;

public class Audio {

    private static File[] p1blf;
    private static File[] p2blf;
    private static File[] menuBGM;

    private static File menuSE1;
    private static File menuSE2;

    private static Clip bgm;

    private static Integer NB_SONG_MENU;
    private static Integer NB_SONG_P1;
    private static Integer NB_SONG_P2;

    private static int volume;


    public static void setFiles() {
        System.out.println(Menu.skin);
        File p1bf = new File("src/main/bobnard/claim/UI/resources/" + Menu.skin + "/audio/phase1/bgm");
        File p2bf = new File("src/main/bobnard/claim/UI/resources/" + Menu.skin + "/audio/phase2/bgm");
        p1blf = p1bf.listFiles();
        p2blf = p2bf.listFiles();
        NB_SONG_P1 = p1blf.length;
        NB_SONG_P2 = p2blf != null ? p2blf.length : 0;
        menuBGM = new File("src/main/bobnard/claim/UI/resources/" + Menu.skin + "/audio/menu/bgm/").listFiles();
        NB_SONG_MENU = menuBGM != null ? menuBGM.length : 0;
        menuSE1 = new File("src/main/bobnard/claim/UI/resources/" + Menu.skin + "/audio/menu/se/sysb.wav");
        if (Menu.skin.equals("Umineko"))
            menuSE2 = new File("src/main/bobnard/claim/UI/resources/" + Menu.skin + "/audio/menu/se/ZS1.WAV");


    }

    public static void setVolume(int volume) {
        Audio.volume = volume;
        float dB = (float) (Math.log((double) volume/100) / Math.log(10.0) * 20.0);
        FloatControl gainControl = (FloatControl) bgm.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(dB);
    }
    public static int getVolume(){
        return volume;
    }

    public static void playBGM(int phase) {
        File song;
        Random rand = new Random();
        song = switch (phase) {
            case 0 -> menuBGM[rand.nextInt(NB_SONG_MENU)];
            case 1 -> p1blf[rand.nextInt(NB_SONG_P1)];
            case 2 -> p2blf[rand.nextInt(NB_SONG_P2)];
            default -> throw new IllegalStateException("Unexpected value: " + phase);
        };

        play(song, true);

    }

    public static void playSE(int number) {
        File song = switch (number) {
            case 0 -> menuSE1;
            case 1 -> menuSE2;
            default -> throw new IllegalStateException("Unexpected value: " + number);
        };

        play(song, false);

    }

    private static void play(File song, boolean loop) {
        Clip clip;
        try {
            clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(song));

            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);

            float dB = (float) (Math.log((double) 15/100) / Math.log(10.0) * 20.0);
            gainControl.setValue(dB);
            if (loop) {
                clip.loop(Clip.LOOP_CONTINUOUSLY);
                bgm = clip;
            } else {
                clip.start();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void reload() {
        bgm.stop();
        setFiles();
        Audio.playBGM(0);
    }

    public static Clip getBGM() {
        return bgm;
    }
}
