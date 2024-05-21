import Human.Human;
import Organism.*;
import OrganismManager.OrganismManager;
import Window.Window;
import Window.MenuOption;
import World.*;
import World.WorldSettings;
import Saving.Saving;

import java.awt.*;
import java.util.Random;

public class Main {

    public static void main(String[] args) {
        Window window = new Window();
        MenuOption menuOption = window.askForMenuOption();

        if (menuOption == MenuOption.NEW_SIMULATION) {
            WorldSettings worldSettings = window.askForWorldSetting();

            World world = World.initializeWorld(worldSettings, window);
            world.placeFields();

            window.setWorld(world);
            window.addPanels();
            window.displayFields();

            window.getFrame().setMinimumSize(new Dimension(950, 600));
            window.setVisible(true);
            window.setFieldsNeighbours();

            window.addKeyListener();

            int humanField = new Random().nextInt(world.getWorldSettings().width() * world.getWorldSettings().height()) + 1;
            Organism human = new Human(world, world.getField(humanField), -1, -1);
            world.addOrganism(human);

            OrganismManager.generateOrganisms(world);
        } else if (menuOption == MenuOption.LOAD_SIMULATION) {
            String saveFileName = window.askForSaveFileName();
            Saving saving = new Saving(window, window.getFrame());
            World world = saving.load(saveFileName);

            window.setWorld(world);
            window.addPanels();
            window.displayFields();
            window.getFrame().setMinimumSize(new Dimension(950, 600));
            window.setVisible(true);
            window.setFieldsNeighbours();

            for (Organism organism : world.getOrganisms()) {
                organism.getCurrentField().setOrganism(organism);
            }

            window.addKeyListener();
        }
    }
}