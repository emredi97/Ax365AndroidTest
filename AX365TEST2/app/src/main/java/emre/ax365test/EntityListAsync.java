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
import java.util.ArrayList;

/**
 * Created by emreh_000 on 28.02.2018.
 */

public class EntityListAsync extends AsyncTask<String, String,  ArrayList<UrlPairs>> {

    private HttpURLConnection Connection;
    URL URL;
    private JSONArray array;
    ArrayList<UrlPairs> UrlPairs = new ArrayList<>();

    @Override
    protected ArrayList<emre.ax365test.UrlPairs> doInBackground(String... args) {
        getDataEntitties();
        getDataManagementEntities();
        int a = 78;
        return UrlPairs;
    }

    private void getDataManagementEntities() {
        for (int i = 0; i < UrlPairs.size(); i++) {
            try {

                UrlPairs U = UrlPairs.get(i);
                Log.i("DataManagementEntities",U.getName());
                //https://rcondev01a952c4861b6a09a1devaos.cloudax.dynamics.com/data/DataManagementEntities?$filter=TargetName eq 'AlcoholActivityKindEntity'
                URL = new URL(Constants.RESOURCE_ID + "/data/DataManagementEntities?$filter=TargetName eq '" + U.getName() + "'");
                Connection = (HttpURLConnection) URL.openConnection();
                Connection.setRequestMethod("GET");
                Connection.setUseCaches(false);
                Connection.setRequestProperty("Authorization", "Bearer " + Constants.CURRENT_RESULT.getAccessToken());
                Connection.setAllowUserInteraction(false);
                Connection.connect();
                int status = Connection.getResponseCode();
                Log.i("EntityList LOG", String.valueOf(status));

                if (status == 200 || status == 201) {
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
                    //createUrlPais();
                    JSONObject temp=array.getJSONObject(0);
                    U.setEntityName(temp.getString("EntityName"));
                    UrlPairs.set(i,U);
                    Log.i("erfolg", "1");
                }
            } catch (ProtocolException e1) {
                Log.i("Protocol Exception", e1.getMessage());
            } catch (MalformedURLException e1) {
                Log.i("Malformed URL Exception", e1.getMessage());
            } catch (IOException e1) {
                Log.i("IO Exception", e1.getMessage());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onPostExecute( ArrayList<UrlPairs> result) {
        super.onPostExecute(result);
    }
    public void createUrlPais(){

        for (int i = 0; i < array.length(); i++) {
            try {
                JSONObject temp=array.getJSONObject(i);
                String name=temp.getString("Name");
                String url=temp.getString("PublicCollectionName");
                UrlPairs UPair=new UrlPairs(name,null,url);
                UrlPairs.add(UPair);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }
    public void getDataEntitties(){

        try {

            //https://rcondev01a952c4861b6a09a1devaos.cloudax.dynamics.com/data/DataManagementEntities?$filter=TargetName eq 'AlcoholActivityKindEntity'
            URL = new URL(Constants.RESOURCE_ID+"/metadata/DataEntities?$filter=DataServiceEnabled eq true");
            Connection = (HttpURLConnection) URL.openConnection();
            Connection.setRequestMethod("GET");
            Connection.setUseCaches(false);
            Connection.setRequestProperty("Authorization", "Bearer " + Constants.CURRENT_RESULT.getAccessToken());
            Connection.setAllowUserInteraction(false);
            Connection.connect();
            int status = Connection.getResponseCode();
            Log.i("EntityList LOG",String.valueOf(status));

            if(status==200||status==201){
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
                createUrlPais();
                Log.i("erfolg","1");

            }
        } catch (ProtocolException e1) {
            Log.i("Protocol Exception", e1.getMessage());
        } catch (MalformedURLException e1) {
            Log.i("Malformed URL Exception", e1.getMessage());
        } catch (IOException e1) {
            Log.i("IO Exception", e1.getMessage());
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
