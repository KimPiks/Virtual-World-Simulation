#include "Antelope.h"
#include "../Settings.h"

Antelope::Antelope(World *world, int x, int y, int parent1Id, int parent2Id) :
    Animal(ANTELOPE_STRENGTH, ANTELOPE_INITIATIVE, ANTELOPE_SYMBOL, ANTELOPE_COLOR, world, x, y, parent1Id, parent2Id) {}

void Antelope::action() {
  Animal::action();
  Animal::action();
}

void Antelope::collision(Organism *organism) {
  Animal::collision(organism);
}

Organism* Antelope::createChild(int x, int y, int parent1Id, int parent2Id) {
  return new Antelope(this->getWorld(), x, y, parent1Id, parent2Id);
}

std::string Antelope::getType() const { return "Antelope"; }

bool Antelope::deflectedAttack(Organism *attacker) {
  int deflectChance = rand() % 2;
  if (deflectChance == 0) {
    Position newPosition = this->getWorld()->generateNewSafePosition(attacker, this->getPosition().getX(), this->getPosition().getY());
    this->setPosition(newPosition.getX(), newPosition.getY());
    this->getWorld()->addLog("%s POS:(%d, %d) run away from %s POS:(%d, %d)", this->getType().c_str(), this->getPreviousX(), this->getPreviousY(), attacker->getType().c_str(), attacker->getPreviousX(), attacker->getPreviousY());
    return true;
  }

  return Animal::deflectedAttack(attacker);
}