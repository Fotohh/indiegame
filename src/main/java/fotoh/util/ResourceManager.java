package fotoh.util;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class ResourceManager {

    private static final Map<String, Image> images = new HashMap<>();

    public static Image getImage(String path) {
        return images.computeIfAbsent(path, ImageLoader::loadImage);
    }

}
