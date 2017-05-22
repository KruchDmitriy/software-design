package ru.spbau.mit.messages;

import ru.spbau.mit.model.GameObject;

public class DeathMessage implements Message {
    private final GameObject who;

    public DeathMessage(GameObject who) {
        this.who = who;
    }

    public GameObject getWho() {
        return who;
    }
}
