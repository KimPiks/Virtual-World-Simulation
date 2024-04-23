#include "Human.h"
#include <stdexcept>
#include "../Settings.h"

Human::Human(World *world, int x, int y, int i, int i1) :
    Animal(HUMAN_STRENGTH,
           HUMAN_INITIATIVE,
           HUMAN_SYMBOL,
           HUMAN_COLOR,
           world,
           x,
           y,
           -1,
           -1) { }

void Human::action() {
  this->setPreviousX(this->getPosition().getX());
  this->setPreviousY(this->getPosition().getY());

  switch (this->getDirection()) {
    case 'u':
      this->setPosition(this->getPosition().getX(), this->getPosition().getY() - 1);
      break;
    case 'd':
      this->setPosition(this->getPosition().getX(), this->getPosition().getY() + 1);
      break;
    case 'r':
      this->setPosition(this->getPosition().getX() + 1, this->getPosition().getY());
      break;
    case 'l':
      this->setPosition(this->getPosition().getX() - 1, this->getPosition().getY());
      break;
    default:
      return;
  }

  Organism* organism = this->getWorld()->getOrganismAtPosition(this, this->getPosition().getX(), this->getPosition().getY());
  if (organism != nullptr && organism->getStrength() > this->getStrength() && this->getAbilityDuration() > 0) {
    Position newPosition = this->getWorld()->generateNewSafePosition(this, this->getPosition().getX(), this->getPosition().getY());
    this->getWorld()->addLog("%s POS:(%d, %d) moved to (%d, %d)",
                             this->getType().c_str(),
                             this->getPreviousX(),
                             this->getPreviousY(),
                             this->getPosition().getX(),
                             this->getPosition().getY());

    this->setPosition(newPosition.getX(), newPosition.getY());

    this->getWorld()->addLog("%s POS:(%d, %d) run away to (%d, %d)",
                             this->getType().c_str(),
                             this->getPreviousX(),
                             this->getPreviousY(),
                             this->getPosition().getX(),
                             this->getPosition().getY());
    return;
  }

  this->getWorld()->addLog("%s POS:(%d, %d) moved to (%d, %d)",
                           this->getType().c_str(),
                           this->getPreviousX(),
                           this->getPreviousY(),
                           this->getPosition().getX(),
                           this->getPosition().getY());
}

void Human::collision(Organism *organism) {
  Animal::collision(organism);
}

Organism* Human::createChild(int x, int y, int parent1Id, int parent2Id) {
  throw std::runtime_error("Human cannot reproduce");
}

std::string Human::getType() const { return "Human"; }

bool Human::deflectedAttack(Organism *attacker) {
  if (this->getAbilityDuration() > 0) return true;
  return Animal::deflectedAttack(attacker);
}

char Human::getDirection() const { return this->direction; }
void Human::setDirection(char d) { this->direction = d; }

int Human::getAbilityDuration() const { return this->abilityDuration; }
int Human::getAbilityCooldown() const { return this->abilityCooldown; }

int Human::getDefaultAbilityDuration() const { return this->defaultAbilityDuration; }
int Human::getDefaultAbilityCooldown() const { return this->defaultAbilityCooldown; }

void Human::setAbilityDuration(int duration) { this->abilityDuration = duration; }
void Human::setAbilityCooldown(int cooldown) { this->abilityCooldown = cooldown; }