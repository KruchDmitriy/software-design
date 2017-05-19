package ru.spbau.mit.view;

import javafx.scene.Group;
import javafx.scene.layout.Pane;
import ru.spbau.mit.model.World;

import java.util.List;
import java.util.stream.Collectors;

public class WorldView {
    private final int cellSize;

    private Pane root;
    private Group objectsGroup = new Group();
    private MapView mapView;
    private World world;

    private List<GameObjectView> objects;

    public WorldView(World world, int windowSize, int mapSize) {
        root = new Pane();
        root.setPrefSize(windowSize, windowSize);
        mapView = new MapView(root, windowSize, windowSize);

        cellSize = windowSize / mapSize;

        this.world = world;
        createContent();
    }

    public Pane getRoot() {
        return root;
    }

    public void draw() {
        mapView.draw();

        for (GameObjectView object : objects) {
            object.draw();
        }
    }

    private void createContent() {
        objects = world.getObjects().stream()
                .map(object -> ViewFactory.createView(
                        object,
                        object.getX() * cellSize,
                        object.getY() * cellSize,
                        cellSize))
                .collect(Collectors.toList());

        objectsGroup.getChildren().addAll(objects
                .stream()
                .map(GameObjectView::toImageView)
                .collect(Collectors.toList()));

        root.getChildren().add(objectsGroup);
    }
}
