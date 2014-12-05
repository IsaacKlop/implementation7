package gcmserver;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.fasterxml.jackson.databind.ObjectMapper;
import gcmserver.Content;

public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Sending POST to GCM" );
        // This is a comment made possible by Laurens Weitkamp
        String apiKey = "AIzaSyC3clKuTxILxby8euNiyO9dqTJy2wqCWcg";
        Content content = createContent();

        PostGcm.post(apiKey, content);
    }

    public static Content createContent(){

        Content c = new Content();

        c.addRegId("APA91bEXu_2F1q0tBggVJwehyvdamnHO7yqzHV85JZ_jtOYGPqCGg2WLmy0bk6SJJBweyU1pIwHcgiDsOD6tDnUMxcSbsCuX8T_hjaOLUz15Q8yH1W94OcKaNmCzOzSpR3aAfyrxHsgcGJl7VWqWhA3oBFPabyBvphIvrsadh9LIzXsye9fuJts");
        c.createData("Hoi Alex", "Test Message");

        return c;
    }
}
