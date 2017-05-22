package ru.spbau.mit.controller;

import javafx.event.ActionEvent;

import java.util.ArrayList;
import java.util.List;

public class Timer implements Runnable {
    private static final ActionEvent TIME_EVENT = new ActionEvent();
    private final int interval;
    private boolean turnedOff = false;
    private List<GameObjectController> controllers = new ArrayList<>();

    public Timer(int interval) {
        this.interval = interval;
    }

    public void addEventHandler(GameObjectController controller) {
        controllers.add(controller);
    }

    public void turnOff() {
        turnedOff = true;
    }

    @Override
    public void run() {
        while (!turnedOff) {
            try {
                Thread.sleep(interval);
            } catch (InterruptedException e) {
                return;
            }

            for (GameObjectController controller : controllers) {
                controller.handle(TIME_EVENT);
            }
        }
    }
}
