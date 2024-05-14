package World;

import Field.Field;
import Field.SquareField;
import IconManager.IconManager;
import Window.Window;

public class RectangleWorld extends World {

    private final IconManager iconManager;

    public RectangleWorld(Window window, WorldSettings worldSettings) {
        super(window, worldSettings);
        this.iconManager = new IconManager(this.window.FIELD_SIZE);
    }

    @Override
    public void placeFields() {
        int fieldNumber = 1;

        for (int i = 1; i <= this.worldSettings.width(); i++) {
            for (int j = 1; j <= this.worldSettings.height(); j++) {
                int x = this.window.WINDOW_OFFSET + (i-1) * this.window.FIELD_SIZE;
                int y = this.window.WINDOW_OFFSET + (j-1) * this.window.FIELD_SIZE;

                Field field = new SquareField(fieldNumber++, x, y, this.window.FIELD_SIZE, this.iconManager);
                this.window.addField(field);
            }
        }
    }
}
