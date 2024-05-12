package World;

import Field.Field;
import Field.HexField;
import Window.Window;

public class HexWorld extends World {

    public HexWorld(Window window, WorldSettings worldSettings) {
        super(window, worldSettings);
    }

    @Override
    public void placeFields() {
        int fieldNumber = 1;

        for (int i = 1; i <= this.worldSettings.width(); i++) {
            for (int j = 1; j <= this.worldSettings.height(); j++) {
                int startX = this.window.FIELD_SIZE * (j-1) / 2;
                int x = this.window.WINDOW_OFFSET + startX + i * this.window.FIELD_SIZE;
                int y = this.window.WINDOW_OFFSET + (int)((double)j * (double)this.window.FIELD_SIZE / 20 * 17);

                Field field = new HexField(fieldNumber++, x, y, this.window.FIELD_SIZE);
                this.window.addField(field);
            }
        }
    }

}
