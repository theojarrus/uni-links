package exception;

import resources.Texts;

public class LinkLimitedException extends Exception {

    public LinkLimitedException() {
        super(Texts.ERROR_LIMITED);
    }
}
