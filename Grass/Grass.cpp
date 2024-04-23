#include "Grass.h"
#include "../Settings.h"

Grass::Grass(World *world, int x, int y)
    : Plant(GRASS_STRENGTH, GRASS_SYMBOL, GRASS_COLOR, world, x, y) {}

void Grass::action() {
  Plant::action();
}

void Grass::collision(Organism* organism) { }

std::string Grass::getType() const { return "Grass"; }

Organism* Grass::createChild(int x, int y, int parent1Id, int parent2Id) {
  return new Grass(this->getWorld(), x, y);
}