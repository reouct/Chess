package result;

public record AuthResult(String username, String authToken, String message) {
    public boolean isSuccess() {
        return message == null;
    }

    public String getAuthToken() {
        return authToken;
    }

    public String getUsername() {
        return username;
    }
}
