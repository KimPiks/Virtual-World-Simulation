package Organism;



public class OrganismData {

    private final int id;
    private final int initiative;
    private final String iconPath;

    private final int parent1Id;
    private final int parent2Id;

    private int strength;
    private int age;
    private boolean born;
    private boolean alive;

    public OrganismData(int id, int initiative, int strength, String iconPath, int parent1Id, int parent2Id) {
        this.id = id;
        this.initiative = initiative;
        this.strength = strength;
        this.age = 0;
        this.born = false;
        this.alive = true;
        this.iconPath = iconPath;
        this.parent1Id = parent1Id;
        this.parent2Id = parent2Id;
    }

    public int getId() {
        return this.id;
    }

    public int getInitiative() {
        return this.initiative;
    }

    public String getIconPath() {
        return this.iconPath;
    }

    public int getStrength() {
        return this.strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public int getAge() {
        return this.age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isBorn() {
        return this.born;
    }

    public void setBorn(boolean born) {
        this.born = born;
    }

    public boolean isAlive() {
        return this.alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public int getParent1Id() {
        return this.parent1Id;
    }

    public int getParent2Id() {
        return this.parent2Id;
    }
}
