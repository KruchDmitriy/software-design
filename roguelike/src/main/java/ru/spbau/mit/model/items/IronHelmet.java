package ru.spbau.mit.model.items;

import ru.spbau.mit.model.Item;
import ru.spbau.mit.model.Player;

public class IronHelmet extends Item {
    private static final int ARMOR = 20;

    public IronHelmet(int x, int y) {
        super(x, y);
    }

    @Override
    public String toString() {
        return "Iron helmet, increase armor by 20";
    }

    @Override
    public void apply(Player.Characteristics chars) {
        chars.setArmor(chars.getArmor() + ARMOR);
    }
}
