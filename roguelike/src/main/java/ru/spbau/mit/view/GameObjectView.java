package ru.spbau.mit.view;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import ru.spbau.mit.Entity;
import ru.spbau.mit.model.GameObject;

public abstract class GameObjectView extends Entity {
    private ImageView view;

    public GameObjectView(GameObject gameObject, String path, int x, int y, int size) {
        gameObject.subscribe(this);
        view = new ImageView(new Image(path, size,
                size, false, false));
        view.setX(x);
        view.setY(y);
        view.setFitWidth(size);
        view.setFitHeight(size);
    }

    public abstract void draw();

    public ImageView toImageView() {
        return view;
    }
}
