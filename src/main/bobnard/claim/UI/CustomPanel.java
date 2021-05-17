package bobnard.claim.UI;

import bobnard.claim.model.Card;
import bobnard.claim.model.Game;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class CustomPanel extends JPanel {
    private static final HashMap<String, BufferedImage> images = new HashMap<>();
    private static final String path = "src/main/bobnard/claim/UI/resources/";

    CFrame frame;

    private Card card;
    private Image image;

    private boolean isFlipped;

    public CustomPanel(CFrame frame) {
        super();

        this.frame = frame;

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                action();
            }
        });
    }

    private static BufferedImage getImage(String name) {
        name = path + name + ".png";
        if (!images.containsKey(name)) {
            try {
                images.put(name, ImageIO.read(new File(name)));
            } catch (IOException ignored) {}
        }

        return images.get(name);
    }

    private void setImage() {
        if (this.isFlipped) {
            this.image = getImage("CARDBACK");
        } else {
            this.image = getImage(this.card.name);
        }

        this.image = image.getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH);
    }

    void setCard(Card card, boolean currentPlayer) {
        this.card = card;
        this.isFlipped = !currentPlayer;

        this.setImage();
    }

    void setCard(Card card) {
        this.setCard(card, true);
    }

    void action() {
        if (!this.isFlipped) {
            System.out.println("Played card : " + this.card.name);

            Game game = this.frame.getGame();

            if (!game.isLegalMove(card)) {
                return;
            }

            game.playCard(card);
            frame.repaint();

            if (game.trickReady()) {
                Timer timer = new Timer(500, e -> {
                    game.endTrick();
                    frame.updateScore();
                    frame.repaint();
                });
                timer.setRepeats(false);
                timer.start();
            } else {
                game.changePlayer();
            }

        }
    }

    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(this.image, 0, 0, getWidth(), getHeight(), null);
    }
}
