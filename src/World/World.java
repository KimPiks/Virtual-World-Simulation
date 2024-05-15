package World;

import Field.Field;
import Human.*;
import Organism.Organism;
import Settings.Settings;
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
                if (this.organisms.get(i).getType().equals("Human")) {
                    Human human = (Human) this.organisms.get(i);
                    if (human.getAbilityDuration() > 0) {
                        human.getData().setAlive(true);
                        continue;
                    }
                }

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

    private void handleHumanAbility() {
        Human human = this.getHuman();
        if (human == null) {
            this.window.removeHumanAbilityComponents();
            return;
        }
        this.window.setHumanAbilityDurationLabel(human.getAbilityDuration());
        if (human.getAbilityDuration() == 0) {
            this.window.setHumanAbilityCooldownLabel(human.getAbilityCooldown());
        }

        if (human.getAbilityDuration() > 0) {
            human.setAbilityDuration(human.getAbilityDuration() - 1);
        }
        if (human.getAbilityCooldown() > 0) {
            human.setAbilityCooldown(human.getAbilityCooldown() - 1);
        }
    }

    private Human getHuman() {
        for (Organism organism : this.organisms) {
            if (organism.getType().equals("Human")) {
                return (Human) organism;
            }
        }
        return null;
    }

    private void increaseOrganismsAge() {
        for (Organism organism : this.organisms) {
            organism.getData().setAge(organism.getData().getAge() + 1);
        }
    }

    private void makeTurn() {
        this.sortOrganisms();
        this.bornAllOrganisms();
        this.handleDay();
        this.handleActionAndCollision();
        this.removeDeadOrganisms();
        this.handleHumanAbility();
        this.increaseOrganismsAge();

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
        if (!this.isHumanAlive() && keyCode == 32) {
            this.makeTurn();
        } else if (this.isHumanAlive()) {
            Human human = this.getHuman();

            if (keyCode == 87 && this.getWorldSettings().worldType() == WorldType.RECTANGULAR && ((human.getCurrentField().getNumber() - 1) % this.getWorldSettings().height() != 0)) {
                this.setHumanDirection(MoveDirection.Up);
            } else if (keyCode == 83 && this.getWorldSettings().worldType() == WorldType.RECTANGULAR && (human.getCurrentField().getNumber() % this.getWorldSettings().height() != 0)) {
                this.setHumanDirection(MoveDirection.Down);
            } else if (keyCode == 65 && (human.getCurrentField().getNumber() - this.getWorldSettings().height() > 0)) {
                this.setHumanDirection(MoveDirection.Left);
            } else if (keyCode == 68 && (human.getCurrentField().getNumber() + this.getWorldSettings().height() <= this.getWorldSettings().width() * this.getWorldSettings().height())) {
                this.setHumanDirection(MoveDirection.Right);
            } else if (keyCode == 81 && this.getWorldSettings().worldType() == WorldType.HEXAGONAL && ((human.getCurrentField().getNumber() - 1) % this.getWorldSettings().height() != 0)) {
                this.setHumanDirection(MoveDirection.UP_LEFT);
            }  else if (keyCode == 69 && this.getWorldSettings().worldType() == WorldType.HEXAGONAL && (human.getCurrentField().getNumber() + this.getWorldSettings().height() <= this.getWorldSettings().width() * this.getWorldSettings().height()) && ((human.getCurrentField().getNumber() - 1) % this.getWorldSettings().height() != 0)) {
                this.setHumanDirection(MoveDirection.UP_RIGHT);
            } else if (keyCode == 90 && this.getWorldSettings().worldType() == WorldType.HEXAGONAL && (human.getCurrentField().getNumber() % this.getWorldSettings().height() != 0) && (human.getCurrentField().getNumber() - this.getWorldSettings().height() > 0)) {
                this.setHumanDirection(MoveDirection.DOWN_LEFT);
            }  else if (keyCode == 67 && this.getWorldSettings().worldType() == WorldType.HEXAGONAL && (human.getCurrentField().getNumber() % this.getWorldSettings().height() != 0)) {
                this.setHumanDirection(MoveDirection.DOWN_RIGHT);
            } else if (keyCode == 80 && human.getAbilityCooldown() == 0) {
                human.setAbilityDuration(Settings.HUMAN_ABILITY_DURATION);
                human.setAbilityCooldown(Settings.HUMAN_ABILITY_COOLDOWN);
                this.handleHumanAbility();
                return;
            } else {
                return;
            }

            this.makeTurn();
        }
    }

    private boolean isHumanAlive() {
        for (Organism organism : this.organisms) {
            if (organism.getType().equals("Human") && organism.getData().isAlive()) {
                return true;
            }
        }
        return false;
    }

    private void setHumanDirection(MoveDirection moveDirection) {
        for (Organism organism : this.organisms) {
            if (organism.getType().equals("Human")) {
                ((Human) organism).setMoveDirection(moveDirection);
            }
        }
    }

    public WorldSettings getWorldSettings() {
        return this.worldSettings;
    }

    public Field getField(int fieldNumber) {
        return this.window.getFields().get(fieldNumber - 1);
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
