
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
	static String errTurn = "WAIT FOR YOUR TURN TO PLAY !!";
	Card mycard;
	JButton mbutton;
	ImageIcon myic;
	int mplayer;
	boolean isvisible;
	
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
		
		int x = w/8 + ((w/20) * rang );
		int y = 5;
		if (mplayer == 1) y = h-(int) ((w/20)*1.5) -5 ;

		mbutton.setIcon(MainWindow.gameUI.resizeIcon(myic,  w/20, (int) ((w/20)*1.5)));
		mbutton.setBounds( x, y, w/20, (int) ((w/20)*1.5));
		mbutton.setVisible(true);
	}
	
	public void DisplayFlipped() {
		int h,w;
		w = (int) MainWindow.gameUI.getSize().getWidth();
		h = (int) MainWindow.gameUI.getSize().getHeight();

		mbutton.setIcon(MainWindow.gameUI.resizeIcon(myic,  w/20, (int) ((w/20)*1.5)));
		mbutton.setBounds( (w/32)+2*(w/20), (h/2)-(h/16), w/20, (int) ((w/20)*1.5));
		mbutton.setVisible(true);
	}
	
	public void DisplayPlayed() {
		
		int h,w;
		w = (int) MainWindow.gameUI.getSize().getWidth();
		h = (int) MainWindow.gameUI.getSize().getHeight();
		
		int x = (w/2)-(w/20);
		int y = (h/2)-((int) ((w/20)*1.5))-(h/16) ;
		if (mplayer == 1) y = (h/2)+(h/16);

		mbutton.setIcon(MainWindow.gameUI.resizeIcon(myic,  w/20, (int) ((w/20)*1.5)));
		mbutton.setBounds( x, y, w/20, (int) ((w/20)*1.5));
		mbutton.setVisible(true);
		
	}
	
	public void setInvisible() {
		mbutton.setVisible(false);
	}
	
	
	public void actionPerformed(ActionEvent e) {
		
		if (MainWindow.gameUI.game.getCurrentPlayer() != mplayer) {
			JOptionPane.showMessageDialog(MainWindow.gameUI,errTurn);
			return;
		}
		
		if (mplayer != -1) MainWindow.gameUI.game.playCard(mycard);
		System.out.println("ActionPerformer" + mycard.name );
		MainWindow.gameUI.repaint();
	}
	
	
	
}