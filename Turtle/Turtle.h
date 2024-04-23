#ifndef VIRTUALWORLD_TURTLE_H
#define VIRTUALWORLD_TURTLE_H


#include "../Animal/Animal.h"
#include "../Settings.h"

class Turtle : public Animal {
public:
  Turtle(World *world, int x, int y, int parent1Id, int parent2Id);

  void action() override;
  void collision(Organism* organism) override;
  std::string getType() const override;
  Organism* createChild(int x, int y, int parent1Id, int parent2Id) override;
  bool deflectedAttack(Organism *attacker) override;
};


#endif
