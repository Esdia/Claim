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
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class ButtonsMouse extends MouseAdapter {
    final Menu menu;

    public ButtonsMouse(Menu menu) {
        this.menu = menu;
    }


    @Override
    public void mouseEntered(MouseEvent e) {
        super.mouseEntered(e);

        ImageIcon ng = Utils.loadIcon(menu.path + "new_game2.png");
        ImageIcon ru = Utils.loadIcon(menu.path + "rules2.png");
        ImageIcon cf = Utils.loadIcon(menu.path + "config2.png");
        ImageIcon sk = Utils.loadIcon(menu.path + "skin2.png");
        ImageIcon ex = Utils.loadIcon(menu.path + "exit2.png");
        ImageIcon pvp = Utils.loadIcon(menu.path + "pvp2.png");
        ImageIcon pve = Utils.loadIcon(menu.path + "pve2.png");


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


        ImageIcon ng = Utils.loadIcon(menu.path + "new_game.png");
        ImageIcon ru = Utils.loadIcon(menu.path + "rules.png");
        ImageIcon cf = Utils.loadIcon(menu.path + "config.png");
        ImageIcon sk = Utils.loadIcon(menu.path + "skin.png");
        ImageIcon ex = Utils.loadIcon(menu.path + "exit.png");
        ImageIcon pvp = Utils.loadIcon(menu.path + "pvp.png");
        ImageIcon pve = Utils.loadIcon(menu.path + "pve.png");


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
            Audio.stopBGM();

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
            InputStream in = Utils.loadInputStream("/" + Menu.skin + "/rules/rules.pdf");

            try {
                Path tmp = Files.createTempFile("tempPdf", ".pdf");

                tmp.toFile().deleteOnExit();

                Files.copy(in, tmp, StandardCopyOption.REPLACE_EXISTING);

                Desktop.getDesktop().open(tmp.toFile());
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
            Audio.stopBGM();
            Menu.frame.dispose();
        }
    }
}
