package bobnard.claim.UI;

import bobnard.claim.model.Game;

import javax.swing.*;
import java.awt.*;

public class Window implements Runnable {
    private static WindowType type;
    private static Game game;

    private JFrame getFrame() {
        JFrame frame = new JFrame("Claim");

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setMinimumSize(new Dimension(480, 360));
        frame.setSize(
                screenSize.width * 3 / 4,
                screenSize.height * 3 / 4
        );
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        return frame;
    }

    @Override
    public void run() {
        JFrame frame = this.getFrame();

        switch (type) {
            case MENU -> frame.add(new Menu(frame));
            case GAME -> {
                frame.add(new CFrame(game));
                frame.setIconImage(new ImageIcon("src/main/bobnard/claim/UI/resources/" + Menu.skin + "/Icon/icon.png").getImage());
            }
        }

        frame.setVisible(true);
    }

    public static void start() {
        Window.type = WindowType.MENU;
        SwingUtilities.invokeLater(new Window());
    }

    public static void start(Game game) {
        Window.type = WindowType.GAME;
        Window.game = game;
        SwingUtilities.invokeLater(new Window());
    }
}
