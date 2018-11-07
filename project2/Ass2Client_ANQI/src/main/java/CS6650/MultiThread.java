package CS6650;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import java.io.IOException;
import java.util.concurrent.*;

public class MultiThread {
  private int maxOfThreads;
  private String REST_URI;
  private Client client;
  private WebTarget webTarget;
  private ArgumentParser argumentParser;
  private Statistics statistics;

  public MultiThread(int maxOfThreads, String REST_URI, ArgumentParser argumentParser,
                     Statistics statistics) {
    this.maxOfThreads = maxOfThreads;
    this.REST_URI = REST_URI;
    this.client = ClientBuilder.newClient();
    this.webTarget = client.target(this.REST_URI);
    this.argumentParser = argumentParser;
    this.statistics = statistics;
  }

  public void startPerPhase(Client client, WebTarget webTarget, int numOfthreads,
                            CountDownLatch latch, int intervalStart, int intervalEnd) {
    long startTime = System.currentTimeMillis();
    long endTime = executeMultiThreads(REST_URI, client, webTarget, numOfthreads, latch, intervalStart, intervalEnd);
  }

  private long executeMultiThreads(String REST_URI, Client client, WebTarget webTarget, int numOfThreads,
                                   CountDownLatch latch, int intervalStart, int intervalEnd){
    ExecutorService executor = Executors.newFixedThreadPool(numOfThreads);

    for (int i = 0; i < numOfThreads; i++) {
      executor.execute(new RestThread(REST_URI, client, webTarget, latch,
          intervalStart, intervalEnd, argumentParser, statistics));
    }
    executor.shutdown();
//    executor.awaitTermination(1, TimeUnit.DAYS);
    return System.currentTimeMillis();
  }

  public void executeFourPhases() throws InterruptedException, IOException {
    int[][] intervals = new int[][]{new int[]{0, 2}, new int[]{3, 7}, new int[]{8, 18}, new int[]{19, 23}};
    int warmUpThreadsNum = (int) (maxOfThreads * Constants.WARMUP_PHASE);
    int loadingThreadsNum = (int) (maxOfThreads * Constants.LOADING_PHASE);
    int peakThreadsNum = (int) (maxOfThreads * Constants.PEAK_PHASE);
    int coolDownThreadsNum = (int) (maxOfThreads * Constants.COOLDOWN_PHASE);
    int[] phaseThreadsNum = new int[4];
    phaseThreadsNum[0] = warmUpThreadsNum;
    phaseThreadsNum[1] = loadingThreadsNum;
    phaseThreadsNum[2] = peakThreadsNum;
    phaseThreadsNum[3] = coolDownThreadsNum;

    long totalStartTime = System.currentTimeMillis();
    for(int i = 0; i < 4; i++){
      CountDownLatch latch = new CountDownLatch((int) (phaseThreadsNum[i] * Constants.OVERLAP_RATIO));
      startPerPhase(this.client, this.webTarget, warmUpThreadsNum, latch, intervals[i][0],
          intervals[i][1]);
      latch.await();
    }
    long totalEndTime = System.currentTimeMillis();
    statistics.results[0] = statistics.requestSent.intValue();
    statistics.results[1] = statistics.requestSuccess.intValue();
    statistics.results[2] = (totalEndTime - totalStartTime)/1000; // wall time
    statistics.results[3] = statistics.results[1]/statistics.results[2]; // throughput
  }

  public void execute() throws InterruptedException, IOException {
    this.executeFourPhases();
    statistics.measure();
  }
}
