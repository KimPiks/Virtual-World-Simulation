package World;

import Field.Field;
import Organism.Organism;
import Window.Window;

import java.util.ArrayList;

public abstract class World {

    protected Window window;
    protected WorldSettings worldSettings;

    private int nextOrganismId;
    private int day;

    private final ArrayList<Organism> organisms = new ArrayList<Organism>();

    public World(Window window, WorldSettings worldSettings) {
        this.window = window;
        this.worldSettings = worldSettings;
    }

    public int getNextOrganismId() {
        return ++this.nextOrganismId;
    }

    public int getDay() {
        return this.day;
    }

    public void incrementDay() {
        this.day++;
    }

    private void handleDay() {
        this.incrementDay();
        this.window.setDayLabel(this.day);
    }

    public void addOrganism(Organism organism) {
        this.organisms.add(organism);
        organism.getCurrentField().setOrganism(organism);
    }

    private void handleAction() {
        int organismsCount = this.organisms.size();
        for (int i = 0; i < organismsCount; i++) {
            if (!this.organisms.get(i).getData().isAlive() || !this.organisms.get(i).getData().isBorn()) continue;

            this.organisms.get(i).action();
        }
    }

    private void bornAllOrganisms() {
        for (Organism organism : this.organisms) {
            organism.getData().setBorn(true);
        }
    }

    private void removeDeadOrganisms() {
        ArrayList<Organism> toRemove = new ArrayList<Organism>();

        for (int i = 0; i < this.organisms.size(); i++) {
            if (!this.organisms.get(i).getData().isAlive()) {
                toRemove.add(this.organisms.get(i));
            }
        }

        for (Organism organism : toRemove) {
            organism.getCurrentField().setOrganism(null);
            this.organisms.remove(organism);
        }
    }

    private void makeTurn() {
        this.bornAllOrganisms();
        this.handleDay();
        this.handleAction();
        this.removeDeadOrganisms();
    }

    public void keyAction(int keyCode) {
        if (keyCode == 32) {
            this.makeTurn();
        }
    }

    public WorldSettings getWorldSettings() {
        return this.worldSettings;
    }

    public Field getFreeNeighbourField(Field field) {
        for (Field f : field.getNeighbours()) {
            if (f.getOrganism() == null) {
                return f;
            }
        }
        return null;
    }

    public static World initializeWorld(WorldSettings worldSettings, Window window) {
        if (worldSettings.worldType() == WorldType.HEXAGONAL) {
            return new HexWorld(window, worldSettings);
        }
        return new RectangleWorld(window, worldSettings);
    }

    public abstract void placeFields();
}
