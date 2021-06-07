package bobnard.claim.UI;


import bobnard.claim.AI.AI;
import bobnard.claim.model.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class CFrame extends JComponent  {

    Game game;

    final JFrame frame;
    static String path;

    BufferedImage image;
    BufferedImage image2;

    final BufferedImage[] FactionIm = new BufferedImage[5];

    Player[] players;
    public Card FlippedCard;

    final int[][] Score = new int[2][5];


    CardUI[][] handPanels;
    CardUI flippedPanel;
    public CardUI[] playedPanels;

    CardUI[][] followPanels;

    int w;
    int h;
    int imgWidth;
    int imgHeight;

    int Phase ;

    private final Timer gameLoop = new Timer(16, null);
    final ArrayList<AnimatedPanel> movingPanels = new ArrayList<>();

    final PauseMenu pm;
    final JButton pause;
    final ImageIcon p;
    public static Boolean isPaused;

    public CFrame(JFrame frame) {
        path = "src/main/bobnard/claim/UI/resources/" + Menu.skin + "/gameboard/";
        FlippedCard = null;
        this.frame = frame;

        isPaused = false;

        pm = new PauseMenu(this);
        this.add(pm);



        for (int i = 0; i < 5; i++) {
            try {
                FactionIm[i] = ImageIO.read(new File(path + "SCORE" + i + ".png"));
            } catch (IOException ignored) {
            }
        }

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

        pause = new JButton();
        pause.addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent e) {
            }

            public void mousePressed(MouseEvent e) {
                if(e.getSource().equals(pause)){
                    isPaused = true;
                    pm.repaint();
                    repaint();

                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }

        });

        add(pause);

        p = new ImageIcon(path+ "pause.png");



    }


    void setGame(Game game) {
        this.game = game;

        this.initHandButtons();
        this.initFollowButtons();
        this.initFlippedButton();
        this.initPlayedButton();

        this.setPlayers();
        
        Phase = this.game.getPhaseNum();

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
                switch (game.getState()) {
                    case GAME_FINISHED -> Audio.getBGM().stop();
                    case STARTED_PHASE_ONE -> Audio.playBGM(1);
                    case FIRST_PHASE_FINISHED -> {
                        Audio.getBGM().stop();
                        Audio.playBGM(2);
                    }
                }

                game.nextStep();

                repaint();
            }
        });
        this.gameLoop.setRepeats(true);
        this.gameLoop.start();
    }

    void stopLoop() {
        this.gameLoop.stop();
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

    void initFollowButtons() {
        this.followPanels = new CardUI[2][36];

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 36; j++) {
                this.followPanels[i][j] = new CardUI(this);
                this.add(followPanels[i][j]);
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

        setPauseButton(resize);

        drawPM(pm,isPaused);


        g.clearRect(0, 0, w, h);

        this.drawHands(resize);
        if (Phase != this.game.getPhaseNum()) {
            SetFollowersInvisible();
            Phase = this.game.getPhaseNum();
        }

        if (this.game.getPhaseNum() == 1 )
            this.drawFollowers(resize);
        else
            this.drawScorePile(resize);

        this.displayPlayed(resize);
        this.displayFlipped(resize);


        int wb = w / 18;
        int hb = (int) (wb * 1.5);

        g.drawImage(image, 0, 0, w, h, null);
        g.drawImage(image2, w / 32, (h / 2) - (hb / 2), wb, hb, null);

        this.updateScore();
        paintscore(g, h, w);




        this.setVisible(true);
    }

    private void drawPM(JComponent component, boolean cond) {
        if (cond) {
            component.setBounds(0, 0, getWidth(), getHeight());
            component.setOpaque(false);

            if (!component.isVisible()) {
                component.setVisible(true);
            }

            pause.setVisible(false);

        } else {
            component.setVisible(false);
            pause.setVisible(true);
        }
    }


    private void setPauseButton(boolean resize) {
        int x = w/16;
        int y = h/9;
        if(resize){
            pause.setIcon(new ImageIcon(p.getImage().getScaledInstance(x, y, Image.SCALE_SMOOTH)));
            pause.setBounds(0, 0, x, y);
            pause.setBorderPainted(false);
            pause.setOpaque(false);
            pause.setContentAreaFilled(false);

            pause.setVisible(true);

        }
    }

    void drawHands(boolean resize) {
        int x, size;
        int[] y = {5, h - imgHeight - 5};
        int[] yRaised = {y[0] + imgHeight / 10, y[1] - imgHeight / 10};

        int currentPlayer = this.game.getCurrentPlayerID();

        Iterator<Card> it;
        Card c;

        Hand playable;
        boolean raise;

        for (int j = 0; j < 2; j++) {
        	size = players[j].getCards().size();

            playable = game.getPlayableCards();
            raise = (
        	        game.getState() == GameState.WAITING_FOLLOW_ACTION && game.getCurrentPlayerID() == j && size != playable.size()
            );
        	
        	if (size % 2 !=0) size +=1;
            x =(w/2) - ( size/2* imgWidth);
            
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
                    if (raise && playable.contains(c)) {
                        this.handPanels[j][i].setLocation(x, yRaised[j]);
                    } else {
                        this.handPanels[j][i].setLocation(x, y[j]);
                    }
                    x += imgWidth;
                }
            }
        }
    }

    public void getHandBack(CardUI card) {
    	int ind = card.Position();
    	int currentPlayer = this.game.getCurrentPlayerID();
    	int size = players[currentPlayer].getCards().size();
    	int x = (w/2) - ( size/2* imgWidth);
    	int[] y = {5, h - imgHeight - 5};
    	
    	Point Po = new Point(x*ind , y[currentPlayer]);
    	AnimatedPanel backhand = new AnimatedPanel(card, Po);
    	this.movingPanels.add(backhand);
    	
    }
    
    
    void SetFollowersInvisible() {
    	flippedPanel.setVisible(false);
    	for (int j = 0; j < 2; j++) {
              for (int i = 0; i < 36; i++) {
                  this.followPanels[j][i].setVisible(false);
              }
          }
    }


    private int drawFollowPanel(boolean resize, int x, int[] y, int currentPlayer, Iterator<Card> it, int j, int i) {
        Card c;
        this.followPanels[j][i].setVisible(it.hasNext());
        if (it.hasNext() ) {
            if (resize) {
                this.followPanels[j][i].setSize(imgWidth, imgHeight);
            }
            c = it.next();
            this.followPanels[j][i].setSize(imgWidth, imgHeight);
            if (c.name != null) {
                this.followPanels[j][i].setCard(c, j == currentPlayer);
                this.followPanels[j][i].setLocation(x, y[j]);

            }
            x += imgWidth/3;
        }
        return x;
    }

    void drawFollowers(boolean resize) {
    	int x;
    	int[] y = {(int)(imgHeight*1.2), h - (int)(imgHeight*2.2)};

    	int currentPlayer = this.game.getCurrentPlayerID();

    	Iterator<Card> it;

    	for (int j = 0; j < 2; j++) {
    		x =(w/2)+ 3*imgWidth;
    		it = players[j].getFollowers().iterator();
    		for (int i = 0; i < 13; i++) {
                x = drawFollowPanel(resize, x, y, currentPlayer, it, j, i);
            }
    	}

    }

    void drawScorePile(boolean resize) {
    	int size;
    	int max =  (w/2)+ 2*imgWidth+ 16*(imgWidth/3);
    	int x = (w/2)+ 2*imgWidth;
    	int[] y = {(int)(imgHeight*1.2), h - (int)(imgHeight*2.2)};

    	int currentPlayer = this.game.getCurrentPlayerID();

    	Iterator<Card> it;

    	for (int j = 0; j < 2; j++) {
    		it = players[j].getScoreStack().iterator();
    		size = players[j].getScoreStack().size();
    		for (int i = 0; i < size; i++) {
    			if (x == max) {
					x =(w/2)+ 3*imgWidth;
					y[0] += imgHeight;
					y[1] -= imgHeight;
				}
                x = drawFollowPanel(resize, x, y, currentPlayer, it, j, i);
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
    	Point start = new Point((w / 32) + 3 * imgWidth, (h - imgHeight) / 2);
    	Point dest;
    	if (this.game.getTrickWinnerID() == 0) {
    		dest = new Point((w/2)+ 3*imgWidth, (int)(imgHeight*1.2));
    	} else {
    		dest = new Point((w/2)+ 3*imgWidth,h - (int)(imgHeight*2.2));
    	}


    	if (this.game.getPhaseNum() == 2) {
    		this.movePlayedCards( dest);
    		return;
    	}

    	this.movingPanels.add(new AnimatedEndTrick(flippedPanel, start, dest, this));
    }


    void paintscore(Graphics g, int h, int w) {
        int t = w / 20;
        int th = (int) (t * 1.5);
        int x = w - (int)(1.5*t);
        int y = ((int) (t* 1.5)) + (h / 30);
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

    Point DwarfLocation(int id) {
    	if (id == 0)
    		return new Point((w/2)+ 2*imgWidth,h - (int)(imgHeight*2.2));
    	else
    		return new Point((w/2)+ 2*imgWidth, (int)(imgHeight*1.2));
    }

    public void movePlayedCards( Point dest) {
    	Point start;
    	dest.x = (w/2)+ 2*imgWidth;
        for (int i = 0; i < 2; i++) {
        	start = new Point(playedPanels[i].getLocation());
        	if (this.playedPanels[i].getCard().faction == Faction.DWARVES) {
                this.movingPanels.add(new AnimatedEndTrick(playedPanels[i], start, this.DwarfLocation(this.game.getTrickWinnerID() ), this));
            }else {
                this.movingPanels.add(new AnimatedEndTrick(playedPanels[i], start, dest, this));
            }
        }
    }

    public void removePlayedCards() {
        for (int i = 0; i < 2; i++) {
        	playedPanels[i].setVisible(false);
        }
    }


}


