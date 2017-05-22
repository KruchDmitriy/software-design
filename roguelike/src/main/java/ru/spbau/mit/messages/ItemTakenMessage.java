package ru.spbau.mit.messages;

import ru.spbau.mit.model.Item;

public class ItemTakenMessage implements Message {
    private final Item item;

    public ItemTakenMessage(Item item) {
        this.item = item;
    }

    public Item getItem() {
        return item;
    }
}
