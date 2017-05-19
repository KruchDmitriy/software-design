package ru.spbau.mit.view;

import ru.spbau.mit.model.Item;

public class ItemView extends GameObjectView {
    public ItemView(Item item, int x, int y, int size) {
        super(item, "file:src/main/resources/item.png", x, y, size);
    }

    @Override
    public void draw() {

    }
}
