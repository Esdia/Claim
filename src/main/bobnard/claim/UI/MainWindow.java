package bobnard.claim.UI;

import bobnard.claim.model.Game;

import javax.swing.*;

import java.awt.*;

public class MainWindow implements Runnable {
    GameController controller;
    private JFrame frame;


    
    
    @Override
    public void run() {
        Game game = new Game();
        CFrame gameUI = new CFrame(game);

        this.controller = new GameController(game, gameUI);
        this.frame = new JFrame("Claim");

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setMinimumSize(new Dimension(480, 360));
        frame.setSize(
                screenSize.width * 3 / 4,
                screenSize.height * 3 / 4
        );
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.addMouseListener(new Mouse(controller));

        this.frame.add(controller.getGameUI());
        frame.setVisible(true);
    }

    public static void start() {
        MainWindow bw = new MainWindow();
        SwingUtilities.invokeLater(bw);
    }
}