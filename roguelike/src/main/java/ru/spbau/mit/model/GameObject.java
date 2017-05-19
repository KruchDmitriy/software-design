package ru.spbau.mit.model;

import ru.spbau.mit.Entity;

public abstract class GameObject extends Entity {
    private int x;
    private int y;

    GameObject(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getX() {
        return x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getY() {
        return y;
    }
}
