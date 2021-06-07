package bobnard.claim.UI;
import bobnard.claim.model.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;



public class MenuDeFin extends JComponent {

    Game game ;
    public static boolean isConfig = false;
    final CFrame frame;
    BufferedImage bg, lg;
    Config c;
    JButton me, re ;

    MFMouseListener m = new MFMouseListener(this);

    ImageIcon ime, ire ;


    public MenuDeFin(CFrame frame, Game game)  {
        this.game = game ;
        this.frame = frame;

        setImages();

        me = new JButton("Menu");
        re = new JButton("Rejouer");



        me.addMouseListener(m);
        re.addMouseListener(m);




        this.add(me);
        this.add(re);
        
    }

    public Icon resizeIcon(ImageIcon i, int w, int h) {
        if (i == null) return null;
        Image im = i.getImage();
        Image resIm = im.getScaledInstance(w-25, h, Image.SCALE_SMOOTH);
        return new ImageIcon(resIm);

    }

    public void setImages() {

        try {
            System.out.println("ici");
            if (game.getWinnerID() ==  1 ) {
                bg = ImageIO.read(new File("src/main/bobnard/claim/UI/resources/" + Menu.skin + "/menu_fin/victoryP1.png"));
            }
            else{
                bg = ImageIO.read(new File("src/main/bobnard/claim/UI/resources/" + Menu.skin + "/menu_fin/victoryP2.png"));}
            ime = new ImageIcon("src/main/bobnard/claim/UI/resources/" + Menu.skin + "/menu_fin/.png");
            ire = new ImageIcon("src/main/bobnard/claim/UI/resources/" + Menu.skin + "/menu_fin/.png");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void setButton(JButton button, int x, int y, int w, int h, ImageIcon icon) {

        button.setIcon(resizeIcon(icon, w, h));
        button.setBounds(x, y, w, h);
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
    }


    public void setButtons() {

        int w = this.frame.getWidth();
        int h = this.frame.getHeight();


        this.setButton(me, (int) (w/2.70), (int) (h/1.20), w / 7, h / 15, ime);

        this.setButton(re, (int) (w/1.90), (int) (h/1.20), w / 10, h / 12, ire);




    }

    private void draw(JComponent component, boolean cond) {
        if (cond) {
            component.setBounds(0, 0, getWidth(), getHeight());
            component.setOpaque(false);

            if (!component.isVisible()) {
                component.setVisible(true);
            }

            me.setVisible(false);
            re.setVisible(false);

        } else {
            component.setVisible(false);
            me.setVisible(true);
            re.setVisible(true);
        }
    }

    public void paintComponent(Graphics g) {
        int w = this.frame.getWidth();
        int h = this.frame.getHeight();

        g.drawImage(bg, 0, 0, this.frame.getWidth(), this.frame.getHeight(), null);
        g.drawImage(lg, (int) (w/2.5), h/30, w/5, h/3, null);

        setButtons();

    }

}
