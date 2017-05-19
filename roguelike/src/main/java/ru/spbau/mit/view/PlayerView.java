package ru.spbau.mit.view;


import ru.spbau.mit.model.Player;

public class PlayerView extends GameObjectView {
    public PlayerView(Player player, int x, int y, int size) {
        super(player, "file:src/main/resources/player.png", x, y, size);
    }

    @Override
    public void draw() {

    }
}
