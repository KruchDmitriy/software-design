package ru.spbau.mit.messages;

import ru.spbau.mit.model.ShiftDirection;

public class ShiftMessage implements Message {
    private ShiftDirection direction;

    public ShiftMessage(ShiftDirection direction) {
        this.direction = direction;
    }

    public ShiftDirection getDirection() {
        return direction;
    }

    public void setDirection(ShiftDirection direction) {
        this.direction = direction;
    }
}
