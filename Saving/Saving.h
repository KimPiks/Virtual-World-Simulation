#ifndef VIRTUALWORLD_SAVING_H
#define VIRTUALWORLD_SAVING_H

#include <string>

class World;
class Human;

class Saving {
private:
  World* world;

  World* getWorld() const;

public:
  Saving(World* world);
  void save(const std::string& fileName);
  World load(const std::string& fileName);

  void setWorld(World* w);
};


#endif
