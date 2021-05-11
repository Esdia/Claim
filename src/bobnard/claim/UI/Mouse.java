package bobnard.claim.UI;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Mouse extends MouseAdapter {
    GameController controller;

    public Mouse(GameController controller) {
        this.controller = controller;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        this.controller.interpretClick(e.getX(), e.getY());
    }
}
