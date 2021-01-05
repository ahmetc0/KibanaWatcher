package tools;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;

public class Elasticsearch {

    public static String getUrlAsString(String url)
    {
        try
        {
            URL urlObj = new URL(url);
            URLConnection con = urlObj.openConnection();

            con.setDoOutput(true); // we want the response
            con.setRequestProperty("Cookie", "myCookie=test123");
            con.connect();

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));

            StringBuilder response = new StringBuilder();
            String inputLine;

            String newLine = System.getProperty("line.separator");
            while ((inputLine = in.readLine()) != null)
            {
                response.append(inputLine + newLine);
            }

            in.close();

            return response.toString();
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public int getErrorNumner(String url){
        String result = getUrlAsString(url);
        try {
            JSONObject myResponse = new JSONObject(result);
            return myResponse.getInt("count");
        } catch (JSONException e) {
            return -1;
        }
    }

    public int getErrorMessage(String url){
        String result = getUrlAsString(url);
        try {
            JSONObject myResponse = new JSONObject(result);
            return myResponse.getInt("count");
        } catch (JSONException e) {
            return -1;
        }
    }
}
