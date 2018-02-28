package emre.ax365test;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class OrderByScreen extends AppCompatActivity {

    ArrayList<String>keys=new ArrayList<>();
    ArrayAdapter<String>ArrayAdapter;
    ListView LV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        convertCreateJson((String) getIntent().getExtras().get("Entity"));

        setContentView(R.layout.activity_order_by_screen);
        ArrayAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,keys) ;
        LV= (ListView) findViewById(R.id.Listview_order_screen);
        LV.setAdapter(ArrayAdapter);

        LV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Intent I=new Intent();
                I.putExtra("OrderKey",(String)LV.getItemAtPosition(i));
                setResult(25,I);
                finish();
            }
        });


    }
    public void convertCreateJson(String EntityString){
        try {
            JSONObject EntityArray = new JSONObject(EntityString);
            Iterator i=EntityArray.keys();
            i.next().toString();
            do{
                keys.add(i.next().toString());
            }while(i.hasNext());

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
