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

public class deleteDataAsync extends AsyncTask < String, String, String > {


    String Token;
    final static String AUTHORIZATION_HEADER = "Authorization";

    final static String AUTHORIZATION_HEADER_BEARER = "Bearer ";
    String BearerToken;
    StringBuilder data;
    int tmp2;

    @Override
    protected String doInBackground(String...args) {


        HttpURLConnection Connection = null;
        JSONObject JSON_object = null;
        JSONArray array = null;


        URL URL = null;

        try {

            URL = new URL(Constants.RESOURCE_ID + "/data/DocumentTypes(ID='EDI',dataAreaId='USMF')");

            int status;

            JSONObject object = new JSONObject();
            object.put("@odata.type", "#Microsoft.Dynamics.DataEntities.DocumentType");
            object.put("ID", "EDI");
            object.put("dataAreaId", "USMF");

            Connection = (HttpURLConnection) URL.openConnection();
            Connection.setDoOutput(true);
            Connection.setRequestMethod("DELETE");
            Connection.setUseCaches(false);
            Connection.setConnectTimeout(10000);
            Connection.setReadTimeout(10000);
            Connection.setRequestProperty("Authorization", "Bearer " + Constants.CURRENT_RESULT.getAccessToken());
            Connection.setRequestProperty("Content-Type", "application/json;odata.metadata=minimal");
            Connection.setRequestProperty("Host", Constants.RESOURCE_ID);
            Connection.setRequestProperty("Accept", "application/json;odata.metadata=minimal");
            Connection.setRequestProperty("Accept-Charset", "UTF-8");
            Connection.setRequestProperty("OData-Version", "4.0");
            Connection.setRequestProperty("OData-MaxVersion", "4.0");
            Connection.connect();

            OutputStreamWriter out = new OutputStreamWriter(Connection.getOutputStream());
            out.write(object.toString());
            out.close();
            Log.i("Status", Connection.getResponseMessage());
            status = Connection.getResponseCode();
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