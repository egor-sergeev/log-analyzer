import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Map;

public class Report {
    private final Map<LocalDate, DailyStatistics> requestStatsByDay;
    private final Map<LocalDate, DailyStatistics> errorWarningStatsByDay;

    public Report(Map<LocalDate, DailyStatistics> requestStatsByDay, Map<LocalDate, DailyStatistics> errorWarningStatsByDay) {
        this.requestStatsByDay = requestStatsByDay;
        this.errorWarningStatsByDay = errorWarningStatsByDay;
    }

    public void generateReportFile(String fileName) {
        try (FileWriter writer = new FileWriter(fileName)) {
            // Write total number of log entries
            int totalLogEntries = getTotalLogEntries();
            writer.write("Total number of log entries: " + totalLogEntries + "\n");

            // Write daily statistics
            writer.write("\nDaily statistics:\n");
            for (Map.Entry<LocalDate, DailyStatistics> entry : requestStatsByDay.entrySet()) {
                LocalDate date = entry.getKey();
                DailyStatistics requestStats = entry.getValue();
                DailyStatistics errorWarningStats = errorWarningStatsByDay.get(date);

                writer.write(date + ":\n");
                writer.write("Total requests: " + requestStats.getTotalRequests() + "\n");
                writer.write("Request processing time (ms): min=" + requestStats.getMinProcessingTime() +
                        ", mean=" + requestStats.getMeanProcessingTime() +
                        ", max=" + requestStats.getMaxProcessingTime() + "\n");
                writer.write("Requests with ERRORs: " + errorWarningStats.getErrorCount() + " (" +
                        String.format("%.2f", (double) errorWarningStats.getErrorCount() / requestStats.getTotalRequests() * 100) + "%)\n");
                writer.write("Requests with WARNINGs: " + errorWarningStats.getWarningCount() + " (" +
                        String.format("%.2f", (double) errorWarningStats.getWarningCount() / requestStats.getTotalRequests() * 100) + "%)\n\n");
            }

            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int getTotalLogEntries() {
        int total = 0;
        for (DailyStatistics stats : requestStatsByDay.values()) {
            total += stats.getTotalRequests();
        }
        return total;
    }
}