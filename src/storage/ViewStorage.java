package storage;

import model.Link;

public interface ViewStorage {

    boolean validate(String link, int viewLimit);

    boolean remove(String link);

    void clear();

    default boolean validate(Link link) {
        return validate(link.getKey(), link.getViewLimit());
    }

    default boolean remove(Link link) {
        return remove(link.getKey());
    }
}
