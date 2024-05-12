package SosnowskyHogweed;

import Field.Field;
import NightshadeBerries.NightshadeBerries;
import Organism.Organism;
import Organism.OrganismData;
import Plant.Plant;
import Settings.Settings;
import World.World;

public class SosnowskyHogweed extends Plant {

    public SosnowskyHogweed(World world, Field field) {
        super(new OrganismData(world.getNextOrganismId(), Settings.SOSNOWSKY_HOGWEED_INITIATIVE, Settings.SOSNOWSKY_HOGWEED_STRENGTH, Settings.SOSNOWSKY_HOGWEED_IMAGE), world, field);
    }

    @Override
    public void action() {
        for (Field field : this.currentField.getNeighbours()) {
            if (field.getOrganism() != null && field.getOrganism().isAnimal()) {
                field.getOrganism().getData().setAlive(false);
                System.out.println(this.getType() + " killed " + field.getOrganism().getType());
            }
        }

        super.action();
    }

    @Override
    public void collision(Organism attacker) {
        if (attacker.isAnimal()) {
            attacker.getData().setAlive(false);
        }
    }

    @Override
    public String getType() {
        return "SosnowskyHogweed";
    }

    @Override
    public void reproduce(Field field) {
        Organism sosnowskyHogweed = new SosnowskyHogweed(this.world, field);
        this.world.addOrganism(sosnowskyHogweed);
        System.out.println(this.getType() + " reproduced (" + field.getNumber() + ")");
    }

}
