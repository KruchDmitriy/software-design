package ru.spbau.mit.model;

import ru.spbau.mit.model.items.ItemFactory;

public final class ObjectFactory {
    private ObjectFactory() {}

    public static GameObject createObject(World.LoadObjectInfo info) {
        switch (info.kindObject) {
            case PLAYER:
                return new Player(info.x, info.y);
            case ENEMY:
                return new Enemy(info.x, info.y);
            case ITEM:
                return ItemFactory.createItem(info.x, info.y);
            case LANDSCAPE:
                return new Landscape(info.x, info.y);
            default:
                return null;
        }
    }
}
