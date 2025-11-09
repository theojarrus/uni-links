package storage;

public interface TokenStorage {

    boolean exists(String token);

    String create();

    void delete(String token);

    void clear();
}