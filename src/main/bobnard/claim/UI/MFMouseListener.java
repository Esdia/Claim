package bobnard.claim.UI;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MFMouseListener extends MouseAdapter {
    final MenuDeFin mf;

    public MFMouseListener(MenuDeFin mf) {
        this.mf = mf;
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
    }
}

