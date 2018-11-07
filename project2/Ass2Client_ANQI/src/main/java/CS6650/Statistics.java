package CS6650;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class Statistics {
  List<Long> latencies;
  ConcurrentHashMap<Integer, Integer> map;
  Long globalStartTime;
  AtomicInteger requestSent;
  AtomicInteger requestSuccess;
  long[] results = new long[9];

  public Statistics(){
    this.latencies = Collections.synchronizedList(new LinkedList<Long>());
    this.map = new ConcurrentHashMap<>();
    globalStartTime = System.currentTimeMillis();
    requestSent = new AtomicInteger();
    requestSuccess = new AtomicInteger();
  }

  public void measure() {
    Collections.sort(this.latencies);
    long totalLatency = 0;
    for (long latency : latencies) {
      totalLatency += latency;
    }
    int size = latencies.size();
    if (size == 0) return;

    results[4] = totalLatency / size; // mean
    results[5] = latencies.get(size / 2); // median
    results[6] = latencies.get((int) Math.round(size * 0.95) - 1); // 95th
    results[7] = latencies.get((int) Math.round(size * 0.99) - 1); // 99th
  }

  public long[] getResults() {
    return this.results;
  }
}
