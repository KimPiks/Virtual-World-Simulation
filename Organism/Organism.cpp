#include <cstdio>
#include "Organism.h"

Organism::Organism(int strength, int initiative, char symbol, Color color, World *world,
                   Position position, int parentId1, int parentId2) : position(position) {
  this->strength = strength;
  this->initiative = initiative;
  this->age = 0;
  this->symbol = symbol;
  this->born = false;
  this->alive = true;
  this->color = color;
  this->world = world;
  this->id = this->getWorld()->getNextOrganismId();
  this->parent1Id = parentId1;
  this->parent2Id = parentId2;
  this->previousX = this->position.getX();
  this->previousY = this->position.getY();
}

int Organism::getStrength() const { return this->strength; }
int Organism::getInitiative() const { return this->initiative; }
int Organism::getAge() const { return this->age; }
char Organism::getSymbol() const { return this->symbol; }
bool Organism::isBorn() const { return this->born; }
bool Organism::isAlive() const { return this->alive; }
Color Organism::getColor() const { return this->color; }
Position Organism::getPosition() const { return this->position; }
World* Organism::getWorld() const { return this->world; }
int Organism::getId() const { return this->id; }
int Organism::getParent1Id() const { return this->parent1Id; }
int Organism::getParent2Id() const { return this->parent2Id; }

void Organism::setStrength(int s) { this->strength = s; }
void Organism::setInitiative(int i) { this->initiative = i; }
void Organism::setId(int i) { this->id = i; }
void Organism::setAge(int a) { this->age = a; }
void Organism::setBorn(bool isBorn) { this->born = isBorn; }
void Organism::setAlive(bool isAlive) { this->alive = isAlive; }
void Organism::setPosition(int x, int y) {
  this->previousX = this->position.getX();
  this->previousY = this->position.getY();
  this->position.set(x, y);
}

void Organism::draw() const {
  GUI::color(this->getColor());
  printf("%c", this->getSymbol());
  GUI::color();
}

bool Organism::deflectedAttack(Organism *attacker) {
  if (attacker->getStrength() >= this->getStrength()) return false;
  return true;
}

int Organism::getPreviousX() const { return this->previousX; }
int Organism::getPreviousY() const { return this->previousY; }
void Organism::setPreviousX(int x) { this->previousX = x; }
void Organism::setPreviousY(int y) { this->previousY = y; }