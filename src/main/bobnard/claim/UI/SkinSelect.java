package bobnard.claim.UI;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class SkinSelect extends JComponent {

    JFrame frame;
    Menu menu;
    BufferedImage image;

    JButton b1, b2, b3;
    ImageIcon ul, vl, ba;

    SSMouseListener m = new SSMouseListener(this);

    public SkinSelect(JFrame frame, Menu menu){
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

    public void setImages(){
        try {
            ul = new ImageIcon("src/main/bobnard/claim/UI/resources/"+ Menu.skin +"/menu/skin/logou.png");
            vl = new ImageIcon("src/main/bobnard/claim/UI/resources/"+ Menu.skin +"/menu/skin/logov.png");
            ba = new ImageIcon("src/main/bobnard/claim/UI/resources/"+ Menu.skin +"/menu/skin/back.png");
            image = ImageIO.read(new File("src/main/bobnard/claim/UI/resources/"+ Menu.skin +"/menu/skin/bg2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setButtons(){
        int w = this.frame.getWidth();
        int h = this.frame.getHeight();
        int x = 10;
        int y = 10;

        b1.setBounds(x,y,w/16*4,h/9*2);
        b1.setIcon(resizeIcon(vl, w/16*4, h/9*2));
        b1.setOpaque(false);
        b1.setContentAreaFilled(false);


        x += (w/16 * 4) + ((w/16 * 4)/10);

        b2.setBounds(x,y,w/16*4,h/9*2);
        b2.setIcon(resizeIcon(ul, w/16*4, h/9*2));
        b2.setOpaque(false);
        b2.setContentAreaFilled(false);


        b3.setBounds(10,this.frame.getHeight()-100,w/6,h/19);
        b3.setIcon(resizeIcon(ba, w/6, h/19));
        b3.setOpaque(false);
        b3.setContentAreaFilled(false);
        b3.setBorderPainted(false);
    }

    public void paintComponent(Graphics g) {

        g.drawImage(this.image, 0, 0, getWidth(), getHeight(), null);

        setButtons();


    }

    public Icon resizeIcon(ImageIcon i, int w, int h) {
        if(i == null) return null;
        Image im = i.getImage();
        Image resIm = im.getScaledInstance (w, h, Image.SCALE_SMOOTH);
        return new ImageIcon(resIm);

    }

    public void refresh() {
        setImages();
    }
}
