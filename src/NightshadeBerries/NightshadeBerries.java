package NightshadeBerries;

import Field.Field;
import Guarana.Guarana;
import Organism.Organism;
import Organism.OrganismData;
import Plant.Plant;
import Settings.Settings;
import World.World;

public class NightshadeBerries extends Plant {

    public NightshadeBerries(World world, Field field) {
        super(new OrganismData(world.getNextOrganismId(), Settings.NIGHTSHADE_BERRIES_INITIATIVE, Settings.NIGHTSHADE_BERRIES_STRENGTH, Settings.NIGHTSHADE_BERRIES_IMAGE), world, field);
    }

    @Override
    public void action() {
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
        return "NightshadeBerries";
    }

    @Override
    public void reproduce(Field field) {
        Organism nightshadeBerries = new NightshadeBerries(this.world, field);
        this.world.addOrganism(nightshadeBerries);
        System.out.println(this.getType() + " reproduced (" + field.getNumber() + ")");
    }

}