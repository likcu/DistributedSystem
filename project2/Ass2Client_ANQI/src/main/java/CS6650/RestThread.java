package CS6650;

import javax.ws.rs.client.*;
import javax.ws.rs.core.*;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadLocalRandom;

public class RestThread implements Runnable {
  private String REST_URI;
  private Client client;
  private WebTarget webTarget;
  private ArgumentParser argumentParser;
  private CountDownLatch latch;
  private int intervalStart;
  private int intervalEnd;
  private Statistics statistics;


  public RestThread(String REST_URI, Client client, WebTarget webTarget,
                    CountDownLatch latch, int intervalStart, int intervalEnd,
                    ArgumentParser argumentParser, Statistics statistics) {
    this.REST_URI = REST_URI;
    this.client = client;
    this.webTarget = client.target(this.REST_URI);
    this.argumentParser = argumentParser;
    this.latch = latch;
    this.intervalStart = intervalStart;
    this.intervalEnd = intervalEnd;
    this.statistics = statistics;
  }

  @Override
  public void run() {
//    System.out.println("threadId :" + Thread.currentThread().getId());
    int interval = this.intervalEnd - this.intervalStart + 1;
    int intervalMin = this.intervalStart;
    int numOfIterations = argumentParser.testsPerPhase * interval;
    int[] userId = new int[3];
    int[] timeInterval = new int[3];
    int[] stepCount = new int[3];
    long currentCount = latch.getCount();
    long start = System.currentTimeMillis();
    for (int i = 0; i < numOfIterations; i++) {
      for (int j = 0; j < 3; j++) {
        userId[j] = ThreadLocalRandom.current().nextInt(argumentParser.population);
        timeInterval[j] = ThreadLocalRandom.current().nextInt(interval) + intervalMin;
        stepCount[j] = ThreadLocalRandom.current().nextInt(Constants.stepCount);
      }

      int day = ThreadLocalRandom.current().nextInt(argumentParser.dayNum);
      try {
        sendPostRequest(userId[0], day, timeInterval[0], stepCount[0]);
      } catch (IOException e) {
        e.printStackTrace();
      }
      try {
        sendPostRequest(userId[1], day, timeInterval[1], stepCount[1]);
      } catch (IOException e) {
        e.printStackTrace();
      }
      try {
        sendGetCurrentRequest(userId[0]);
      } catch (IOException e) {
        e.printStackTrace();
      }
      try {
        sendGetSingleRequest(userId[1], day);
      } catch (IOException e) {
        e.printStackTrace();
      }
      try {
        sendPostRequest(userId[2], day, timeInterval[2], stepCount[2]);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    latch.countDown();

//    System.out.println("currentcount before: " + currentCount);
    currentCount = latch.getCount();
    System.out.println("currentcount after: " + currentCount);
  }

  private void sendGetCurrentRequest(int userId) throws IOException {
    long latency = -1;
    long startTime = System.currentTimeMillis();
    Response response = webTarget.path("current/" + userId).
        request(javax.ws.rs.core.MediaType.TEXT_PLAIN).get(Response.class);
    int status = response.getStatus();
    long endTime = System.currentTimeMillis();

    // DEBUG
    String output = response.readEntity(String.class);
//    System.out.println("sendGetCurrentRequest: " + output + ". using: " + (endTime - startTime));

    int requestSuccess = 0;
    if (status == 200) {
      latency = endTime - startTime;
      requestSuccess++;
    }
    response.close();

//    System.out.println("latency: " + latency);
    this.addToStats(startTime, endTime, requestSuccess);
  }

  private void sendGetSingleRequest(int userId, int day) throws IOException {
    long latency = -1;
    long startTime = System.currentTimeMillis();
    Response response = webTarget.path("single/" + userId + "/" + day).
        request(javax.ws.rs.core.MediaType.TEXT_PLAIN).get(Response.class);
    int status = response.getStatus();
    long endTime = System.currentTimeMillis();

    // DEBUG
    String output = response.readEntity(String.class);
//    System.out.println("sendGetSingleRequest: " + output + ". using " + (endTime - startTime));

    int requestSuccess = 0;

    if (status == 200) {
//      latency = endTime - startTime;
      requestSuccess++;
    }
    response.close();
    this.addToStats(startTime, endTime, requestSuccess);
  }

  private void sendPostRequest(int userId, int day, int timeInterval, int stepCount) throws IOException {
    long latency = -1;
    long startTime = System.currentTimeMillis();

    Response response = webTarget
        .path(userId + "/" + day + "/" + timeInterval + "/" + stepCount)
        .request(javax.ws.rs.core.MediaType.TEXT_PLAIN)
        .post(Entity.entity("TEST", MediaType.TEXT_PLAIN_TYPE), Response.class);

    int status = response.getStatus();
    long endTime = System.currentTimeMillis();

    // DEBUG
    String output = response.readEntity(String.class);
//    System.out.println("output: " + output + "using: " + (endTime - startTime));

    int requestSuccess = 0;
    if (status == 200) {
      latency = (endTime - startTime);
      requestSuccess++;
    }
    response.close();
    this.addToStats(startTime, endTime, requestSuccess);
  }

  public void addToStats(long startTime, long endTime, int requestSuccess) throws IOException {
    int requestEndTime = (int) ((endTime - statistics.globalStartTime) / 1000);
    statistics.map.putIfAbsent(requestEndTime, 1);
    statistics.map.put(requestEndTime, statistics.map.get(requestEndTime) + 1);
    statistics.latencies.add(endTime - startTime);
    statistics.requestSent.addAndGet(1);
    statistics.requestSuccess.addAndGet(requestSuccess);
  }
}
