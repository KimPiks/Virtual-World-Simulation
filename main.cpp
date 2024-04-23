#include <iostream>
#include "World/World.h"
#include "GUI/GUI.h"
#include "Wolf/Wolf.h"
#include "Sheep/Sheep.h"
#include "Fox/Fox.h"
#include "Turtle/Turtle.h"
#include "Antelope/Antelope.h"
#include "Grass/Grass.h"
#include "Milkweed/Milkweed.h"
#include "Guarana/Guarana.h"
#include "NightshadeBerries/NightshadeBerries.h"
#include "SosnowskyHogweed/SosnowskyHogweed.h"
#include "Human/Human.h"
#include "Saving/Saving.h"

int main() {
  GUI::clear();
  printf("Kamil Prorok\n");
  printf("1. New simulation\n");
  printf("2. Load simulation\n");
  printf("3. Exit\n");

  int choice = GUI::getch();
  switch (choice) {
    case '1':
    {
      int mapWidth;
      int mapHeight;

      printf("Enter map width: ");
      scanf("%d", &mapWidth);
      printf("Enter map height: ");
      scanf("%d", &mapHeight);

      World world(mapWidth, mapHeight, BOARD_START_X, BOARD_START_Y);
      Saving saving(&world);

      world.init();
      world.startSimulation(&saving);
      GUI::clear();
      break;
    }
    case '2':
    {
      printf("Enter file name: ");
      std::string fileName;
      std::cin >> fileName;
      Saving s(nullptr);
      World w = s.load(fileName);
      s.setWorld(&w);
      w.init(false);
      w.startSimulation(&s);
      GUI::clear();
      break;
    }
    case '3':
      return 0;
    default:
      return 0;
  }

  return 0;
}
