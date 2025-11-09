package exception;

import resources.Texts;

public class LinkOutdatedException extends Exception {

    public LinkOutdatedException() {
        super(Texts.ERROR_OUTDATED);
    }
}
