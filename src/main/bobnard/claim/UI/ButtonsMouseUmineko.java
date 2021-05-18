package bobnard.claim.UI;


import bobnard.claim.model.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

public class ButtonsMouseUmineko extends MouseAdapter {
    MenuUmineko menu;

    public ButtonsMouseUmineko(MenuUmineko menu) {
        this.menu = menu;
    }


    @Override
    public void mouseEntered(MouseEvent e) {
        super.mouseEntered(e);

        if (menu.b1.equals(e.getSource())) {
            menu.ng = new ImageIcon(menu.path+"new_game2.png");
        }
        if (menu.b2.equals(e.getSource())) {
            menu.ru = new ImageIcon(menu.path+"rules2.png");
        }
        if (menu.b3.equals(e.getSource())) {
            menu.ex = new ImageIcon(menu.path+"exit2.png");
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        super.mouseEntered(e);

        if (menu.b1.equals(e.getSource())) {
            menu.ng = new ImageIcon(menu.path+"new_game.png");
        }
        if (menu.b2.equals(e.getSource())) {
            menu.ru = new ImageIcon(menu.path+"rules.png");
        }
        if (menu.b3.equals(e.getSource())) {
            menu.ex = new ImageIcon(menu.path+"exit.png");
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

        Audio.playSE(0);

        if(menu.b1.equals(e.getSource())) {
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
