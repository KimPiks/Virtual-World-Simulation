package Human;

public enum MoveDirection {
    NONE,

    // Only in rectangular variant
    UP,
    DOWN,

    // Only in hex variant
    UP_LEFT,
    UP_RIGHT,
    DOWN_LEFT,
    DOWN_RIGHT,

    // In both variants
    LEFT,
    RIGHT
}
