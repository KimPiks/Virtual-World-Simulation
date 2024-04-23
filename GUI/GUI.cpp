#include <cstdio>
#include "GUI.h"
#include <termios.h>


void GUI::gotoxy(int x, int y) {
  printf("\x1B[%d;%df", y, x);
  fflush(stdout);
}

int GUI::getch() {
  int ch;
  struct termios oldt, newt;
  tcgetattr(0, &oldt);
  newt = oldt;
  newt.c_lflag &= ~(ICANON | ECHO);
  tcsetattr(0, TCSANOW, &newt);
  ch = getchar();
  tcsetattr(0, TCSANOW, &oldt);

  return ch;
}

void GUI::color(Color color) {
  printf("\x1B[%dm", color);
  fflush(stdout);
}

void GUI::color() {
  color(Color::WHITE);
}

void GUI::clear() {
  printf("\x1B[2J\x1B[H");
  fflush(stdout);
}