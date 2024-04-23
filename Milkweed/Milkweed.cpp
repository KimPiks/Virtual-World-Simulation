#include "Milkweed.h"

Milkweed::Milkweed(World *world, int x, int y)
    : Plant(MILKWEED_STRENGTH, MILKWEED_SYMBOL, MILKWEED_COLOR, world, x, y) {}

void Milkweed::action() {
  Plant::action();
  Plant::action();
  Plant::action();
}

void Milkweed::collision(Organism* organism) { }

std::string Milkweed::getType() const { return "Milkweed"; }

Organism* Milkweed::createChild(int x, int y, int parent1Id, int parent2Id) {
  return new Milkweed(this->getWorld(), x, y);
}