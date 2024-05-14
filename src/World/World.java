package World;

import Field.Field;
import Organism.Organism;
import Window.Window;
import Logging.Logging;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public abstract class World {

    protected Window window;
    protected WorldSettings worldSettings;

    private int nextOrganismId;
    private int day;

    private final ArrayList<Organism> organisms = new ArrayList<Organism>();

    private final Logging logging;

    public World(Window window, WorldSettings worldSettings) {
        this.window = window;
        this.worldSettings = worldSettings;
        this.logging = new Logging();
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

    private void handleActionAndCollision() {
        int organismsCount = this.organisms.size();
        for (int i = 0; i < organismsCount; i++) {
            // Action
            if (!this.organisms.get(i).getData().isAlive() || !this.organisms.get(i).getData().isBorn()) continue;

            this.organisms.get(i).action();

            // Collision
            if (!this.organisms.get(i).isAnimal()) continue;

            Organism organism = this.getOrganismAtField(this.organisms.get(i).getCurrentField(), this.organisms.get(i));
            if (organism == null || !organism.getData().isAlive() || !organism.getData().isBorn()) continue;

            this.organisms.get(i).collision(organism);
        }
    }

    private void bornAllOrganisms() {
        for (Organism organism : this.organisms) {
            organism.getData().setBorn(true);
            organism.getCurrentField().setOrganism(organism);
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
            Organism otherOrganismAtField = this.getOrganismAtField(organism.getCurrentField(), organism);
            organism.getCurrentField().setOrganism(null);

            if (otherOrganismAtField != null) {
                otherOrganismAtField.getCurrentField().setOrganism(otherOrganismAtField);
            }
            this.organisms.remove(organism);
        }
    }

    private void makeTurn() {
        this.sortOrganisms();
        this.bornAllOrganisms();
        this.handleDay();
        this.handleActionAndCollision();
        this.removeDeadOrganisms();

        this.logging.showLogs();
    }

    public Organism getOrganismAtField(Field field, Organism skipOrganism) {
        for (Organism organism : this.organisms) {
            if (organism.getCurrentField() == field && organism != skipOrganism) {
                return organism;
            }
        }
        return null;
    }

    public void keyAction(int keyCode) {
        if (keyCode == 32) {
            this.makeTurn();
        }
    }

    public WorldSettings getWorldSettings() {
        return this.worldSettings;
    }

    public Field getNeighbourField(Field field) {
        ArrayList<Field> neighbours = field.getNeighbours();
        Collections.shuffle(neighbours);

        if (!neighbours.isEmpty()) return neighbours.getFirst();
        return null;
    }

    public Field getFreeNeighbourField(Field field) {
        ArrayList<Field> neighbours = field.getNeighbours();
        Collections.shuffle(neighbours);

        for (Field f : neighbours) {
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

    public void sortOrganisms() {
        Collections.sort(this.organisms, new Comparator<Organism>() {
            @Override
            public int compare(Organism a, Organism b) {
                if (a.getData().getInitiative() > b.getData().getInitiative()) {
                    return -1;
                } else if (a.getData().getInitiative() == b.getData().getInitiative()) {
                    if (a.getData().getAge() > b.getData().getAge()) {
                        return -1;
                    } else if (a.getData().getAge() < b.getData().getAge()) {
                        return 1;
                    }
                } else if (a.getData().getInitiative() < b.getData().getInitiative()) {
                    return 1;
                }
                return 0;
            }
        });
    }

    public void addLog(String log) {
        this.logging.addLog(log);
    }

    public abstract void placeFields();
}
