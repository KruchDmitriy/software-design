package ru.spbau.mit.view;


import ru.spbau.mit.messages.DeathMessage;
import ru.spbau.mit.messages.Message;
import ru.spbau.mit.messages.ShiftMessage;
import ru.spbau.mit.model.Player;

public class PlayerView extends GameObjectView {
    public PlayerView(Player player, int x, int y, int size) {
        super(player, "file:src/main/resources/player.png", x, y, size);
    }

    @Override
    public void process(Message message) {
        message = applyFilters(message);

        if (message instanceof ShiftMessage) {
            ShiftMessage shiftMessage = (ShiftMessage) message;
            shift(shiftMessage.getDirection(), 1);
        }

        if (message instanceof DeathMessage) {
            stopDrawing();
        }
    }
}
