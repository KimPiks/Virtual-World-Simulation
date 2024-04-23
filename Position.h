#ifndef VIRTUALWORLD_POSITION_H
#define VIRTUALWORLD_POSITION_H

struct Position {
  int x;
  int y;

  Position(int x, int y) {
    this->x = x;
    this->y = y;
  }
  void set(int x, int y) {
    this->x = x;
    this->y = y;
  }

  int getX() const {
    return x;
  }

  int getY() const {
    return y;
  }

  bool operator==(const Position &pos) const {
    return x == pos.x &&
           y == pos.y;
  }
};

#endif
