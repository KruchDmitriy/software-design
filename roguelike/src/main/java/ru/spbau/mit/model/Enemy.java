package ru.spbau.mit.model;

import ru.spbau.mit.messages.DeathMessage;
import ru.spbau.mit.messages.FightMessage;
import ru.spbau.mit.messages.Message;
import ru.spbau.mit.messages.ShiftMessage;

import java.util.HashMap;
import java.util.Map;

public class Enemy extends GameObject {
    public class Characteristics {
        private static final int DEFAULT_HEALTH = 100;
        private static final int DEFAULT_ARMOR = 100;
        private static final int DEFAULT_DAMAGE = 5;

        private int health = DEFAULT_HEALTH;
        private int armor = DEFAULT_ARMOR;
        private int damage = DEFAULT_DAMAGE;

        public Characteristics() {}

        public Map<String, String> getChars() {
            Map<String, String> chars = new HashMap<>();
            chars.put("health", Integer.toString(health));
            chars.put("armor", Integer.toString(armor));
            return chars;
        }

        public int getDamage() {
            return damage;
        }
    }

    private static final String NAME = "Fox";
    private Characteristics chars = new Characteristics();

    Enemy(int x, int y) {
        super(x, y);
    }

    public int getDamage() {
        return chars.getDamage();
    }

    public int getHealth() {
        return chars.health;
    }

    @Override
    public String toString() {
        return NAME;
    }

    @Override
    public void process(Message message) {
        message = applyFilters(message);

        if (message instanceof ShiftMessage) {
            ShiftMessage shiftMessage = (ShiftMessage) message;
            shift(shiftMessage.getDirection());
            notify(shiftMessage);
            return;
        }

        if (message instanceof FightMessage) {
            chars.health -= ((FightMessage) message).getDamage();
            notify(message);
        }

        if (message instanceof DeathMessage) {
            notify(message);
        }
    }
}
