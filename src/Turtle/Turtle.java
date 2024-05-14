package Turtle;

import Animal.Animal;
import Field.Field;
import Organism.Organism;
import Organism.OrganismData;
import Settings.Settings;
import World.World;

import java.util.Random;

public class Turtle extends Animal {

    public Turtle(World world, Field field, int parent1Id, int parent2Id) {
        super(new OrganismData(world.getNextOrganismId(), Settings.TURTLE_INITIATIVE, Settings.TURTLE_STRENGTH, Settings.TURTLE_IMAGE, parent1Id, parent2Id), world, field);
    }

    @Override
    public void action() {
        Random rand = new Random();
        int moveChance = rand.nextInt(4);
        if (moveChance != 0) return;
        super.action();
    }

    @Override
    public String getType() {
        return "Turtle";
    }

    @Override
    public void reproduce(Field field, int parent1Id, int parent2Id) {
        Organism turtle = new Turtle(this.world, field, parent1Id, parent2Id);
        this.world.addOrganism(turtle);
    }

    @Override
    public boolean deflectedAttack(Organism attacker) {
        if (attacker.getData().getStrength() < 5) {
            Animal animal = (Animal) attacker;
            animal.moveOrganismToPreviousField();

            this.world.addLog(this.getType() + " (" + this.currentField.getNumber() + ") scared away " + attacker.getType());
            return true;
        }

        return super.deflectedAttack(attacker);
    }
}
