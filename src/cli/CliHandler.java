package cli;

public interface CliHandler<V> {

    void handle(V input);
}
