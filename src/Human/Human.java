package Human;

import Animal.Animal;
import Field.Field;
import Organism.Organism;
import Organism.OrganismData;
import Settings.Settings;
import World.World;

public class Human extends Animal {

    private int abilityDuration;
    private int abilityCooldown;
    private MoveDirection moveDirection;

    public Human(World world, Field field, int parent1Id, int parent2Id) {
        super(new OrganismData(world.getNextOrganismId(), Settings.HUMAN_INITIATIVE, Settings.HUMAN_STRENGTH, Settings.HUMAN_IMAGE, parent1Id, parent2Id), world, field);
    }

    @Override
    public void action() {
        this.lastField = this.currentField;
        this.currentField.setOrganism(null);

        switch (this.getMoveDirection()) {
            case MoveDirection.Up:
                this.currentField = this.getWorld().getField(this.currentField.getNumber() - 1);
                break;
            case MoveDirection.Down:
                this.currentField = this.getWorld().getField(this.currentField.getNumber() + 1);
                break;
            case MoveDirection.Left:
                this.currentField = this.getWorld().getField(this.currentField.getNumber() - this.getWorld().getWorldSettings().height());
                break;
            case MoveDirection.Right:
                this.currentField = this.getWorld().getField(this.currentField.getNumber() + this.getWorld().getWorldSettings().height());
                break;
            case MoveDirection.UP_LEFT:
                this.currentField = this.getWorld().getField(this.currentField.getNumber() - 1);
                break;
            case MoveDirection.UP_RIGHT:
                this.currentField = this.getWorld().getField(this.currentField.getNumber() + this.getWorld().getWorldSettings().height() - 1);
                break;
            case MoveDirection.DOWN_LEFT:
                this.currentField = this.getWorld().getField(this.currentField.getNumber() - this.getWorld().getWorldSettings().height() + 1);
                break;
            case MoveDirection.DOWN_RIGHT:
                this.currentField = this.getWorld().getField(this.currentField.getNumber() + 1);
                break;
            default:
                break;
        }

        this.currentField.setOrganism(this);
    }

    @Override
    public void reproduce(Field field, int parent1Id, int parent2Id) {
        throw new UnsupportedOperationException("Human cannot reproduce.");
    }

    @Override
    public String getType() {
        return "Human";
    }

    @Override
    public boolean deflectedAttack(Organism attacker) {
        if (this.getAbilityDuration() > 0) return true;
        return super.deflectedAttack(attacker);
    }

    public int getAbilityDuration() {
        return this.abilityDuration;
    }

    public void setAbilityDuration(int abilityDuration) {
        this.abilityDuration = abilityDuration;
    }

    public int getAbilityCooldown() {
        return this.abilityCooldown;
    }

    public void setAbilityCooldown(int abilityCooldown) {
        this.abilityCooldown = abilityCooldown;
    }

    public MoveDirection getMoveDirection() {
        return this.moveDirection;
    }

    public void setMoveDirection(MoveDirection moveDirection) {
        this.moveDirection = moveDirection;
    }


}
