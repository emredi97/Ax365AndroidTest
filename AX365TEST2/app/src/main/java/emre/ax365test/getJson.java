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

public class getJson extends AsyncTask<String, String, JSONArray> {

    HttpURLConnection urlConnection;
    String Token;
    final static String AUTHORIZATION_HEADER = "Authorization";

    final static String AUTHORIZATION_HEADER_BEARER = "Bearer ";
    String BearerToken;
    StringBuilder data;

    @Override
    protected JSONArray doInBackground(String... args) {

        String RequestedData = args[0];

        HttpURLConnection Connection = null;
        JSONObject JSON_object = null;
        JSONArray array = null;


        URL URL = null;
        String tmp2 = null;
        try {

            URL = new URL("https://ax7demoarmad447060a2c1da4daos.cloudax.dynamics.com/data/" + RequestedData);
            Connection = (HttpURLConnection) URL.openConnection();
            Connection.setRequestMethod("GET");
            Connection.setUseCaches(false);
            Connection.setAllowUserInteraction(false);
            Connection.setRequestProperty("Authorization", "Bearer " + Constants.CURRENT_RESULT.getAccessToken());
            Connection.setRequestProperty("Accept", "application/json");

            Connection.connect();


            int status = Connection.getResponseCode();

            switch (status) {
                case 200://Erfolgreich


                    InputStream inputStream = Connection.getInputStream();

                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder builder = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        builder.append(line);
                    }
                    String json2 = builder.toString();


                    JSONObject jsonObject = new JSONObject(json2);
                    array = jsonObject.getJSONArray("value");



                    bufferedReader.close();


                    break;
                case 201:
                    BufferedReader br = new BufferedReader(new InputStreamReader(Connection.getInputStream()));
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
