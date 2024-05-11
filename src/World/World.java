package World;

import Window.Window;

public abstract class World {

    protected Window window;
    protected WorldSettings worldSettings;

    public World(Window window, WorldSettings worldSettings) {
        this.window = window;
        this.worldSettings = worldSettings;
    }

    public static World initializeWorld(WorldSettings worldSettings, Window window) {
        if (worldSettings.worldType() == WorldType.HEXAGONAL) {
            return new HexWorld(window, worldSettings);
        }
        return new RectangleWorld(window, worldSettings);
    }

    public abstract void placeFields();
}
