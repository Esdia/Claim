package bobnard.claim.UI;


import bobnard.claim.AI.AIMinimaxEasy;
import bobnard.claim.AI.AIMinimaxNormal;
import bobnard.claim.AI.AIRandom;
import bobnard.claim.model.Game;
import bobnard.claim.model.Player;

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

        ImageIcon ng = new ImageIcon(menu.path + "new_game2.png");
        ImageIcon ru = new ImageIcon(menu.path + "rules2.png");
        ImageIcon cf = new ImageIcon(menu.path + "config2.png");
        ImageIcon sk = new ImageIcon(menu.path + "skin2.png");
        ImageIcon ex = new ImageIcon(menu.path + "exit2.png");
        ImageIcon pvp = new ImageIcon(menu.path + "pvp2.png");
        ImageIcon pve = new ImageIcon(menu.path + "pve2.png");


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
        if (menu.b1b.equals(e.getSource())) {
            menu.pve = pve;
        }

    }

    @Override
    public void mouseExited(MouseEvent e) {
        super.mouseEntered(e);


        ImageIcon ng = new ImageIcon(menu.path + "new_game.png");
        ImageIcon ru = new ImageIcon(menu.path + "rules.png");
        ImageIcon cf = new ImageIcon(menu.path + "config.png");
        ImageIcon sk = new ImageIcon(menu.path + "skin.png");
        ImageIcon ex = new ImageIcon(menu.path + "exit.png");
        ImageIcon pvp = new ImageIcon(menu.path + "pvp.png");
        ImageIcon pve = new ImageIcon(menu.path + "pve.png");


        setHover(e, ng, ru, cf, sk, ex, pvp, pve);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);
    }

    @Override
    public void mousePressed(MouseEvent e) {

        Audio.playSE(0);

        if (menu.b1a.equals(e.getSource()) || menu.b1b.equals(e.getSource())) {
            if (Menu.skin.equals("Umineko")) {
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
            Audio.getBGM().stop();

            Game game = new Game();
            Player[] players = new Player[]{
                    null,
                    new Player(1)
            };

            if (menu.b1b.equals(e.getSource())) {
                if(Config.difficulty.equals("Easy")) players[0] = new AIRandom(game, 0);
                if(Config.difficulty.equals("Normal")) players[0] = new AIMinimaxEasy(game, 0);
                if(Config.difficulty.equals("Hard")) players[0] = new AIMinimaxNormal(game, 0);
            } else {
                players[0] = new Player(0);
            }

            game.setPlayers(players);

            Window.switchToGame(game);
        }


        if (menu.b1.equals(e.getSource())) {
            if (menu.b1a.isVisible()) {
                menu.b1a.setVisible(false);
                menu.b1b.setVisible(false);
            } else {
                menu.b1a.setVisible(true);
                menu.b1b.setVisible(true);
            }
        }


        if (menu.b2.equals(e.getSource())) {
            File pdfFile = new File("src/main/bobnard/claim/UI/resources/" + Menu.skin + "/rules/rules.pdf");
            try {
                Desktop.getDesktop().open(pdfFile);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }

        if (menu.b3.equals(e.getSource())) {
            menu.isConfig = !menu.isConfig;
            menu.config.refresh();
            menu.refresh();
        }

        if (menu.b4.equals(e.getSource())) {
            menu.isSs = !menu.isSs;
            menu.panel.refresh();
            menu.refresh();
        }

        if (menu.b5.equals(e.getSource())) {
            Audio.getBGM().stop();
            Menu.frame.dispose();
        }
    }
}
