package ru.spbau.mit.model.items;

import ru.spbau.mit.model.Item;
import ru.spbau.mit.model.Player;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Rubbish extends Item {
    private static final List<String> POSSIBLE_NAMES = Arrays.asList(
            "strange pi-pi",
            "watches",
            "bone"
    );

    public Rubbish(int x, int y) {
        super(x, y);
    }

    @Override
    public String toString() {
        return POSSIBLE_NAMES.get((new Random()).nextInt(POSSIBLE_NAMES.size()));
    }

    @Override
    public void apply(Player.Characteristics chars) {}
}
