package World;

import Field.Field;
import Field.SquareField;
import IconManager.IconManager;
import Window.Window;

public class RectangleWorld extends World {

    private final IconManager iconManager;

    public RectangleWorld(Window window, WorldSettings worldSettings) {
        super(window, worldSettings);
        this.iconManager = new IconManager();
    }

    @Override
    public void placeFields() {
        int fieldNumber = 1;

        for (int i = 1; i <= this.worldSettings.width(); i++) {
            for (int j = 1; j <= this.worldSettings.height(); j++) {
                Field field = new SquareField(fieldNumber++, this.iconManager, this);
                this.window.addField(field);
            }
        }
    }
}
