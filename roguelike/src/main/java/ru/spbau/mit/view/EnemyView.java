package ru.spbau.mit.view;

import ru.spbau.mit.model.Enemy;

public class EnemyView extends GameObjectView {
    public EnemyView(Enemy enemy, int x, int y, int size) {
        super(enemy, "file:src/main/resources/enemy.png", x, y, size);
    }

    @Override
    public void draw() {

    }
}
