package bobnard.claim.UI;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public abstract class Utils {
    private static String appendPath(String path) {
        return "/" + Menu.skin + "/" + path;
    }

    public static File loadFile(String path) {
        path = appendPath(path);

        if (isInJar()) {
            return null;
        } else {
            try {
                return new File(Objects.requireNonNull(Utils.class.getResource(path)).toURI());
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    public static boolean isInJar() {
        String tmp = Objects.requireNonNull(Utils.class.getResource("Utils.class")).toString();
        return tmp.startsWith("jar");
    }

    public static File[] loadFiles(String path) {
        path = appendPath(path);

        ArrayList<File> res = new ArrayList<>();

        if (isInJar()) {
            return null;
        } else {
            try {
                System.out.println("path = " + path);
                File folder = new File(Objects.requireNonNull(Utils.class.getResource(path)).toURI());
                res.addAll(Arrays.asList(Objects.requireNonNull(folder.listFiles())));
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }

        File[] list = new File[res.size()];
        for (int i = 0; i < res.size(); i++) {
            list[i] = res.get(i);
        }

        return list;
    }

    public static BufferedImage loadImg(String path) {
        path = appendPath(path);
        try {
            return ImageIO.read(Objects.requireNonNull(Utils.class.getResourceAsStream(path)));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static ImageIcon loadIcon(String path) {
        return new ImageIcon(Objects.requireNonNull(loadImg(path)));
    }

    public static InputStream loadInputStream(String path) {
        return Utils.class.getResourceAsStream(path);
    }
}
