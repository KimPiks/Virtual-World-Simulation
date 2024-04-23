#ifndef VIRTUALWORLD_FOX_H
#define VIRTUALWORLD_FOX_H


#include "../Animal/Animal.h"

class Fox : public Animal {
public:
  Fox(World *world, int x, int y, int parent1Id, int parent2Id);

  void action() override;
  void collision(Organism* organism) override;
  std::string getType() const override;

  Organism* createChild(int x, int y, int parent1Id, int parent2Id) override;
};


#endif