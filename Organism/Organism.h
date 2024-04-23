#ifndef VIRTUALWORLD_ORGANISM_H
#define VIRTUALWORLD_ORGANISM_H


#include <string>
#include "../Position.h"
#include "../World/World.h"
#include "../GUI/GUI.h"

class World;

class Organism {
private:
  int id;
  int strength;
  int initiative;
  int age;
  char symbol;
  bool born;
  bool alive;
  Color color;
  Position position;
  World* world;
  int previousX;
  int previousY;
  int parent1Id;
  int parent2Id;

  Color getColor() const;
  char getSymbol() const;

protected:
  World* getWorld() const;

public:
  Organism(int strength, int initiative, char symbol, Color color, World *world, Position position, int parentId1, int parentId2);
  virtual ~Organism() = default;

  int getStrength() const;
  int getInitiative() const;
  int getAge() const;
  bool isBorn() const;
  bool isAlive() const;
  Position getPosition() const;
  int getId() const;
  int getParent1Id() const;
  int getParent2Id() const;

  void setStrength(int strength);
  void setInitiative(int initiative);
  void setId(int id);
  void setAge(int age);
  void setBorn(bool isBorn);
  void setAlive(bool isAlive);
  void setPosition(int x, int y);
  int getPreviousX() const;
  int getPreviousY() const;
  void setPreviousX(int x);
  void setPreviousY(int y);

  virtual void action() = 0;
  virtual void collision(Organism* organism) = 0;
  virtual std::string getType() const = 0;
  virtual Organism* createChild(int x, int y, int parent1Id, int parent2Id) = 0;
  void draw() const;
  virtual bool deflectedAttack(Organism* attacker);
};


#endif
