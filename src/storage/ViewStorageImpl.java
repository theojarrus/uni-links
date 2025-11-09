package storage;

import java.util.HashMap;

public class ViewStorageImpl implements ViewStorage {

    private final HashMap<String, Integer> storage = new HashMap<>();

    @Override
    public boolean validate(String link, int viewLimit) {
        int views = storage.getOrDefault(link, 0) + 1;
        if (views <= viewLimit) {
            storage.put(link, views);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean remove(String link) {
        return storage.remove(link) != null;
    }

    @Override
    public void clear() {
        storage.clear();
    }
}
