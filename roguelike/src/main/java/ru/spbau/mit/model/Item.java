package ru.spbau.mit.model;

import ru.spbau.mit.Entity;
import ru.spbau.mit.messages.Message;
import ru.spbau.mit.view.ItemView;


public abstract class Item extends GameObject {
    public Item(int x, int y) {
        super(x, y);
    }

    @Override
    public abstract String toString();

    @Override
    public void process(Message message) {
        super.process(message);

        for (Entity subscriber : subscribers) {
            if (subscriber instanceof ItemView) {
                ((ItemView) subscriber).stopDrawing();
            }
        }
    }

    public abstract void apply(Player.Characteristics chars);
}
