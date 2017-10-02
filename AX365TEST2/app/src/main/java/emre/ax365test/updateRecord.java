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
 * Created by emreh_000 on 27.09.2017.
 */

public class updateRecord extends AsyncTask<String, String, String> {

    String Token;
    final static String AUTHORIZATION_HEADER = "Authorization";

    final static String AUTHORIZATION_HEADER_BEARER = "Bearer ";
    String BearerToken;
    StringBuilder data;
    int tmp2;

    @Override
    protected String doInBackground(String... args) {


        HttpURLConnection connection = null;
        JSONObject JSON_object = null;
        JSONArray array = null;


        URL url = null;
        //https://servername.cloudax.dynamics.com/data/Customers(CustomerAccount='US-018',%20dataAreaId='USMF')?cross-company=true
        try {

            url = new URL(Constants.RESOURCE_ID + "/data/DocumentTypes(ID='Te',dataAreaId='usmf')");

            int status;


            JSONObject object = new JSONObject();
            object.put("@odata.type", "#Microsoft.Dynamics.DataEntities.DocumentType");
            object.put("ID", "Te");
            object.put("Name", "EMRE");
            object.put("ActionClassName", "DocuActionArchive");
            object.put("dataAreaId", "USMF");

            connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("PATCH");
            connection.setUseCaches(false);
            connection.setConnectTimeout(10000);
            connection.setReadTimeout(10000);
            connection.setRequestProperty("Authorization", "Bearer " + Constants.CURRENT_RESULT.getAccessToken());
            // Connection2.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Content-Type", "application/json;odata.metadata=minimal");
            connection.setRequestProperty("Host", "rdagdmo0173a91cbdf4ee46d3aos.cloudax.dynamics.com");
            connection.setRequestProperty("Accept", "application/json;odata.metadata=minimal");
            connection.setRequestProperty("Accept-Charset", "UTF-8");
            connection.setRequestProperty("OData-Version", "4.0");
            connection.setRequestProperty("OData-MaxVersion", "4.0");
            connection.connect();

            OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
            out.write(object.toString());
            out.close();

            status = connection.getResponseCode();
            int a;


        } catch (ProtocolException e1) {
            Log.i("Fehler", e1.getMessage());

        } catch (MalformedURLException e1) {
            Log.i("URL", e1.getMessage());

        } catch (IOException e1) {
            Log.i("IO", e1.getMessage());

        } catch (JSONException e) {
            Log.i("JSON_LOG", e.getMessage());

        }

        return null;


    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
    }
}