#ifndef VIRTUALWORLD_MILKWEED_H
#define VIRTUALWORLD_MILKWEED_H


#include "../Plant/Plant.h"

class Milkweed : public Plant {
public:
  Milkweed(World *world, int x, int y);

  void action() override;
  void collision(Organism* organism) override;
  std::string getType() const override;

  Organism* createChild(int x, int y, int parent1Id, int parent2Id) override;
};


#endif
