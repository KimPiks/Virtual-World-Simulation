package IconManager;

import Settings.Settings;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class IconManager {

    private final HashMap<String, Image> originalIcons;
    private final HashMap<String, Image> scaledIcons;
    private int fieldSize = -1;

    public IconManager() {
        originalIcons = new HashMap<String, Image>();
        scaledIcons = new HashMap<String, Image>();

        try {
            originalIcons.put(Settings.ANTELOPE_IMAGE, ImageIO.read(new File(Settings.ANTELOPE_IMAGE)));
            originalIcons.put(Settings.NIGHTSHADE_BERRIES_IMAGE, ImageIO.read(new File(Settings.NIGHTSHADE_BERRIES_IMAGE)));
            originalIcons.put(Settings.MILKWEED_IMAGE, ImageIO.read(new File(Settings.MILKWEED_IMAGE)));
            originalIcons.put(Settings.FOX_IMAGE, ImageIO.read(new File(Settings.FOX_IMAGE)));
            originalIcons.put(Settings.GRASS_IMAGE, ImageIO.read(new File(Settings.GRASS_IMAGE)));
            originalIcons.put(Settings.GUARANA_IMAGE, ImageIO.read(new File(Settings.GUARANA_IMAGE)));
            originalIcons.put(Settings.HUMAN_IMAGE, ImageIO.read(new File(Settings.HUMAN_IMAGE)));
            originalIcons.put(Settings.SHEEP_IMAGE, ImageIO.read(new File(Settings.SHEEP_IMAGE)));
            originalIcons.put(Settings.SOSNOWSKY_HOGWEED_IMAGE, ImageIO.read(new File(Settings.SOSNOWSKY_HOGWEED_IMAGE)));
            originalIcons.put(Settings.TURTLE_IMAGE, ImageIO.read(new File(Settings.TURTLE_IMAGE)));
            originalIcons.put(Settings.WOLF_IMAGE, ImageIO.read(new File(Settings.WOLF_IMAGE)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Image getIcon(String iconPath, int fieldSize) {
        if (this.fieldSize != fieldSize) {
            this.fieldSize = fieldSize;
            originalIcons.forEach((k, v) -> scaledIcons.put(k, v.getScaledInstance(fieldSize, fieldSize, Image.SCALE_SMOOTH)));
        }
        return scaledIcons.get(iconPath);
    }
}
