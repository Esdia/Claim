package bobnard.claim.UI;


import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;



public class CFrame extends JFrame implements ActionListener{
	

	
	boolean phase;
	JButton b1;
	ImageIcon icb1;
	JButton b2;
	ImageIcon icb2;
	
	int ArraySize = 13;
	ArrayList<JButton> firstP = new ArrayList<JButton>(ArraySize);
	ArrayList<JButton> secondP = new ArrayList<JButton>(ArraySize);
	ArrayList<ImageIcon> images = new ArrayList<ImageIcon>(ArraySize);
	
	public CFrame() {
;
		
		
		phase = true;
		
		icb1 =  new ImageIcon("C:\\Users\\Nad\\Pictures\\Images du Pif\\Logo Wolf.jpg");
		icb2 =  new ImageIcon("C:\\Users\\Nad\\Pictures\\Images du Pif\\Beatrice.png");
		images.add(new ImageIcon("C:\\Users\\Nad\\Pictures\\Images du Pif\\Zelda.png"));
		images.add(new  ImageIcon("C:\\Users\\Nad\\Pictures\\Images du Pif\\Bloodborne Maria.jpg"));
		images.add(new  ImageIcon("C:\\Users\\Nad\\Pictures\\Images du Pif\\Logo Wolf.jpg"));
		images.add(new  ImageIcon("C:\\Users\\Nad\\Pictures\\Images du Pif\\Zelda.png"));
		images.add(new  ImageIcon("C:\\Users\\Nad\\Pictures\\Images du Pif\\Bloodborne Maria.jpg"));
		images.add(new  ImageIcon("C:\\Users\\Nad\\Pictures\\Images du Pif\\Beatrice.png"));
		images.add(new  ImageIcon("C:\\Users\\Nad\\Pictures\\Images du Pif\\Logo Wolf.jpg"));
		images.add(new  ImageIcon("C:\\Users\\Nad\\Pictures\\Images du Pif\\Zelda.png"));
		images.add(new  ImageIcon("C:\\Users\\Nad\\Pictures\\Images du Pif\\Bloodborne Maria.jpg"));
		images.add(new  ImageIcon("C:\\Users\\Nad\\Pictures\\Images du Pif\\Beatrice.png"));
		images.add(new  ImageIcon("C:\\Users\\Nad\\Pictures\\Images du Pif\\Logo Wolf.jpg"));
		images.add(new  ImageIcon("C:\\Users\\Nad\\Pictures\\Images du Pif\\Zelda.png"));
		images.add(new  ImageIcon("C:\\Users\\Nad\\Pictures\\Images du Pif\\Bloodborne Maria.jpg"));
		
		

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
		
		setLayout(null);
		this.setVisible(true);
		
	

		// TODO Dimensions 

		
		
		
	}
	public void BMapping() {
		ButtonMapping(firstP, false);
		ButtonMapping(secondP, true);		
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
	
	
		
	
	public void ButtonMapping(ArrayList<JButton> buttons, boolean P) {
		int h = (int) getSize().getHeight();
		int w = (int) getSize().getWidth();
		int x = 30;
		for (int i=0; i< ArraySize; i++) {

			buttons.add(new JButton(images.get(i)));
			buttons.get(i).setIcon(resizeIcon(images.get(i), w/16, h/10));
//			if (P) { buttons.get(i).setBounds( x, 700, 100, 150); 
//			}else {
//				  buttons.get(i).setBounds( x, 1, 100, 150); }

			if (P) { 
				buttons.get(i).setBounds( x, h-(h/10)-30, w/16, h/10); 
			}else {
				buttons.get(i).setBounds( x, 1, w/16, h/10); }


			buttons.get(i).setVisible(true);
			this.add(buttons.get(i));
			buttons.get(i).addActionListener(this);
			x += buttons.get(i).getWidth();
			
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
	

