package ru.spbau.mit;

import java.util.ArrayList;
import java.util.List;

public abstract class Entity {
    private List<Entity> subscribers = new ArrayList<>();

    public void subscribe(Entity subscriber) {
        subscribers.add(subscriber);
    }

    public void send(Message message) {}

    public void notify(Message message) {
        for (Entity subscriber : subscribers) {
            subscriber.send(message);
        }
    }
}
