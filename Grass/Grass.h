#ifndef VIRTUALWORLD_GRASS_H
#define VIRTUALWORLD_GRASS_H


#include "../Plant/Plant.h"

class Grass : public Plant {
public:
  Grass(World *world, int x, int y);

  void action() override;
  void collision(Organism* organism) override;
  std::string getType() const override;

  Organism* createChild(int x, int y, int parent1Id, int parent2Id) override;
};


#endif
