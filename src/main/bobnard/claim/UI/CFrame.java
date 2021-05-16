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
	JButton bf;
	ImageIcon icbf;

	JButton PlayedButton1;
	ImageIcon PBimage1;
	JButton PlayedButton2;
	ImageIcon PBimage2;

	String path = "src/main/bobnard/claim/UI/resources/";

	int ArraySize = 13;
	ArrayList<JButton> firstP = new ArrayList<JButton>(ArraySize);
	ArrayList<JButton> secondP = new ArrayList<JButton>(ArraySize);

	BufferedImage image;
	BufferedImage image2;
	BufferedImage ImPlayed1;
	BufferedImage ImPlayed2;
	
	BufferedImage FactionIm1;
	BufferedImage FactionIm2;
	BufferedImage FactionIm3;
	BufferedImage FactionIm4;
	BufferedImage FactionIm5;

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
		
	
		
		
		try {
			FactionIm1 = ImageIO.read(new File(path+"Icon1.png"));
			FactionIm2 = ImageIO.read(new File(path+"Icon2.png"));
			FactionIm3 = ImageIO.read(new File(path+"Icon3.png"));
			FactionIm4 = ImageIO.read(new File(path+"Icon4.png"));
			FactionIm5 = ImageIO.read(new File(path+"Icon5.png"));
		} catch (IOException  ee) {}	
		

		icb1 =  new ImageIcon(path+"CardBack.jpg");
		icb2 =  new ImageIcon(path+"CardBack.jpg");
		icbf = new ImageIcon(path+ "GOBLINS0.png");

		b1 =  new JButton(icb1);
		b1.setVisible(false);
		this.add(b1);
		b1.addActionListener(this);

		b2 =  new JButton(icb2);
		b2.setVisible(false);
		this.add(b2);
		b2.addActionListener(this);

		bf = new JButton(icbf);
		bf.setVisible(false);
		this.add(bf);
		bf.addActionListener(this);

		setLayout(null);		
		this.setVisible(true);

		try {
			image = ImageIO.read(new File(path+"Wood.jpg"));
		} catch (IOException  ee) {}	

		try {
			image2 = ImageIO.read(new File(path+"CardBack.jpg"));
		} catch (IOException  ee) {}

	}


	public void paintComponent(Graphics g) {
		int h = (int) getSize().getHeight();
		int w = (int) getSize().getWidth();
		int t = w/36;
		int x = w-2*(t);
		int y = ((int) ((w/20)*1.5))+(h/12);

		g.drawImage(image, 0, 0, w, h, null);

		g.drawImage(image2, w/32, (h/2)-(h/12), w/16, (int) ((w/16)*1.5), null); // h/6
		
		
		
		g.drawImage(FactionIm1, x, y, t, t, null);
		g.drawImage(FactionIm2, x, y+2*(t), t, t, null);
		g.drawImage(FactionIm3, x, y+4*(t), t, t, null);
		g.drawImage(FactionIm4, x, y+6*(t), t, t, null);
		g.drawImage(FactionIm5, x, y+8*(t), t, t, null);
		
		Font fonte = new Font("Serif", Font.BOLD, 12);
		g.setFont(fonte);
		g.drawString("PLAYER 1 :" + Score[0][0] + " / " + Score[1][0] + ": PLAYER 2", x - t, y+t + (t/2));
		g.drawString("PLAYER 1 :" + Score[0][1] + " / " + Score[1][1] + ": PLAYER 2", x - t, y+3*t+ (t/2));
		g.drawString("PLAYER 1 :" + Score[0][2] + " / " + Score[1][2] + ": PLAYER 2", x - t, y+5*t+ (t/2));
		g.drawString("PLAYER 1 :" + Score[0][3] + " / " + Score[1][3] + ": PLAYER 2", x - t, y+7*t+ (t/2));
		g.drawString("PLAYER 1 :" + Score[0][4] + " / " + Score[1][4] + ": PLAYER 2", x - t, y+9*t+ (t/2));

		b1.setIcon(resizeIcon(icb1, w/18, (int) ((w/18)*1.5))); // h/8
		b1.setBounds(w/32, 10, w/18, (int) ((w/18)*1.5));  // h/8
		b1.setVisible(true);

		b2.setIcon(resizeIcon(icb2, w/18, (int) ((w/18)*1.5)));  // h/8
		b2.setBounds(w/32, h-10-((int) ((w/18)*1.5)), w/18, (int) ((w/18)*1.5));  // h/8
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

	
	public void ButtonDraw(String Name, int player, int indexc) {
		setVisible(true);
		repaint(0);
		int h = (int) getSize().getHeight();
		int w = (int) getSize().getWidth();
		int x = w/8 + ((w/20) * (indexc+1) );
		int y = 1 ;
		if (player == 1) y = h-(h/8);
		ImageIcon ic =  new ImageIcon(path+Name+".png");
		JButton jb = new JButton(ic);
		jb.setIcon(resizeIcon(ic,  w/20, h/8));
		jb.setBounds( x, y, w/20, h/8);
		jb.setVisible(true);
		this.add(jb);
		jb.addActionListener(this);
	}


	
	public void getScoredCard(int id, Card card) {
		switch(card.faction) {
			case GOBLINS : Score[id][0]++;
			case KNIGHTS : Score[id][1]++;
			case UNDEADS : Score[id][2]++;
			case DWARVES : Score[id][3]++;
			case DOPPELGANGERS : Score[id][4]++;
		}
	
	}
}
	

