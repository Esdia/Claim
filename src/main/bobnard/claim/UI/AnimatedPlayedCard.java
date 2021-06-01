package bobnard.claim.UI;

import bobnard.claim.model.GameState;

import java.awt.*;

public class AnimatedPlayedCard extends AnimatedPanel {
    private final CFrame frame;

    public AnimatedPlayedCard(CardUI c, Point dest, CFrame frame) {
        super(c, dest);
        this.frame = frame;
    }

    @Override
    void whenFinished() {
        if (frame.game.getState() == GameState.TRICK_FINISHED) {
            frame.animateEndTrick();
        }
    }
}
