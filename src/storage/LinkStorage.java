package storage;

import exception.LinkLimitedException;
import exception.LinkNotFoundException;
import exception.LinkOutdatedException;
import model.Link;

import java.util.List;

public interface LinkStorage {

    Link create(String url, int timeLimit, int viewLimit);

    Link get(String key) throws LinkNotFoundException, LinkOutdatedException, LinkLimitedException;

    List<Link> getAll(String owner);

    boolean updateViewLimit(String key, int viewLimit);

    boolean delete(String key);
}
