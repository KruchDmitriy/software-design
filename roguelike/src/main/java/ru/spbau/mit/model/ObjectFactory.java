package ru.spbau.mit.model;

public final class ObjectFactory {
    private ObjectFactory() {}

    public static GameObject createObject(World.LoadObjectInfo info) {
        switch (info.kindObject) {
            case PLAYER:
                return new Player(info.x, info.y);
            case ENEMY:
                return new Enemy(info.x, info.y);
            case ITEM:
                return new Item(info.x, info.y);
            case LANDSCAPE:
                return new Landscape(info.x, info.y);
            default:
                return null;
        }
    }
}
