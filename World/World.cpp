#include <ctime>
#include "World.h"
#include "../Human/Human.h"
#include "../Settings.h"
#include "../Wolf/Wolf.h"
#include "../Sheep/Sheep.h"
#include "../SosnowskyHogweed/SosnowskyHogweed.h"
#include "../NightshadeBerries/NightshadeBerries.h"
#include "../Guarana/Guarana.h"
#include "../Milkweed/Milkweed.h"
#include "../Grass/Grass.h"
#include "../Turtle/Turtle.h"
#include "../Fox/Fox.h"
#include "../Antelope/Antelope.h"

World::World(int width, int height, int startX, int startY) :
  width(width), height(height), startX(startX), startY(startY) { }

World::~World() {
  while (!this->getOrganisms().empty()) {
    delete this->getOrganisms().front();
    this->getOrganisms().pop_front();
  }
}

void World::init(std::list<Organism*> organisms) {
  srand(time(nullptr));
  this->id = this->generateId();

  for (Organism *organism : organisms) {
    this->addOrganism(organism);
  }

  this->bornAllOrganisms();
  this->displayGUI();
  this->displayNewState();
}

void World::init(bool random) {
  srand(time(nullptr));

  if (random) {
    this->generateOrganisms();
    this->bornAllOrganisms();
    this->id = this->generateId();
  }

  this->displayGUI();
  this->displayNewState();
}

void World::generateOrganisms() {
  int i;
  for (i = 0; i < WOLF_COUNT; i++) {
    Position position = this->generateNewSafePosition(nullptr, rand() % this->getWidth() + 1, rand() % this->getHeight() + 1);
    this->addOrganism(new Wolf(this, position.getX(), position.getY(), -1, -1));
  }

  for (i = 0; i < SHEEP_COUNT; i++) {
    Position position = this->generateNewSafePosition(nullptr, rand() % this->getWidth() + 1, rand() % this->getHeight() + 1);
    this->addOrganism(new Sheep(this, position.getX(), position.getY(), -1, -1));
  }

  for (i = 0; i < HUMAN_COUNT; i++) {
    Position position = this->generateNewSafePosition(nullptr, rand() % this->getWidth() + 1, rand() % this->getHeight() + 1);
    this->addOrganism(new Human(this, position.getX(), position.getY(), -1, -1));
  }

  for (i = 0; i < ANTELOPE_COUNT; i++) {
    Position position = this->generateNewSafePosition(nullptr, rand() % this->getWidth() + 1, rand() % this->getHeight() + 1);
    this->addOrganism(new Antelope(this, position.getX(), position.getY(), -1, -1));
  }

  for (i = 0; i < FOX_COUNT; i++) {
    Position position = this->generateNewSafePosition(nullptr, rand() % this->getWidth() + 1, rand() % this->getHeight() + 1);
    this->addOrganism(new Fox(this, position.getX(), position.getY(), -1, -1));
  }

  for (i = 0; i < TURTLE_COUNT; i++) {
    Position position = this->generateNewSafePosition(nullptr, rand() % this->getWidth() + 1, rand() % this->getHeight() + 1);
    this->addOrganism(new Turtle(this, position.getX(), position.getY(), -1, -1));
  }

  for (i = 0; i < GRASS_COUNT; i++) {
    Position position = this->generateNewSafePosition(nullptr, rand() % this->getWidth() + 1, rand() % this->getHeight() + 1);
    this->addOrganism(new Grass(this, position.getX(), position.getY()));
  }

  for (i = 0; i < MILKWEED_COUNT; i++) {
    Position position = this->generateNewSafePosition(nullptr, rand() % this->getWidth() + 1, rand() % this->getHeight() + 1);
    this->addOrganism(new Milkweed(this, position.getX(), position.getY()));
  }

  for (i = 0; i < GUARANA_COUNT; i++) {
    Position position = this->generateNewSafePosition(nullptr, rand() % this->getWidth() + 1, rand() % this->getHeight() + 1);
    this->addOrganism(new Guarana(this, position.getX(), position.getY()));
  }

  for (i = 0; i < NIGHTSHADE_BERRIES_COUNT; i++) {
    Position position = this->generateNewSafePosition(nullptr, rand() % this->getWidth() + 1, rand() % this->getHeight() + 1);
    this->addOrganism(new NightshadeBerries(this, position.getX(), position.getY()));
  }

  for (i = 0; i < SOSNOWSKY_HOGWEED_COUNT; i++) {
    Position position = this->generateNewSafePosition(nullptr, rand() % this->getWidth() + 1, rand() % this->getHeight() + 1);
    this->addOrganism(new SosnowskyHogweed(this, position.getX(), position.getY()));
  }
}

void World::bornAllOrganisms() {
  for (Organism* organism : this->getOrganisms()) {
    organism->setBorn(true);
  }
}

std::string World::generateId() {
  time_t t = std::time(nullptr);
  int r = rand() % 9999;
  return std::to_string(t) + "_" + std::to_string(r);
}

void World::setId(std::string i) { this->id = i; }

void World::makeTurn() {
  this->displayGUI();
  this->displayOldState();
  this->sortOrganisms();

  this->handleOrganismsAction();
  this->handleDyingAndBirthing();
  this->increaseAgeOfEachOrganism();

  this->displayNewState();
  this->displayLogs();
}

void World::handleOrganismsAction() {
  for (Organism *organism: this->getOrganisms()) {
    if (!organism->isAlive() || !organism->isBorn()) continue;

    organism->action();

    if (organism->getInitiative() == 0) continue;
    Organism *otherOrganism = this->getOrganismAtPosition(organism,
                                                          organism->getPosition().getX(),
                                                          organism->getPosition().getY());
    if (otherOrganism == nullptr || !otherOrganism->isAlive() || !otherOrganism->isBorn()) continue;

    organism->collision(otherOrganism);
  }
}

void World::handleDyingAndBirthing() {
  std::list<Organism*> organismsCopy = this->getOrganisms();
  for (Organism *organism: organismsCopy) {
    if (!organism->isAlive()) {
      if (organism->getType() == "Human" && this->getHuman()->getAbilityDuration() > 0) {
        this->addLog("Human POS:(%d, %d) escaped death",
                     organism->getPosition().getX(),
                     organism->getPosition().getY());
        organism->setAlive(true);
        continue;
      }
      this->removeOrganism(organism);
    }

    if (!organism->isBorn()) organism->setBorn(true);
  }
}

void World::increaseAgeOfEachOrganism() {
  for (Organism *organism: this->getOrganisms()) {
    organism->setAge(organism->getAge() + 1);
  }
}

void World::startSimulation(Saving* saving) {
  while (true) {
    Human* human = dynamic_cast<Human*>(this->getHuman());

    int keyPressed = GUI::getch();
    if (keyPressed == '\033') {
      if (!this->isHumanAlive()) continue;

      Position humanPosition = this->getHumanPosition();
      GUI::getch();
      switch(GUI::getch()) {
        case 'A': // up arrow
          if (!this->isPositionCorrect(humanPosition.getX(), humanPosition.getY()-1)) continue;
          human->setDirection('u');
          break;
        case 'B': // down arrow
          if (!this->isPositionCorrect(humanPosition.getX(), humanPosition.getY()+1)) continue;
          human->setDirection('d');
          break;
        case 'C': // right arrow
          if (!this->isPositionCorrect(humanPosition.getX()+1, humanPosition.getY())) continue;
          human->setDirection('r');
          break;
        case 'D': // left arrow
          if (!this->isPositionCorrect(humanPosition.getX()-1, humanPosition.getY())) continue;
          human->setDirection('l');
          break;
      }
    } else if (keyPressed == 'q') {
      break;
    } else if (keyPressed == 'a') {
      if (human->getAbilityCooldown() == 0 && human->isAlive()) {
        human->setAbilityDuration(human->getDefaultAbilityDuration());
        human->setAbilityCooldown(human->getDefaultAbilityCooldown());
      }
      continue;
    } else if (keyPressed == 's') {
      saving->save(this->getId() + "_" + std::to_string(this->getDay()-1));
      continue;
    } else if (this->isHumanAlive()) continue;

    this->makeTurn();

    if (human != nullptr) {
      human->setAbilityCooldown(human->getAbilityCooldown() > 0 ? human->getAbilityCooldown() - 1 : 0);
      human->setAbilityDuration(human->getAbilityDuration() > 0 ? human->getAbilityDuration() - 1 : 0);
    }
  }
}

Position World::generateNewPosition(int oldX, int oldY) const {
  int newX = oldX;
  int newY = oldY;

  int direction = rand() % 4;
  switch (direction) {
    case 0:
      if (this->isPositionCorrect(newX, newY-1)) {
        newY--;
        break;
      }
    case 1:
      if (this->isPositionCorrect(newX, newY+1)) {
        newY++;
        break;
      }
    case 2:
      if (this->isPositionCorrect(newX-1, newY)) {
        newX--;
        break;
      }
    case 3:
      if (this->isPositionCorrect(newX+1, newY)) {
        newX++;
        break;
      }
    default:
      break;
  }

  return {newX, newY};
}

Position World::generateNewSafePosition(Organism* organism, int oldX, int oldY) {
  int newX = oldX;
  int newY = oldY;

  int direction = rand() % 4;
  switch (direction) {
    case 0:
      if (this->isPositionCorrect(newX, newY-1) &&
          this->getOrganismAtPosition(organism, newX, newY-1) == nullptr) {
        newY--;
        break;
      }
    case 1:
      if (this->isPositionCorrect(newX, newY+1) &&
          this->getOrganismAtPosition(organism, newX, newY+1) == nullptr) {
        newY++;
        break;
      }
    case 2:
      if (this->isPositionCorrect(newX-1, newY) &&
          this->getOrganismAtPosition(organism, newX-1, newY) == nullptr) {
        newX--;
        break;
      }
    case 3:
      if (this->isPositionCorrect(newX+1, newY) &&
          this->getOrganismAtPosition(organism, newX+1, newY) == nullptr) {
        newX++;
        break;
      }
    default:
      break;
  }

  return {newX, newY};
}

Human* World::getHuman() {
  for (Organism *organism : this->getOrganisms()) {
    if (organism->getType() == "Human") return dynamic_cast<Human*>(organism);
  }
  return nullptr;
}

void World::displayGUI() {
  GUI::clear();
  printf("Kamil Prorok\n");

  this->displayBoard();
  this->displayDay();

  if (this->isHumanAlive()) {
    this->displayAbilityInfo();
  }

  GUI::gotoxy(40, 0);
  printf("ID: %s", this->getId().c_str());
}

void World::displayNewState() {
  this->displayOrganisms(true);
}

void World::displayOldState() {
  this->displayOrganisms(false);
}

void World::displayBoard() const {
  GUI::gotoxy(this->getStartX(), this->getStartY());
  int i;
  for (i = 0; i < this->getWidth()*2 + 3; i++) {
    printf("-");
  }
  printf("\n");

  for (i = 0; i < this->getHeight(); i++) {
    printf("|");
    int j;
    for (j = 0; j < this->getWidth(); j++) {
      printf(" ");
    }
    printf("|");
    for (j = 0; j < this->getWidth(); j++) {
      printf(" ");
    }
    printf("|\n");
  }

  for (i = 0; i < this->getWidth()*2 + 3; i++) {
    printf("-");
  }
}

void World::displayOrganisms(bool newState) {
  for (Organism *organism : this->getOrganisms()) {
    if (newState) {
      GUI::gotoxy(this->getWidth() + 1 + this->getStartX() + organism->getPosition().getX(),
                  this->getStartY() + organism->getPosition().getY());
    } else {
      GUI::gotoxy(this->getStartX() + organism->getPosition().getX(),
                  this->getStartY() + organism->getPosition().getY());
    }
    organism->draw();
  }
}

void World::sortOrganisms() {
  this->organisms.sort([](Organism* a, Organism* b) {
    if (a->getInitiative() > b->getInitiative()) return true;
    if (a->getInitiative() == b->getInitiative() && a->getAge() > b->getAge()) return true;
    return false;
  });
}

Organism* World::getOrganismAtPosition(Organism* skipOrganism, int x, int y) {
  for (Organism *organism : this->getOrganisms()) {
    if (skipOrganism != nullptr && organism->getId() == skipOrganism->getId()) continue;

    if (organism->getPosition().getX() == x && organism->getPosition().getY() == y) {
      return organism;
    }
  }

  return nullptr;
}

void World::addOrganism(Organism *organism) {
  this->organisms.push_back(organism);
}

void World::removeOrganism(Organism *organism) {
  this->organisms.remove(organism);
}

std::list<Organism*>& World::getOrganisms() {
  return this->organisms;
}

void World::addLog(const char* log, ...) {
  va_list args;
  va_start(args, log);

  char logToAdd[256];
  vsprintf(logToAdd, log, args);

  this->logs.emplace(logToAdd);

  va_end(args);
}

void World::displayLogs() {
  int y = 3;
  while (!this->logs.empty()) {
    GUI::gotoxy(this->getWidth()*2 + this->getStartX() + 7, y++);
    if (this->logs.front().find("killed") != std::string::npos) GUI::color(Color::RED);
    else if (this->logs.front().find("attacked") != std::string::npos) GUI::color(Color::ORANGE);
    else if (this->logs.front().find("ate") != std::string::npos) GUI::color(Color::PURPLE);
    else if (this->logs.front().find("moved") != std::string::npos) GUI::color(Color::GRAY);
    else if (this->logs.front().find("scared") != std::string::npos) GUI::color(Color::BLUE);
    else if (this->logs.front().find("deflected") != std::string::npos) GUI::color(Color::BLUE);
    else if (this->logs.front().find("spreaded") != std::string::npos) GUI::color(Color::GREEN);
    else if (this->logs.front().find("breeds") != std::string::npos) GUI::color(Color::YELLOW);

    printf("%s\n", this->logs.front().c_str());
    this->logs.pop();

    GUI::color();
  }
}

void World::displayDay() {
  GUI::gotoxy(0, this->getHeight() + this->getStartY() + 2);
  printf("Day: %d", this->day-1);
  GUI::gotoxy(this->getWidth() + 2, this->getHeight() + this->getStartY() + 2);
  printf("Day: %d", this->day++);
}

void World::displayAbilityInfo() {
  GUI::gotoxy(0, this->getHeight() + this->getStartY() + 3);
  if (this->getHuman()->getAbilityDuration() > 0) {
    printf("Human special ability status: ");
    GUI::color(Color::GREEN);
    printf("ON ");
    GUI::color();
    printf("(%d days left)", this->getHuman()->getAbilityDuration()-1);
  } else {
    printf("Human special ability status: ");
    GUI::color(Color::RED);
    printf("OFF ");
    GUI::color();
    printf("(%d days cooldown)", (this->getHuman()->getAbilityCooldown() == 0) ? 0 : this->getHuman()->getAbilityCooldown()-1);
  }
}

int World::getNextOrganismId() {
  int nextId = nextOrganismId;
  nextOrganismId++;
  return nextId;
}

int World::getWidth() const { return this->width; }
int World::getHeight() const { return this->height; }
int World::getStartX() const { return this->startX; }
int World::getStartY() const { return this->startY; }
bool World::isPositionCorrect(int x, int y) const { return x >= 1 && x <= this->getWidth() && y >= 1 && y <= this->getHeight(); }
int World::getDay() const { return this->day; }
std::string World::getId() const { return this->id; }

void World::setDay(int d) { this->day = d; }

bool World::isHumanAlive() {
  for (Organism *organism : this->getOrganisms()) {
    if (organism->getType() == "Human" && organism->isAlive()) return true;
  }

  return false;
}

Position World::getHumanPosition() {
  for (Organism *organism : this->getOrganisms()) {
    if (organism->getType() == "Human") return organism->getPosition();
  }

  return {-1, -1};
}