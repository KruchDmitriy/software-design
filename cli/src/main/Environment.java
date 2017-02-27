package main;

import java.util.HashMap;

public class Environment {
    private HashMap<String, String> hashMap = new HashMap<>();

    public String read(final String name) {
        String value = hashMap.get(name);
        if (value == null) {
            return "";
        }
        return value;
    }

    public void write(final String name, final String value) {
        hashMap.put(name, value);
    }
}
