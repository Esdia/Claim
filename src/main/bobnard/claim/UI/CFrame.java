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

	CCanvas c1;
	String path = "src/main/bobnard/claim/UI/resources/";

	int ArraySize = 13;
	ArrayList<JButton> firstP = new ArrayList<JButton>(ArraySize);
	ArrayList<JButton> secondP = new ArrayList<JButton>(ArraySize);

	BufferedImage image;
	BufferedImage image2;
	BufferedImage ImPlayed1;
	BufferedImage ImPlayed2;

	Player Myp0, Myp1;
	public Card FlippedCard;
	public Card PlayedCard1;
	public Card PlayedCard2;
	
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

		c1 = new CCanvas(10, 20);
		c1.setVisible(false);
		this.add(c1);

		setLayout(null);		
		this.setVisible(true);
		//ButtonMaking();

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

		g.drawImage(image, 0, 0, w, h, null);

		g.drawImage(image2, w/32, (h/2)-(h/12), w/16, h/6, null);

		//TODO REPLACEMENT FOR A BETTER FLIPPED CARD SOLUTION (maybe ?)
		//g.drawImage(image2, (w/32)+2*(w/16), (h/2)-(h/16), w/20, h/8, null);

		b1.setIcon(resizeIcon(icb1, w/18, h/8));
		b1.setBounds(w/32, 10, w/18, h/8);
		b1.setVisible(true);

		b2.setIcon(resizeIcon(icb2, w/18, h/8));
		b2.setBounds(w/32, h-10-(h/8), w/18, h/8);
		b2.setVisible(true);

		c1.h = h;
		c1.w = w;
		c1.setVisible(true);
		setLayout(null);

		this.setVisible(true);
		
		if (Myp0 != null) Myp0.DrawHand();
		if (Myp1 != null) Myp1.DrawHand();
		if (FlippedCard != null) FlippedCard.myGui.DisplayFlipped();
		if (PlayedCard1 != null) PlayedCard1.myGui.DisplayPlayed();
		if (PlayedCard2 != null) PlayedCard2.myGui.DisplayPlayed();

		/*		
		  if (PlayedButton1 != null) { 
			  PlayedButton1.setIcon(resizeIcon(PBimage1,  w/20, h/8));
			  PlayedButton1.setBounds((w/2)-(w/20), (h/2)-(h/8)-(h/16), w/20, h/8);
			  PlayedButton1.setVisible(true);
			 }
		  if (PlayedButton2 != null) {
			  PlayedButton2.setIcon(resizeIcon(PBimage2,  w/20, h/8));
			  PlayedButton2.setBounds((w/2)-(w/20), (h/2)+(h/16), w/20, h/8);
			  PlayedButton2.setVisible(true);
		  }
		  if ((PlayedButton2 != null) && (PlayedButton1 != null)) {
			  PlayedButton2.setVisible(false);
			  PlayedButton1.setVisible(false);
			  PlayedButton2 = null;
			  PlayedButton1 = null;
		  }
		 */
		//ButtonMapping();
		//MappingFlipped();
		c1.repaint();


		//TODO REPLACEMENT FOR A BETTER FLIPPED CARD SOLUTION
		//g.drawImage(image2, (w/32)+2*(w/16), (h/2)-(h/16), w/20, h/8, null);


	}

	@Override
	public void actionPerformed(ActionEvent e) {
		int h = (int) getSize().getHeight();
		int w = (int) getSize().getWidth();



		if (firstP.contains(e.getSource()) && game.getLegal(game.getPlayer(0).getCards().get(firstP.indexOf(e.getSource())))) {

			//firstP.get(firstP.indexOf(e.getSource())).setBounds( (w/2)-(w/20), (h/2)-(h/10)-(w/20), w/20, h/8);
			System.out.println("J1 :" + game.getPlayer(0).getCards().get(firstP.indexOf(e.getSource())).name);
			PBimage1 =  new ImageIcon(path+game.getPlayer(0).getCards().get(firstP.indexOf(e.getSource())).name+".png");
			game.playCard(game.getPlayer(0).getCards().get(firstP.indexOf(e.getSource())));
			PlayedButton1 = new JButton(PBimage1);
			PlayedButton1.setVisible(false);
			this.add(PlayedButton1);
			PlayedButton1.addActionListener(this);
			//firstP.get(firstP.indexOf(e.getSource())-1).setVisible(false);
			firstP.remove(firstP.get(firstP.indexOf(e.getSource())-1));
			this.repaint(0);
		}
		else if (secondP.contains(e.getSource()) && game.getLegal(game.getPlayer(1).getCards().get(secondP.indexOf(e.getSource())))) {

			//secondP.get(secondP.indexOf(e.getSource())).setBounds( (w/2)-(w/20), (h/2)+(h/10)+(w/20), w/20, h/8);
			System.out.println("J2:" + game.getPlayer(1).getCards().get(secondP.indexOf(e.getSource())).name);
			PBimage2 =  new ImageIcon(path+game.getPlayer(1).getCards().get(secondP.indexOf(e.getSource())).name+".png");
			game.playCard(game.getPlayer(1).getCards().get(secondP.indexOf(e.getSource())));
			PlayedButton2 = new JButton(PBimage2);
			PlayedButton2.setVisible(false);
			this.add(PlayedButton2);
			PlayedButton2.addActionListener(this);
			//secondP.get(secondP.indexOf(e.getSource())-1).setVisible(false);
			secondP.remove(secondP.get(secondP.indexOf(e.getSource())-1));
			this.repaint(0);
		} else {
			if (firstP.contains(e.getSource())) {
				System.out.println("WRONG 1 :" + game.getPlayer(0).getCards().get(firstP.indexOf(e.getSource())).name);
			}
			if (secondP.contains(e.getSource())) {
				System.out.println("WRONG 2:" + game.getPlayer(1).getCards().get(secondP.indexOf(e.getSource())).name);
			}
		}


	}


	public Icon resizeIcon(ImageIcon i, int w, int h) {
		Image im = i.getImage();
		Image resIm = im.getScaledInstance (w, h, java.awt.Image.SCALE_SMOOTH);
		return new ImageIcon(resIm);

	}


	public void ButtonMapping() {
		int h = (int) getSize().getHeight();
		int w = (int) getSize().getWidth();


		int x = w/8;
		//for (int i=0; i< game.getPlayer(0).getCards().size()-1; i++) {
		for (int i=0; i< firstP.size(); i++) {
			firstP.get(i).setVisible(false);
			ImageIcon ic =  new ImageIcon(path+game.getPlayer(0).getCards().get(i).name+".png");
			firstP.get(i).setIcon(resizeIcon(ic,  w/20, h/8));
			firstP.get(i).setBounds( x, 1, w/20, h/8);
			firstP.get(i).setVisible(true);
			x += w/20;			
		}
		x= w/8;
		//for (int i=0; i< game.getPlayer(1).getCards().size(); i++) {
		for (int i=0; i< secondP.size(); i++) {
			secondP.get(i).setVisible(false);
			ImageIcon ic =  new ImageIcon(path+game.getPlayer(1).getCards().get(i).name+".png");
			secondP.get(i).setIcon(resizeIcon(ic,  w/20, h/8));
			secondP.get(i).setBounds( x, h-(h/8), w/20, h/8);
			secondP.get(i).setVisible(true);
			x += w/20;
		}
	}

	public void ButtonDraw(String Name, int player, int indexc) {
		setVisible(true);
		repaint(0);
		int h = (int) getSize().getHeight();
		int w = (int) getSize().getWidth();
		//if (h == 0) h = 600;
		//if (w == 0) w = 400;
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

	public void MappingFlipped() {
		int h = (int) getSize().getHeight();
		int w = (int) getSize().getWidth();
		//TODO Implement getFlippedCard() method in Game.java
		//icbf = new ImageIcon(path+ game.getFlippedCard().name + ".png");
		icbf = new ImageIcon(path+ "GOBLINS0.png");
		bf.setIcon(resizeIcon(icbf,  w/20, h/8));
		bf.setBounds( (w/32)+2*(w/20), (h/2)-(h/16), w/20, h/8);
		bf.setVisible(true);
	}	








}
	

