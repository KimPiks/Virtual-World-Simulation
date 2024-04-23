#include "SosnowskyHogweed.h"

SosnowskyHogweed::SosnowskyHogweed(World *world, int x, int y)
    : Plant(SOSNOWSKY_HOGWEED_STRENGTH,
            SOSNOWSKY_HOGWEED_SYMBOL,
            SOSNOWSKY_HOGWEED_COLOR,
            world,
            x,
            y) {}

void SosnowskyHogweed::killNeighbour(int x, int y) {
  if (!this->getWorld()->isPositionCorrect(x, y)) return;

  Organism* organism = this->getWorld()->getOrganismAtPosition(this, x, y);
  if (organism == nullptr || organism->getInitiative() == 0) return;

  organism->setAlive(false);
  this->getWorld()->addLog("%s POS:(%d, %d) killed %s POS:(%d, %d)",
                           this->getType().c_str(),
                           this->getPosition().getX(),
                           this->getPosition().getY(),
                           organism->getType().c_str(),
                           organism->getPosition().getX(),
                           organism->getPosition().getY());
}

void SosnowskyHogweed::action() {
  int x = this->getPosition().getX();
  int y = this->getPosition().getY();

  this->killNeighbour(x + 1, y);
  this->killNeighbour(x - 1, y);
  this->killNeighbour(x, y + 1);
  this->killNeighbour(x, y - 1);

  Plant::action();
}

void SosnowskyHogweed::collision(Organism* organism) {
  organism->setAlive(false);
  this->getWorld()->addLog("%s POS:(%d, %d) killed %s POS:(%d, %d)",
                           this->getType().c_str(),
                           this->getPosition().getX(),
                           this->getPosition().getY(),
                           organism->getType().c_str(),
                           organism->getPosition().getX(),
                           organism->getPosition().getY());
}

std::string SosnowskyHogweed::getType() const { return "SosnowskyHogweed"; }

Organism* SosnowskyHogweed::createChild(int x, int y, int parent1Id, int parent2Id) {
  return new SosnowskyHogweed(this->getWorld(), x, y);
}