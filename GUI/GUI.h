#ifndef VIRTUALWORLD_GUI_H
#define VIRTUALWORLD_GUI_H


enum Color {
  RED = 31,
  GREEN = 32,
  BLUE = 34,
  YELLOW = 33,
  WHITE = 37,
  GRAY = 90,
  PURPLE = 35,
  ORANGE = 91
};

class GUI {
public:
  static void gotoxy(int x, int y);
  static int getch();
  static void color(Color color);
  static void color();
  static void clear();
};


#endif
