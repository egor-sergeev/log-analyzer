import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class LogAnalyzer {

    public static Map<LocalDate, List<LogEntry>> groupLogsByDay(List<LogEntry> logEntries) {
        return logEntries.stream()
                .collect(Collectors.groupingBy(logEntry -> logEntry.getDatetime().toLocalDate()));
    }

    public static DailyStatistics getDailyRequestStatistics(List<LogEntry> logsForDay) {

        List<Integer> requestIds = logsForDay.stream()
                .filter(entry -> entry.getMessage().equals("request_received"))
                .map(LogEntry::getRequestId)
                .distinct()
                .toList();

        List<Duration> processingTimes = new ArrayList<>();

        for (Integer requestId : requestIds) {
            LocalDateTime receivedDatetime = logsForDay.stream()
                    .filter(entry -> entry.getRequestId() == requestId && entry.getMessage().equals("request_received"))
                    .map(LogEntry::getDatetime)
                    .findFirst()
                    .orElse(null);

            LocalDateTime processedDatetime = logsForDay.stream()
                    .filter(entry -> entry.getRequestId() == requestId && entry.getMessage().equals("request_processed"))
                    .map(LogEntry::getDatetime)
                    .findFirst()
                    .orElse(null);

            if (receivedDatetime != null && processedDatetime != null) {
                Duration processingTime = Duration.between(receivedDatetime, processedDatetime);
                processingTimes.add(processingTime);
            }
        }

        double meanProcessingTime = processingTimes.stream()
                .mapToLong(Duration::toMillis)
                .average()
                .orElse(0);

        long minProcessingTime = processingTimes.stream()
                .mapToLong(Duration::toMillis)
                .min()
                .orElse(0);

        long maxProcessingTime = processingTimes.stream()
                .mapToLong(Duration::toMillis)
                .max()
                .orElse(0);

        int numRequests = requestIds.size();

        return new DailyStatistics(numRequests, minProcessingTime, maxProcessingTime, meanProcessingTime, 0, 0);
    }

    public static DailyStatistics getDailyErrorWarningStatistics(List<LogEntry> logEntries) {
        int numErrors = (int) logEntries.stream()
                .filter(entry -> entry.getSeverity().equals("ERROR"))
                .count();

        int numWarnings = (int) logEntries.stream()
                .filter(entry -> entry.getSeverity().equals("WARNING"))
                .count();

        return new DailyStatistics(0, 0, 0, 0, numErrors, numWarnings);
    }
}