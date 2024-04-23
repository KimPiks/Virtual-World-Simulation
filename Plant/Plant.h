#ifndef VIRTUALWORLD_PLANT_H
#define VIRTUALWORLD_PLANT_H


#include "../Organism/Organism.h"
#include "../Settings.h"

class Plant : public Organism {
public:
  Plant(int strength, char symbol, Color color, World *world, int x, int y);
  ~Plant() override = default;

  void action() override;
  virtual void collision(Organism* organism) = 0;
};


#endif
