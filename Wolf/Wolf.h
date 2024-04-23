#ifndef VIRTUALWORLD_WOLF_H
#define VIRTUALWORLD_WOLF_H


#include "../Animal/Animal.h"
#include "../GUI/GUI.h"
#include "../Settings.h"

class Wolf : public Animal {
public:
  Wolf(World *world, int x, int y, int parent1Id, int parent2Id);

  void action() override;
  void collision(Organism* organism) override;
  std::string getType() const override;
  Organism* createChild(int x, int y, int parent1Id, int parent2Id) override;
};


#endif
