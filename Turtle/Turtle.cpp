#include "Turtle.h"

Turtle::Turtle(World *world, int x, int y, int parent1Id, int parent2Id) :
    Animal(TURTLE_STRENGTH,
           TURTLE_INITIATIVE,
           TURTLE_SYMBOL,
           TURTLE_COLOR,
           world,
           x,
           y,
           parent1Id,
           parent2Id) {}

void Turtle::action() {
  int changePositionChance = rand() % 4;
  if (changePositionChance == 0) Animal::action();
}

void Turtle::collision(Organism *organism) {
  Animal::collision(organism);
}

Organism* Turtle::createChild(int x, int y, int parent1Id, int parent2Id) {
  return new Turtle(this->getWorld(), x, y, parent1Id, parent2Id);
}

std::string Turtle::getType() const { return "Turtle"; }

bool Turtle::deflectedAttack(Organism *attacker) {
  if (attacker->getStrength() < 5) {
    int attackerX = attacker->getPreviousX();
    int attackerY = attacker->getPreviousY();

    attacker->setPosition(attackerX, attackerY);
    this->getWorld()->addLog("%s POS:(%d, %d) scared away %s POS:(%d, %d)",
                             this->getType().c_str(),
                             this->getPosition().getX(),
                             this->getPosition().getX(),
                             attacker->getType().c_str(),
                             attackerX,
                             attackerY);
    return true;
  }

  return Animal::deflectedAttack(attacker);
}