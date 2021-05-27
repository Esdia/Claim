package bobnard.claim.UI;


import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class Menu extends JComponent {

    BufferedImage image;
    BufferedImage image2;

    Boolean isStart;
    Boolean isSs;

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

    ButtonsMouse m = new ButtonsMouse(this);

    public static String skin;

    SkinSelect panel;

    public Menu(JFrame frame) {
        skin = "Vanilla";
        this.isStart = false;
        Menu.frame = frame;
        this. isSs = false;

        panel = new SkinSelect(frame, this);
        this.add(panel);

        Audio.setFiles();
        Audio.playBGM(0);

        creatButtons();
        setImages();

    }

    public void changeSkin(String name){
        skin = name;
        setImages();
        refresh();
        Audio.reload();
    }

    private void setImages() {

        frame.setIconImage(new ImageIcon("src/main/bobnard/claim/UI/resources/"+skin+"/Icon/icon.png").getImage());
        this.path = "src/main/bobnard/claim/UI/resources/"+skin+"/menu/";

        try {
                ng =  new ImageIcon(path+"new_game.png");
                ru =  new ImageIcon(path+"rules.png");
                cf =  new ImageIcon(path+"config.png");
                sk =  new ImageIcon(path+"skin.png");
                ex =  new ImageIcon(path+"exit.png");
                pvp = new ImageIcon(path+"pvp.png");
                pve = new ImageIcon(path+"pve.png");

                image = ImageIO.read(new File(path + "menu.png"));
                if(skin.equals("Umineko")) image2 = ImageIO.read(new File(path + "ware.png"));

        }catch(IOException e){
                e.printStackTrace();
            }

    }



    private void creatButtons(){

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

            b4 =  new JButton();
            b4.setVisible(false);
            this.add(b4);
            b4.addMouseListener(m);

            b5 =  new JButton();
            b5.setVisible(false);
            this.add(b5);
            b5.addMouseListener(m);
            
            b1a = new JButton();
            b1a.setVisible(false);
            this.add(b1a);
            b1a.addMouseListener(m);
            
            b1b = new JButton();
            b1b.setVisible(false);
            this.add(b1b);
            b1b.addMouseListener(m);
    }


    public Icon resizeIcon(ImageIcon i, int w, int h) {
        if(i == null) return null;
        Image im = i.getImage();
        Image resIm = im.getScaledInstance (w, h, Image.SCALE_SMOOTH);
        return new ImageIcon(resIm);

    }

    public void refresh(){
        paintComponent(this.getGraphics());
    }

    private void setButtons(int h, int w){

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
            b3.setIcon(resizeIcon(cf, w/5, h/17));
            b3.setBorderPainted(false);
            b3.setOpaque(false);
            b3.setContentAreaFilled(false);

            b3.setVisible(true);

            b4.setBounds((int) (w/1.35), (int) (h/1.69), w/5, h/17);
            b4.setBackground(color);
            b4.setIcon(resizeIcon(sk, w/5, h/17));
            b4.setBorderPainted(false);
            b4.setOpaque(false);
            b4.setContentAreaFilled(false);

            b4.setVisible(true);

            b5.setBounds((int) (w/1.35), (int) (h/1.50), w/5, h/17);
            b5.setBackground(color);
            b5.setIcon(resizeIcon(ex, w/5, h/17));
            b5.setBorderPainted(false);
            b5.setOpaque(false);
            b5.setContentAreaFilled(false);

            b5.setVisible(true);
            
            b1a.setBounds(((int) ((w/1.35) - w/(5.25) )), (int) (h/3), w/5, h/17);
            b1a.setBackground(color);
            b1a.setIcon(resizeIcon(pvp, w/7, h/22));
            b1a.setBorderPainted(false);
            b1a.setOpaque(false);
            b1a.setContentAreaFilled(false);


            b1b.setBounds(((int) ((w/1.35) - w/(5.25) )), (int) (h/2.5), w/5, h/17);
            b1b.setBackground(color);
            b1b.setIcon(resizeIcon(pve, w/7, h/22));
            b1b.setBorderPainted(false);
            b1b.setOpaque(false);
            b1b.setContentAreaFilled(false);



    }

    public void paintComponent(Graphics g) {
        int h = (int) getSize().getHeight();
        int w = (int) getSize().getWidth();

        g.drawImage(image, 0, 0, w, h, null);

        setButtons(h,w);

        if(isSs){

            panel.setBounds(0,0,w,h);
            panel.setOpaque(false);

            if(!panel.isVisible()) {
                panel.setVisible(true);
            }

            b1.setVisible(false);
            b2.setVisible(false);
            b3.setVisible(false);
            b4.setVisible(false);
            b5.setVisible(false);


        }else{
            panel.setVisible(false);
        }

        if(isStart) g.drawImage(image2, 0, 0, w, h, null);

        this.setVisible(true);

    }


}
	

