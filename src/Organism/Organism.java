package Organism;

import Field.Field;
import World.World;

public abstract class Organism {

    protected OrganismData data;
    protected World world;
    protected Field currentField;

    public Organism(OrganismData data, World world, Field field) {
        this.data = data;
        this.world = world;
        this.currentField = field;
    }

    public OrganismData getData() {
        return this.data;
    }

    public World getWorld() {
        return this.world;
    }

    public Field getCurrentField() {
        return this.currentField;
    }

    public boolean isAnimal() {
        return this.getData().getInitiative() > 0;
    }

    public abstract void action();
    public abstract void collision(Organism organism);
    public abstract String getType();
}
