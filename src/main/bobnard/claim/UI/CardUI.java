package bobnard.claim.UI;

import bobnard.claim.model.Card;
import bobnard.claim.model.Game;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class CardUI extends JPanel implements MouseInputListener {
    private static final HashMap<String, BufferedImage> images = new HashMap<>();
    private static final String path = "src/main/bobnard/claim/UI/resources/" + Menu.skin + "/gameboard/";

    private final CFrame frame;
    private final Game game;

    private Card card;
    private Image image;

    private boolean isFlipped;

    public boolean dragged;

    public CardUI(CFrame frame) {
        super();

        this.frame = frame;
        this.game = frame.getGame();

        this.addMouseMotionListener(this);
        this.addMouseListener(this);
        dragged = false;

    }

    public Card getCard() {
        return this.card;
    }

    private static BufferedImage getImage(String name) {
        name = path + name + ".png";
        if (!images.containsKey(name)) {
            try {
                images.put(name, ImageIO.read(new File(name)));
            } catch (IOException ignored) {
            }
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

    public void action() {
        if (!this.isFlipped) {
            /////
            CardUI cui = frame.playedPanels[frame.getGame().getCurrentPlayerID()];
            cui.setLocation(this.getLocation());
            cui.setSize(this.getSize());
            cui.setCard(this.card);
            this.setVisible(false);
            AnimatedPanel ap = new AnimatedPanel(cui, frame.getPlayedLocation());
            ap.startanimation();
            /////
            game.playCard(card);
            System.out.println(card.name);
            frame.repaint();

            if (!game.isWaitingHumanAction()) {
                frame.resumeLoop();
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(this.image, 0, 0, getWidth(), getHeight(), null);

    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseMoved(MouseEvent e) {
    }


    public void mouseDragged(MouseEvent e) {
        if (frame.game.getLegalCard(this.card)) {
            setFocusable(true);
            this.requestFocus();
            dragged = true;
            Point pe = e.getLocationOnScreen();
            Point pf = frame.getLocationOnScreen();
            Point p = new Point(pe.x - pf.x, pe.y - pf.y);
            setLocation(p.x - (getWidth() / 2), p.y - (getHeight() / 2));
            setVisible(true);
        }
    }

    public void mouseReleased(MouseEvent e) {
        dragged = false;
        if (!frame.getGame().isCurrentPlayerAI() && frame.game.getLegalCard(this.card)) {
            action();
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }


}
