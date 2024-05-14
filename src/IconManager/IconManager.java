package IconManager;

import Settings.Settings;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class IconManager {

    private final HashMap<String, Image> icons;

    public IconManager(int fieldSize) {
        icons = new HashMap<String, Image>();

        try {
            icons.put(Settings.ANTELOPE_IMAGE, ImageIO.read(new File(Settings.ANTELOPE_IMAGE)).getScaledInstance(fieldSize, fieldSize, Image.SCALE_SMOOTH));
            icons.put(Settings.NIGHTSHADE_BERRIES_IMAGE, ImageIO.read(new File(Settings.NIGHTSHADE_BERRIES_IMAGE)).getScaledInstance(fieldSize, fieldSize, Image.SCALE_SMOOTH));
            icons.put(Settings.MILKWEED_IMAGE, ImageIO.read(new File(Settings.MILKWEED_IMAGE)).getScaledInstance(fieldSize, fieldSize, Image.SCALE_SMOOTH));
            icons.put(Settings.FOX_IMAGE, ImageIO.read(new File(Settings.FOX_IMAGE)).getScaledInstance(fieldSize, fieldSize, Image.SCALE_SMOOTH));
            icons.put(Settings.GRASS_IMAGE, ImageIO.read(new File(Settings.GRASS_IMAGE)).getScaledInstance(fieldSize, fieldSize, Image.SCALE_SMOOTH));
            icons.put(Settings.GUARANA_IMAGE, ImageIO.read(new File(Settings.GUARANA_IMAGE)).getScaledInstance(fieldSize, fieldSize, Image.SCALE_SMOOTH));
            icons.put(Settings.HUMAN_IMAGE, ImageIO.read(new File(Settings.HUMAN_IMAGE)).getScaledInstance(fieldSize, fieldSize, Image.SCALE_SMOOTH));
            icons.put(Settings.SHEEP_IMAGE, ImageIO.read(new File(Settings.SHEEP_IMAGE)).getScaledInstance(fieldSize, fieldSize, Image.SCALE_SMOOTH));
            icons.put(Settings.SOSNOWSKY_HOGWEED_IMAGE, ImageIO.read(new File(Settings.SOSNOWSKY_HOGWEED_IMAGE)).getScaledInstance(fieldSize, fieldSize, Image.SCALE_SMOOTH));
            icons.put(Settings.TURTLE_IMAGE, ImageIO.read(new File(Settings.TURTLE_IMAGE)).getScaledInstance(fieldSize, fieldSize, Image.SCALE_SMOOTH));
            icons.put(Settings.WOLF_IMAGE, ImageIO.read(new File(Settings.WOLF_IMAGE)).getScaledInstance(fieldSize, fieldSize, Image.SCALE_SMOOTH));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Image getIcon(String iconPath) {
        return icons.get(iconPath);
    }
}
