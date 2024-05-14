package Fox;

import Animal.Animal;
import Field.Field;
import Organism.Organism;
import Organism.OrganismData;
import Settings.Settings;
import World.World;

public class Fox extends Animal {

    public Fox(World world, Field field, int parent1Id, int parent2Id) {
        super(new OrganismData(world.getNextOrganismId(), Settings.FOX_INITIATIVE, Settings.FOX_STRENGTH, Settings.FOX_IMAGE, parent1Id, parent2Id), world, field);
    }

    @Override
    public void action() {
        Field field = this.world.getFreeNeighbourField(this.currentField);

        this.lastField = this.currentField;
        this.currentField.setOrganism(null);
        this.currentField = field;
        this.currentField.setOrganism(this);
    }

    @Override
    public String getType() {
        return "Fox";
    }

    @Override
    public void reproduce(Field field, int parent1Id, int parent2Id) {
        Organism fox = new Fox(this.world, field, parent1Id, parent2Id);
        this.world.addOrganism(fox);
    }

}
