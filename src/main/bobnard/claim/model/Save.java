package bobnard.claim.model;

import bobnard.claim.UI.Audio;
import bobnard.claim.UI.Config;
import bobnard.claim.UI.Menu;
import bobnard.claim.UI.Window;

import javax.swing.*;
import java.io.*;

public class Save {
    static PhaseOne p1 ;
    static PhaseTwo p2 ;
    static Game game ;
    static ScoreStack score ;

    public static void save(String fileName){
        try{
            FileOutputStream fsave = new FileOutputStream(fileName);
            ObjectOutputStream out = new ObjectOutputStream(fsave);
            out.writeObject(game);
            out.writeObject(score);
            /*phase 1 */
            if (game.getPhaseNum()== 1 ) {
                out.writeObject(p1);
            }else/*phase 2 */{
                out.writeObject(p2);
            }
            out.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void load(String fileName){
        try {
            FileInputStream fload = new FileInputStream(fileName);
            ObjectInputStream in = new ObjectInputStream(fload);
            game = (Game) in.readObject();
            score = (ScoreStack) in.readObject();
            if (game.getPhaseNum()== 1 ){
                p1 = (PhaseOne) in.readObject();
            }else{
                p2 = (PhaseTwo) in.readObject();
            }
            in.close();
        }catch (IOException | ClassNotFoundException e ){
            e.printStackTrace();
        }
    }

    public static void syssave(Config config){
        try{
            FileOutputStream fsave = new FileOutputStream("sys");
            ObjectOutputStream out = new ObjectOutputStream(fsave);
            out.writeObject(Menu.skin);
            out.writeObject(Audio.getVolume());
            out.writeObject(config.isFS);
            out.writeObject(Config.difficulty);
            out.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }


    public static void sysload(JFrame frame){
        try {
            FileInputStream fload = new FileInputStream("sys");
            ObjectInputStream in = new ObjectInputStream(fload);
            Menu.skin = (String) in.readObject();
            Audio.setVolume((Integer) in.readObject());
            boolean isFS = (Boolean) in.readObject();
            Window.isFS = isFS;
            if(isFS) Config.device.setFullScreenWindow(frame);

            String difficulty = (String) in.readObject();
            Config.difficulty = difficulty;

            in.close();
        }catch (IOException | ClassNotFoundException e ){
            e.printStackTrace();
            Window.isFS = false;
            Config.difficulty = "Normal";
        }
    }

    public static void sysloadSkin(){
        try {
            FileInputStream fload = new FileInputStream("sys");
            ObjectInputStream in = new ObjectInputStream(fload);
            Menu.skin = (String) in.readObject();

            in.close();
        }catch (IOException | ClassNotFoundException e ){
            e.printStackTrace();
            Menu.skin = "Vanilla";
        }
    }



}

