package cli;

public class StringCliParser implements CliParser<String> {

    @Override
    public String parse(String input) {
        return input;
    }
}
