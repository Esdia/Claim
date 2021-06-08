package bobnard.claim.UI;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Config extends JComponent {

    final JFrame frame;
    final Menu menu;
    final PauseMenu pm;
    BufferedImage image;

    final JButton b1;
    final JSlider s;

    final JLabel l1;
    final JLabel l2;
    final JLabel l3;

    final JComboBox<String> cb;
    final JComboBox<String> cb2;

    public boolean isFS;

    ImageIcon ba;

    final ConfigMouseListener m = new ConfigMouseListener(this);

    public static final GraphicsDevice device = GraphicsEnvironment
            .getLocalGraphicsEnvironment().getScreenDevices()[0];

    public static String difficulty;


    public Config(JFrame frame, Menu menu) {
        this.isFS = Window.isFS;
        this.frame = frame;
        this.menu = menu;
        this.pm = null;

        s = new JSlider(0, 15, Audio.getVolume());

        s.setOpaque(false);
        s.setVisible(true);

        l1 = new JLabel("Volume :");
        l2 = new JLabel("Screen :");
        l3 = new JLabel("Difficulty :");

        setImages();

        b1 = new JButton();

        b1.addMouseListener(m);

        String[] str = {"Windowed" , "Fullscreen"};
        cb = new JComboBox<>(str);

        if(isFS) cb.setSelectedItem(cb.getItemAt(1));

        String[] d = {"Easy" , "Normal", "Hard"};
        cb2 = new JComboBox<>(d);

        cb2.setSelectedItem(difficulty);

        this.add(cb);
        this.add(cb2);

        this.add(b1);

        this.add(l1);
        this.add(l2);
        this.add(l3);

        this.add(s);



    }

    public Config(JFrame frame, PauseMenu pm) {
        this.isFS = Window.isFS;
        this.frame = frame;
        this.pm = pm;
        this.menu = null;

        s = new JSlider(0, 15, Audio.getVolume());

        s.setOpaque(false);
        s.setVisible(true);

        l1 = new JLabel("Volume :");
        l2 = new JLabel("Screen :");
        l3 = new JLabel("Difficulty :");

        setImages();

        b1 = new JButton();

        b1.addMouseListener(m);

        String[] str = {"Windowed" , "Fullscreen"};
        cb = new JComboBox<>(str);

        String[] d = {"Easy" , "Normal", "Hard"};
        cb2 = new JComboBox<>(d);

        if(isFS) cb.setSelectedItem(cb.getItemAt(1));

        cb2.setSelectedItem(difficulty);

        this.add(cb);
        this.add(cb2);

        this.add(b1);

        this.add(l1);
        this.add(l2);
        this.add(l3);

        this.add(s);


    }

    public void setImages() {
        try {
            ba = new ImageIcon("src/main/bobnard/claim/UI/resources/" + Menu.skin + "/menu/Config/back.png");
            image = ImageIO.read(new File("src/main/bobnard/claim/UI/resources/" + Menu.skin + "/menu/Config/bg.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void setButton(JButton button, int y, int w, int h, ImageIcon icon) {
        button.setBounds(10, y, w, h);
        button.setIcon(resizeIcon(icon, w, h));
        button.setOpaque(false);
        button.setContentAreaFilled(false);
    }

    public void setButtons() {
        int w = this.frame.getWidth();
        int h = this.frame.getHeight();

        this.setButton(b1, h - 100, w / 6, h / 19, ba);
        b1.setBorderPainted(false);

    }

    public void paintComponent(Graphics g) {
        int w = this.frame.getWidth();
        int h = this.frame.getHeight();

        setButtons();

        l1.setBounds(w/2 - w/12,h/20, w,h/10);
        l1.setFont(new Font("Forte", Font.PLAIN, w/30));

        l2.setBounds(w/2 - w/12,h/4, w,h/10);
        l2.setFont(new Font("Forte", Font.PLAIN, w/30));

        l3.setBounds(w/2 - w/12,h/2, w,h/10);
        l3.setFont(new Font("Forte", Font.PLAIN, w/30));

        this.s.setBounds(w/2 - w/10,h/7,w/7,h/10);
        this.s.setOpaque(false);

        int x = this.s.getValue();
        Audio.setVolume(x);

        g.drawImage(this.image, 0, 0, getWidth(), getHeight(), null);


        cb.setBounds(w/2 - w/10, (int) (h/2.9),w/6,h/18);

        cb2.setBounds(w/2 - w/10, (int) (h/1.65),w/6,h/18);

        if(cb.getItemAt(cb.getSelectedIndex()).equals("Fullscreen") && !isFS){
            device.setFullScreenWindow(frame);
            isFS = true;
            Window.isFS = true;
        }

        if(cb.getItemAt(cb.getSelectedIndex()).equals("Windowed") && isFS){
            device.setFullScreenWindow(null);
            isFS = false;
            Window.isFS = false;
        }

        difficulty = cb2.getItemAt(cb2.getSelectedIndex());

        Save.syssave(this);

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
