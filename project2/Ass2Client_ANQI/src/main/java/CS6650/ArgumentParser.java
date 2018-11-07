package CS6650;

public class ArgumentParser {
  int maxOfThreads = 32;
  String REST_URI = "";
  int dayNum = 1;
  int population = 100;
  int testsPerPhase = 100;

  public ArgumentParser(String[]args) {
    if(args.length > 0) maxOfThreads = Integer.valueOf(args[0]);
    System.out.println("maxOfThreads: "+maxOfThreads);
    if(args.length > 1) REST_URI = args[1];
    System.out.println("REST_URI: "+REST_URI);

    if(args.length > 2) dayNum = Integer.valueOf(args[2]);
    System.out.println("dayNum: "+dayNum);

    if(args.length > 3) population = Integer.valueOf(args[3]);
    System.out.println("population: "+population);

    if(args.length > 4) testsPerPhase = Integer.valueOf(args[4]);
    System.out.println("testsPerPhase: "+testsPerPhase);
  }
}
