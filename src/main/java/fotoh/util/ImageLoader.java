package fotoh.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

class ImageLoader {

    protected static Image loadImage(String filename){
        return bufferImage(filename);
    }

    private static BufferedImage bufferImage(String filePath){
        BufferedImage img;
        try{
           img = ImageIO.read(new File(filePath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return img;
    }

}
