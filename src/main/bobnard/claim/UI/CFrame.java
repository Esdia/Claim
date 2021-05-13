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
	
  
	ArrayList<JButton> buttons = new ArrayList<JButton>(26);
	ArrayList<ImageIcon> images = new ArrayList<ImageIcon>(26);
	
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
		images.add(new ImageIcon("C:\\Users\\Nad\\Pictures\\Images du Pif\\Beatrice.png"));
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
		setSize(800,800);
		this.setVisible(true);
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (buttons.contains(e.getSource()) && buttons.indexOf(e.getSource()) < buttons.size()/2 ) {
			buttons.get(buttons.indexOf(e.getSource())).setBounds(275, 250, 60, 90);		
		}
		if (buttons.contains(e.getSource()) && buttons.indexOf(e.getSource()) >= buttons.size()/2 ) {
			buttons.get(buttons.indexOf(e.getSource())).setBounds(275, 350, 60, 90);		
		}

		
	}
	
	
	private static Icon resizeIcon(ImageIcon i, int w, int h) {
		Image im = i.getImage();
		Image resIm = im.getScaledInstance (w, h, java.awt.Image.SCALE_SMOOTH);
		return new ImageIcon(resIm);
	}
	
	
		
	
	public void ButtonMapping() {
		int x = 30;
		for (int i=0; i< 24; i++) {
			if (i == 12) {
				x = 30;
			}
			buttons.add(new JButton(images.get(i)));
			buttons.get(i).setIcon(resizeIcon(images.get(i), 50, 75));
			if (i < 12) {
				buttons.get(i).setBounds( x, 1, 50, 75);
			}else {
				buttons.get(i).setBounds( x, 700, 50, 75);
			}
			buttons.get(i).setVisible(true);
			this.add(buttons.get(i));
			buttons.get(i).addActionListener(this);
			x += 50;
			
		}
	}

	
	
	
}
	

