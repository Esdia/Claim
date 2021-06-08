package bobnard.claim.UI;


import bobnard.claim.model.Game;

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
        ImageIcon isv = new ImageIcon("src/main/bobnard/claim/UI/resources/" + Menu.skin + "/pause_menu/save2.png");
        ImageIcon ire = new ImageIcon("src/main/bobnard/claim/UI/resources/" + Menu.skin + "/pause_menu/resume2.png");
        ImageIcon icf = new ImageIcon("src/main/bobnard/claim/UI/resources/" + Menu.skin + "/pause_menu/config2.png");
        ImageIcon ild = new ImageIcon("src/main/bobnard/claim/UI/resources/" + Menu.skin + "/pause_menu/load2.png");
        ImageIcon ibm = new ImageIcon("src/main/bobnard/claim/UI/resources/" + Menu.skin + "/pause_menu/backtomenu2.png");
        setHover(e, isv, ire, ild, icf, ibm);

    }

    private void setHover(MouseEvent e, ImageIcon isv, ImageIcon ire, ImageIcon ild, ImageIcon icf, ImageIcon ibm) {
        if (pm.sv.equals(e.getSource())) {
            pm.isv = isv;

        } if (pm.re.equals(e.getSource())) {
            pm.ire = ire;
        }
        if (pm.ld.equals(e.getSource())) {
            pm.ild = ild;
        }
        if (pm.cf.equals(e.getSource())) {
            pm.icf = icf;
        }
        if (pm.bm.equals(e.getSource())) {
            pm.ibm = ibm;
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        super.mouseEntered(e);
        ImageIcon isv = new ImageIcon("src/main/bobnard/claim/UI/resources/" + Menu.skin + "/pause_menu/save.png");
        ImageIcon ire = new ImageIcon("src/main/bobnard/claim/UI/resources/" + Menu.skin + "/pause_menu/resume.png");
        ImageIcon icf = new ImageIcon("src/main/bobnard/claim/UI/resources/" + Menu.skin + "/pause_menu/config.png");
        ImageIcon ild = new ImageIcon("src/main/bobnard/claim/UI/resources/" + Menu.skin + "/pause_menu/load.png");
        ImageIcon ibm = new ImageIcon("src/main/bobnard/claim/UI/resources/" + Menu.skin + "/pause_menu/backtomenu.png");
        setHover(e, isv, ire, ild, icf, ibm);

    }

    @Override
    public void mousePressed(MouseEvent e) {
        Audio.playSE(0);
        if (e.getSource().equals(pm.re)) {
            CFrame.isPaused = false;
            pm.frame.repaint();
        }
        if (e.getSource().equals(pm.cf)) {
            PauseMenu.isConfig = true;
            pm.c.refreshSettings();
            pm.repaint();
        }
        if (e.getSource().equals(pm.sv)) {
            Save.save("File1", pm.frame.getGame());
        }
        if (e.getSource().equals(pm.ld)) {
            Game game = Save.load("File1");
            CFrame.isPaused = false;

            pm.frame.setGame(game);
        }
        if (e.getSource().equals(pm.bm)) {
            CFrame.isPaused = false;
            Window.switchToMainMenu();
        }

    }

}

