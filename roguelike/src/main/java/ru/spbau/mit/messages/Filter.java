package ru.spbau.mit.messages;

import ru.spbau.mit.Entity;

public interface Filter {
    Message apply(Message message, Entity receiver);
}
