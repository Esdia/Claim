package bobnard.claim.UI;

import bobnard.claim.model.Game;

import javax.swing.*;
import java.awt.*;

public class Window implements Runnable {
    private static JFrame frame;
    public static Boolean isFS;
    private static Container contentPane;
    private static CFrame cFrame;

    private JFrame getFrame() {
        frame = new JFrame("Claim");
        contentPane = frame.getContentPane();

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setMinimumSize(new Dimension(480, 360));
        frame.setSize(
                screenSize.width * 3 / 4,
                screenSize.height * 3 / 4
        );
        contentPane.setLayout(new CardLayout());
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        return frame;
    }

    @Override
    public void run() {
        frame = this.getFrame();

        Menu menu = new Menu(frame);


        contentPane.add("Page1", menu);

        cFrame = new CFrame(frame);
        contentPane.add("Page2", cFrame);

        frame.setVisible(true);
    }

    public static void start() {
        SwingUtilities.invokeLater(new Window());
    }

    public static void switchToGame(Game game) {
        game.reset();
        game.start();
        cFrame.setGame(game);
        ((CardLayout) contentPane.getLayout()).next(contentPane);

        frame.setIconImage(new ImageIcon("src/main/bobnard/claim/UI/resources/" + Menu.skin + "/Icon/icon.png").getImage());
    }

    public static void switchToMainMenu() {
        Audio.getBGM().stop();
        cFrame.stopLoop();
        ((CardLayout) contentPane.getLayout()).previous(contentPane);
    }
}
