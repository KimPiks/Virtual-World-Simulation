#include "Wolf.h"

Wolf::Wolf(World *world, int x, int y, int parent1Id, int parent2Id) :
  Animal(WOLF_STRENGTH, WOLF_INITIATIVE, WOLF_SYMBOL, WOLF_COLOR, world, x, y, parent1Id, parent2Id) {}

void Wolf::action() {
  Animal::action();
}

void Wolf::collision(Organism *organism) {
  Animal::collision(organism);
}

Organism* Wolf::createChild(int x, int y, int parent1Id, int parent2Id) {
  return new Wolf(this->getWorld(), x, y, parent1Id, parent2Id);
}

std::string Wolf::getType() const { return "Wolf"; }