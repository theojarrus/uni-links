package auth;

import storage.TokenStorage;

public class TokenHolderImpl implements TokenHolder {

    private final TokenStorage tokenStorage;

    private String token = null;

    public TokenHolderImpl(TokenStorage tokenStorage) {
        this.tokenStorage = tokenStorage;
    }

    @Override
    public String getToken() {
        if (token != null && tokenStorage.exists(token)) {
            return token;
        } else {
            String token = tokenStorage.create();
            this.token = token;
            return token;
        }
    }

    @Override
    public boolean login(String token) {
        if (token != null && tokenStorage.exists(token)) {
            this.token = token;
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void logout() {
        this.token = null;
    }
}
