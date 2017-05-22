package ru.spbau.mit.controller;

import javafx.event.Event;
import javafx.event.EventHandler;
import ru.spbau.mit.Entity;
import ru.spbau.mit.model.GameObject;

public class GameObjectController extends Entity implements EventHandler<Event> {
    public GameObjectController(GameObject object) {
        subscribe(object);
    }

    @Override
    public void handle(Event event) {

    }
}
