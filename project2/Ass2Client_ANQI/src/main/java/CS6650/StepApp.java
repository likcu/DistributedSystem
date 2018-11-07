package CS6650;

import java.io.IOException;

/**
 * Hello world!
 *
 */
public class StepApp {
    public static void main( String[] args ) throws InterruptedException, IOException {
        ArgumentParser argumentParser = new ArgumentParser(args);
        int maxOfThreads = argumentParser.maxOfThreads;
        String REST_URI = argumentParser.REST_URI;
        Statistics statistics = new Statistics();
        MultiThread multiThread = new MultiThread(maxOfThreads, REST_URI, argumentParser, statistics);
        multiThread.execute();
        long[] results = statistics.getResults();
        System.out.println("Statistics results are as below:");
        System.out.println("Total requests sent: " + results[0]);
        System.out.println("Total successful requests: " + results[1]);
        System.out.println("Total wall time across all phases: " + results[2] + " seconds");
        System.out.println("Total throughput across all phases: " + results[3]);
        System.out.println("Mean latencies for all requests: " + results[4] + " ms");
        System.out.println("Median latencies for all requests: " + results[5] + " ms");
        System.out.println("95th percentile latency: " + results[6] + " ms");
        System.out.println("99th percentile latency: " + results[7] + " ms");
        CsvWriter csvWriter = new CsvWriter();
        csvWriter.writeFile(statistics.map);
    }
}