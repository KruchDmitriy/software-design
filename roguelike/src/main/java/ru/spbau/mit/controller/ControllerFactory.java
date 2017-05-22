package ru.spbau.mit.controller;

import ru.spbau.mit.model.*;

public final class ControllerFactory {
    private ControllerFactory() {}

    public static GameObjectController createController(GameObject gameObject) {
        if (gameObject instanceof Player) {
            return new PlayerController((Player) gameObject);
        }

        if (gameObject instanceof Enemy) {
            return new EnemyController(gameObject);
        }

        if (gameObject instanceof Item) {
            return new ItemController(gameObject);
        }

        if (gameObject instanceof Landscape) {
            return new LandscapeController(gameObject);
        }

        return null;
    }
}
