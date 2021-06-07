package bobnard.claim.UI;


import bobnard.claim.AI.AIMinimaxEasy;
import bobnard.claim.model.Game;
import bobnard.claim.model.Player;
import bobnard.claim.model.Save;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MFMouseListener extends MouseAdapter {
    final MenuDeFin mf;

    public MFMouseListener (MenuDeFin mf) {
        this.mf = mf;
    }



    @Override
    public void mouseEntered(MouseEvent e) {
        super.mouseEntered(e);
    }

    private void setHover(MouseEvent e, ImageIcon ime, ImageIcon ire) {
        if (mf.me.equals(e.getSource())) {
            mf.ime = ime;

        } if (mf.re.equals(e.getSource())) {
            mf.ire= ire;
        }

    }

    @Override
    public void mouseExited(MouseEvent e) {
        super.mouseEntered(e);
    }

    @Override
    public void mousePressed(MouseEvent e) {


    }

}

