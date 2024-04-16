package fotoh.util;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class ImageLoader {

    public static Image loadImage(String filename){
        return bufferImage(filename);
    }

    private static BufferedImage bufferImage(String filename){

        BufferedImage img;

        try{
           img = ImageIO.read(new File(filename));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return img;
    }

}
