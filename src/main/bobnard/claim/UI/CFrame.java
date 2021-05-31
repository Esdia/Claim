package bobnard.claim.UI;


import bobnard.claim.AI.AI;
import bobnard.claim.model.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class CFrame extends JComponent {

    final Game game;
    final CardUI b1;
    final CardUI b2;


    static final String path = "src/main/bobnard/claim/UI/resources/" + Menu.skin + "/gameboard/";

    BufferedImage image;
    BufferedImage image2;

    final BufferedImage[] FactionIm = new BufferedImage[5];

    Player[] players;
    public Card FlippedCard;

    final int[][] Score = new int[2][5];


    CardUI[][] handPanels;
    CardUI flippedPanel;
    public CardUI[] playedPanels;

    int w;
    int h;
    int imgWidth;
    int imgHeight;

    private final Timer gameLoop = new Timer(16, null);
    final ArrayList<AnimatedPanel> movingPanels = new ArrayList<>();

    public CFrame(Game game) {
        FlippedCard = null;
        this.game = game;

        for (int i = 0; i < 5; i++) {
            try {
                FactionIm[i] = ImageIO.read(new File(path + "SCORE" + i + ".png"));
            } catch (IOException ignored) {
            }
        }

        b1 = new CardUI(this);
        this.add(b1);

        b2 = new CardUI(this);
        this.add(b2);

        setLayout(null);
        this.setVisible(true);

        try {
            image = ImageIO.read(new File(path + "board.png"));
        } catch (IOException ignored) {
        }

        try {
            image2 = ImageIO.read(new File(path + "CARDBACK.png"));
        } catch (IOException ignored) {
        }

        this.initHandButtons();
        this.initFlippedButton();
        this.initPlayedButton();

        this.setPlayers();

        this.startLoop();
    }

    void startLoop() {
        this.gameLoop.addActionListener(e -> {

            ArrayList<AnimatedPanel> tmp = new ArrayList<>(this.movingPanels);
            for (AnimatedPanel animatedPanel : tmp) {
                animatedPanel.nextStep();
                if (animatedPanel.isDone()) {
                    animatedPanel.whenFinished();

                    this.movingPanels.remove(animatedPanel);
                }
            }

            if (this.movingPanels.size() == 0 && !game.isWaitingHumanAction()) {
                game.nextStep();
                repaint();
            }
        });
        this.gameLoop.setRepeats(true);
        this.gameLoop.start();
    }

    public void setPlayers() {
        this.players = new Player[2];

        for (int i = 0; i < 2; i++) {
            Player player = game.getPlayer(i);
            this.players[i] = player;

            if (player.isAI()) {
                ((AI) player).setCardUIs(this.handPanels[i]);
            }
        }
    }

    void initHandButtons() {
        this.handPanels = new CardUI[2][13];

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 13; j++) {
                this.handPanels[i][j] = new CardUI(this);
                this.add(handPanels[i][j]);
            }
        }
    }

    void initFlippedButton() {
        this.flippedPanel = new CardUI(this);
        this.add(flippedPanel);
    }

    void initPlayedButton() {
        this.playedPanels = new CardUI[2];

        for (int i = 0; i < 2; i++) {
            this.playedPanels[i] = new CardUI(this);
            this.add(playedPanels[i]);
        }
    }


    @Override
    public void paintComponent(Graphics g1) {
        Graphics2D g = (Graphics2D) g1;

        boolean resize = false;
        if (h != getHeight() || w != getWidth()) {
            resize = true;
            this.h = getHeight();
            this.w = getWidth();
            this.imgWidth = w / 20;
            this.imgHeight = imgWidth * 3 / 2;
        }

        g.clearRect(0, 0, w, h);

        this.drawHands(resize);
        this.displayPlayed(resize);
        this.displayFlipped(resize);

        int wb = w / 18;
        int hb = (int) (wb * 1.5);

        g.drawImage(image, 0, 0, w, h, null);

        g.drawImage(image2, w / 32, (h / 2) - (hb / 2), wb, hb, null);

        this.updateScore();
        paintscore(g, h, w);

        b1.setBounds(w / 32, 10, wb, hb);
        b1.setCard(null, false);
        b1.setVisible(true);

        b2.setBounds(w / 32, h - 10 - ((int) ((w / 18) * 1.5)), wb, hb);
        b2.setCard(null, false);
        b2.setVisible(true);

        this.setVisible(true);
    }

    void drawHands(boolean resize) {
        int x;
        int[] y = {5, h - imgHeight - 5};

        int currentPlayer = this.game.getCurrentPlayerID();

        Iterator<Card> it;
        Card c;

        for (int j = 0; j < 2; j++) {
            x = w / 8 + imgWidth;
            it = players[j].getCards().iterator();
            for (int i = 0; i < 13; i++) {
                if (this.handPanels[j][i].dragged) {
                    x += imgWidth;
                    continue;
                }
                this.handPanels[j][i].setVisible(it.hasNext());
                if (it.hasNext()) {
                    if (resize) {
                        this.handPanels[j][i].setSize(imgWidth, imgHeight);
                    }
                    c = it.next();
                    this.handPanels[j][i].setCard(c, j == currentPlayer);
                    this.handPanels[j][i].setLocation(x, y[j]);
                    x += imgWidth;
                }
            }
        }
    }

    void animateFlipped() {
        int wb = w / 18;
        int hb = (int) (wb * 1.5);
        Point p1 = new Point(w / 32, (h / 2) - (hb / 2));
        Point p2 = new Point((w / 32) + 3 * imgWidth, (h - imgHeight) / 2);
        AnimatedPanel flipped = new AnimatedPanel(this.flippedPanel, p1, p2);
        this.movingPanels.add(flipped);
    }

    void displayPlayed(boolean resize) {
        if (resize) {
            CardUI cardUI;
            for (int i = 0; i < 2; i++) {
                cardUI = playedPanels[i];
                cardUI.setSize(imgWidth, imgHeight);
                cardUI.setLocation(this.getPlayedLocation(i));
            }
        }
    }


    void displayFlipped(boolean resize) {
        Card c = this.game.getFlippedCard();
        if (c == null) return;

        if (resize) {
            this.flippedPanel.setSize(imgWidth, imgHeight);
            this.flippedPanel.setLocation(this.getFlippedLocation());
        }

        if (this.isAnimating()) {
            return;
        }

        if (this.FlippedCard != c) {
            this.FlippedCard = c;
            this.flippedPanel.setCard(this.FlippedCard);

            this.animateFlipped();
        }
    }

    void addAnimation(AnimatedPanel animatedPanel) {
        this.movingPanels.add(animatedPanel);
    }

    void animateEndTrick() {
        if (this.game.getPhaseNum() == 2) {
            this.removePlayedCards();
            return;
        }

        Point start = new Point((w / 32) + 3 * imgWidth, (h - imgHeight) / 2);
        Point dest;
        if (this.game.getTrickWinnerID() == 0) {
            dest = new Point(w / 32, 10);
        } else {
            dest = new Point(w / 32, h - 10 - ((int) ((w / 18) * 1.5)));
        }

        this.movingPanels.add(new AnimatedEndTrick(flippedPanel, start, dest, this));
    }


    void paintscore(Graphics g, int h, int w) {
        int t = w / 20;
        int th = (int) (t * 1.5);
        int x = w - 2 * (t);
        int y = ((int) ((w / 20) * 1.5)) + (h / 30);
        int xx = x + (t / 2) - 5;
        int fsize = h / 54;

        Font fonte = new Font("Serif", Font.BOLD, fsize);
        g.setColor(Color.WHITE);
        g.setFont(fonte);

        for (int i = 0; i < 5; i++) {
            g.drawImage(FactionIm[i], x, y, t, th, null);
            g.drawString("" + Score[0][i], xx, y + (int) (fsize * 1.5));
            g.drawString("" + Score[1][i], xx, y + th - (int) (fsize / 1.5));
            y += th;
        }

    }

    public Game getGame() {
        return this.game;
    }

    public void updateScore() {
        ScoreStack stack;
        for (int i = 0; i < 2; i++) {
            stack = players[i].getScoreStack();
            for (int j = 0; j < 5; j++) {
                Score[i][j] = stack.getNbCardsFaction(Faction.values()[j]);
            }
        }
    }

    public Point getPlayedLocation(int playerID) {
        int x = w / 2 - imgWidth;
        int y;
        if (playerID == 0) {
            y = (h / 2) - imgHeight - (h / 16);
        } else {
            y = (h / 2) + (h / 16);
        }

        return new Point(x, y);
    }

    Point getFlippedLocation() {
        return new Point((w / 32) + 3 * imgWidth, (h - imgHeight) / 2);
    }

    public boolean isAnimating() {
        return this.movingPanels.size() != 0;
    }

    public void removePlayedCards() {
        for (int i = 0; i < 2; i++) {
            playedPanels[i].setVisible(false);
        }
    }
}


