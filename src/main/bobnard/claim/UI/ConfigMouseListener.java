package bobnard.claim.UI;


import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ConfigMouseListener extends MouseAdapter {
    final Config c;

    public ConfigMouseListener(Config c) {
        this.c = c;
    }


    @Override
    public void mouseEntered(MouseEvent e) {
        super.mouseEntered(e);

        ImageIcon ba = Utils.loadIcon("menu/Config/back2.png");
        setHover(e, ba);
    }

    private void setHover(MouseEvent e, ImageIcon ba) {
        if (c.b1.equals(e.getSource())) {
            c.ba = ba;

        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        super.mouseEntered(e);

        ImageIcon ba = Utils.loadIcon("menu/Config/back.png");

        setHover(e, ba);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        Audio.playSE(0);
        if (e.getSource().equals(c.b1)) {
            if(c.menu != null){
                c.menu.isConfig = false;
            }
            else{
                PauseMenu.isConfig = false;
                if (c.pm != null) {
                    c.pm.repaint();
                }
            }
        }

    }

}

