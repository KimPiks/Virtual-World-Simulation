package Human;

public enum MoveDirection {
    None,

    // Only in rectangular variant
    Up,
    Down,

    // Only in hex variant
    UP_LEFT,
    UP_RIGHT,
    DOWN_LEFT,
    DOWN_RIGHT,

    // In both variants
    Left,
    Right
}
