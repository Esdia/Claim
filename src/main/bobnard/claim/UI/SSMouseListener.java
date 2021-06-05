package bobnard.claim.UI;


import bobnard.claim.model.Save;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SSMouseListener extends MouseAdapter {
    final SkinSelect ss;

    public SSMouseListener(SkinSelect ss) {
        this.ss = ss;
    }


    @Override
    public void mouseEntered(MouseEvent e) {
        super.mouseEntered(e);

        ImageIcon ba = new ImageIcon("src/main/bobnard/claim/UI/resources/" + Menu.skin + "/menu/skin/back2.png");
        setHover(e, ba);
    }

    private void setHover(MouseEvent e, ImageIcon ba) {
        if (ss.b3.equals(e.getSource())) {
            ss.ba = ba;

        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        super.mouseEntered(e);

        ImageIcon ba = new ImageIcon("src/main/bobnard/claim/UI/resources/" + Menu.skin + "/menu/skin/back.png");

        setHover(e, ba);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        Audio.playSE(0);
        if (e.getSource().equals(ss.b3)) {
            ss.menu.isSs = false;
        }
        if (e.getSource().equals(ss.b2)) {
            ss.menu.changeSkin("Umineko");
            Save.syssave(ss.menu.config);
            ss.menu.isSs = false;
        }
        if (e.getSource().equals(ss.b1)) {
            ss.menu.changeSkin("Vanilla");
            ss.menu.isSs = false;
        }
    }

}

