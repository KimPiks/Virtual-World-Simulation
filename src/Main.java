import Window.Window;
import World.*;
import World.WorldSettings;
import World.WorldType;

public class Main {

    public static void main(String[] args) {
        Window window = new Window();
        WorldSettings worldSettings = new WorldSettings(WorldType.HEXAGONAL, 20, 10);

        World world = World.initializeWorld(worldSettings, window);
        world.placeFields();

        window.setWindowSize(worldSettings.worldType(), worldSettings.width(), worldSettings.height());
        window.displayFields();
        window.setVisible(true);
    }
}