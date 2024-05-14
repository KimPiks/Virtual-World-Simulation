package Wolf;

import Animal.Animal;
import Field.Field;
import Organism.*;
import Settings.Settings;
import World.World;

public class Wolf extends Animal {

    public Wolf(World world, Field field, int parent1Id, int parent2Id) {
        super(new OrganismData(world.getNextOrganismId(), Settings.WOLF_INITIATIVE, Settings.WOLF_STRENGTH, Settings.WOLF_IMAGE, parent1Id, parent2Id), world, field);
    }

    @Override
    public String getType() {
        return "Wolf";
    }

    @Override
    public void reproduce(Field field, int parent1Id, int parent2Id) {
        Organism wolf = new Wolf(this.world, field, parent1Id, parent2Id);
        this.world.addOrganism(wolf);
    }
}
