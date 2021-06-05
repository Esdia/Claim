package bobnard.claim.UI;


import bobnard.claim.model.Save;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PMMouseListener extends MouseAdapter {
    final PauseMenu pm;

    public PMMouseListener(PauseMenu pm) {
        this.pm = pm;
    }


    @Override
    public void mouseEntered(MouseEvent e) {
        super.mouseEntered(e);


    }

    @Override
    public void mouseExited(MouseEvent e) {
        super.mouseEntered(e);

    }

    @Override
    public void mousePressed(MouseEvent e) {
        Audio.playSE(0);
        if (e.getSource().equals(pm.re)) {
            CFrame.isPaused = false;
        }
        if (e.getSource().equals(pm.cf)) {
            PauseMenu.isConfig = true;
        }
        if (e.getSource().equals(pm.sv)) {
            Save.save("File1");
        }
        if (e.getSource().equals(pm.ld)) {
            Save.load("File1");
            CFrame.isPaused = false;
        }
        if (e.getSource().equals(pm.bm)) {
            pm.frame.frame.dispose();
            Audio.getBGM().stop();
            Window.start();

        }

    }

}

