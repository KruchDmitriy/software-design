package ru.spbau.mit.model;

import ru.spbau.mit.messages.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Player extends GameObject {
    public static final String NAME = "Raccoon";

    public class Characteristics {
        private static final int DEFAULT_HEALTH = 100;
        private static final int DEFAULT_ARMOR = 100;
        private static final int DEFAULT_DAMAGE = 10;

        private int health = DEFAULT_HEALTH;
        private int armor = DEFAULT_ARMOR;
        private int damage = DEFAULT_DAMAGE;

        public Characteristics() {}

        public Map<String, String> getChars() {
            Map<String, String> chars = new HashMap<>();
            chars.put("damage", Integer.toString(damage));
            chars.put("armor", Integer.toString(armor));
            chars.put("health", Integer.toString(health));
            return chars;
        }

        public int getDamage() {
            return damage;
        }

        public int getHealth() {
            return health;
        }

        public int getArmor() {
            return armor;
        }

        public void setDamage(int damage) {
            this.damage = damage;
        }

        public void setHealth(int health) {
            this.health = health;
        }

        public void setArmor(int armor) {
            this.armor = armor;
        }
    }

    private Characteristics chars = new Characteristics();
    private List<Item> takenItems = new ArrayList<>();

    Player(int x, int y) {
        super(x, y);
    }

    public void sendStatus() {
        notify(new PlayerStatusMessage(chars.getChars()));
    }

    @Override
    public String toString() {
        return NAME;
    }

    public int getDamage() {
        return chars.getDamage();
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

        if (message instanceof ItemTakenMessage) {
            ItemTakenMessage itemMsg = (ItemTakenMessage) message;
            Item item = itemMsg.getItem();
            takenItems.add(item);
            item.apply(chars);
            notify(itemMsg);
            notify(new PlayerStatusMessage(chars.getChars()));
        }

        if (message instanceof FightMessage) {
            if (chars.armor > 0) {
                chars.armor -= ((FightMessage) message).getDamage();
            } else {
                chars.health -= ((FightMessage) message).getDamage();
            }
            notify(new PlayerStatusMessage(chars.getChars()));

            if (chars.health < 0) {
                notify(new DeathMessage(this));
                notify(new GameOver());
            }
        }
    }
}
