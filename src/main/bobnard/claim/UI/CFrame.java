package bobnard.claim.UI;


import bobnard.claim.AI.AI;
import bobnard.claim.model.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

public class CFrame extends JComponent {

	boolean phase;
	Game game;
	CardUI b1;
	CardUI b2;

	JButton PlayedButton1;
	ImageIcon PBimage1;
	JButton PlayedButton2;
	ImageIcon PBimage2;

	static final String path = "src/main/bobnard/claim/UI/resources/";
	static BufferedImage back;

	static {
		try {
			back = ImageIO.read(new File(path + "CARDBACK.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	BufferedImage image;
	BufferedImage image2;

	BufferedImage[] FactionIm =  new BufferedImage[5];

	Player[] players;
	public Card FlippedCard;
	public Card PlayedCard1;
	public Card PlayedCard2;

	int[][] Score = new int[2][5];


	CardUI[][] handPanels;
	CardUI flippedPanel;
	CardUI[] playedPanels;

	int w;
	int h;
	int imgWidth;
	int imgHeight;

	private final Timer nextStepTimer = new Timer(500, null);

	public CFrame(Game game) {
		FlippedCard = null;
		PlayedCard1 = null;
		PlayedCard2 = null;
		this.game = game;
		phase = true;

		PlayedButton1 = null;
		PlayedButton2 = null;
		PBimage1 = null;
		PBimage2 = null;


		for (int i=0; i<5; i++) {
			try {
				FactionIm[i] = ImageIO.read(new File(path+"SCORE"+i+".png"));
			} catch (IOException ignored) {}
		}

		b1 = new CardUI(this);
		this.add(b1);

		b2 =  new CardUI(this);
		this.add(b2);

		setLayout(null);
		this.setVisible(true);

		try {
			image = ImageIO.read(new File(path+"Wood.jpg"));
		} catch (IOException ignored) {}

		try {
			image2 = ImageIO.read(new File(path+"CARDBACK.png"));
		} catch (IOException ignored) {}

		this.initHandButtons();
		this.initFlippedButton();
		this.initPlayedButton();

		this.setPlayers();

		this.startLoop();
	}

	void startLoop() {
		this.nextStepTimer.addActionListener(e -> {
			game.nextStep();
			repaint();
			if (game.isWaitingHumanAction()) {
				stopLoop();
			}
		});
		this.nextStepTimer.setRepeats(true);
		this.nextStepTimer.start();
	}

	void stopLoop() {
		this.nextStepTimer.stop();
	}

	void resumeLoop() {
		if (!this.nextStepTimer.isRunning()) {
			this.nextStepTimer.start();
		}
	}

	public void setPlayers() {
		this.players = new Player[2];

		for (int i = 0; i < 2; i++) {
			Player player = game.getPlayer(i);
			this.players[i] = player;

			if (player instanceof AI) {
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
		this.displayFlipped(resize);
		this.displayPlayed();

		int wb = w/18;
		int hb = (int) (wb*1.5);

		g.drawImage(image, 0, 0, w, h, null);

		g.drawImage(image2, w/32, (h/2)-(hb/2), wb, hb, null);

		this.updateScore();
		paintscore(g, h, w);

		b1.setBounds(w/32, 10, wb, hb);
		b1.setCard(null, false);
		b1.setVisible(true);

		b2.setBounds(w/32, h-10-((int) ((w/18)*1.5)), wb, hb);
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
				this.handPanels[j][i].setVisible(it.hasNext());

				if (it.hasNext()) {
					if (resize) {
						this.handPanels[j][i].setBounds(x, y[j], imgWidth, imgHeight);
					}
					c = it.next();
					this.handPanels[j][i].setCard(c, j == currentPlayer);
				}
				x += imgWidth;
			}
		}
	}

	void displayFlipped(boolean resize) {
		this.FlippedCard = this.game.getFlippedCard();

		this.flippedPanel.setVisible(this.FlippedCard != null);

		if (this.FlippedCard != null) {
			if (resize) {
				this.flippedPanel.setBounds((w/32) + 2*imgWidth, (h - imgHeight) / 2, imgWidth, imgHeight);
			}
			this.flippedPanel.setCard(this.FlippedCard);
		}
	}

	void displayPlayed() {
		int x = w/2 - imgWidth;
		int[] y = {
				(h / 2) - imgHeight - (h / 16),
				(h / 2) + (h / 16)
		};

		Card[] cards = this.game.getPlayedCards();

		for (int i = 0; i < 2; i++) {
			this.playedPanels[i].setVisible(cards[i] != null);
			if (cards[i] != null) {
				this.playedPanels[i].setBounds(x, y[i], imgWidth, imgHeight);
				this.playedPanels[i].setCard(cards[i]);
			}
		}
	}

	public Icon resizeIcon(ImageIcon i, int w, int h) {
		Image im = i.getImage();
		Image resIm = im.getScaledInstance (w, h, java.awt.Image.SCALE_SMOOTH);
		return new ImageIcon(resIm);

	}


	void paintscore(Graphics g, int h, int w) {
		int t = w/20;
		int th = (int) (t * 1.5);
		int x = w-2*(t);
		int y = ((int) ((w/20)*1.5))+(h/30);
		int xx  = x + (t/2)-5 ;
		int fsize = h/54;

		Font fonte = new Font("Serif", Font.BOLD, fsize);
		g.setColor(Color.WHITE);
		g.setFont(fonte);

		for(int i=0; i<5; i++) {
			g.drawImage(FactionIm[i], x, y, t, th, null);
			g.drawString( "" + Score[0][i]  , xx ,y  + (int) (fsize*1.5));
			g.drawString( "" +Score[1][i] , xx,y + th -(int)(fsize/1.5));
			y+=th;
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
}


