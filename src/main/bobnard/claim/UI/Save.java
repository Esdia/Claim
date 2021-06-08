package bobnard.claim.UI;

import bobnard.claim.model.Game;

import javax.swing.*;
import java.io.*;

public class Save {

    public static void save(String fileName, Game game) {
        try {
            FileOutputStream fSave = new FileOutputStream(fileName);
            ObjectOutputStream out = new ObjectOutputStream(fSave);

            out.writeObject(game);

            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Game load(String fileName) {
        try {
            FileInputStream fLoad = new FileInputStream(fileName);
            ObjectInputStream in = new ObjectInputStream(fLoad);

            Game game = (Game) in.readObject();

            in.close();

            return game;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void syssave(Config config) {
        try {
            FileOutputStream fsave = new FileOutputStream("sys");
            ObjectOutputStream out = new ObjectOutputStream(fsave);
            out.writeObject(Menu.skin);
            out.writeObject(Audio.getVolume());
            out.writeObject(config.isFS);
            out.writeObject(Config.difficulty);
            out.writeObject(Config.firstPlayer);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void sysload(JFrame frame) {
        try {
            FileInputStream fload = new FileInputStream("sys");
            ObjectInputStream in = new ObjectInputStream(fload);
            Menu.skin = (String) in.readObject();
            Audio.setVolume((Integer) in.readObject());
            boolean isFS = (Boolean) in.readObject();
            Window.isFS = isFS;
            if (isFS) Config.device.setFullScreenWindow(frame);

            Config.difficulty = (String) in.readObject();
            Config.firstPlayer = (String) in .readObject();

            in.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            Window.isFS = false;
            Config.difficulty = "Normal";
            Config.firstPlayer = "Random";
        }
    }

    public static void sysloadSkin() {
        try {
            FileInputStream fload = new FileInputStream("sys");
            ObjectInputStream in = new ObjectInputStream(fload);
            Menu.skin = (String) in.readObject();

            in.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            Menu.skin = "Vanilla";
        }
    }


}

