package DistributedSystem_Assignment1;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.JerseyWebTarget;

import java.security.Timestamp;
import java.util.LinkedList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("myresource")
public class SingleThread implements Runnable{
    public int iteration;
    public String url;
    public List<Long> latency;
    public long totalLatency;
    public int request;
    public int successRequest;
    public Client client;
    public WebTarget webTarget;

    public SingleThread(String url,Integer iteration){
        this.url = url;
        this.iteration = iteration;
        this.client = ClientBuilder.newClient();
        this.webTarget = client.target(url);
        request = 0;
        successRequest = 0;
        latency = new LinkedList<>();
        totalLatency = 0;
    }

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt() {
        return "Got it!";
    }

    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {

        //Invocation.Builder ib = webTarget.request(MediaType.TEXT_PLAIN);

        for(int i = 0; i < iteration; i++) {
            //long start = System.currentTimeMillis();
            post();
            //long end = System.currentTimeMillis();
            //long dif = end - start;
            //latency.add(dif);
            //totalLatency += dif;
        }
        //Invocation.Builder ib1 = webTarget.request(MediaType.TEXT_PLAIN);
        for(int i = 0; i < iteration; i++) {
            //long start = System.currentTimeMillis();
            get();
            //long end = System.currentTimeMillis();
            //long dif = end - start;
            //latency.add(dif);
         //   System.out.println("singleGetLatency:"+dif);
            //totalLatency += dif;
        }
    }

    public void post(){
        long start = System.currentTimeMillis();
        Response response = webTarget.request().post(Entity.entity("123", MediaType.TEXT_PLAIN));
        int status = response.getStatus();
        long end = System.currentTimeMillis();
        long dif = end - start;
        if(status == 200) {
            successRequest++;
            System.out.println(dif);
            latency.add(dif);
            totalLatency += dif;
        }
        request++;
        //System.out.println("threadId:"+Thread.currentThread().getId());
        response.close();
    }
    public void get(){
        long start = System.currentTimeMillis();
        Response response = webTarget.request().get();
        int status = response.getStatus();
        long end = System.currentTimeMillis();
        long dif = end - start;
        if(status == 200) {
            successRequest++;
            System.out.println(dif);
            latency.add(dif);
            totalLatency += dif;
        }
        request++;
        response.close();
    }
}
