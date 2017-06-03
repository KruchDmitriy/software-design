package ru.spbau.mit.model;

import ru.spbau.mit.Entity;

public abstract class GameObject extends Entity {
    protected Position position;

    GameObject(int x, int y) {
        position = new Position(x, y);
    }

    public void setX(int x) {
        position.x = x;
    }

    public int getX() {
        return position.x;
    }

    public void setY(int y) {
        position.y = y;
    }

    public int getY() {
        return position.y;
    }

    public void shift(ShiftDirection direction) {
        position.shift(direction);
    }

    public static class Position {
        public int x;
        public int y;

        Position(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public int hashCode() {
            return Integer.hashCode(x) + Integer.hashCode(y);
        }

        @Override
        public boolean equals(Object object) {
            if (object instanceof Position) {
                Position pos = ((Position) object);
                return x == pos.x && y == pos.y;
            }

            return false;
        }

        public void shift(ShiftDirection direction) {
            if (direction == ShiftDirection.DOWN) {
                y += 1;
            } else if (direction == ShiftDirection.UP) {
                y -= 1;
            } else if (direction == ShiftDirection.LEFT) {
                x -= 1;
            } else if (direction == ShiftDirection.RIGHT) {
                x += 1;
            }
        }
    }

    public Position getPosition() {
        return new Position(position.x, position.y);
    }
}
