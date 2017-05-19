package ru.spbau.mit.view;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class MapView {
    private final Image mapImage;

    MapView(Pane pane, int width, int height) {
        mapImage = new Image("file:src/main/resources/defaultMapImg.jpg",
                width, height, false, false);

        pane.getChildren().add(new ImageView(mapImage));
    }

    public void draw() {

    }
}
