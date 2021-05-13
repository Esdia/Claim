package bobnard.claim.UI;


import bobnard.claim.model.Game;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;



public class CFrame extends JComponent implements ActionListener{
	

	
	boolean phase;
	Game game;
	JButton b1;
	ImageIcon icb1;
	JButton b2;
	ImageIcon icb2;
	String path = "src/main/bobnard/claim/UI/resources/";
	
	int ArraySize = 13;
	ArrayList<JButton> firstP = new ArrayList<JButton>(ArraySize);
	ArrayList<JButton> secondP = new ArrayList<JButton>(ArraySize);

	public CFrame(Game game) {
		this.game = game;

		phase = true;
		icb1 =  new ImageIcon(path+"Logo Wolf.jpg");
		icb2 =  new ImageIcon(path+"Beatrice.png");
		
		
		
		b1 =  new JButton(icb1);
		b1.setIcon(resizeIcon(icb1, 150, 75));
		b1.setBounds(400, 400, 100, 70);
		b1.setVisible(true);
		this.add(b1);
		b1.addActionListener(this);
		
		b2 =  new JButton(icb2);
		b2.setIcon(resizeIcon(icb2, 50, 75));
		b2.setBounds(80, 300, 60, 90);
		b2.setVisible(true);
		this.add(b2);
		b2.addActionListener(this);
		
		
		
		ButtonMapping();
	
		setLayout(null);
		// TODO Dimensions 

		this.setVisible(true);
	}



	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (firstP.contains(e.getSource()) ) {
			firstP.get(firstP.indexOf(e.getSource())).setBounds(275, 250, 60, 90);		
		}
		if (secondP.contains(e.getSource()) ) {
			secondP.get(secondP.indexOf(e.getSource())).setBounds(275, 350, 60, 90);		
		}

		
	}
	
	
	private static Icon resizeIcon(ImageIcon i, int w, int h) {
		Image im = i.getImage();
		Image resIm = im.getScaledInstance (w, h, java.awt.Image.SCALE_SMOOTH);
		return new ImageIcon(resIm);
	}


	
	public void ButtonMapping() {
		int x = 30;
		for (int i=0; i< 12; i++) {
			ImageIcon ic =  new ImageIcon(path+game.getPlayer(0).getCards().get(i).name+".png");
			firstP.add(new JButton(ic));
			firstP.get(i).setIcon(resizeIcon(ic,  50, 75));
			firstP.get(i).setBounds( x, 1, 50, 75);

			firstP.get(i).setVisible(true);
			this.add(firstP.get(i));
			firstP.get(i).addActionListener(this);
			x += 50;
			
		}
		x=30;
		for (int i=0; i< 12; i++) {
			ImageIcon ic =  new ImageIcon(path+game.getPlayer(1).getCards().get(i).name+".png");
			secondP.add(new JButton(ic));
			secondP.get(i).setIcon(resizeIcon(ic,  50, 75));
			secondP.get(i).setBounds( x, 700, 50, 75);

			secondP.get(i).setVisible(true);
			this.add(secondP.get(i));
			secondP.get(i).addActionListener(this);
			x += 50;

		}
	}

	
	//TODO Method to apply ButtonRepaint on EVERY button
	

	
	public void ButtonRepaint(JButton b, ImageIcon i) {
		
		int h = (int) getSize().getHeight();
		int w = (int) getSize().getWidth();

		int nh = h/10;
		int nw = w/16;
		int x  = 2*nw;
		int y  = 1;
		
		if (b.getX() > w/2) {
			y = h - (nh+30); // Error Margin
		}

		
		b.setIcon(resizeIcon(i, nw, nh));
		b.setBounds(x, y, nw, nh);
	}


}
	

