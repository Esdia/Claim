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
    CFrame frame;
    BufferedImage bg, lg;
    final JButton me;
    final JButton re;

    final MFMouseListener m = new MFMouseListener(this, frame);

    ImageIcon ime, ire;


    public MenuDeFin(CFrame frame) {

        this.frame = frame;


        me = new JButton();
        re = new JButton();


        me.addMouseListener(m);
        re.addMouseListener(m);


        this.add(me);
        this.add(re);

    }

    public Icon resizeIcon(ImageIcon i, int w, int h) {
        if (i == null) return null;
        Image im = i.getImage();
        Image resIm = im.getScaledInstance(w , h, Image.SCALE_SMOOTH);
        return new ImageIcon(resIm);

    }
    public void setgame(Game game){
        this.game = game;
        setImages();
    }
    public void setImages() {

        try {
            if (game.getWinnerID() == 1) {
                if(game.getPlayer(0).isAI()) {
                    bg = ImageIO.read(new File("src/main/bobnard/claim/UI/resources/" + Menu.skin + "/menu_fin/defeatscreen.png"));
                }else {
                    bg = ImageIO.read(new File("src/main/bobnard/claim/UI/resources/" + Menu.skin + "/menu_fin/victoryP1.png"));
                }
            } else {
                if(game.getPlayer(0).isAI()){
                    bg = ImageIO.read(new File("src/main/bobnard/claim/UI/resources/" + Menu.skin + "/menu_fin/victory.png"));

                }else{
                    bg = ImageIO.read(new File("src/main/bobnard/claim/UI/resources/" + Menu.skin + "/menu_fin/victoryP2.png"));

                }
            }
            ime = new ImageIcon("src/main/bobnard/claim/UI/resources/" + Menu.skin + "/menu_fin/menu.png");
            ire = new ImageIcon("src/main/bobnard/claim/UI/resources/" + Menu.skin + "/menu_fin/newgame.png");
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

        int imgHeight = h / 15;

        int widthMenu = (int) (imgHeight * 3.22);
        int widthNG = (int) (imgHeight * 6.36);

        int y = (int) (h / 1.20);
        int xMenu = w / 10 + (widthNG - widthMenu) / 2;
        int xNG = 9 * w / 10 - widthNG;

        this.setButton(me, xMenu, y, widthMenu, imgHeight, ime);

        this.setButton(re, xNG, y, widthNG, imgHeight, ire);


    }

    public void paintComponent(Graphics g) {
        int w = this.frame.getWidth();
        int h = this.frame.getHeight();

        g.drawImage(bg, 0, 0, this.frame.getWidth(), this.frame.getHeight(), null);
        g.drawImage(lg, (int) (w / 2.5), h / 30, w / 5, h / 3, null);

        setButtons();

    }

}
