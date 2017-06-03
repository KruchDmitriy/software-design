package ru.spbau.mit;

import ru.spbau.mit.messages.Filter;
import ru.spbau.mit.messages.Message;

import java.util.ArrayList;
import java.util.List;

public abstract class Entity {
    protected List<Entity> subscribers = new ArrayList<>();
    protected List<Filter> filters = new ArrayList<>();

    public void subscribe(Entity subscriber) {
        subscribers.add(subscriber);
    }

    public void process(Message message) {}

    protected Message applyFilters(Message message) {
        for (Filter filter : filters) {
            message = filter.apply(message, this);
        }

        return message;
    }

    public void addFilter(Filter filter) {
        filters.add(filter);
    }

    public void notify(Message message) {
        for (Entity subscriber : subscribers) {
            subscriber.process(message);
        }
    }
}
