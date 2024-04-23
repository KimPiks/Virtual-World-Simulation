#include "Guarana.h"
#include "../Settings.h"

Guarana::Guarana(World *world, int x, int y)
    : Plant(GUARANA_STRENGTH, GUARANA_SYMBOL, GUARANA_COLOR, world, x, y) {}

void Guarana::action() {
  Plant::action();
}

void Guarana::collision(Organism* organism) {
  organism->setStrength(organism->getStrength() + 3);
  this->getWorld()->addLog("%s POS:(%d, %d) added 3 strength to %s POS:(%d, %d)",
                           this->getType().c_str(),
                           this->getPosition().getX(),
                           this->getPosition().getY(),
                           organism->getType().c_str(),
                           organism->getPosition().getX(),
                           organism->getPosition().getY());
}

std::string Guarana::getType() const { return "Guarana"; }

Organism* Guarana::createChild(int x, int y, int parent1Id, int parent2Id) {
  return new Guarana(this->getWorld(), x, y);
}