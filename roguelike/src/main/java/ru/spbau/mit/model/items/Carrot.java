package ru.spbau.mit.model.items;

import ru.spbau.mit.model.Item;
import ru.spbau.mit.model.Player;

public class Carrot extends Item {
    private static final int HEALING = 20;

    public Carrot(int x, int y) {
        super(x, y);
    }

    @Override
    public String toString() {
        return "Carrot, increase health by 20";
    }

    @Override
    public void apply(Player.Characteristics chars) {
        chars.setHealth(chars.getHealth() + HEALING);
    }
}
