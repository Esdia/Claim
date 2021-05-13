package bobnard.claim.UI;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

public class CCanvas extends Canvas{
	
	BufferedImage image;
	
	public CCanvas() {
		setBounds(0,0,800, 800);
	}
	
	public void paint (Graphics g) {
		
		try {
			image = ImageIO.read(new File("C:\\Users\\Nad\\Pictures\\Images du Pif\\CardBack.jpg"));
		} catch (IOException  e) {}	
		g.drawImage(image, 20, 300, 50, 75, null); 
	
		try {
			image = ImageIO.read(new File("C:\\Users\\Nad\\Pictures\\Images du Pif\\The Scroll.jpg"));
		} catch (IOException  e) {}	
		g.drawImage(image, 150, 150, 200, 150, null);
	
	
	
	
	
	}
	
	
	
	
	
}