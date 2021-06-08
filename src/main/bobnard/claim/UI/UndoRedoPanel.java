package bobnard.claim.UI;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class UndoRedoPanel extends JPanel {
    private String path = null;

    private final String imageName;

    private Image image;
    private Image image2;

    private Image currentImage;

    public UndoRedoPanel(CFrame frame, String imageName) {
        this.imageName = imageName;

        if (imageName.equals("undo")) {
            this.addMouseListener(new UndoRedoMouseListener(frame, this, true));
        } else if (imageName.equals("redo")) {
            this.addMouseListener(new UndoRedoMouseListener(frame, this, false));
        } else {
            throw new IllegalArgumentException();
        }

        this.setOpaque(false);
    }

    public void refreshImagePath() {
        String path_tmp = "src/main/bobnard/claim/UI/resources/" + Menu.skin + "/gameboard/";
        if (!path_tmp.equals(path)) {
            path = path_tmp;
            try {
                image = ImageIO.read(new File(path + imageName + ".png"));
                image = image.getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH);

                image2 = ImageIO.read(new File(path + imageName + "2.png"));
                image2 = image2.getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH);

                this.currentImage = image;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    void switchImage() {
        if (this.currentImage.equals(image)) {
            this.currentImage = image2;
        } else {
            this.currentImage = image;
        }
        this.repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(this.currentImage, 0, 0, getWidth(), getHeight(), null);
    }
}
