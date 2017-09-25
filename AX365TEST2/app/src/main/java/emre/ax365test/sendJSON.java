package emre.ax365test;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by emreh_000 on 22.09.2017.
 */

public class sendJSON extends AsyncTask<String, String, JSONArray> {

    HttpURLConnection connection;
    String Token;
    final static String AUTHORIZATION_HEADER = "Authorization";

    final static String AUTHORIZATION_HEADER_BEARER = "Bearer ";
    String BearerToken;
    StringBuilder data;
    int tmp2;

    @Override
    protected JSONArray doInBackground(String... args) {


        HttpURLConnection Connection2 = null;
        JSONObject JSON_object = null;
        JSONArray array = null;


        URL URL = null;
        URL url2 = null;

        try {

            url2 = new URL("https://ax7demoarmad447060a2c1da4daos.cloudax.dynamics.com/data/DocumentTypes");

            int status;


            JSONObject object = new JSONObject();
            object.put("@odata.type", "#Microsoft.Dynamics.DataEntities.DocumentType");
            object.put("ID", "Te");
            object.put("Name", "ED");
            object.put("ActionClassName", "DocuActionArchive");

            object.put("dataAreaId", "USMF");

            Connection2 = (HttpURLConnection) url2.openConnection();
            Connection2.setDoOutput(true);
            Connection2.setRequestMethod("POST");
            Connection2.setUseCaches(false);
            Connection2.setConnectTimeout(10000);
            Connection2.setReadTimeout(10000);
            Connection2.setRequestProperty("Authorization", "Bearer " + Constants.CURRENT_RESULT.getAccessToken());
            // Connection2.setRequestProperty("Content-Type", "application/json");
            Connection2.setRequestProperty("Content-Type", "application/json;odata.metadata=minimal");
            Connection2.setRequestProperty("Host", "ax7demoarmad447060a2c1da4daos.cloudax.dynamics.com");
            Connection2.setRequestProperty("Accept", "application/json;odata.metadata=minimal");
            Connection2.setRequestProperty("Accept-Charset", "UTF-8");
            Connection2.setRequestProperty("OData-Version", "4.0");
            Connection2.setRequestProperty("OData-MaxVersion", "4.0");
            Connection2.connect();







            OutputStreamWriter out = new OutputStreamWriter(Connection2.getOutputStream());
            out.write(object.toString());
            out.close();

            status = Connection2.getResponseCode();
            int a;


        } catch (ProtocolException e1) {
            Log.i("Fehler", e1.getMessage());
            e1.printStackTrace();
        } catch (MalformedURLException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        } catch (JSONException e) {
            Log.i("JSON_LOG", e.getMessage());
            e.printStackTrace();
        }

        return array;
    }

    @Override
    protected void onPostExecute(JSONArray result) {
        super.onPostExecute(result);
    }

}
