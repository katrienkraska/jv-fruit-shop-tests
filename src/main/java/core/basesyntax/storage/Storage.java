package core.basesyntax.storage;

import java.util.HashMap;
import java.util.Map;

public class Storage {
    static final Map<String, Integer> inventory = new HashMap<>();

    public static Map<String, Integer> getInventory() {
        return inventory;
    }
}
