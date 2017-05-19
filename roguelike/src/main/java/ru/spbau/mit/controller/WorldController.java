package ru.spbau.mit.controller;

import ru.spbau.mit.model.World;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class WorldController {
    private List<GameObjectController> controllers = new ArrayList<>();

    public WorldController(World world) {
        controllers = world.getObjects()
                .stream()
                .map(ControllerFactory::createController)
                .collect(Collectors.toList());
    }
}
