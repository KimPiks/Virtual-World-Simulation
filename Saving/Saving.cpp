#include "Saving.h"
#include "../Organism/Organism.h"
#include "../World/World.h"
#include "../Human/Human.h"
#include "../Sheep/Sheep.h"
#include "../Antelope/Antelope.h"
#include "../SosnowskyHogweed/SosnowskyHogweed.h"
#include "../Guarana/Guarana.h"
#include "../Grass/Grass.h"
#include "../Wolf/Wolf.h"
#include "../Fox/Fox.h"
#include "../Turtle/Turtle.h"
#include "../NightshadeBerries/NightshadeBerries.h"
#include "../Milkweed/Milkweed.h"
#include <fstream>

// File format:
// 1. World id
// 2. Day number
// 3. World width
// 4. World height
// 5. Board start x
// 6. Board start y
// 7. Human ability duration
// 8. Human ability cooldown
// 9. Organisms count
// 10. Organisms:
//    - Type
//    - Id
//    - Strength
//    - Initiative
//    - Position x
//    - Position y
//    - Parent 1 id
//    - Parent 2 id
//    - Age
//    - Previous x
//    - Previous y
//    - Born
//    - Alive

Saving::Saving(World* world) : world(world) {}
World* Saving::getWorld() const { return this->world; }

void Saving::save(const std::string& fileName) {
  std::ofstream file(fileName + ".txt");

  World* w = this->getWorld();

  file << w->getId() << std::endl;
  file << w->getDay()-1 << std::endl;
  file << w->getWidth() << std::endl;
  file << w->getHeight() << std::endl;
  file << w->getStartX() << std::endl;
  file << w->getStartY() << std::endl;

  file << w->getHuman()->getAbilityDuration() << std::endl;
  file << w->getHuman()->getAbilityCooldown() << std::endl;

  file << w->getOrganisms().size() << std::endl;

  for (Organism* organism : w->getOrganisms()) {
    file << organism->getType() << " ";
    file << organism->getId() << " ";
    file << organism->getStrength() << " ";
    file << organism->getInitiative() << " ";
    file << organism->getPosition().getX() << " ";
    file << organism->getPosition().getY() << " ";
    file << organism->getParent1Id() << " ";
    file << organism->getParent2Id() << " ";
    file << organism->getAge() << " ";
    file << organism->getPreviousX() << " ";
    file << organism->getPreviousY() << " ";
    file << organism->isBorn() << " ";
    file << organism->isAlive() << std::endl;
  }

  file.close();
}

World Saving::load(const std::string &fileName) {
  std::ifstream file(fileName + ".txt");

  std::string wId;
  int day;
  int width;
  int height;
  int startX;
  int startY;
  int humanAbilityDuration;
  int humanAbilityCooldown;
  int organismsCount;
  std::list<Organism*> organisms;

  file >> wId;
  file >> day;
  file >> width;
  file >> height;
  file >> startX;
  file >> startY;
  file >> humanAbilityDuration;
  file >> humanAbilityCooldown;
  file >> organismsCount;

  World w(width, height, startX, startY);

  w.setDay(day-1);
  w.setId(wId);

  int i;
  for (i = 0; i < organismsCount; i++) {
    std::string type;
    int id;
    int strength;
    int initiative;
    int x;
    int y;
    int parent1Id;
    int parent2Id;
    int age;
    int previousX;
    int previousY;
    bool born;
    bool alive;

    file >> type;
    file >> id;
    file >> strength;
    file >> initiative;
    file >> x;
    file >> y;
    file >> parent1Id;
    file >> parent2Id;
    file >> age;
    file >> previousX;
    file >> previousY;
    file >> born;
    file >> alive;

    Organism *organism = nullptr;
    if (type == "Sheep") {
      organism = new Sheep(&w, x, y, parent1Id, parent2Id);
    } else if (type == "Antelope") {
      organism = new Antelope(&w, x, y, parent1Id, parent2Id);
    } else if (type == "SosnowskyHogweed") {
      organism = new SosnowskyHogweed(&w, x, y);
    } else if (type == "Guarana") {
      organism = new Guarana(&w, x, y);
    } else if (type == "Grass") {
      organism = new Grass(&w, x, y);
    } else if (type == "Wolf") {
      organism = new Wolf(&w, x, y, parent1Id, parent2Id);
    } else if (type == "Fox") {
      organism = new Fox(&w, x, y, parent1Id, parent2Id);
    } else if (type == "Turtle") {
      organism = new Turtle(&w, x, y, parent1Id, parent2Id);
    } else if (type == "Human") {
      organism = new Human(&w, x, y, parent1Id, parent2Id);
    } else if (type == "NightshadeBerries") {
      organism = new NightshadeBerries(&w, x, y);
    } else if (type == "Milkweed") {
      organism = new Milkweed(&w, x, y);
    }

    organism->setStrength(strength);
    organism->setInitiative(initiative);
    organism->setId(id);
    organism->setAge(age);
    organism->setPreviousX(previousX);
    organism->setPreviousY(previousY);
    organism->setBorn(born);
    organism->setAlive(alive);

    organisms.push_back(organism);
  }

  file.close();

  w.init(organisms);
  w.setId(wId);

  if (w.getHuman() != nullptr) {
    w.getHuman()->setAbilityCooldown(humanAbilityCooldown);
    w.getHuman()->setAbilityDuration(humanAbilityDuration);
  }

  return w;
}

void Saving::setWorld(World* w) { this->world = w; }