package bobnard.claim.UI;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

public class CCanvas extends Canvas{
	
	BufferedImage image;
	String path = "src/main/bobnard/claim/UI/resources/";
	public int h;
	public int w;
	public CCanvas(int nw, int nh) {
		h = nh;
		w = nw;
		setBounds(200,200, w/2, h/3);
		this.setBackground(Color.getHSBColor(100, 200, 500));
	}
	
	public void paint (Graphics g) {
		
		setBounds(w - (w/6),h/4, w/6, h/2);
		int x = this.getWidth();
		int y = this.getHeight();
		int t = x/6;
		
		 try { 
			 image = ImageIO.read(new File(path+"Icon1.png"));
			 } catch (IOException e) {}	
		g.drawImage(image, (x/2)-(t/2), t/2, t, t, null); 

		
		try { 
		   image = ImageIO.read(new File(path+"Icon2.png")); 
		  } catch (IOException e) {} 
		g.drawImage(image, (x/2)-(t/2), (2*t)+(t/2), t, t, null);
		 		

		try {
			image = ImageIO.read(new File(path+"Icon3.png"));
		} catch (IOException  e) {}	
		g.drawImage(image, (x/2)-(t/2), (4*t)+(t/2), t, t, null);
		

		try {
			image = ImageIO.read(new File(path+"Icon4.png"));
		} catch (IOException  e) {}	
		g.drawImage(image, (x/2)-(t/2), (6*t)+(t/2), t, t, null);
		

		try {
			image = ImageIO.read(new File(path+"Icon5.png"));
		} catch (IOException  e) {}	
		g.drawImage(image, (x/2)-(t/2), (8*t)+(t/2), t, t, null);
	
	
	}
	
	
	
	
	
}