package bobnard.claim.UI;


import bobnard.claim.model.Game;
import bobnard.claim.model.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
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
    public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);
    }

    @Override
    public void mousePressed(MouseEvent e) {

        Audio.playSE(0);

        if(menu.b1a.equals(e.getSource()) || menu.b1b.equals(e.getSource()) ) {
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
            Menu.frame.dispose();
            Audio.getBGM().stop();

            Game game = new Game();
            Player[] players = new Player[] {
                    null,
                    new Player(1)
            };

            if(menu.b1b.equals(e.getSource())){
                System.out.println("ddd");
                players[0] = new AIMinimax(game, 0, Difficulty.EASY);
            }
            else{
                players[0] = new Player(0);
            }

            game.setPlayers(players);
            game.start();

            MainWindow.start(game);
        }


        if(menu.b1.equals(e.getSource())) {
            if(menu.b1a.isVisible()) {
                menu.b1a.setVisible(false);
                menu.b1b.setVisible(false);
            }else{
                menu.b1a.setVisible(true);
                menu.b1b.setVisible(true);
            }
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
            menu.isSs = !menu.isSs;
            menu.panel.refresh();
            menu.refresh();
        }

        if(menu.b5.equals(e.getSource())) {
            Audio.getBGM().stop();
            Menu.frame.dispose();
        }
    }
}
