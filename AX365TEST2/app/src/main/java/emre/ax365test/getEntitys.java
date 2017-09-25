package emre.ax365test;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by emreh_000 on 22.09.2017.
 */

public class getEntitys extends AsyncTask<String, String, JSONArray> {

    HttpURLConnection urlConnection;
    String Token;
    final static String AUTHORIZATION_HEADER = "Authorization";

    final static String AUTHORIZATION_HEADER_BEARER = "Bearer ";
    String BearerToken;
    StringBuilder data;

    @Override
    protected JSONArray doInBackground(String... args) {



        HttpURLConnection c = null;
        JSONObject final_json = null;
        JSONArray array = null;



        URL u = null;
        String tmp2 = null;
        try {

            u = new URL("https://ax7demoarmad447060a2c1da4daos.cloudax.dynamics.com/data/");
            c = (HttpURLConnection) u.openConnection();
            c.setRequestMethod("GET");
            c.setUseCaches(false);
            c.setAllowUserInteraction(false);
            c.setRequestProperty("Authorization", "Bearer " + Constants.CURRENT_RESULT.getAccessToken());
            c.setRequestProperty("Accept", "application/json");

            c.connect();


            int status = c.getResponseCode();

            switch (status) {
                case 200://Erfolgreich


                    InputStream inputStream = c.getInputStream();

                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder builder = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        builder.append(line);
                    }
                    String json2 = builder.toString();


                    JSONObject jsonObject = new JSONObject(json2);
                    array = jsonObject.getJSONArray("value");


                    final_json = array.getJSONObject(0);

                    bufferedReader.close();


                    break;
                case 201:
                    BufferedReader br = new BufferedReader(new InputStreamReader(c.getInputStream()));
                    StringBuilder sb = new StringBuilder();


                    while ((line = br.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    Constants.setTmp(line);
                    Constants.sb = sb;
                    br.close();


            }

        } catch (ProtocolException e1) {
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
