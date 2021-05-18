package bobnard.claim.UI;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class MenuVanilla extends JComponent{

    BufferedImage image;
    BufferedImage image2;

    Boolean isStart;

    String path = "src/main/bobnard/claim/UI/resources/menu/";

    JButton b1;
    JButton b2;
    JButton b3;

    JFrame frame;

    ImageIcon ng;
    ImageIcon ru;
    ImageIcon ex;

    ButtonsMouseVanilla m = new ButtonsMouseVanilla(this);

    public MenuVanilla(JFrame frame) {
        this.isStart = false;
        this.frame = frame;

        ng =  new ImageIcon(path+"new_game.png");
        ru =  new ImageIcon(path+"rules.png");
        ex =  new ImageIcon(path+"exit.png");

        Audio.playBGM(0);

        b1 =  new JButton();
        b1.setVisible(false);
        this.add(b1);
        b1.addMouseListener(m);

        b2 =  new JButton();
        b2.setVisible(false);
        this.add(b2);
        b2.addMouseListener(m);

        b3 =  new JButton();
        b3.setVisible(false);
        this.add(b3);
        b3.addMouseListener(m);

        try {
            image = ImageIO.read(new File(path+".png"));
            image2 = ImageIO.read(new File(path+"ware.png"));
        } catch (IOException  exception) {

        }

    }


    public Icon resizeIcon(ImageIcon i, int w, int h) {
        Image im = i.getImage();
        Image resIm = im.getScaledInstance (w, h, Image.SCALE_SMOOTH);
        return new ImageIcon(resIm);

    }

    public void refresh(){
        paintComponent(this.getGraphics());
    }

    public void paintComponent(Graphics g) {
        int h = (int) getSize().getHeight();
        int w = (int) getSize().getWidth();

        g.drawImage(image, 0, 0, w, h, null);
        Color color = new Color(0,0,0,0);

        b1.setBounds((int) (w/1.35), (int) (h/2.7), w/5, h/17);
        b1.setBackground(color);
        b1.setIcon(resizeIcon(ng, w/5, h/17));
        b1.setBorderPainted(false);
        b1.setOpaque(false);
        b1.setContentAreaFilled(false);

        b1.setVisible(true);

        b2.setBounds((int) (w/1.35), (int) (h/2.25), w/5, h/17);
        b2.setBackground(color);
        b2.setIcon(resizeIcon(ru, w/5, h/17));
        b2.setBorderPainted(false);
        b2.setOpaque(false);
        b2.setContentAreaFilled(false);

        b2.setVisible(true);

        b3.setBounds((int) (w/1.35), (int) (h/1.93), w/5, h/17);
        b3.setBackground(color);
        b3.setIcon(resizeIcon(ex, w/5, h/17));
        b3.setBorderPainted(false);
        b3.setOpaque(false);
        b3.setContentAreaFilled(false);

        b3.setVisible(true);

        if(isStart) g.drawImage(image2, 0, 0, w, h, null);

        this.setVisible(true);

    }



}
	

