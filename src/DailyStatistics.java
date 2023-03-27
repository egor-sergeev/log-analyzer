public class DailyStatistics {
    private final long totalRequests;
    private final long minProcessingTime;
    private final long maxProcessingTime;
    private final double meanProcessingTime;
    private final long errorCount;
    private final long warningCount;

    public DailyStatistics(long totalRequests, long minProcessingTime, long maxProcessingTime, double meanProcessingTime,
                           long errorCount, long warningCount) {
        this.totalRequests = totalRequests;
        this.minProcessingTime = minProcessingTime;
        this.maxProcessingTime = maxProcessingTime;
        this.meanProcessingTime = meanProcessingTime;
        this.errorCount = errorCount;
        this.warningCount = warningCount;
    }

    public long getTotalRequests() {
        return totalRequests;
    }

    public long getMinProcessingTime() {
        return minProcessingTime;
    }

    public long getMaxProcessingTime() {
        return maxProcessingTime;
    }

    public double getMeanProcessingTime() {
        return meanProcessingTime;
    }

    public long getErrorCount() {
        return errorCount;
    }

    public long getWarningCount() {
        return warningCount;
    }
}