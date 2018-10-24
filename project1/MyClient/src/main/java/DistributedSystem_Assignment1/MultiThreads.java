package DistributedSystem_Assignment1;

import org.glassfish.jersey.client.ClientConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

public class MultiThreads {
  public int numThreads;
  public String url;
  public int totalSuccess;
  public int totalRequest;
  public long totalLatency;
  public List<Long> latency;
  public MultiThreads(Integer numThreads,String url){
    this.numThreads = numThreads;
    this.url = url;
    totalSuccess = 0;
    totalRequest = 0;
    totalLatency = 0;
    latency = new ArrayList<>();
  }
  public void process() throws InterruptedException{
    SingleThread[] threads = new SingleThread[numThreads];
    //Client client = ClientBuilder.newClient(new ClientConfig());
    //WebTarget target = client.target(url);
    ExecutorService executor = Executors.newFixedThreadPool(numThreads);
    for(int i = 0; i < numThreads; i++) {
      threads[i] = new SingleThread(url, 100);
      executor.submit(threads[i]);
    }
    executor.shutdown();
    executor.awaitTermination(10, TimeUnit.DAYS);
    for(SingleThread myThread : threads) {
      totalSuccess += myThread.successRequest;
      totalRequest += myThread.request;
      totalLatency += myThread.totalLatency;
      latency.addAll(myThread.latency);
    }
  }
}
