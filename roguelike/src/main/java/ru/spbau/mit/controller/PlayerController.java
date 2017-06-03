package ru.spbau.mit.controller;

import javafx.event.Event;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import ru.spbau.mit.messages.ShiftMessage;
import ru.spbau.mit.model.Player;
import ru.spbau.mit.model.ShiftDirection;

public class PlayerController extends GameObjectController {
    private static boolean keyIsPressed = false;

    PlayerController(Player player) {
        super(player);
    }

    @Override
    public void handle(Event event) {
        if (event instanceof KeyEvent) {
            KeyEvent keyEvent = (KeyEvent) event;
            keyIsPressed = !keyIsPressed;

            if (!keyIsPressed) {
                return;
            }

            if (keyEvent.getCode() == KeyCode.LEFT) {
                notify(new ShiftMessage(ShiftDirection.LEFT));
            } else if (keyEvent.getCode() == KeyCode.RIGHT) {
                notify(new ShiftMessage(ShiftDirection.RIGHT));
            } else if (keyEvent.getCode() == KeyCode.DOWN) {
                notify(new ShiftMessage(ShiftDirection.DOWN));
            } else if (keyEvent.getCode() == KeyCode.UP) {
                notify(new ShiftMessage(ShiftDirection.UP));
            }
        }
    }
}
