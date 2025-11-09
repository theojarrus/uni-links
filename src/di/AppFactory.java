package di;

import app.App;
import auth.TokenHolder;
import auth.TokenHolderImpl;
import storage.LinkStorageImpl;
import storage.TokenStorageImpl;
import storage.ViewStorageImpl;
import time.TimestampCheckerImpl;

import java.util.Scanner;

public class AppFactory {

    public static App create() {
        TokenHolder tokenHolder = new TokenHolderImpl(new TokenStorageImpl());
        return new App(
                new Scanner(System.in),
                new LinkStorageImpl(
                        new ViewStorageImpl(),
                        new TimestampCheckerImpl(),
                        tokenHolder
                ),
                tokenHolder
        );
    }
}
