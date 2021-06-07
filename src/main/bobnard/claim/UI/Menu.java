package bobnard.claim.UI;



import bobnard.claim.model.Save;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;


public class Menu extends JComponent {

    BufferedImage image;
    BufferedImage image2;

    Boolean isStart;
    Boolean isSs;
    Boolean isConfig;

    String path;

    JButton b1;
    JButton b2;
    JButton b3;
    JButton b4;
    JButton b5;
    JButton b1a;
    JButton b1b;

    static JFrame frame;

    ImageIcon ng;
    ImageIcon ru;
    ImageIcon cf;
    ImageIcon sk;
    ImageIcon ex;
    ImageIcon pvp;
    ImageIcon pve;

    final ButtonsMouse m = new ButtonsMouse(this);

    public static String skin;

    final SkinSelect panel;
    Config config;

    public Menu(JFrame frame) {

        Save.sysloadSkin();
        this.isStart = false;
        this.isSs = false;
        this.isConfig = false;

        Menu.frame = frame;


        panel = new SkinSelect(frame, this);
        this.add(panel);


        Audio.setFiles();

        Audio.playBGM(0);



        config = new Config(frame, this);
        this.add(config);

        creatButtons();
        setImages();

    }


    public void changeSkin(String name) {
        skin = name;
        setImages();
        refresh();
        Audio.reload();
    }

    private void setImages() {

        frame.setIconImage(new ImageIcon("src/main/bobnard/claim/UI/resources/" + skin + "/Icon/icon.png").getImage());
        this.path = "src/main/bobnard/claim/UI/resources/" + skin + "/menu/";

        try {
            ng = new ImageIcon(path + "new_game.png");
            ru = new ImageIcon(path + "rules.png");
            cf = new ImageIcon(path + "config.png");
            sk = new ImageIcon(path + "skin.png");
            ex = new ImageIcon(path + "exit.png");
            pvp = new ImageIcon(path + "pvp.png");
            pve = new ImageIcon(path + "pve.png");

            image = ImageIO.read(new File(path + "menu.png"));
            if (skin.equals("Umineko")) image2 = ImageIO.read(new File(path + "ware.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private JButton getButton() {

        JButton button = new JButton();
        button.setVisible(false);
        this.add(button);
        button.addMouseListener(m);

        return button;
    }

    private void creatButtons() {
        b1 = getButton();
        b2 = getButton();
        b3 = getButton();
        b4 = getButton();
        b5 = getButton();
        b1a = getButton();
        b1b = getButton();
    }


    public Icon resizeIcon(ImageIcon i, int w, int h) {
        if (i == null) return null;
        Image im = i.getImage();
        Image resIm = im.getScaledInstance(w, h, Image.SCALE_SMOOTH);
        return new ImageIcon(resIm);

    }

    public void refresh() {
        paintComponent(this.getGraphics());
    }

    private void setButtons(int h, int w) {

        Color color = new Color(0, 0, 0, 0);

        b1.setBounds((int) (w / 1.35), (int) (h / 2.7), w / 5, h / 17);
        b1.setBackground(color);
        b1.setIcon(resizeIcon(ng, w / 5, h / 17));
        b1.setBorderPainted(false);
        b1.setOpaque(false);
        b1.setContentAreaFilled(false);

        b1.setVisible(true);

        b2.setBounds((int) (w / 1.35), (int) (h / 2.25), w / 5, h / 17);
        b2.setBackground(color);
        b2.setIcon(resizeIcon(ru, w / 5, h / 17));
        b2.setBorderPainted(false);
        b2.setOpaque(false);
        b2.setContentAreaFilled(false);

        b2.setVisible(true);

        b3.setBounds((int) (w / 1.35), (int) (h / 1.93), w / 5, h / 17);
        b3.setBackground(color);
        b3.setIcon(resizeIcon(cf, w / 5, h / 17));
        b3.setBorderPainted(false);
        b3.setOpaque(false);
        b3.setContentAreaFilled(false);

        b3.setVisible(true);

        b4.setBounds((int) (w / 1.35), (int) (h / 1.69), w / 5, h / 17);
        b4.setBackground(color);
        b4.setIcon(resizeIcon(sk, w / 5, h / 17));
        b4.setBorderPainted(false);
        b4.setOpaque(false);
        b4.setContentAreaFilled(false);

        b4.setVisible(true);

        b5.setBounds((int) (w / 1.35), (int) (h / 1.50), w / 5, h / 17);
        b5.setBackground(color);
        b5.setIcon(resizeIcon(ex, w / 5, h / 17));
        b5.setBorderPainted(false);
        b5.setOpaque(false);
        b5.setContentAreaFilled(false);

        b5.setVisible(true);

        b1a.setBounds(((int) ((w / 1.35) - w / (5.25))), h / 3, w / 5, h / 17);
        b1a.setBackground(color);
        b1a.setIcon(resizeIcon(pvp, w / 7, h / 22));
        b1a.setBorderPainted(false);
        b1a.setOpaque(false);
        b1a.setContentAreaFilled(false);


        b1b.setBounds(((int) ((w / 1.35) - w / (5.25))), (int) (h / 2.5), w / 5, h / 17);
        b1b.setBackground(color);
        b1b.setIcon(resizeIcon(pve, w / 7, h / 22));
        b1b.setBorderPainted(false);
        b1b.setOpaque(false);
        b1b.setContentAreaFilled(false);


    }

    private void draw(JComponent component, boolean cond) {
        if (cond) {
            component.setBounds(0, 0, getWidth(), getHeight());
            component.setOpaque(false);

            if (!component.isVisible()) {
                component.setVisible(true);
            }

            b1.setVisible(false);
            b2.setVisible(false);
            b3.setVisible(false);
            b4.setVisible(false);
            b5.setVisible(false);
        } else {
            component.setVisible(false);
        }
    }

    public void paintComponent(Graphics g) {
        int h = (int) getSize().getHeight();
        int w = (int) getSize().getWidth();

        g.drawImage(image, 0, 0, w, h, null);

        setButtons(h, w);

        this.draw(panel, isSs);
        this.draw(config, isConfig);

        if (isStart) g.drawImage(image2, 0, 0, w, h, null);

        this.setVisible(true);

    }

}




	

