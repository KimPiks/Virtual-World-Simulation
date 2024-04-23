#ifndef VIRTUALWORLD_SHEEP_H
#define VIRTUALWORLD_SHEEP_H

#include "../Organism/Organism.h"
#include "../Animal/Animal.h"
#include "../GUI/GUI.h"
#include "../Settings.h"

class Sheep : public Animal {
public:
  Sheep(World *world, int x, int y, int parent1Id, int parent2Id);

  void action() override;
  void collision(Organism* organism) override;
  std::string getType() const override;
  Organism* createChild(int x, int y, int parent1Id, int parent2Id) override;
};


#endif
