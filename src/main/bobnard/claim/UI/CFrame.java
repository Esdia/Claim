package bobnard.claim.UI;


import bobnard.claim.model.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.*;



public class CFrame extends JComponent implements ActionListener{

	boolean phase;
	Game game;
	JButton b1;
	ImageIcon icb1;
	JButton b2;
	ImageIcon icb2;

	JButton PlayedButton1;
	ImageIcon PBimage1;
	JButton PlayedButton2;
	ImageIcon PBimage2;

	static String path = "src/main/bobnard/claim/UI/resources/";

	
	BufferedImage image;
	BufferedImage image2;
	BufferedImage ImPlayed1;
	BufferedImage ImPlayed2;

	BufferedImage[] FactionIm =  new BufferedImage[5];

	Player Myp0, Myp1;
	public Card FlippedCard;
	public Card PlayedCard1;
	public Card PlayedCard2;

	int[][] Score = new int[2][5];

	public void setplayers (Player p0, Player p1) {
		Myp0 = p0;
		Myp1 = p1;
	}

	public void setGame(Game game) {
		this.game = game;
	}
	public CFrame(Game game) {
		Myp0 = null;
		Myp1 = null;
		FlippedCard = null;
		PlayedCard1 = null;
		PlayedCard2 = null;
		this.game = game;
		phase = true;

		PlayedButton1 = null;
		PlayedButton2 = null;
		PBimage1 = null;
		PBimage2 = null;

		if(Math.random()>0.5){
			Audio.play("organ_shokyoku_nioku_hachi.wav",true);
		}
		else{
			Audio.play("Fishy_aroma.wav",true);
		}


		for (int i=0; i<5; i++) {
			try {
				FactionIm[i] = ImageIO.read(new File(path+"SCORE"+i+".png"));
			} catch (IOException  ee) {}
		}


		icb1 =  new ImageIcon(path+"CARDBACK.png");
		icb2 =  new ImageIcon(path+"CARDBACK.png");
		

		Color mycolor = new Color(0, 0, 0, 0);

		b1 =  new JButton(icb1);

		b1.setBackground(mycolor);
		b1.setBorderPainted(false);
		b1.setVisible(false);
		this.add(b1);
		b1.addActionListener(this);

		b2 =  new JButton(icb2);
		b2.setBackground(mycolor);
		b2.setBorderPainted(false);
		b2.setVisible(false);
		this.add(b2);
		b2.addActionListener(this);


		setLayout(null);		
		this.setVisible(true);

		try {
			image = ImageIO.read(new File(path+"Wood.jpg"));
		} catch (IOException  ee) {}	

		try {
			image2 = ImageIO.read(new File(path+"CARDBACK.png"));
		} catch (IOException  ee) {}

	}



	public void paintComponent(Graphics g) {
		int h = (int) getSize().getHeight();
		int w = (int) getSize().getWidth();
		int wb = w/18;
		int hb = (int) (wb*1.5);

		g.drawImage(image, 0, 0, w, h, null);

		g.drawImage(image2, w/32, (h/2)-(hb/2), wb, hb, null);
		
		paintscore(g, h, w);

		b1.setIcon(resizeIcon(icb1, wb, hb));
		b1.setBounds(w/32, 10, wb, hb);
		b1.setVisible(true);

		b2.setIcon(resizeIcon(icb2, wb, hb));
		b2.setBounds(w/32, h-10-((int) ((w/18)*1.5)), wb, hb);
		b2.setVisible(true);

		this.setVisible(true);

		if (Myp0 != null) Myp0.DrawHand();
		if (Myp1 != null) Myp1.DrawHand();
		if (FlippedCard != null) FlippedCard.myGui.DisplayFlipped();
		if (PlayedCard1 != null) PlayedCard1.myGui.DisplayPlayed();
		if (PlayedCard2 != null) PlayedCard2.myGui.DisplayPlayed();

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if( e.getSource() == b1) {
			System.out.println("B1");
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


	public void getScoredCard(int id, Card card) {
		switch(card.faction) {
		case GOBLINS : Score[id][0]++; break;
		case KNIGHTS : Score[id][1]++; break;
		case UNDEADS : Score[id][2]++; break;
		case DWARVES : Score[id][3]++; break;
		case DOPPELGANGERS : Score[id][4]++; break;

		}
		this.repaint();
	}
}


