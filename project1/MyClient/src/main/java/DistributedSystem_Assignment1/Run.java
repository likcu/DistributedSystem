package DistributedSystem_Assignment1;

import com.sun.jmx.snmp.Timestamp;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class Run {

  public static void main(String[] args) throws InterruptedException {
    String url = "https://ayrw4vfh11.execute-api.us-east-2.amazonaws.com/myStage/server";
    Calendar cal = Calendar.getInstance();
    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
    System.out.println("Client Starting ..... Time: " + sdf.format(cal.getTime()));
    int maxThreads = 100;
    int maxIteration = 100;//multithread process里
    int totalSuc = 0;
    int totalReq = 0;
    long totalLatency = 0;
    List<Long> latency = new ArrayList<>();
    double[] phases = new double[]{0.1,0.5,1.0,0.25};
    String[] titles = new String[]{"Warmup phase:","Loading phase:","Peak phase:","Cooldown phase:"};
    for(int i = 0; i < 4; i++){
      System.out.println(titles[i]+"All threads running...");
      int numThreads = (int) (phases[i] * maxThreads);
      MultiThreads multiThreads = new MultiThreads(numThreads,url);
      multiThreads.process();
      totalSuc += multiThreads.totalSuccess;
      totalReq += multiThreads.totalRequest;
      totalLatency += multiThreads.totalLatency;
      latency.addAll(multiThreads.latency);
      System.out.println(titles[i]+"complete:"+"Time "+multiThreads.totalLatency);
    }
    long throughput = totalReq / totalLatency;
    long mean = totalLatency / totalReq;
    long median = latency.get(latency.size()/2);
    Collections.sort(latency);
    System.out.println("======================================");
    System.out.println("Total number of requests sent:"+totalReq);
    System.out.println("Total number of Successful responses:"+totalSuc);
    System.out.println("Test Wall Time:"+totalLatency);
    System.out.println("Overall throughput across all phases:"+throughput);
    System.out.println("Mean latencies for all requests:"+mean);
    System.out.println("Median latencies for all requests:"+median);
    System.out.println("99th percentile latency:"+ latency.get(latency.size() * 99 / 100));
    System.out.println("99th percentile latency:"+ latency.get(latency.size() * 95 / 100));

  }

}
