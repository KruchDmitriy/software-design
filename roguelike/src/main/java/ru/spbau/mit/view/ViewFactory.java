package ru.spbau.mit.view;
import ru.spbau.mit.model.*;


public final class ViewFactory {
    private ViewFactory() {}

    public static GameObjectView createView(GameObject gameObject,
                                            int x, int y, int size) {
        if (gameObject instanceof Player) {
            return new PlayerView((Player) gameObject, x, y, size);
        }

        if (gameObject instanceof Enemy) {
            return new EnemyView((Enemy) gameObject, x, y, size);
        }

        if (gameObject instanceof Item) {
            return new ItemView((Item) gameObject, x, y, size);
        }

        if (gameObject instanceof Landscape) {
            return new LandscapeView((Landscape) gameObject, x, y, size);
        }

        return null;
    }
}
