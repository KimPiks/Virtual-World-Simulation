#ifndef VIRTUALWORLD_SOSNOWSKYHOGWEED_H
#define VIRTUALWORLD_SOSNOWSKYHOGWEED_H


#include "../Plant/Plant.h"
#include "../Settings.h"

class SosnowskyHogweed : public Plant {
private:
  void killNeighbour(int x, int y);

public:
  SosnowskyHogweed(World *world, int x, int y);

  void action() override;
  void collision(Organism* organism) override;
  std::string getType() const override;
  Organism* createChild(int x, int y, int parent1Id, int parent2Id) override;
};


#endif
