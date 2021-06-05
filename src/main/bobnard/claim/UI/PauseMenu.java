package bobnard.claim.UI;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class PauseMenu extends JComponent {

    public static boolean isConfig = false;
    final CFrame frame;
    BufferedImage bg;
    Config c;

    JButton re, cf, sv, ld, bm;

    PMMouseListener m = new PMMouseListener(this);


    public PauseMenu(CFrame frame) {

        c = new Config(frame.frame);

        this.add(c);

        this.frame = frame;

        setImages();

        re = new JButton("Resume");
        cf = new JButton("Config");
        sv = new JButton("Save");
        ld = new JButton("Load");
        bm = new JButton("Back to menu");

        re.addMouseListener(m);
        cf.addMouseListener(m);
        sv.addMouseListener(m);
        ld.addMouseListener(m);
        bm.addMouseListener(m);


        this.add(re);
        this.add(cf);
        this.add(sv);
        this.add(ld);
        this.add(bm);

    }

    public Icon resizeIcon(ImageIcon i, int w, int h) {
        if (i == null) return null;
        Image im = i.getImage();
        Image resIm = im.getScaledInstance(w, h, Image.SCALE_SMOOTH);
        return new ImageIcon(resIm);

    }

    public void setImages() {
        try {
            bg = ImageIO.read(new File("src/main/bobnard/claim/UI/resources/" + Menu.skin + "/pause_menu/bg.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void setButton(JButton button, int x, int y, int w, int h, ImageIcon icon) {
        button.setBounds(x, y, w, h);
        button.setIcon(resizeIcon(icon, w, h));
        button.setOpaque(false);
        button.setContentAreaFilled(false);
    }

    void setButton(JButton button, int x, int y, int w, int h) {
        button.setBounds(x, y, w, h);
        button.setOpaque(false);

    }

    public void setButtons() {
        int w = this.frame.getWidth();
        int h = this.frame.getHeight();


        this.setButton(re, (int) (w/2.2), h/10, w / 10, h / 15);

        this.setButton(cf, (int) (w/2.2), h/5, w / 10, h / 15);

        this.setButton(sv, (int) (w/2.2), (int) (h/3.3), w / 10, h / 15);

        this.setButton(ld, (int) (w/2.2), (int) (h/2.45), w / 10, h / 15);

        this.setButton(bm, (int) (w/2.2), (int) (h/1.95), w / 10, h / 15);


    }

    private void draw(JComponent component, boolean cond) {
        if (cond) {
            component.setBounds(0, 0, getWidth(), getHeight());
            component.setOpaque(false);

            if (!component.isVisible()) {
                component.setVisible(true);
            }

            re.setVisible(false);
            cf.setVisible(false);
            sv.setVisible(false);
            ld.setVisible(false);
            bm.setVisible(false);

        } else {
            component.setVisible(false);
            re.setVisible(true);
            cf.setVisible(true);
            sv.setVisible(true);
            ld.setVisible(true);
            bm.setVisible(true);
        }
    }

    public void paintComponent(Graphics g) {

       draw(c, isConfig);

       g.drawImage(bg, 0, 0, this.frame.getWidth(), this.frame.getHeight(), null);

       setButtons();

    }



    public void refresh() {
        setImages();
    }
}
