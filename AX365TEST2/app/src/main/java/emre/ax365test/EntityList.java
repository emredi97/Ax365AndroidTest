package emre.ax365test;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class EntityList extends AppCompatActivity {

    private HttpURLConnection Connection;
    URL URL;

    ListView LV;
    public ArrayList < UrlPairs > Pairs;
    ArrayList < String > Names = new ArrayList < > ();
    private ResponseReceiver receiver;
    private ProgressBar spinner;
    private SQLiteDatabase myDB;
    boolean loaded = false;
    JSONArray requestedEntity;
    private String orderName;
    private ArrayList<Entity> entities=new ArrayList<>();
    EditText Filter;
    ArrayAdapter < String > ArrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entity_list2);
        LV = (ListView) findViewById(R.id.entitylistview);
        LoadDatabase();
        getXML();
        spinner = (ProgressBar) findViewById(R.id.progressBar);
        spinner.setVisibility(View.GONE);
        Filter= (EditText) findViewById(R.id.entity_filter);


        Filter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                EntityList.this.ArrayAdapter.getFilter().filter(charSequence);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        LV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.i("Name",Constants.Pairs.get(i).getName());
                Log.i("Entity",Constants.Pairs.get(i).getEntityName());
                Log.i("URL",Constants.Pairs.get(i).getURL());
                getJson JsonRequest = new getJson();
                try {
                    //spinner.setVisibility(View.VISIBLE);
                    int ijn = 89;
                    requestedEntity = JsonRequest.execute(Constants.Pairs.get(i).getURL()).get(); //Async Task um JSONs zu bekommen
                    Intent intent = new Intent(EntityList.this, OrderByScreen.class);
                    if(requestedEntity.length()>0) {
                        JSONObject tempJson = requestedEntity.getJSONObject(0);
                        intent.putExtra("Entity", tempJson.toString());
                        //spinner.setVisibility(View.GONE);
                        startActivityForResult(intent, 25);
                    }else{
                        Snackbar.make(view,"Keine Daten",Snackbar.LENGTH_SHORT).show();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public void getXML() {
        if (!loaded) {
            Intent inputIntent = new Intent(this,
                    EntityListService.class);
            startService(inputIntent);
            int k = 28;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
         if (resultCode == 25) {
            Log.i("Value", data.getExtras().getString("OrderKey"));
            orderName = data.getExtras().getString("OrderKey");
            createEntities();
             try {
                 Intent i = new Intent(EntityList.this, RecordList.class);
                 Bundle mbundle = new Bundle();
                 mbundle.putSerializable("JSON", entities);
                 i.putExtra("JsonObjects", mbundle);
                 Log.i("test", "tet");
                 startActivity(i);
             } catch (Exception i) {
                 Log.i("Fehler", i.getMessage());
             }
        }
    }

    public class ResponseReceiver extends BroadcastReceiver {

        public static final String LOCAL_ACTION =
                "com.example.myintentserviceapp.intent_service.ALL_DONE";

        @Override
        public void onReceive(Context context, Intent intent) {
            // ProgressBar spinner = (ProgressBar) findViewById(R.id.progressBar);
            Log.i("fetch","finished");
            Constants.Pairs = (ArrayList < UrlPairs > ) intent.getExtras().getSerializable("keys");
            getUrlNames();
            Log.i("fetch","finished");


        }
    }

    public void getUrlNames() {
        //spinner.setVisibility(View.GONE);
        //Snackbar.make(null,"Fertig",Snackbar.LENGTH_SHORT).show();
        for (UrlPairs UP: Constants.Pairs) {
            if (UP != null && UP.getEntityName() != null) {
                Names.add(UP.getEntityName());
            } else {
                Log.i("null Object", UP.getName());
            }
        } //Collections.sort(Names);
        if (!loaded) {
            setArrayAdapter();
            createDatabase();
        }
    }

    public void setArrayAdapter() {
       // spinner.setVisibility(View.GONE);
         ArrayAdapter = new ArrayAdapter < String > (this, android.R.layout.simple_list_item_1, Names);

        LV.setAdapter(ArrayAdapter);
        ArrayAdapter.notifyDataSetChanged();


    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter broadcastFilter = new IntentFilter(
                ResponseReceiver.LOCAL_ACTION);
        receiver = new ResponseReceiver();
        LocalBroadcastManager localBroadcastManager =
                LocalBroadcastManager.getInstance(this);
        localBroadcastManager.registerReceiver(receiver,
                broadcastFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager localBroadcastManager =
                LocalBroadcastManager.getInstance(this);
        localBroadcastManager.unregisterReceiver(receiver);
    }
    public void createNamesArrayList(){
        for(UrlPairs U:Constants.Pairs){
            Names.add(U.getEntityName());
        }
    }

    public void LoadDatabase() {
        myDB = this.openOrCreateDatabase("AX365AndroidDatabase", MODE_PRIVATE, null);
        myDB.execSQL("CREATE TABLE IF NOT EXISTS Entity(Name VARCHAR Primary Key,EntityName varchar,Url varchar)");
        Cursor c;
        c = myDB.rawQuery("SELECT * FROM Entity", null);
        c.moveToFirst();

        String count = "SELECT count(*) FROM Entity";
        Cursor mcursor = myDB.rawQuery(count, null);
        mcursor.moveToFirst();
        int icount = mcursor.getInt(0);
        if (icount > 0) {
            Constants.Pairs = new ArrayList < > ();
            if (c.moveToFirst()) {
                while (!c.isAfterLast()) {
                    String name = c.getString(0);
                    String EntityName = c.getString(1);
                    String URL = c.getString(2);
                    Constants.Pairs.add(new UrlPairs(name, EntityName, URL));
                    c.moveToNext();
                }
            }
            loaded = true;
            createNamesArrayList();
            setArrayAdapter();
        }else {
            loaded = false;
        }

    }
    public void createDatabase(){
        myDB = this.openOrCreateDatabase("AX365AndroidDatabase", MODE_PRIVATE, null);
        myDB.execSQL("CREATE TABLE IF NOT EXISTS Entity(Name VARCHAR Primary Key,EntityName varchar,Url varchar)");
        Cursor c;
        c = myDB.rawQuery("SELECT * FROM Entity", null);
        c.moveToFirst();

        String count = "SELECT count(*) FROM Entity";
        Cursor mcursor = myDB.rawQuery(count, null);
        mcursor.moveToFirst();
        for (UrlPairs u: Constants.Pairs) {
            if (u.getEntityName() != null) {
                try {
                    String sqlQuery = "insert into Entity(Name,EntityName,url) values('" + u.getName() + "','" + u.getEntityName() + "','" + u.getURL() + "')";
                    myDB.execSQL(sqlQuery);
                    Log.i("SQL", sqlQuery);
                } catch (Exception E) {
                    Log.i("E", E.getMessage());
                }
            }
        }
    }
    public void createEntities() {
        for (int i = 0; i < requestedEntity.length(); i++) {
            try {
                JSONObject tmp = requestedEntity.getJSONObject(i);
                Entity tmpEntity = new Entity(tmp, orderName);
                entities.add(tmpEntity);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    }
