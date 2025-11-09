package storage;

import app.Config;
import auth.TokenHolder;
import exception.LinkLimitedException;
import exception.LinkNotFoundException;
import exception.LinkOutdatedException;
import model.Link;
import time.TimestampChecker;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class LinkStorageImpl implements LinkStorage {

    private final ViewStorage viewStorage;
    private final TimestampChecker timestampChecker;
    private final TokenHolder tokenHolder;

    private final HashMap<String, Link> storage = new HashMap<>();

    public LinkStorageImpl(ViewStorage viewStorage, TimestampChecker timestampChecker, TokenHolder tokenHolder) {
        this.viewStorage = viewStorage;
        this.timestampChecker = timestampChecker;
        this.tokenHolder = tokenHolder;
    }

    @Override
    public Link create(String url, int timeLimit, int viewLimit) {
        String key = createKey();
        String owner = tokenHolder.getToken();
        long timestamp = timestampChecker.now();
        Link link = new Link(key, url, owner, timestamp, Math.max(0, timeLimit), Math.max(0, viewLimit));
        storage.put(key, link);
        return link;
    }

    @Override
    public Link get(String key) throws LinkNotFoundException, LinkOutdatedException, LinkLimitedException {
        Link link = storage.get(key);

        if (link == null) {
            throw new LinkNotFoundException();
        }

        if (!timestampChecker.active(link)) {
            throw new LinkOutdatedException();
        }

        if (!viewStorage.validate(link)) {
            storage.remove(link.getKey());
            throw new LinkLimitedException();
        }

        return link;
    }

    @Override
    public List<Link> getAll(String owner) {
        return storage.values().stream().filter(link -> link.getOwner().equals(owner)).toList();
    }

    @Override
    public boolean updateViewLimit(String key, int viewLimit) {
        Link link = storage.get(key);
        if (validateOwner(link)) {
            link.setViewLimit(Math.max(viewLimit, 0));
            return storage.put(key, link) != null;
        } else {
            return false;
        }
    }

    @Override
    public boolean delete(String key) {
        Link link = storage.get(key);
        if (validateOwner(link)) {
            return storage.remove(key) != null;
        } else {
            return false;
        }
    }

    private String createKey() {
        String key;
        do key = UUID.randomUUID().toString().substring(0, 7); while (storage.containsKey(key));
        return Config.BASE_URL + key;
    }

    private boolean validateOwner(Link link) {
        return link != null && link.getOwner().equals(tokenHolder.getToken());
    }
}
