package bobnard.claim.model;

import java.io.*;

public class Save {
    PhaseOne p1 ;
    PhaseTwo p2 ;
    Game game ;
    ScoreStack score ;
    public void save ( String fileName){
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
    @SuppressWarnings("unchecked")

    public void load (String fileName){
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
}

