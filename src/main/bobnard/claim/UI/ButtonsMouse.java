package bobnard.claim.UI;


import bobnard.claim.model.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

public class ButtonsMouse extends MouseAdapter {
    final Menu menu;

    public ButtonsMouse(Menu menu) {
        this.menu = menu;
    }


    @Override
    public void mouseEntered(MouseEvent e) {
        super.mouseEntered(e);

        ImageIcon ng = null;
        ImageIcon ru = null;
        ImageIcon cf = null;
        ImageIcon sk = null;
        ImageIcon ex = null;
        ImageIcon pvp = null;
        ImageIcon pve = null;

            ng = new ImageIcon(menu.path+"new_game2.png");
            ru = new ImageIcon(menu.path+"rules2.png");
            cf = new ImageIcon(menu.path+"config2.png");
            sk = new ImageIcon(menu.path+"skin2.png");
            ex = new ImageIcon(menu.path+"exit2.png");
            pvp = new ImageIcon(menu.path+"pvp2.png");
            pve = new ImageIcon(menu.path+"pve2.png");
            

        setHover(e, ng, ru, cf, sk, ex, pvp, pve);
    }

    private void setHover(MouseEvent e, ImageIcon ng, ImageIcon ru, ImageIcon cf, ImageIcon sk, ImageIcon ex, ImageIcon pvp, ImageIcon pve) {
        if (menu.b1.equals(e.getSource())) {
            menu.ng = ng;
        }
        if (menu.b2.equals(e.getSource())) {
            menu.ru = ru;
        }
        if (menu.b3.equals(e.getSource())) {
            menu.cf = cf;
        }
        if (menu.b4.equals(e.getSource())) {
            menu.sk = sk;
        }
        if (menu.b5.equals(e.getSource())) {
            menu.ex = ex;
        }
        if (menu.b1a.equals(e.getSource())) {
        	menu.pvp = pvp;
        }
        if(menu.b1b.equals(e.getSource())) {
        	menu.pve = pve;
        }
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        super.mouseEntered(e);

        ImageIcon ng = null;
        ImageIcon ru = null;
        ImageIcon cf = null;
        ImageIcon sk = null;
        ImageIcon ex = null;
        ImageIcon pvp = null;
        ImageIcon pve = null;


            ng = new ImageIcon(menu.path+"new_game.png");
            ru = new ImageIcon(menu.path+"rules.png");
            cf = new ImageIcon(menu.path+"config.png");
            sk = new ImageIcon(menu.path+"skin.png");
            ex = new ImageIcon(menu.path+"exit.png");
            pvp = new ImageIcon(menu.path+"pvp.png");
            pve = new ImageIcon(menu.path+"pve.png");


        setHover(e, ng, ru, cf, sk, ex, pvp, pve);
    }

    @Override
    public void mousePressed(MouseEvent e) {

        Audio.playSE(0);

        if(menu.b1.equals(e.getSource())) {
            if(Menu.skin.equals("Umineko")){
                menu.isStart = true;
                synchronized (menu) {
                    try {
                        menu.refresh();
                        Audio.playSE(1);
                        menu.wait(2500);
                    } catch (InterruptedException interruptedException) {
                        interruptedException.printStackTrace();
                    }
                }
            }

            menu.frame.dispose();
            Audio.getBGM().stop();
            Game game = new Game();
            MainWindow.start(game);
        }

        if(menu.b2.equals(e.getSource())) {
            File pdfFile = new File("src/main/bobnard/claim/UI/resources/"+menu.skin+"/rules/rules.pdf");
            try {
                Desktop.getDesktop().open(pdfFile);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }

        if(menu.b4.equals(e.getSource())) {
            menu.changeSkin();
        }

        if(menu.b5.equals(e.getSource())) {
            Audio.getBGM().stop();
            menu.frame.dispose();
        }
    }
}
