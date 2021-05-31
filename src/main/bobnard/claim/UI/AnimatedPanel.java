package bobnard.claim.UI;

import java.awt.*;

public class AnimatedPanel {
    final CardUI card;
    final Point start;
    final Point dest;
    final int sx;
    final int sy;
    int step;

    public AnimatedPanel(CardUI c, Point start, Point dest) {
        card = c;
        this.start = start;
        this.dest = dest;
        step = 20;
        sx = (this.dest.x - this.start.x) / step;
        sy = (this.dest.y - this.start.y) / step;
        card.setVisible(true);
        card.setLocation(this.start);
    }

    public AnimatedPanel(CardUI c, Point dest) {
        this(c, new Point(c.getX(), c.getY()), dest);
    }

    boolean isDone() {
        return this.step == 0;
    }

    void nextStep() {
        step--;
        if (step == 0) {
            card.setLocation(dest);
        } else {
            start.x += sx;
            start.y += sy;
            card.setLocation(start);
        }
        card.repaint();
    }

    void whenFinished() {
    }
}