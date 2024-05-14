import Field.Field;
import Fox.Fox;
import Grass.Grass;
import Guarana.Guarana;
import Logging.Logging;
import Milkweed.Milkweed;
import NightshadeBerries.NightshadeBerries;
import Organism.*;
import Sheep.Sheep;
import SosnowskyHogweed.SosnowskyHogweed;
import Turtle.Turtle;
import Window.Window;
import Wolf.Wolf;
import Antelope.Antelope;
import World.*;
import World.WorldSettings;
import World.WorldType;

public class Main {

    public static void main(String[] args) {
        Window window = new Window();
        WorldSettings worldSettings = new WorldSettings(WorldType.HEXAGONAL, 10, 10);

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

        Organism grass = new Grass(world, window.getFields().get(1));
        grass.getData().setBorn(true);
        world.addOrganism(grass);

        Organism guarana = new Guarana(world, window.getFields().get(3));
        guarana.getData().setBorn(true);
        world.addOrganism(guarana);

        Organism nightshadeBerries = new NightshadeBerries(world, window.getFields().get(5));
        nightshadeBerries.getData().setBorn(true);
        world.addOrganism(nightshadeBerries);

        Organism sosnowskyHogweed = new SosnowskyHogweed(world, window.getFields().get(7));
        sosnowskyHogweed.getData().setBorn(true);
        world.addOrganism(sosnowskyHogweed);

        // Animals
//        Organism wolf = new Wolf(world, window.getFields().get(20), -1, -1);
//        wolf.getData().setBorn(true);
//        world.addOrganism(wolf);
//
//        Organism wolf2 = new Wolf(world, window.getFields().get(66), -1, -1);
//        wolf2.getData().setBorn(true);
//        world.addOrganism(wolf2);

        Organism sheep1 = new Sheep(world, window.getFields().get(30), -1, -1);
        sheep1.getData().setBorn(true);
        world.addOrganism(sheep1);

        Organism fox1 = new Fox(world, window.getFields().get(40), -1, -1);
        fox1.getData().setBorn(true);
        world.addOrganism(fox1);

        Organism turtle1 = new Turtle(world, window.getFields().get(50), -1, -1);
        turtle1.getData().setBorn(true);
        world.addOrganism(turtle1);

        Organism antelope1 = new Antelope(world, window.getFields().get(60), -1, -1);
        antelope1.getData().setBorn(true);
        world.addOrganism(antelope1);
    }
}