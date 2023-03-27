import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class LogParser {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_OFFSET_DATE_TIME;

    public static List<LogEntry> parseLog(String filename) throws IOException {
        List<LogEntry> logEntries = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                LogEntry logEntry = parseLine(line);
                if (logEntry != null) {
                    logEntries.add(logEntry);
                }
            }
        }

        return logEntries;
    }

    private static LogEntry parseLine(String line) {
        String[] parts = line.split("\\|");
        if (parts.length != 5) {
            return null;
        }

        int userId = Integer.parseInt(parts[0].trim());
        LocalDateTime datetime = LocalDateTime.parse(parts[1].trim(), DATE_FORMATTER);
        String severity = parts[2].trim();
        String message = parts[3].trim();
        int requestId = Integer.parseInt(parts[4].trim());

        return new LogEntry(userId, datetime, severity, message, requestId);
    }
}