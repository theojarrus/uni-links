package cli;

import java.net.URI;

public class UrlCliParser implements CliParser<String> {

    @Override
    public String parse(String input) throws Exception {
        return new URI(input).toURL().toString();
    }
}
