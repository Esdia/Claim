package bobnard.claim.UI;

import bobnard.claim.model.Game;

import javax.swing.*;

import java.awt.*;

public class MainWindow implements Runnable {
    GameController controller;
    private JFrame frame;
    public static CFrame gameUI;


    
    
    @Override
    public void run() {

    	//this.controller = new GameController(game, gameUI);
    	frame = new JFrame("Claim");

    	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    	frame.setMinimumSize(new Dimension(480, 360));
    	frame.setSize(
    			screenSize.width * 3 / 4,
    			screenSize.height * 3 / 4
    			);
    	frame.setLocationRelativeTo(null);
    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    	//frame.addMouseListener(new Mouse(controller));

    	frame.add(gameUI);
    	frame.setVisible(true);
    	frame.repaint(0);
    	gameUI.repaint();
    }

    public static void start(Game game) {
        MainWindow bw = new MainWindow();
        gameUI = new CFrame(game);
        SwingUtilities.invokeLater(bw);
    }
}