package cli;

public class IntCliParser implements CliParser<Integer> {

    @Override
    public Integer parse(String input) {
        return Integer.parseInt(input);
    }
}
