package ru.spbau.mit.controller;

import ru.spbau.mit.Entity;
import ru.spbau.mit.model.GameObject;

public class GameObjectController extends Entity {
    public GameObjectController(GameObject object) {
        subscribe(object);
    }
}
