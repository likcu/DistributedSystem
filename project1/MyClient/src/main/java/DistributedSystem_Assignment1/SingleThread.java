package DistributedSystem_Assignment1;

import org.glassfish.jersey.client.JerseyWebTarget;

import java.security.Timestamp;
import java.util.LinkedList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ClientErrorException;
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
    public WebTarget webTarget;

    public SingleThread(String url,Integer iteration,WebTarget target){
        this.url = url;
        this.iteration = iteration;
        this.webTarget = target;
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
        Invocation.Builder ib = webTarget.request(MediaType.TEXT_PLAIN);
        for(int i = 0; i < iteration; i++) {
            Long start = System.currentTimeMillis();
            post(ib);
            Long end = System.currentTimeMillis();
            Long dif = end - start;
            latency.add(dif);
          //  System.out.println("singleLatency:"+dif);
            totalLatency += dif;
        }
        Invocation.Builder ib1 = webTarget.request(MediaType.TEXT_PLAIN);
        for(int i = 0; i < iteration; i++) {
            Long start = System.currentTimeMillis();
            get(ib1);
            Long end = System.currentTimeMillis();
            Long dif = end - start;
            latency.add(dif);
         //   System.out.println("singleGetLatency:"+dif);
            totalLatency += dif;
        }
    }

    public void post(Invocation.Builder ib){
        Response response = ib.post(Entity.entity("123", MediaType.TEXT_PLAIN));
        int status = response.getStatus();
        if(status == 200) successRequest++;
        request++;
        response.close();
    }
    public void get(Invocation.Builder ib){
        Response response = ib.get();
        int status = response.getStatus();
        if(status == 200) successRequest++;
        request++;
        response.close();
    }
}
