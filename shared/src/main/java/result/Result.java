package result;

public record Result(String message) {
    public boolean isSuccess() {
        return "Success".equalsIgnoreCase(message);
    }
}
