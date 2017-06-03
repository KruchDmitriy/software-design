package ru.spbau.mit.model.items;

import ru.spbau.mit.model.Item;
import ru.spbau.mit.model.Player;

import java.util.Random;

public final class ItemFactory {
    private static final Random RNG = new Random();

    private static final int COUNT_POSSIBLE_SITEMS = 7;

    private ItemFactory() {}

    public static Item createItem(int x, int y) {
        switch (RNG.nextInt(COUNT_POSSIBLE_SITEMS)) {
            case 0:
                return new MilleniumSword(x, y);
            case 1:
                return new Carrot(x, y);
            case 2:
                return new IronHelmet(x, y);
            default:
                return new Rubbish(x, y);
        }
    }
}
