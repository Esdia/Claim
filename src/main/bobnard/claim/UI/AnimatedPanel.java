package bobnard.claim.UI;

import javax.swing.*;
import java.awt.*;

public class AnimatedPanel {
    final CardUI myc;
    final Point mypinit;
    final Point mypdest;
    final int sx;
    final int sy;
    int step;
    Timer animationTimer;

    public AnimatedPanel(CardUI c, Point p1, Point p2) {
        myc = c;
        mypinit = p1;
        mypdest = p2;
        step = 20;
        sx = (mypdest.x - mypinit.x) / step;
        sy = (mypdest.y - mypinit.y) / step;
        myc.setVisible(true);
        myc.setLocation(mypinit);
    }

    public AnimatedPanel(CardUI c, Point p2) {
        myc = c;
        mypinit = new Point(c.getX(), c.getY());
        mypdest = p2;
        step = 20;
        sx = (mypdest.x - mypinit.x) / step;
        sy = (mypdest.y - mypinit.y) / step;
        myc.setVisible(true);
        myc.setLocation(mypinit);
    }


    public void startanimation() {

        animationTimer = new Timer(0, f -> {
            mypinit.x += sx;
            mypinit.y += sy;
            step--;
            if (step == 0) {
                myc.setLocation(mypdest);
                animationTimer.stop();
            } else {
                myc.setLocation(mypinit);
                myc.repaint();
            }

        });
        animationTimer.start();

    }
}