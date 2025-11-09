package cli;

import java.net.URI;

public class UrlCliParser implements CliParser<String> {

    @Override
    public String parse(String input) {
        return URI.create(input).toString();
    }
}
