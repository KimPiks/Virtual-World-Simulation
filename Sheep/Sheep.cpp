#include "Sheep.h"

Sheep::Sheep(World *world, int x, int y, int parent1Id, int parent2Id) :
    Animal(SHEEP_STRENGTH,
           SHEEP_INITIATIVE,
           SHEEP_SYMBOL,
           SHEEP_COLOR,
           world,
           x,
           y,
           parent1Id,
           parent2Id) {}

void Sheep::action() {
  Animal::action();
}

void Sheep::collision(Organism *organism) {
  Animal::collision(organism);
}

Organism* Sheep::createChild(int x, int y, int parent1Id, int parent2Id) {
  return new Sheep(this->getWorld(), x, y, parent1Id, parent2Id);
}

std::string Sheep::getType() const { return "Sheep"; }
