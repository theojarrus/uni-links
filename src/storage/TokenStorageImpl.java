package storage;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class TokenStorageImpl implements TokenStorage {

    private final Set<String> storage = new HashSet<>();

    @Override
    public boolean exists(String token) {
        return storage.contains(token);
    }

    @Override
    public String create() {
        String token;
        do token = UUID.randomUUID().toString(); while (exists(token));
        storage.add(token);
        return token;
    }

    @Override
    public void delete(String token) {
        storage.remove(token);
    }

    @Override
    public void clear() {
        storage.clear();
    }
}
