import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

@SuppressWarnings("ALL")
public class LogFileGenerator {

    private static final int NUM_USERS = 500;
    private static final int NUM_LOGS_PER_USER = 98;
    private static final int OTHER_LOG_CHANCE = 4;
    private static final int MIN_INTERVAL_MS = 50;
    private static final int MAX_INTERVAL_MS = 10000;

    private static final String[] SEVERITY_LEVELS = {"WARNING", "ERROR"};
    private static final String[] MESSAGES = {"Connection reset by peer", "Unable to connect to server", "Invalid request received", "Unexpected error occurred"};

    private static final Random random = new Random();

    public static void main(String[] args) throws IOException {
        List<LogEntry> logs = generateLogs();
        logs.sort(Comparator.comparing(LogEntry::getTime));

        String fileName = "logs.txt";
        writeLogsToFile(logs, fileName);
    }

    private static void writeLogsToFile(List<LogEntry> logs, String fileName) throws IOException {
        File f = new File(fileName);
        if (!f.exists()) {
            f.createNewFile();
        }

        FileWriter writer = new FileWriter(fileName);
        for (LogEntry log : logs) {
            writer.write(getLogStr(log));
        }
        writer.close();
    }

    private static String getLogStr(LogEntry log) {
        return log.getUserId() + "|" + log.getTime().toString() + "|" + log.getSeverity() + "|" + log.getMessage() + "|" + log.getRequestId() + "\n";
    }

    private static List<LogEntry> generateLogs() {
        List<LogEntry> logs = new ArrayList<>();

        for (int userId = 1; userId <= NUM_USERS; userId++) {
            for (int i = 1; i <= NUM_LOGS_PER_USER; i++) {
                long requestId = (userId - 1) * NUM_LOGS_PER_USER + i;
                Instant requestReceivedTime = Instant.now().minusSeconds(random.nextInt(1000000));
                Instant requestProcessedTime = requestReceivedTime.plusMillis(MIN_INTERVAL_MS + random.nextInt(MAX_INTERVAL_MS - MIN_INTERVAL_MS));

                // Log request received
                logs.add(new LogEntry(userId, requestId, requestReceivedTime, "INFO", "request_received"));

                // Generate other logs with small chance
                if (random.nextInt(100) < OTHER_LOG_CHANCE) {
                    String message = MESSAGES[random.nextInt(MESSAGES.length)];
                    String severity = SEVERITY_LEVELS[random.nextInt(SEVERITY_LEVELS.length)];
                    logs.add(new LogEntry(userId, requestId, requestReceivedTime.plusMillis(random.nextInt(MIN_INTERVAL_MS)), severity, message));
                }

                // Log request processed
                logs.add(new LogEntry(userId, requestId, requestProcessedTime, "INFO", "request_processed"));
            }
        }
        return logs;
    }


    private static class LogEntry {
        private final int userId;
        private final long requestId;
        private final Instant time;
        private final String severity;
        private final String message;

        public LogEntry(int userId, long requestId, Instant time, String severity, String message) {
            this.userId = userId;
            this.requestId = requestId;
            this.time = time;
            this.severity = severity;
            this.message = message;
        }

        public int getUserId() {
            return userId;
        }

        public long getRequestId() {
            return requestId;
        }

        public Instant getTime() {
            return time;
        }

        public String getSeverity() {
            return severity;
        }

        public String getMessage() {
            return message;
        }
    }
}