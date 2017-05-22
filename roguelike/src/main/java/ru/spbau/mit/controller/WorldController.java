package ru.spbau.mit.controller;

import javafx.event.EventType;
import javafx.scene.Scene;
import ru.spbau.mit.Entity;
import ru.spbau.mit.model.World;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class WorldController extends Entity {
    private static final Timer TIMER = new Timer(500);
    private List<GameObjectController> controllers = new ArrayList<>();

    public WorldController(World world, Scene scene) {
        controllers = world.getObjects()
                .stream()
                .map(ControllerFactory::createController)
                .peek(controller -> scene.addEventHandler(EventType.ROOT, controller))
                .collect(Collectors.toList());

        controllers.stream()
                .filter(controller -> controller instanceof EnemyController)
                .forEach(TIMER::addEventHandler);

        new Thread(TIMER).start();
    }
}
