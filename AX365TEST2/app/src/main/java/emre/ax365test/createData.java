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

public class createData extends AsyncTask<String, String, JSONArray> {


    String Token;
    final static String AUTHORIZATION_HEADER = "Authorization";

    final static String AUTHORIZATION_HEADER_BEARER = "Bearer ";
    String BearerToken;
    StringBuilder data;
    int tmp2;

    @Override
    protected JSONArray doInBackground(String... args) {

        HttpURLConnection connection = null;
        JSONObject JSON_object = null;
        JSONArray array = null;


        URL URL = null;

        int status;

        try {

            URL = new URL("https://rdagdmo01094f27c6aeba9322aos.cloudax.dynamics.com/data/DocumentTypes");


            JSONObject object = new JSONObject();
            object.put("@odata.type", "#Microsoft.Dynamics.DataEntities.DocumentType");
            object.put("ID", "EDI");
            object.put("Name","TEST");
            object.put("ActionClassName", "DocuActionArchive");

            object.put("dataAreaId", "USMF");

            Log.i("JSON",object.toString());

            connection = (HttpURLConnection) URL.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            //connection.setUseCaches(false);
            //connection.setConnectTimeout(10000);
           // connection.setReadTimeout(10000);
            connection.setRequestProperty("Authorization", "Bearer " + Constants.CURRENT_RESULT.getAccessToken());
            // Connection2.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Content-Type", "application/json;odata.metadata=minimal");
            /*connection.setRequestProperty("Host", "rdagdmo0173a91cbdf4ee46d3aos.cloudax.dynamics.com");
            connection.setRequestProperty("Accept", "application/json;odata.metadata=minimal");
            connection.setRequestProperty("Accept-Charset", "UTF-8");
            connection.setRequestProperty("OData-Version", "4.0");
            connection.setRequestProperty("OData-MaxVersion", "4.0");*/
            connection.connect();

            OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
            out.write(object.toString());
            out.close();
            Log.i("Connection",connection.getResponseMessage());
            status = connection.getResponseCode();

        } catch (ProtocolException e1) {
            Log.i("Fehler", e1.getMessage());
            Log.i("Fehler", e1.getCause().getMessage());

        } catch (MalformedURLException e1) {
            Log.i("URL", e1.getMessage());
            Log.i("URL", e1.getCause().getMessage());
        } catch (IOException e1) {
            Log.i("IO", e1.getMessage());
            Log.i("IO", e1.getCause().getMessage());
        } catch (JSONException e) {
            Log.i("JSON_LOG", e.getMessage());
            Log.i("JSON_LOG", e.getCause().getMessage());
        }

        return array;
    }

    @Override
    protected void onPostExecute(JSONArray result) {
        super.onPostExecute(result);
    }

}
