# log-analyzer
This is a demo Java project for analyzing logs and generating daily statistics report. The implementation contains many performance bottlenecks that can be used to practice CPU problems investigation.

## How to use
1. Clone the repository and open it in your IDE of choice
2. Generate sample file with logs by running the `src/LogFileGenerator` class
3. Run the `src/Main` class to analyze the logs and create a new file `report.txt` with basic daily statistics
4. Try to find the bottleneck and optimize the code