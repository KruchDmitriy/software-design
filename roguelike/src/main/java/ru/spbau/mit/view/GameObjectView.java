package ru.spbau.mit.view;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import ru.spbau.mit.Entity;
import ru.spbau.mit.controller.GameObjectController;
import ru.spbau.mit.model.GameObject;
import ru.spbau.mit.model.ShiftDirection;

public abstract class GameObjectView extends Entity {
    protected ImageView view;
    protected final int size;

    public GameObjectView(GameObject gameObject,
                          String path, int x, int y, int size) {
        gameObject.subscribe(this);
        view = new ImageView(new Image(path, size,
                size, false, false));
        view.setX(x);
        view.setY(y);
        view.setFitWidth(size);
        view.setFitHeight(size);
        this.size = size;
    }

    public void shift(ShiftDirection direction, int times) {
        double x = view.getX();
        double y = view.getY();

        x += size * direction.getX() * times;
        y -= size * direction.getY() * times;

        view.setX(x);
        view.setY(y);
    }

    public ImageView toFXNode() {
        return view;
    }

    public void stopDrawing() {
        view.setVisible(false);
    }
}
