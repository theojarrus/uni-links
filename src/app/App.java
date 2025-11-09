package app;

import auth.TokenHolder;
import cli.*;
import model.Link;
import resources.Texts;
import storage.LinkStorage;

import java.awt.Desktop;
import java.net.URI;
import java.util.List;
import java.util.Scanner;

public class App {

    private final Scanner scanner;
    private final LinkStorage linkStorage;
    private final TokenHolder tokenHolder;

    public App(Scanner scanner, LinkStorage linkStorage, TokenHolder tokenHolder) {
        this.scanner = scanner;
        this.linkStorage = linkStorage;
        this.tokenHolder = tokenHolder;
    }

    public void run() {
        print(Texts.DELIMITER, Texts.TITLE, Texts.INFO);
        printStatus(null);
        read(true, Texts.ACTION, new IntCliParser(), input -> {
            switch (input) {
                case 1:
                    print(Texts.INFO);
                    printStatus(null);
                    break;
                case 2:
                    openLink();
                    break;
                case 3:
                    shortLink();
                    break;
                case 4:
                    controlLink();
                    break;
                case 5:
                    login();
                    break;
                case 6:
                    logout();
                    break;
                default:
                    print(Texts.INFO);
                    break;
            }
        });
    }

    private <V> void read(boolean infinite, String title, CliParser<V> parser, CliHandler<V> handler) {
        while (true) {
            print(Texts.DELIMITER);
            printInline(title, Texts.CANCEL);
            print(Texts.INPUT);
            try {
                String input = scanner.next();
                try {
                    if (new IntCliParser().parse(input) == 0) break;
                } catch (Exception ignored) {
                }
                handler.handle(parser.parse(input));
                if (!infinite) break;
            } catch (Exception e) {
                print(Texts.INCORRECT);
            }
        }
    }

    private void openLink() {
        read(false, Texts.OPEN_LINK, new UrlCliParser(), input -> {
            try {
                String link = linkStorage.get(input).getUrl();
                if (link != null) Desktop.getDesktop().browse(URI.create(link));
            } catch (Exception e) {
                print(e.getMessage());
            }
        });
    }

    private void shortLink() {
        read(false, Texts.SHORT_LINK, new UrlCliParser(), url -> {
            read(false, Texts.TIME_LIMIT, new IntCliParser(), timeLimit -> {
                read(false, Texts.VIEW_LIMIT, new IntCliParser(), viewLimit -> {
                    Link link = linkStorage.create(url, timeLimit, viewLimit);
                    printLink(link);
                    printStatus(tokenHolder.getToken());
                });
            });
        });
    }

    private void controlLink() {
        String token = tokenHolder.getToken();
        if (token != null) {
            List<Link> links = linkStorage.getAll(token);
            if (!links.isEmpty()) {
                for (int i = 0; i < links.size(); i++) {
                    Link link = links.get(i);
                    printInline(i + ".", link.getKey(), "(" + link.getUrl() + ")");
                }
                read(true, Texts.EDIT_LINK, new IntCliParser(), input -> {

                });
            } else {
                print(Texts.NO_LINKS);
            }
        } else {
            printStatus(null);
        }
    }

    private void login() {
        read(false, Texts.LOGIN, new StringCliParser(), input -> {
            if (tokenHolder.login(input)) {
                printStatus(input);
            } else {
                print(Texts.USER_NOT_FOUND);
            }
        });
    }

    private void logout() {
        tokenHolder.logout();
        print(Texts.LOGOUT);
    }

    private void printLink(Link link) {
        print(
                Texts.LINK_SHORTENED,
                link.getKey(),
                Texts.LINK_FULL,
                link.getUrl(),
                Texts.LINK_OWNER,
                link.getOwner(),
                Texts.LINK_TIMESTAMP,
                String.valueOf(link.getTimestamp()),
                Texts.LINK_TIME_LIMIT,
                String.valueOf(link.getTimeLimit()),
                Texts.LINK_VIEW_LIMIT,
                String.valueOf(link.getViewLimit())
        );
    }

    private void printStatus(String token) {
        print(token != null ? String.format(Texts.AUTHORIZED, token) : Texts.NOT_AUTHORIZED);
    }


    private void printInline(String... texts) {
        System.out.println(String.join(" ", texts));
    }

    private void print(String... texts) {
        System.out.println(String.join("\n", texts));
    }
}
