package Milkweed;

import Organism.*;
import World.World;
import Plant.Plant;
import Field.Field;
import Settings.Settings;

public class Milkweed extends Plant {

    public Milkweed(World world, Field field) {
        super(new OrganismData(world.getNextOrganismId(), Settings.MILKWEED_INITIATIVE, Settings.MILKWEED_STRENGTH, Settings.MILKWEED_IMAGE), world, field);
    }

    @Override
    public void action() {
        super.action();
        super.action();
        super.action();
    }

    @Override
    public void collision(Organism attacker) {
        super.collision(attacker);
    }

    @Override
    public String getType() {
        return "Milkweed";
    }

    @Override
    public void reproduce(Field field) {
        Organism milkweed = new Milkweed(this.world, field);
        this.world.addOrganism(milkweed);
        System.out.println(this.getType() + " reproduced (" + field.getNumber() + ")");
    }
}
