#ifndef VIRTUALWORLD_HUMAN_H
#define VIRTUALWORLD_HUMAN_H

#include "../Animal/Animal.h"

class Human : public Animal {
private:
  char direction;

  int abilityDuration = 0;
  int abilityCooldown = 0;

  int defaultAbilityDuration = 5;
  int defaultAbilityCooldown = 10;

  char getDirection() const;

public:
  Human(World *world, int x, int y, int i, int i1);

  void action() override;
  void collision(Organism* organism) override;
  Organism* createChild(int x, int y, int parent1Id, int parent2Id) override;
  std::string getType() const override;
  bool deflectedAttack(Organism *attacker) override;

  void setDirection(char d);

  int getAbilityDuration() const;
  int getAbilityCooldown() const;
  int getDefaultAbilityDuration() const;
  int getDefaultAbilityCooldown() const;
  void setAbilityDuration(int duration);
  void setAbilityCooldown(int cooldown);
};


#endif
