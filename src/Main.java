import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws IOException {
        String logFilePath = "./logs.txt";

        // Step 1: Parse the log file and group logs by day
        List<LogEntry> logEntries = LogParser.parseLog(logFilePath);
        Map<LocalDate, List<LogEntry>> logsByDay = LogAnalyzer.groupLogsByDay(logEntries);

        // Step 2: Calculate daily request statistics
        Map<LocalDate, DailyStatistics> requestStatsByDay = new HashMap<>();
        for (LocalDate date : logsByDay.keySet()) {
            List<LogEntry> logsForDay = logsByDay.get(date);
            DailyStatistics stats = LogAnalyzer.getDailyRequestStatistics(logsForDay);
            requestStatsByDay.put(date, stats);
        }

        // Step 3: Calculate daily error/warning statistics
        Map<LocalDate, DailyStatistics> errorWarningStatsByDay = new HashMap<>();
        for (LocalDate date : logsByDay.keySet()) {
            List<LogEntry> logsForDay = logsByDay.get(date);
            DailyStatistics stats = LogAnalyzer.getDailyErrorWarningStatistics(logsForDay);
            errorWarningStatsByDay.put(date, stats);
        }

        // Step 4: Combine daily statistics into a single report
        Report report = new Report(requestStatsByDay, errorWarningStatsByDay);
        report.generateReportFile("report.txt");
    }
}