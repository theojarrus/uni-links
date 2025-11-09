package cli;

public interface CliParser<V> {

    V parse(String input);
}
