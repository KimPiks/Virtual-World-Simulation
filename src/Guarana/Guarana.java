package Guarana;

import Field.Field;
import Organism.Organism;
import Organism.OrganismData;
import Plant.Plant;
import Settings.Settings;
import World.World;

public class Guarana extends Plant {

    public Guarana(World world, Field field) {
        super(new OrganismData(world.getNextOrganismId(), Settings.GUARANA_INITIATIVE, Settings.GUARANA_STRENGTH, Settings.GUARANA_IMAGE, -1, -1), world, field);
    }

    @Override
    public void action() {
        super.action();
    }

    @Override
    public void collision(Organism attacker) {
        if (attacker.isAnimal()) {
            attacker.getData().setStrength(attacker.getData().getStrength() + 3);
            this.world.addLog(this.getType() + " increased " + attacker.getType() + " strength by 3");
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
        this.world.addLog(this.getType() + " reproduced (" + field.getNumber() + ")");
    }

}
