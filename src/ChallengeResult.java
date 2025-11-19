public class ChallengeResult<T> {
    private final boolean success;
    private final T data;
    private final String message;

    public ChallengeResult(boolean success, T data, String message) {
        this.success = success;
        this.data = data;
        this.message = message;
    }

    public boolean isSuccess() { return success; }
    public T getData() { return data; }
    public String getMessage() { return message; }
}