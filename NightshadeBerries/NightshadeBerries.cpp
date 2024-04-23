#include "NightshadeBerries.h"
#include "../Settings.h"

NightshadeBerries::NightshadeBerries(World *world, int x, int y)
    : Plant(NIGHTSHADE_BERRIES_STRENGTH, NIGHTSHADE_BERRIES_SYMBOL, NIGHTSHADE_BERRIES_COLOR, world, x, y) {}

void NightshadeBerries::action() {
  Plant::action();
}

void NightshadeBerries::collision(Organism* organism) {
  organism->setAlive(false);
  this->getWorld()->addLog("%s POS:(%d, %d) killed %s POS:(%d, %d)", this->getType().c_str(), this->getPosition().getX(), this->getPosition().getY(), organism->getType().c_str(), organism->getPosition().getX(), organism->getPosition().getY());
}

std::string NightshadeBerries::getType() const { return "NightshadeBerries"; }

Organism* NightshadeBerries::createChild(int x, int y, int parent1Id, int parent2Id) {
  return new NightshadeBerries(this->getWorld(), x, y);
}