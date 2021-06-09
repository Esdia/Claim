package bobnard.claim.UI;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.io.File;
import java.util.Random;

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

    private static int volume = 15;


    public static void setFiles() {
        if (Utils.isInJar()) {
            return;
        }

        System.out.println(Menu.skin);

        String path = "audio/";

        p1blf = Utils.loadFiles(path + "phase1/BGM");
        p2blf = Utils.loadFiles(path + "phase2/BGM");
        NB_SONG_P1 = p1blf.length;
        NB_SONG_P2 = p2blf != null ? p2blf.length : 0;
        menuBGM = Utils.loadFiles(path + "menu/BGM");
        NB_SONG_MENU = menuBGM != null ? menuBGM.length : 0;
        menuSE1 = Utils.loadFile(path + "menu/SE/sysb.wav");
        if (Menu.skin.equals("Umineko"))
            menuSE2 = Utils.loadFile(path + "menu/SE/ZS1.WAV");
    }

    public static void setVolume(int volume) {
        if (Utils.isInJar()) {
            return;
        }

        Audio.volume = volume;
        float dB = (float) (Math.log((double) volume/100) / Math.log(10.0) * 20.0);
        FloatControl gainControl = (FloatControl) bgm.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(dB);
    }

    public static void playBGM(int phase) {
        if (Utils.isInJar()) {
            return;
        }

        File song;
        Random rand = new Random();

        switch (phase) {
            case 0:
                song = menuBGM[rand.nextInt(NB_SONG_MENU)];
                break;
            case 1:
                song = p1blf[rand.nextInt(NB_SONG_P1)];
                break;
            case 2:
                song = p2blf[rand.nextInt(NB_SONG_P2)];
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + phase);
        }

        play(song, true);

    }

    public static void playSE(int number) {
        if (Utils.isInJar()) {
            return;
        }

        File song;

        switch (number) {
            case 0:
                song = menuSE1;
                break;
            case 1:
                song = menuSE2;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + number);
        }

        play(song, false);

    }

    private static void play(File song, boolean loop) {
        Clip clip;
        try {
            clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(song));

            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            float dB = (float) (Math.log((double) volume/100) / Math.log(10.0) * 20.0);
            gainControl.setValue(dB);
            if (loop) {
                bgm = clip;
                Save.sysload(Menu.frame);
                clip.loop(Clip.LOOP_CONTINUOUSLY);
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

    public static void stopBGM() {
        if (!Utils.isInJar()) {
            bgm.stop();
        }
    }

    public static int getVolume() {
        return volume;
    }
}
