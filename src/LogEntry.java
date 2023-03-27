import java.time.LocalDateTime;

public class LogEntry {
    private final int userId;
    private final LocalDateTime datetime;
    private final String severity;
    private final String message;
    private final int requestId;

    public LogEntry(int userId, LocalDateTime datetime, String severity, String message, int requestId) {
        this.userId = userId;
        this.datetime = datetime;
        this.severity = severity;
        this.message = message;
        this.requestId = requestId;
    }

    public int getUserId() {
        return userId;
    }

    public LocalDateTime getDatetime() {
        return datetime;
    }

    public String getSeverity() {
        return severity;
    }

    public String getMessage() {
        return message;
    }

    public int getRequestId() {
        return requestId;
    }

    @Override
    public String toString() {
        return userId + "|" + datetime + "|" + severity + "|" + message + "|" + requestId;
    }
}