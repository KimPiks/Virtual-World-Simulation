package Plant;

import Organism.*;
import Settings.Settings;
import World.World;
import Field.Field;

import java.util.Random;

public abstract class Plant extends Organism {

    public Plant(OrganismData data, World world, Field field) {
        super(data, world, field);
    }

    @Override
    public void action() {
        Random rand = new Random();
        int spreadChance = rand.nextInt(100);

        if (spreadChance >= Settings.PLANT_REPRODUCE_CHANCE) return;

        // Get free neighbour field
        Field field = this.world.getFreeNeighbourField(this.currentField);
        // Plant
        if (field == null) return;

        this.reproduce(field);
    }

    @Override
    public void collision(Organism attacker) {
        System.out.println("Collision");
    }

    public abstract void reproduce(Field field);

}
