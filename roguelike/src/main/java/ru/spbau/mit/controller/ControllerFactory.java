package ru.spbau.mit.controller;

import ru.spbau.mit.model.*;
import ru.spbau.mit.view.*;

public final class ControllerFactory {
    private ControllerFactory() {}

    public static GameObjectController createController(GameObject gameObject) {
        if (gameObject instanceof Player) {
            return new PlayerController((Player) gameObject);
        }

        if (gameObject instanceof Enemy) {
            return new EnemyController((Enemy) gameObject);
        }

        if (gameObject instanceof Item) {
            return new ItemController((Item) gameObject);
        }

        if (gameObject instanceof Landscape) {
            return new LandscapeController((Landscape) gameObject);
        }

        return null;
    }
}
