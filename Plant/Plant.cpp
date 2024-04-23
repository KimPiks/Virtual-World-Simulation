#include "Plant.h"

Plant::Plant(int strength, char symbol, Color color, World *world, int x, int y)
    : Organism(strength,
               0,
               symbol,
               color,
               world,
               Position(x, y),
               -1,
               -1) {}

void Plant::action() {
  int spreadChance = rand() % 100;
  if (spreadChance >= PLANT_SPREAD_CHANCE) return;

  Position newPosition = this->getWorld()->generateNewSafePosition(this,
                                                                   this->getPosition().getX(),
                                                                   this->getPosition().getY());
  if (newPosition == this->getPosition()) return;
  if (!this->getWorld()->isPositionCorrect(newPosition.x, newPosition.y)) return;

  this->getWorld()->addOrganism(this->createChild(newPosition.x,
                                                  newPosition.y,
                                                  this->getId(),
                                                  -1));

  this->getWorld()->addLog("%s POS:(%d, %d) spreaded to POS:(%d, %d)",
                           this->getType().c_str(),
                           this->getPosition().getX(),
                           this->getPosition().getY(),
                           newPosition.x,
                           newPosition.y);
}