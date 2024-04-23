#include "Animal.h"
#include "../Human/Human.h"

Animal::Animal(int strength, int initiative, char symbol, Color color, World *world, int x, int y, int parent1Id, int parent2Id) :
    Organism(strength,
             initiative,
             symbol,
             color,
             world,
             Position(x, y),
             parent1Id,
             parent2Id) { }

void Animal::action() {
  this->setPreviousX(this->getPosition().getX());
  this->setPreviousY(this->getPosition().getY());

  Position newPosition = this->getWorld()->generateNewPosition(this->getPosition().getX(),
                                                               this->getPosition().getY());
  this->setPosition(newPosition.getX(), newPosition.getY());

  this->getWorld()->addLog("%s POS:(%d, %d) moved to (%d, %d)",
                           this->getType().c_str(),
                           this->getPreviousX(),
                           this->getPreviousY(),
                           this->getPosition().getX(),
                           this->getPosition().getY());
}

void Animal::collision(Organism *organism) {
  if (this->getType() == organism->getType()) {
    if (this->isParent(organism) ||
        this->isParent(this) ||
        this->hasSameParents(organism))
      return;

    int x = this->getPosition().getX();
    int y = this->getPosition().getY();

    if (this->getWorld()->getOrganismAtPosition(this, this->getPreviousX(), this->getPreviousY()) == nullptr &&
      this->getWorld()->getOrganismAtPosition(organism, organism->getPreviousX(), organism->getPreviousY()) == nullptr) {

      this->setPosition(this->getPreviousX(), this->getPreviousY());
      organism->setPosition(organism->getPreviousX(), organism->getPreviousY());

      this->getWorld()->addOrganism(this->createChild(x,
                                                      y,
                                                      this->getId(),
                                                      organism->getId()));

      this->getWorld()->addLog("%s POS:(%d, %d) breeds (%d w %d)",
                               this->getType().c_str(),
                               this->getPosition().getX(),
                               this->getPosition().getY(),
                               this->getId(),
                               organism->getId());
    }
  } else if (organism->getInitiative() == 0) {
    this->getWorld()->addLog("%s POS:(%d, %d) ate %s POS:(%d, %d)",
                             this->getType().c_str(),
                             this->getPosition().getX(),
                             this->getPosition().getY(),
                             organism->getType().c_str(),
                             organism->getPosition().getX(),
                             organism->getPosition().getY());

    organism->collision(this);
    organism->setAlive(false);
  } else {
    this->getWorld()->addLog("%s POS:(%d, %d) attacked %s POS:(%d, %d)",
                             this->getType().c_str(),
                             this->getPosition().getX(),
                             this->getPosition().getY(),
                             organism->getType().c_str(),
                             organism->getPosition().getX(),
                             organism->getPosition().getY());

    if (organism->deflectedAttack(this)) {
      if (organism->getPosition().getX() ==  this->getPosition().getX() &&
          organism->getPosition().getY() == this->getPosition().getY() &&
          organism->getType() != "Human") {
        this->getWorld()->addLog("%s POS:(%d, %d) S:%d killed %s POS:(%d, %d) S:%d",
                                 organism->getType().c_str(),
                                 this->getPosition().getX(),
                                 this->getPosition().getY(),
                                 organism->getStrength(),
                                 this->getType().c_str(),
                                 organism->getPosition().getY(),
                                 organism->getPosition().getX(),
                                 this->getStrength());

        this->setAlive(false);
      } else {
        this->getWorld()->addLog("%s POS:(%d, %d) deflected attack from %s POS:(%d, %d)",
                                 organism->getType().c_str(),
                                 organism->getPosition().getX(),
                                 organism->getPosition().getY(),
                                 this->getType().c_str(),
                                 this->getPosition().getX(),
                                 this->getPosition().getY());
      }

    } else {
      this->getWorld()->addLog("%s POS:(%d, %d) S:%d killed %s POS:(%d, %d) S:%d",
                               this->getType().c_str(),
                               this->getPosition().getX(),
                               this->getPosition().getY(),
                               this->getStrength(),
                               organism->getType().c_str(),
                               organism->getPosition().getX(),
                               organism->getPosition().getY(),
                               organism->getStrength());

      organism->setAlive(false);
    }
  }
}

bool Animal::isParent(Organism* organism) const {
  if (organism->getParent1Id() == this->getId() ||
      organism->getParent2Id() == this->getId()) {
    return true;
  }
  return false;
}

bool Animal::hasSameParents(Organism* organism) const {
  if (organism->getParent1Id() == -1 || this->getParent1Id() == -1) return false;

  if ((organism->getParent1Id() == this->getParent1Id() && organism->getParent2Id() == this->getParent2Id()) ||
      (organism->getParent1Id() == this->getParent2Id() && organism->getParent2Id() == this->getParent1Id())) {
    return true;
  }
  return false;
}