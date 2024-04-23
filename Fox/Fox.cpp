#include "Fox.h"
#include "../Settings.h"

Fox::Fox(World *world, int x, int y, int parent1Id, int parent2Id) :
  Animal(FOX_STRENGTH, FOX_INITIATIVE, FOX_SYMBOL, FOX_COLOR, world, x, y, parent1Id, parent2Id) {}

void Fox::action() {
  Position oldPosition = this->getPosition();

  int tries = 0;
  while (true) {
    if (tries == 10) {
      this->getWorld()->addLog("%s POS:(%d, %d) couldn't find a place to move", this->getType().c_str(), oldPosition.getX(), oldPosition.getY());
      break;
    }
    Position newPosition = this->getWorld()->generateNewPosition(oldPosition.getX(), oldPosition.getY());
    Organism* organism = this->getWorld()->getOrganismAtPosition(this, newPosition.getX(), newPosition.getY());
    if (organism != nullptr && organism->getStrength() > this->getStrength()) {
      this->getWorld()->addLog("%s POS:(%d, %d) ran away from %s POS:(%d, %d)", this->getType().c_str(), oldPosition.getX(), oldPosition.getY(), organism->getType().c_str(), organism->getPosition().getX(), organism->getPosition().getY());
      tries++;
      continue;
    }
    this->setPosition(newPosition.getX(), newPosition.getY());
    this->getWorld()->addLog("%s POS:(%d, %d) moved to (%d, %d)", this->getType().c_str(), oldPosition.getX(), oldPosition.getY(), newPosition.getX(), newPosition.getY());
    break;
  }
}

void Fox::collision(Organism *organism) {
  Animal::collision(organism);
}

Organism* Fox::createChild(int x, int y, int parent1Id, int parent2Id) {
  return new Fox(this->getWorld(), x, y, parent1Id, parent2Id);
}

std::string Fox::getType() const { return "Fox"; }