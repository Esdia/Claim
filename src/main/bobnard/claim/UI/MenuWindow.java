package bobnard.claim.UI;


import javax.swing.*;
import java.awt.*;

public class MenuWindow implements Runnable {
    private JFrame frame;

    
    
    @Override
    public void run() {

		SkinSelect ss =  new SkinSelect();
		if(!ss.selected) System.exit(0);

    	frame = new JFrame("Claim menu");


    	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    	frame.setMinimumSize(new Dimension(480, 360));
    	frame.setSize(
    			screenSize.width * 3 / 4,
    			screenSize.height * 3 / 4
    			);
    	frame.setLocationRelativeTo(null);
    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


    	frame.add(new Menu(frame,ss.skin));

    	frame.setVisible(true);

    }

    public static void start() {
        MenuWindow bw = new MenuWindow();
        SwingUtilities.invokeLater(bw);
    }
}