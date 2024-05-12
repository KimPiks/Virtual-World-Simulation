import Field.Field;
import Grass.Grass;
import Guarana.Guarana;
import Milkweed.Milkweed;
import NightshadeBerries.NightshadeBerries;
import Organism.*;
import SosnowskyHogweed.SosnowskyHogweed;
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

        window.setWorld(world);
        window.setFieldsNeighbours();
        window.setWindowSize(worldSettings.worldType(), worldSettings.width(), worldSettings.height());
        window.displayFields();
        window.setVisible(true);

        window.addKeyListener();

        // Organisms
        Organism milkweed = new Milkweed(world, window.getFields().getFirst());
        milkweed.getData().setBorn(true);
        world.addOrganism(milkweed);

        Organism grass = new Grass(world, window.getFields().get(9));
        grass.getData().setBorn(true);
        world.addOrganism(grass);

        Organism guarana = new Guarana(world, window.getFields().get(39));
        guarana.getData().setBorn(true);
        world.addOrganism(guarana);

        Organism nightshadeBerries = new NightshadeBerries(world, window.getFields().get(100));
        nightshadeBerries.getData().setBorn(true);
        world.addOrganism(nightshadeBerries);

        Organism sosnowskyHogweed = new SosnowskyHogweed(world, window.getFields().get(50));
        sosnowskyHogweed.getData().setBorn(true);
        world.addOrganism(sosnowskyHogweed);

    }
}