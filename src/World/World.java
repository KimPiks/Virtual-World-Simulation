package World;

import Field.Field;
import Human.*;
import Organism.Organism;
import Saving.Saving;
import Settings.Settings;
import Window.Window;
import Logging.Logging;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public abstract class World {

    protected Window window;
    protected WorldSettings worldSettings;

    private int nextOrganismId;
    private int day;
    private String worldId;

    private final ArrayList<Organism> organisms = new ArrayList<Organism>();
    private final Logging logging;
    private final Saving saving;

    public World(Window window, WorldSettings worldSettings) {
        this.window = window;
        this.worldSettings = worldSettings;
        this.logging = new Logging(window.getLoggingPanel());
        this.saving = new Saving(window, window.getFrame());
        this.worldId = java.time.LocalDateTime.now().toString().replace(":", "-").replace("T", "-").replace(".", "-") + "-" + (int) (Math.random() * 10000);

        this.saving.setWorld(this);
    }

    public int getNextOrganismId() {
        return ++this.nextOrganismId;
    }

    public void setNextOrganismId(int nextOrganismId) {
        this.nextOrganismId = nextOrganismId;
    }

    public void addOrganisms(ArrayList<Organism> organisms) {
        this.organisms.addAll(organisms);
    }

    public int getDay() {
        return this.day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public void incrementDay() {
        this.day++;
    }

    public String getWorldId() {
        return this.worldId;
    }

    public void setWorldId(String worldId) {
        this.worldId = worldId;
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

    public ArrayList<Organism> getOrganisms() {
        return this.organisms;
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

    public void handleHumanAbility() {
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

    public Saving getSaving() {
        return this.saving;
    }

    public Human getHuman() {
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

    public void handleButtons() {
        ArrayList<JButton> buttons = window.getButtons();
        for (JButton button : buttons) {
            switch (button.getText()) {
                case "New turn":
                    button.setEnabled(!this.isHumanAlive());
                    break;
                case "Special ability":
                    button.setEnabled(this.isHumanAlive() && ((Human) this.getHuman()).getAbilityCooldown() == 0);
                    break;
                case "Move up left": case "Move up right": case "Move down left": case "Move down right": case "Move left": case "Move right": case "Move up": case "Move down":
                    button.setEnabled(this.isHumanAlive());
                    break;
            }
        }
    }

    public void makeTurn() {
        this.sortOrganisms();
        this.bornAllOrganisms();
        this.handleDay();
        this.handleActionAndCollision();
        this.removeDeadOrganisms();
        this.handleHumanAbility();
        this.increaseOrganismsAge();
        this.handleButtons();

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

    public void playerMoveLeft() {
        Human human = this.getHuman();
        if ((human.getCurrentField().getNumber() - 1) % this.getWorldSettings().width() != 0) {
            this.setHumanDirection(MoveDirection.LEFT);
            this.makeTurn();
        }
    }

    public void playerMoveRight() {
        Human human = this.getHuman();
        if (human.getCurrentField().getNumber() % this.getWorldSettings().width() != 0) {
            this.setHumanDirection(MoveDirection.RIGHT);
            this.makeTurn();
        }
    }

    public void playerMoveUp() {
        Human human = this.getHuman();
        if (this.worldSettings.worldType() == WorldType.RECTANGULAR && human.getCurrentField().getNumber() - this.getWorldSettings().width() > 0) {
            this.setHumanDirection(MoveDirection.UP);
            this.makeTurn();
        }
    }

    public void playerMoveDown() {
        Human human = this.getHuman();
        if (this.worldSettings.worldType() == WorldType.RECTANGULAR && human.getCurrentField().getNumber() + this.getWorldSettings().width() <= this.getWorldSettings().width() * this.getWorldSettings().height()) {
            this.setHumanDirection(MoveDirection.DOWN);
            this.makeTurn();
        }
    }

    public void playerMoveUpLeft() {
        Human human = this.getHuman();
        if (this.getWorldSettings().worldType() == WorldType.HEXAGONAL && ((human.getCurrentField().getNumber() - this.getWorldSettings().width()) > 0)) {
            this.setHumanDirection(MoveDirection.UP_LEFT);
            this.makeTurn();
        }
    }

    public void playerMoveUpRight() {
        Human human = this.getHuman();
        if (this.getWorldSettings().worldType() == WorldType.HEXAGONAL && (human.getCurrentField().getNumber() - this.getWorldSettings().width() > 0 && (human.getCurrentField().getNumber() % this.getWorldSettings().width() != 0))) {
            this.setHumanDirection(MoveDirection.UP_RIGHT);
            this.makeTurn();
        }
    }

    public void playerMoveDownLeft() {
        Human human = this.getHuman();
        if (this.getWorldSettings().worldType() == WorldType.HEXAGONAL && ((human.getCurrentField().getNumber() - 1) % this.getWorldSettings().width() != 0) && (human.getCurrentField().getNumber() + this.getWorldSettings().width() <= this.getWorldSettings().width() * this.getWorldSettings().height())) {
            this.setHumanDirection(MoveDirection.DOWN_LEFT);
            this.makeTurn();
        }
    }

    public void playerMoveDownRight() {
        Human human = this.getHuman();
        if (this.getWorldSettings().worldType() == WorldType.HEXAGONAL && (human.getCurrentField().getNumber() + this.getWorldSettings().width()) <= this.getWorldSettings().width() * this.getWorldSettings().height()) {
            this.setHumanDirection(MoveDirection.DOWN_RIGHT);
            this.makeTurn();
        }
    }

    public void keyAction(int keyCode) {
        if (keyCode == Settings.SAVE_KEYCODE) {
            this.saving.save();
            return;
        }

        if (!this.isHumanAlive() && keyCode == Settings.NEW_TURN_KEYCODE) {
            this.makeTurn();
        } else if (this.isHumanAlive()) {
            Human human = this.getHuman();

            if (keyCode == Settings.MOVE_UP_KEYCODE) {
                this.playerMoveUp();
            } else if (keyCode == Settings.MOVE_DOWN_KEYCODE) {
                this.playerMoveDown();
            } else if (keyCode == Settings.MOVE_LEFT_KEYCODE) {
                this.playerMoveLeft();
            } else if (keyCode == Settings.MOVE_RIGHT_KEYCODE) {
                this.playerMoveRight();
            } else if (keyCode == Settings.MOVE_UP_LEFT_KEYCODE) {
                this.playerMoveUpLeft();
            } else if (keyCode == Settings.MOVE_UP_RIGHT_KEYCODE) {
                this.playerMoveUpRight();
            } else if (keyCode == Settings.MOVE_DOWN_LEFT_KEYCODE) {
                this.playerMoveDownLeft();
            }  else if (keyCode == Settings.MOVE_DOWN_RIGHT_KEYCODE) {
                this.playerMoveDownRight();
            } else if (keyCode == Settings.ABILITY_KEYCODE && human.getAbilityCooldown() == 0) {
                human.setAbilityDuration(Settings.HUMAN_ABILITY_DURATION);
                human.setAbilityCooldown(Settings.HUMAN_ABILITY_COOLDOWN);
                this.handleHumanAbility();
                this.handleButtons();
            }
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
