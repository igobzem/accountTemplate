package account.exceptions;

import java.time.LocalDateTime;

public class CustomErrorMessage {
    private int status;
    private LocalDateTime timestamp;
    private String message;
    private String path;
    private String error;

    public CustomErrorMessage(
            int status,
            String error,
            LocalDateTime timestamp,
            String message,
            String path) {

        this.status = status;
        this.error = error;
        this.timestamp = timestamp;
        this.message = message;
        this.path = path;
    }

    public CustomErrorMessage() {
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
