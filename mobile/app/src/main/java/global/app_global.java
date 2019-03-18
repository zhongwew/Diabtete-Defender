package global;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;

public class app_global {
    static final String host_url = "http://10.0.2.2:5000";


    public static String sendGet(String parm) throws IOException {
        URL obj = new URL(host_url+parm);
        HttpURLConnection conn = (HttpURLConnection) obj.openConnection();

        //set the request method to GET
        conn.setRequestMethod("GET");

        //add request header
        conn.addRequestProperty("User-Agent","Mozilla/5.0");

        int responseCode = conn.getResponseCode();

        BufferedReader bufIn = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        System.out.println("return code: "+responseCode);

        String tempLine;
        StringBuilder returnStr = new StringBuilder();

        //get the response string from buffer reader
        while((tempLine = bufIn.readLine()) != null){
            returnStr.append(tempLine);
        }
        bufIn.close();

        return returnStr.toString();
    }

    static String sendPost(String parm){
        StringBuilder retStr = new StringBuilder();


        return retStr.toString();
    }
}
