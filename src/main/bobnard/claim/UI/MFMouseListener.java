package bobnard.claim.UI;


import bobnard.claim.AI.AIMinimaxEasy;
import bobnard.claim.model.Game;
import bobnard.claim.model.Player;


import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MFMouseListener extends MouseAdapter {
    final MenuDeFin mf;
    final CFrame cframe;

    public MFMouseListener(MenuDeFin mf, CFrame cframe) {
        this.mf = mf;
        this.cframe = cframe;
    }


    @Override
    public void mouseEntered(MouseEvent e) {
        super.mouseEntered(e);
        ImageIcon ime = Utils.loadIcon("menu_fin/menu2.png");
        ImageIcon ire = Utils.loadIcon("menu_fin/newgame2.png");
        setHover(e, ime, ire);
    }

    private void setHover(MouseEvent e, ImageIcon ime, ImageIcon ire) {
        if (mf.me.equals(e.getSource())) {
            mf.ime = ime;

        }
        if (mf.re.equals(e.getSource())) {
            mf.ire = ire;
        }

    }

    @Override
    public void mouseExited(MouseEvent e) {
        super.mouseEntered(e);
        ImageIcon ime = Utils.loadIcon("menu_fin/menu.png");
        ImageIcon ire = Utils.loadIcon("menu_fin/newgame.png");
        setHover(e, ime, ire);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        Audio.stopBGM();
        if (e.getSource().equals(mf.me)) {
            CFrame.isfin = false;
            Window.switchToMainMenu();

        }
        if (e.getSource().equals(mf.re)) {
            mf.game.reset();
            mf.frame.startLoop();
            mf.game.start();
            CFrame.isfin = false;


        }

    }

}

