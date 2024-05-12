package Organism;



public class OrganismData {

    private final int id;
    private final int initiative;
    private final String iconPath;

    private int strength;
    private int age;
    private boolean born;
    private boolean alive;

    public OrganismData(int id, int initiative, int strength, String iconPath) {
        this.id = id;
        this.initiative = initiative;
        this.strength = strength;
        this.age = 0;
        this.born = false;
        this.alive = true;
        this.iconPath = iconPath;
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
}
