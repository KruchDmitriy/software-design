package ru.spbau.mit.view;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import ru.spbau.mit.Entity;
import ru.spbau.mit.messages.GameOver;
import ru.spbau.mit.messages.Message;
import ru.spbau.mit.model.GameObject;
import ru.spbau.mit.model.Player;
import ru.spbau.mit.model.World;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class WorldView extends Entity {
    private final int cellSize;
    private final int windowSize;

    private Pane root;
    private Group objectsGroup = new Group();
    private MapView mapView;
    private LogView logView;

    private int width;
    private int height;

    private List<GameObjectView> objects = new ArrayList<>();

    private ImageView gameOver = new ImageView(
            new Image("file:src/main/resources/gameOver.png"));

    public WorldView(final World world, int windowSize, int mapSize) {
        this.windowSize = windowSize;
        world.subscribe(this);

        root = new Pane();
        mapView = new MapView(root, windowSize, windowSize);
        logView = new LogView(windowSize);

        width = windowSize + logView.getWidth();
        height = windowSize;

        gameOver.setX(0.);
        gameOver.setY(0.);
        gameOver.setFitHeight(height);
        gameOver.setFitWidth(width);

        root.setPrefSize(width, height);
        root.getChildren().add(logView.toFXNode());

        cellSize = windowSize / mapSize;

        createContent(world);
    }

    public Pane getRoot() {
        return root;
    }

    private void createContent(final World world) {
        List<GameObject> gameObjects = world.getObjects();

        for (int i = 0; i < gameObjects.size(); i++) {
            GameObject object = gameObjects.get(i);
            objects.add(ViewFactory.createView(object,
                    object.getX() * cellSize,
                    object.getY() * cellSize,
                    cellSize));
            object.subscribe(logView);

            if (object instanceof Player) {
                ((Player) object).sendStatus();
            }
        }

        objectsGroup.getChildren().addAll(objects
                .stream()
                .map(GameObjectView::toFXNode)
                .collect(Collectors.toList()));

        root.getChildren().add(objectsGroup);
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    @Override
    public void process(Message message) {
        super.process(message);

        if (message instanceof GameOver) {
            root.getChildren().clear();
            root.getChildren().add(gameOver);
        }
    }
}
