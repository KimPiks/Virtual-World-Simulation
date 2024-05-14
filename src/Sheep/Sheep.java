package Sheep;

import Animal.Animal;
import Field.Field;
import Organism.Organism;
import Organism.OrganismData;
import Settings.Settings;
import World.World;

public class Sheep extends Animal {

    public Sheep(World world, Field field, int parent1Id, int parent2Id) {
        super(new OrganismData(world.getNextOrganismId(), Settings.SHEEP_INITIATIVE, Settings.SHEEP_STRENGTH, Settings.SHEEP_IMAGE, parent1Id, parent2Id), world, field);
    }

    @Override
    public String getType() {
        return "Sheep";
    }

    @Override
    public void reproduce(Field field, int parent1Id, int parent2Id) {
        Organism sheep = new Sheep(this.world, field, parent1Id, parent2Id);
        this.world.addOrganism(sheep);
    }

}
