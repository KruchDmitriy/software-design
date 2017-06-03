package ru.spbau.mit.messages;

import ru.spbau.mit.model.ShiftDirection;

public class ShiftMessage implements Message, Comparable {
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

    @Override
    public int compareTo(Object o) {
        if (o instanceof ShiftMessage) {
            ShiftMessage shiftMessage = (ShiftMessage) o;
            if (shiftMessage.direction == direction) {
                return 0;
            } else {
                return 1;
            }
        }

        return 1;
    }
}
