package ru.spbau.mit.model;

public enum ShiftDirection {
    UP, RIGHT, LEFT, DOWN, NO_SHIFT;

    public int getX() {
        if (this == UP || this == DOWN || this == NO_SHIFT) {
            return 0;
        }

        if (this == RIGHT)
            return 1;

        return -1;
    }

    public int getY() {
        if (this == LEFT || this == RIGHT || this == NO_SHIFT) {
            return 0;
        }

        if (this == UP)
            return 1;

        return -1;
    }
}
