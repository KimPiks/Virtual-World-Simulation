package Animal;

import Field.Field;
import Organism.*;
import World.World;

public abstract class Animal extends Organism {

    protected Field lastField;

    public Animal(OrganismData data, World world, Field field) {
        super(data, world, field);
        this.lastField = field;
    }

    @Override
    public void action() {
        Field field = this.world.getNeighbourField(this.currentField);

        this.lastField = this.currentField;
        this.currentField.setOrganism(null);
        this.currentField = field;
        this.currentField.setOrganism(this);
    }

    @Override
    public void collision(Organism organism) {
        if (this.getType().equals(organism.getType())) {
            this.reproduce(organism);
        } else if (organism.getData().getInitiative() == 0) {
            this.eatPlant(organism);
        } else {
            this.fight(organism);
        }
    }

    public void moveOrganismToPreviousField() {
        Organism otherOrganism = this.world.getOrganismAtField(this.currentField, this);
        if (otherOrganism != null) {
            this.currentField.setOrganism(otherOrganism);
        }

        this.currentField = lastField;
        this.currentField.setOrganism(this);
    }

    private void reproduce(Organism organism) {
        Animal animal = (Animal) organism;
        Field childField = this.getCurrentField();

        this.moveOrganismToPreviousField();
        animal.moveOrganismToPreviousField();

        if (this.hasSameParents(organism) || this.isParent(organism) || animal.isParent(this)) return;

        this.reproduce(childField, this.getData().getId(), organism.getData().getId());
        this.getWorld().addLog(this.getType() + " (" + this.getCurrentField().getNumber() + ", " + organism.getCurrentField().getNumber() + ") reproduced (" + childField.getNumber() + ")");
     }

    private void eatPlant(Organism organism) {
        this.getWorld().addLog(this.getType() + " (" + this.getCurrentField().getNumber() + ") ate " + organism.getType());
        organism.collision(this);
        organism.getData().setAlive(false);
    }

    private void fight(Organism organism) {
        this.world.addLog(this.getType() + " (" + this.getCurrentField().getNumber() + ") attacked " + organism.getType() + " (" + organism.getCurrentField().getNumber() + ")");

        Animal animal = (Animal) organism;
        if (this.getData().getStrength() >= organism.getData().getStrength()) {
            if (animal.deflectedAttack(this)) {
                this.world.addLog(animal.getType() + " (" + animal.getCurrentField().getNumber() + ") deflected attack");
            } else {
                this.world.addLog(this.getType() + " killed " + animal.getType() + " (" + animal.getCurrentField().getNumber() + ")");
                animal.getData().setAlive(false);
            }
        } else {
            this.world.addLog(animal.getType() + " killed " + this.getType() + " (" + this.getCurrentField().getNumber() + ")");
            this.getData().setAlive(false);
        }
    }

    private boolean hasSameParents(Organism organism) {
        if (this.getData().getParent1Id() == -1 || organism.getData().getParent1Id() == -1) return false;
        return this.getData().getParent1Id() == organism.getData().getParent1Id() || this.getData().getParent2Id() == organism.getData().getParent2Id() || this.getData().getParent1Id() == organism.getData().getParent2Id() || this.getData().getParent2Id() == organism.getData().getParent1Id();
    }

    public boolean isParent(Organism organism) {
        return this.getData().getId() == organism.getData().getParent1Id() || this.getData().getId() == organism.getData().getParent2Id();
    }

    public boolean deflectedAttack(Organism attacker) {
        return attacker.getData().getStrength() < this.getData().getStrength();
    }

    public abstract void reproduce(Field field, int parent1Id, int parent2Id);
}
