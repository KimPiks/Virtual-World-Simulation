#ifndef VIRTUALWORLD_ANTELOPE_H
#define VIRTUALWORLD_ANTELOPE_H


#include "../Animal/Animal.h"

class Antelope : public Animal {
public:
  Antelope(World *world, int x, int y, int parent1Id, int parent2Id);

  void action() override;
  void collision(Organism* organism) override;
  std::string getType() const override;

  Organism* createChild(int x, int y, int parent1Id, int parent2Id) override;

  bool deflectedAttack(Organism *attacker) override;
};


#endif