package Grass;

import Field.Field;
import Milkweed.Milkweed;
import Organism.Organism;
import Organism.OrganismData;
import Plant.Plant;
import Settings.Settings;
import World.World;

public class Grass extends Plant {

    public Grass(World world, Field field) {
        super(new OrganismData(world.getNextOrganismId(), Settings.GRASS_INITIATIVE, Settings.GRASS_STRENGTH, Settings.GRASS_IMAGE), world, field);
    }

    @Override
    public void action() {
        super.action();
    }

    @Override
    public void collision(Organism attacker) {
        super.collision(attacker);
    }

    @Override
    public String getType() {
        return "Grass";
    }

    @Override
    public void reproduce(Field field) {
        Organism grass = new Grass(this.world, field);
        this.world.addOrganism(grass);
        System.out.println(this.getType() + " reproduced (" + field.getNumber() + ")");
    }

}
