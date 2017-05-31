package ru.spbau.mit.model.items;

import ru.spbau.mit.model.Item;
import ru.spbau.mit.model.Player;

public class MilleniumSword extends Item {
    private static final int DAMAGE = 50;

    MilleniumSword(int x, int y) {
        super(x, y);
    }

    @Override
    public String toString() {
        return "Millenium sword, increase damage by " + DAMAGE;
    }

    @Override
    public void apply(Player.Characteristics chars) {
        chars.setDamage(chars.getDamage() + DAMAGE);
    }
}
