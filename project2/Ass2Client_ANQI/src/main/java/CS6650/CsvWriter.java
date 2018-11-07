package CS6650;

import java.io.FileWriter;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CsvWriter {

  public CsvWriter() {
  }

   void writeFile(ConcurrentHashMap<Integer, Integer> map) {
    String file = "/Users/anqibi/documents/github/cs6650/cs6650/assignment2/metrics.csv";
    FileWriter fileWriter = null;
    try {
      fileWriter = new FileWriter(file);
      fileWriter.append("Second,NumberOfRequests\r\n");
      for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
        Integer second = entry.getKey();
        Integer requestNum = entry.getValue();
        fileWriter.append(String.valueOf(second)).append(",").append(String.valueOf(requestNum)).append("\r\n");

      }
    } catch (Exception ex) {
      System.out.println("Failure" + ex);
    } finally {
      try {
        fileWriter.flush();
        fileWriter.close();
      } catch (Exception ex) {
        ex.printStackTrace();
      }
    }
  }
}