package bobnard.claim.UI;

import java.awt.*;

public class AnimatedEndTrick extends AnimatedPanel {
    private final CFrame frame;

    public AnimatedEndTrick(CardUI c, Point start, Point dest, CFrame frame) {
        super(c, start, dest);
        this.frame = frame;
    }

    @Override
    void whenFinished() {
        frame.removePlayedCards();
    }
}
