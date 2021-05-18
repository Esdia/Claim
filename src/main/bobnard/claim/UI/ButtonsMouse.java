package bobnard.claim.UI;


import bobnard.claim.model.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

public class ButtonsMouse extends MouseAdapter {
    Menu menu;

    public ButtonsMouse(Menu menu) {
        this.menu = menu;
    }


    @Override
    public void mouseEntered(MouseEvent e) {
        super.mouseEntered(e);

        ImageIcon ng = null;
        ImageIcon ru = null;
        ImageIcon ex = null;

        if(menu.skin == "Umineko"){

            ng = new ImageIcon(menu.path+"new_game2.png");
            ru = new ImageIcon(menu.path+"rules2.png");
            ex = new ImageIcon(menu.path+"exit2.png");

        }
        setHover(e, ng, ru, ex);
    }

    private void setHover(MouseEvent e, ImageIcon ng, ImageIcon ru, ImageIcon ex) {
        if (menu.b1.equals(e.getSource())) {
            menu.ng = ng;
        }
        if (menu.b2.equals(e.getSource())) {
            menu.ru = ru;
        }
        if (menu.b3.equals(e.getSource())) {
            menu.ex = ex;
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        super.mouseEntered(e);

        ImageIcon ng = null;
        ImageIcon ru = null;
        ImageIcon ex = null;

        if(menu.skin == "Umineko"){

            ng = new ImageIcon(menu.path+"new_game.png");
            ru = new ImageIcon(menu.path+"rules.png");
            ex = new ImageIcon(menu.path+"exit.png");

        }
        setHover(e, ng, ru, ex);
    }

    @Override
    public void mousePressed(MouseEvent e) {

        Audio.playSE(0);

        if(menu.b1.equals(e.getSource())) {
            if(menu.skin == "Umineko"){
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
            File pdfFile = new File("src/main/bobnard/claim/UI/resources/rules/rules.pdf");
            try {
                Desktop.getDesktop().open(pdfFile);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }

        if(menu.b3.equals(e.getSource())) {
            Audio.getBGM().stop();
            menu.frame.dispose();
        }
    }
}
