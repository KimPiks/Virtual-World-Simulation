package Antelope;

import Animal.Animal;
import Field.Field;
import Organism.Organism;
import Organism.OrganismData;
import Settings.Settings;
import World.World;

import java.util.Random;

public class Antelope extends Animal {

    public Antelope(World world, Field field, int parent1Id, int parent2Id) {
        super(new OrganismData(world.getNextOrganismId(), Settings.ANTELOPE_INITIATIVE, Settings.ANTELOPE_STRENGTH, Settings.ANTELOPE_IMAGE, parent1Id, parent2Id), world, field);
    }

    @Override
    public void action() {
        super.action();
        super.action();
    }

    @Override
    public String getType() {
        return "Antelope";
    }

    @Override
    public void reproduce(Field field, int parent1Id, int parent2Id) {
        Organism antelope = new Antelope(this.world, field, parent1Id, parent2Id);
        this.world.addOrganism(antelope);
    }

    @Override
    public boolean deflectedAttack(Organism attacker) {
        Random rand = new Random();
        int deflectionChance = rand.nextInt(2);
        if (deflectionChance == 0) {
            Field field = this.world.getFreeNeighbourField(this.currentField);
            if (field != null) {
                this.currentField = field;
            }
            return true;
        }

        return super.deflectedAttack(attacker);
    }
}
