package bobnard.claim.UI;


import bobnard.claim.model.Game;

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
	
	CCanvas c1;
	String path = "src/main/bobnard/claim/UI/resources/";
	
	int ArraySize = 13;
	ArrayList<JButton> firstP = new ArrayList<JButton>(ArraySize);
	ArrayList<JButton> secondP = new ArrayList<JButton>(ArraySize);
	
	BufferedImage image;
	BufferedImage image2;

	public CFrame(Game game) {
		this.game = game;
		phase = true;
		
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
		ButtonMaking();
		
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
		
		ButtonMapping();
		MappingFlipped();
		c1.repaint();
		
		
		//TODO REPLACEMENT FOR A BETTER FLIPPED CARD SOLUTION
		//g.drawImage(image2, (w/32)+2*(w/16), (h/2)-(h/16), w/20, h/8, null);
		

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		int h = (int) getSize().getHeight();
		int w = (int) getSize().getWidth();
			

		
		if (firstP.contains(e.getSource()) ) {
			firstP.get(firstP.indexOf(e.getSource())).setBounds( (w/2)-(w/20), (h/2)-(h/10)-(w/20), w/20, h/8);		
		}
		if (secondP.contains(e.getSource()) ) {
			secondP.get(secondP.indexOf(e.getSource())).setBounds( (w/2)-(w/20), (h/2)+(h/10)+(w/20), w/20, h/8);		
		}

		
	}
	
	
	private static Icon resizeIcon(ImageIcon i, int w, int h) {
		Image im = i.getImage();
		Image resIm = im.getScaledInstance (w, h, java.awt.Image.SCALE_SMOOTH);
		return new ImageIcon(resIm);
		
	}


	public void ButtonMapping() {
		int h = (int) getSize().getHeight();
		int w = (int) getSize().getWidth();

		int x = w/8;
		for (int i=0; i< 12; i++) {
			ImageIcon ic =  new ImageIcon(path+game.getPlayer(0).getCards().get(i).name+".png");
			firstP.get(i).setIcon(resizeIcon(ic,  w/20, h/8));
			firstP.get(i).setBounds( x, 1, w/20, h/8);
			firstP.get(i).setVisible(true);
			x += w/20;			
		}
		x= w/8;
		for (int i=0; i< 12; i++) {
			ImageIcon ic =  new ImageIcon(path+game.getPlayer(1).getCards().get(i).name+".png");
			secondP.get(i).setIcon(resizeIcon(ic,  w/20, h/8));
			secondP.get(i).setBounds( x, h-(h/8), w/20, h/8);
			secondP.get(i).setVisible(true);
			x += w/20;
		}
	}

	public void ButtonMaking() {
		for (int i=0; i< 12; i++) {
			ImageIcon ic =  new ImageIcon(path+game.getPlayer(0).getCards().get(i).name+".png");
			firstP.add(new JButton(ic));
			firstP.get(i).setVisible(false);
			this.add(firstP.get(i));
			firstP.get(i).addActionListener(this);
 		}
		for (int i=0; i< 12; i++) {
			ImageIcon ic =  new ImageIcon(path+game.getPlayer(1).getCards().get(i).name+".png");
			secondP.add(new JButton(ic));
			secondP.get(i).setVisible(false);
			this.add(secondP.get(i));
			secondP.get(i).addActionListener(this);
		}
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
	

