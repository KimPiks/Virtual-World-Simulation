package Guarana;

import Field.Field;
import Organism.Organism;
import Organism.OrganismData;
import Plant.Plant;
import Settings.Settings;
import World.World;

public class Guarana extends Plant {

    public Guarana(World world, Field field) {
        super(new OrganismData(world.getNextOrganismId(), Settings.GUARANA_INITIATIVE, Settings.GUARANA_STRENGTH, Settings.GUARANA_IMAGE), world, field);
    }

    @Override
    public void action() {
        super.action();
    }

    @Override
    public void collision(Organism attacker) {
        if (attacker.isAnimal()) {
            attacker.getData().setStrength(attacker.getData().getStrength() + 3);
        }
    }

    @Override
    public String getType() {
        return "Guarana";
    }

    @Override
    public void reproduce(Field field) {
        Organism guarana = new Guarana(this.world, field);
        this.world.addOrganism(guarana);
        System.out.println(this.getType() + " reproduced (" + field.getNumber() + ")");
    }

}
