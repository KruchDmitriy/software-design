package core;

import java.util.HashMap;

/**
 * Class that collect all environment variables.
 */
public class Environment {
    private HashMap<String, String> hashMap = new HashMap<>();

    /**
     * @param name
     * @return value of variable name in environment
     */
    String read(final String name) {
        String value = hashMap.get(name);
        if (value == null) {
            return "";
        }
        return value;
    }

    /**
     * Add variable name with value to the environment
     * @param name
     * @param value
     */
    public void write(final String name, final String value) {
        hashMap.put(name, value);
    }
}
