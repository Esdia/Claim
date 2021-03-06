package bobnard.claim.UI;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class SkinSelect extends JComponent {

    final JFrame frame;
    final Menu menu;
    BufferedImage image;

    final JButton b1;
    final JButton b2;
    final JButton b3;
    ImageIcon ul, vl, ba;

    final SSMouseListener m = new SSMouseListener(this);

    public SkinSelect(JFrame frame, Menu menu) {
        this.frame = frame;
        this.menu = menu;

        setImages();

        b1 = new JButton();
        b2 = new JButton();
        b3 = new JButton();

        this.add(b1);
        this.add(b2);
        this.add(b3);

        b1.addMouseListener(m);
        b2.addMouseListener(m);
        b3.addMouseListener(m);
    }

    public void setImages() {
        ul = Utils.loadIcon("menu/skin/logou.png");
        vl = Utils.loadIcon("menu/skin/logov.png");
        ba = Utils.loadIcon("menu/skin/back.png");
        image = Utils.loadImg("menu/skin/bg2.png");
    }

    void setButton(JButton button, int x, int y, int w, int h, ImageIcon icon) {
        button.setBounds(x, y, w, h);
        button.setIcon(resizeIcon(icon, w, h));
        button.setOpaque(false);
        button.setContentAreaFilled(false);
    }

    public void setButtons() {
        int w = this.frame.getWidth();
        int h = this.frame.getHeight();
        int x = 10;
        int y = 10;

        this.setButton(b1, x, y, w / 16 * 4, h / 9 * 2, vl);

        x += (w / 16 * 4) + ((w / 16 * 4) / 10);

        this.setButton(b2, x, y, w / 16 * 4, h / 9 * 2, ul);

        this.setButton(b3, 10, h - 100, w / 6, h / 19, ba);
        b3.setBorderPainted(false);
    }

    public void paintComponent(Graphics g) {

        g.drawImage(this.image, 0, 0, getWidth(), getHeight(), null);

        setButtons();


    }

    public Icon resizeIcon(ImageIcon i, int w, int h) {
        if (i == null) return null;
        Image im = i.getImage();
        Image resIm = im.getScaledInstance(w, h, Image.SCALE_SMOOTH);
        return new ImageIcon(resIm);

    }

    public void refresh() {
        setImages();
    }
}
