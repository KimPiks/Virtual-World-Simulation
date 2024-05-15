package OrganismManager;

import Fox.Fox;
import Grass.Grass;
import Guarana.Guarana;
import Milkweed.Milkweed;
import NightshadeBerries.NightshadeBerries;
import Organism.Organism;
import Antelope.Antelope;
import Wolf.Wolf;

import javax.swing.*;
import java.util.Arrays;

import Sheep.Sheep;
import SosnowskyHogweed.SosnowskyHogweed;
import Turtle.Turtle;
import World.World;
import Field.Field;

public class OrganismManager {

    public static OrganismType openOrganismSelection(JFrame frame) {
        String[] options = Arrays.asList(OrganismType.values())
                            .stream()
                            .map(f -> f.toString())
                            .toArray(String[]::new);

        JComboBox optionList = new JComboBox(options);
        JOptionPane.showMessageDialog(null, optionList, "Select organism type", JOptionPane.QUESTION_MESSAGE);

        if (optionList.getSelectedItem() == null) return null;
        return OrganismType.valueOf((String) optionList.getSelectedItem());
    }

    public static Organism createOrganism(OrganismType type, World world, Field field) {
        switch (type) {
            case OrganismType.Antelope:
                return new Antelope(world, field, -1, -1);
            case OrganismType.Fox:
                return new Fox(world, field, -1, -1);
            case OrganismType.Grass:
                return new Grass(world, field);
            case OrganismType.Guarana:
                return new Guarana(world, field);
            case OrganismType.Milkweed:
                return new Milkweed(world, field);
            case OrganismType.NightshadeBerries:
                return new NightshadeBerries(world, field);
            case OrganismType.Sheep:
                return new Sheep(world, field, -1, -1);
            case OrganismType.SosnowskyHogweed:
                return new SosnowskyHogweed(world, field);
            case OrganismType.Turtle:
                return new Turtle(world, field, -1, -1);
            case OrganismType.Wolf:
                return new Wolf(world, field, -1, -1);
            default:
                return null;
        }
    }
}
