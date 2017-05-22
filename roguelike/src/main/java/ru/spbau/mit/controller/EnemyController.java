package ru.spbau.mit.controller;

import javafx.event.ActionEvent;
import javafx.event.Event;
import ru.spbau.mit.messages.ShiftMessage;
import ru.spbau.mit.model.GameObject;
import ru.spbau.mit.model.ShiftDirection;

import java.util.Random;

public class EnemyController extends GameObjectController {
    private static final Random RNG = new Random();

    public EnemyController(GameObject object) {
        super(object);
    }

    @Override
    public void handle(Event event) {
        if (event instanceof ActionEvent) {
            boolean upDown = RNG.nextBoolean();

            if (upDown) {
                if (RNG.nextBoolean()) {
                    notify(new ShiftMessage(ShiftDirection.UP));
                } else {
                    notify(new ShiftMessage(ShiftDirection.DOWN));
                }
            } else {
                if (RNG.nextBoolean()) {
                    notify(new ShiftMessage(ShiftDirection.LEFT));
                } else {
                    notify(new ShiftMessage(ShiftDirection.RIGHT));
                }
            }
        }
    }
}
