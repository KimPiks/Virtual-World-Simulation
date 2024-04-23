#ifndef VIRTUALWORLD_WORLD_H
#define VIRTUALWORLD_WORLD_H


#include <cstdio>
#include <algorithm>
#include <cstdarg>
#include <list>
#include <queue>
#include <string>
#include "../Organism/Organism.h"
#include "../Saving/Saving.h"

class Human;
class Organism;
class Saving;

class World {
private:
  std::string id;
  int width;
  int height;
  int startX; // x coordinate of the top left corner of the board
  int startY; // y coordinate of the top left corner of the board
  int nextOrganismId = 1;
  int day = 1;

  std::list<Organism*> organisms;
  std::queue<std::string> logs;

  void displayGUI();
  void displayBoard() const;
  void displayLogs();
  void displayDay();
  void displayAbilityInfo();
  void displayNewState();
  void displayOldState();

  void displayOrganisms(bool newState);
  void sortOrganisms();

  void handleOrganismsAction();
  void handleDyingAndBirthing();
  void increaseAgeOfEachOrganism();

  void removeOrganism(Organism* organism);

  void generateOrganisms();

  void bornAllOrganisms();

public:
  World(int width, int height, int startX, int startY);
  ~World();

  /// Initialize the world with the given organisms
  /// @param organisms List of organisms to be added to the world
  void init(std::list<Organism*> organisms);
  /// Initialize the world with random organisms (count of organisms is defined in Settings.h)
  /// @param random If true, the organisms will be randomly generated, if false the organisms will not be generated
  void init(bool random = true);

  std::string generateId();
  void setId(std::string i);

  std::list<Organism*>& getOrganisms();
  Human* getHuman();

  void makeTurn();
  Organism* getOrganismAtPosition(Organism* skipOrganism, int x, int y);
  bool isPositionCorrect(int x, int y) const;
  Position generateNewPosition(int oldX, int oldY) const;
  Position generateNewSafePosition(Organism* organism, int oldX, int oldY);
  bool isHumanAlive();
  Position getHumanPosition();
  void addOrganism(Organism* organism);

  void startSimulation(Saving* saving);

  int getWidth() const;
  int getHeight() const;
  int getStartX() const;
  int getStartY() const;
  int getNextOrganismId();
  int getDay() const;
  std::string getId() const;

  void setDay(int d);

  void addLog(const char* log, ...);
};


#endif
