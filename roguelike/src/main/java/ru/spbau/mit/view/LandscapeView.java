package ru.spbau.mit.view;

import ru.spbau.mit.model.Landscape;

public class LandscapeView extends GameObjectView {
    public LandscapeView(Landscape landscape,
                         int x, int y, int size) {
        super(landscape, "file:src/main/resources/tree.png", x, y, size);
    }
}
