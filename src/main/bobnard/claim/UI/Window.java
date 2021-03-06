package bobnard.claim.UI;

import bobnard.claim.model.Game;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Window implements Runnable {
    private static final Random random = new Random();

    private static JFrame frame;
    public static boolean isFS;
    private static Container contentPane;
    private static CFrame cFrame;
    private static Menu menu;

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

        menu = new Menu(frame);


        contentPane.add("Page1", menu);

        cFrame = new CFrame(frame);
        contentPane.add("Page2", cFrame);

        frame.setVisible(true);
    }

    public static void start() {
        SwingUtilities.invokeLater(new Window());
    }

    public static void switchToGame(Game game) {
        CardUI.refreshImagePath();

        game.reset();
        switch (Config.firstPlayer) {
            case "1 (top)":
                game.setFirstPlayer(0);
                break;
            case "2 (bottom)":
                game.setFirstPlayer(1);
                break;
            case "Random":
                game.setFirstPlayer(random.nextInt(2));
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + Config.firstPlayer);
        }
        game.start();
        cFrame.setGame(game);
        cFrame.refreshImages();
        ((CardLayout) contentPane.getLayout()).next(contentPane);

        frame.setIconImage(Utils.loadImg("Icon/icon.png"));
    }

    public static void switchToMainMenu() {
        menu.reset();
        Audio.reload();
        cFrame.stopLoop();
        ((CardLayout) contentPane.getLayout()).previous(contentPane);
    }
}
