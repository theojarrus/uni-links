package exception;

import resources.Texts;

public class LinkNotFoundException extends Exception {

    public LinkNotFoundException() {
        super(Texts.ERROR_NOT_FOUND);
    }
}
