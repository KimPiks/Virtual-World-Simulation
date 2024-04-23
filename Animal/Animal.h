#ifndef VIRTUALWORLD_ANIMAL_H
#define VIRTUALWORLD_ANIMAL_H


#include "../Organism/Organism.h"
#include "../GUI/GUI.h"

class Animal : public Organism {
private:
  bool isParent(Organism* organism) const;
  bool hasSameParents(Organism* organism) const;

public:
  Animal(int strength, int initiative, char symbol, Color color, World *world, int x, int y, int parent1Id, int parent2Id);
  ~Animal() override = default;

  void action() override;
  void collision(Organism* organism) override;
};


#endif
