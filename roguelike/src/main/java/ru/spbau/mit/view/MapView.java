package ru.spbau.mit.view;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class MapView {
    private final Image mapImage;

    MapView(Pane pane, int width, int height) {
        mapImage = new Image("file:src/main/resources/defaultMapImg.jpg",
                width, height, false, false);

        ImageView map = new ImageView(mapImage);

        pane.getChildren().add(map);
    }

    public Image getMapImage() {
        return mapImage;
    }
}
