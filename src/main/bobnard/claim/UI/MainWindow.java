package bobnard.claim.UI;

import bobnard.claim.model.Game;

import javax.swing.*;

import java.awt.*;

public class MainWindow implements Runnable {
    GameController controller;
    private CFrame frame;
    
    public MainWindow (CFrame cf) {
		frame = cf;
	}

    @Override
    public void run() {
        Game game = new Game();
        this.controller = new GameController(game);
        this.frame.setTitle("Claim");

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setMinimumSize(new Dimension(280, 360));
        frame.setSize(
                screenSize.width * 3 / 4,
                screenSize.height * 3 / 4
        );
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.addMouseListener(new Mouse(controller));
        frame.BMapping();

        frame.setVisible(true);
    }

    public static void start(CFrame cf) {
    	MainWindow bw = new MainWindow(cf);
        SwingUtilities.invokeLater(bw);
    }
}
