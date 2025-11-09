package auth;

public interface TokenHolder {

    String getToken();

    boolean login(String token);

    void logout();
}
