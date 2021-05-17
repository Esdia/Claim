
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

import bobnard.claim.UI.*;

public class CardUI extends JButton implements ActionListener{
	static String path = "src/main/bobnard/claim/UI/resources/";
	static String errTurn = "SELECT ONE OF YOUR CARDS!!";
	Card mycard;
	JButton mbutton;
	ImageIcon myic;
	static ImageIcon myback =  new ImageIcon(path+"CARDBACK.png");;
	int mplayer;
	boolean isvisible;
	int wb, hb;
	
	
	public CardUI(Card c) {
		mycard = c;
		myic =  new ImageIcon(path+c.name+".png");
		mbutton = new JButton(myic);
		Color mycolor = new Color(0, 0, 0, 0);
		mbutton.setBackground(mycolor);
		mbutton.setBorderPainted(false);
		mbutton.setVisible(false);
		isvisible = false;
		mplayer = 0;
		MainWindow.gameUI.add(mbutton);
		mbutton.addActionListener(this);
	}
	
	public void setplayer(int p, boolean visible) {
		mplayer = p;
		isvisible = visible;
		mbutton.setVisible(visible);
	}
	
	public void Display(int rang) {
		int h,w;
		w = (int) MainWindow.gameUI.getSize().getWidth();
		h = (int) MainWindow.gameUI.getSize().getHeight();
		computewbhb(w,h);
		int x = w/8 + (wb * rang );
		int y = 5;
		if (mplayer == 1) y = h-hb -5 ;
		
		boolean flip = false;
		try {
			flip = MainWindow.gameUI.game.getCurrentPlayer() != mplayer;
		}catch (Exception e) {}
		
		
		if (flip) {
			mbutton.setIcon(MainWindow.gameUI.resizeIcon(myback,   wb, hb));
		}else {
			mbutton.setIcon(MainWindow.gameUI.resizeIcon(myic,   wb, hb));
		}

		
		mbutton.setBounds( x, y,  wb, hb);
		mbutton.setVisible(true);
	}
	
	void computewbhb(int w, int h) {
		wb = w/20;
		hb = (int) (wb*1.5);		
	}
	public void DisplayFlipped() {
		int h,w;
		w = (int) MainWindow.gameUI.getSize().getWidth();
		h = (int) MainWindow.gameUI.getSize().getHeight();

		computewbhb(w,h);
		mbutton.setIcon(MainWindow.gameUI.resizeIcon(myic,  wb, hb));
		mbutton.setBounds( (w/32)+2*wb, (h/2)-(hb/2), wb, hb);
		mbutton.setVisible(true);
	}
	
	public void DisplayPlayed() {
		
		int h,w;
		w = (int) MainWindow.gameUI.getSize().getWidth();
		h = (int) MainWindow.gameUI.getSize().getHeight();
		
		int x = (w/2)-wb;
		int y = (h/2)- hb-(h/16) ;
		if (mplayer == 1) y = (h/2)+(h/16);
		computewbhb(w,h);
		mbutton.setIcon(MainWindow.gameUI.resizeIcon(myic,   wb, hb));
		mbutton.setBounds( x, y,  wb, hb);
		mbutton.setVisible(true);
		
	}
	
	public void setInvisible() {
		mbutton.setVisible(false);
	}
	
	
	public void actionPerformed(ActionEvent e) {
		
		if (MainWindow.gameUI.game.getCurrentPlayer() != mplayer) {
			//JOptionPane.showMessageDialog(MainWindow.gameUI,errTurn); 
			//DEBUG
			return;
		}
		
		if (mplayer != -1) MainWindow.gameUI.game.playCard(mycard);
		System.out.println("ActionPerformer" + mycard.name );
		MainWindow.gameUI.repaint();
	}
	
	
	
}