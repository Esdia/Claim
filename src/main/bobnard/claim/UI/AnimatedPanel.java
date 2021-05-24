package bobnard.claim.UI;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;
import bobnard.claim.UI.*;
import bobnard.claim.AI.AI;

public class AnimatedPanel {
	CardUI myc;
	Point mypinit;
	Point mypdest;
	int sx, sy;
	int step;
	Timer animationTimer;
	
	public AnimatedPanel(CardUI c, Point p1, Point p2) {
		myc = c;
		mypinit = p1;
		mypdest = p2;
		step =20;
		sx = (mypdest.x - mypinit.x)/step;
		sy = (mypdest.y - mypinit.y)/step;
		myc.setVisible(true);
		myc.setLocation(mypinit);
	}
		
	public AnimatedPanel(CardUI c, Point p2) {
		myc = c;
		mypinit = new Point (c.getX(), c.getY());
		mypdest = p2;
		step =20;
		sx = (mypdest.x - mypinit.x)/step;
		sy = (mypdest.y - mypinit.y)/step;
		myc.setVisible(true);
		myc.setLocation(mypinit);
	}
	
	
	public void startanimation() {
		
		animationTimer = new Timer(00, f -> {
			mypinit.x += sx;
			mypinit.y += sy;
			step --;
			if (step == 0) {
				myc.setLocation(mypdest);
				animationTimer.stop();
			} else {
				myc.setLocation(mypinit);
				myc.repaint();
			}
			
        });
		animationTimer.start();		
		 
	}
	
	public void setmyc(CardUI card) {
		myc = card;
	}

	
}