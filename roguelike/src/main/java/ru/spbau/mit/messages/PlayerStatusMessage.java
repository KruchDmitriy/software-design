package ru.spbau.mit.messages;

import java.util.Map;

public class PlayerStatusMessage implements Message {
    private Map<String, String> chars;

    public PlayerStatusMessage(Map<String, String> chars) {
        this.chars = chars;
    }

    public Map<String, String> getChars() {
        return chars;
    }
}
