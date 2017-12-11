package emre.ax365test;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class RecordList extends AppCompatActivity {

    ArrayList<Entity> entities;
    Intent intent;
    ArrayAdapter adapter;
    ArrayList<String> CustomerNames;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_entity_list);
            intent = getIntent();
            Bundle tmp = intent.getBundleExtra("JsonObjects");
            entities = (ArrayList<Entity>) tmp.getSerializable("JSON");
        }catch(Exception o){
            Log.i("Error",o.getMessage());
        }
        CustomerNames = new ArrayList<String>();


        for (int i = 0; i < entities.size(); i++) {
            CustomerNames.add(entities.get(i).getName());
        }

        adapter = new ArrayAdapter<String>(this, R.layout.activity_entitylist_textview, CustomerNames);

        listView = (ListView) findViewById(R.id.list);


        listView.setAdapter(adapter);
        registerForContextMenu(listView);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent in = new Intent(view.getContext(), EditEntity.class);
                Bundle mbundle = new Bundle();
                mbundle.putSerializable("JSON", entities.get(i));
                in.putExtra("JsonObjects", mbundle);
                startActivity(in);
            }
        });

        listView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {


                return false;
            }
        });

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        AdapterView.AdapterContextMenuInfo info =
                (AdapterView.AdapterContextMenuInfo) menuInfo;
        String selectedWord = ((TextView) info.targetView).getText().toString();
        long selectedWordId = info.id;

        menu.setHeaderTitle("Option");
        menu.add(0, v.getId(), 0, "Löschen");

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getTitle() == "Löschen") {


            deleteData d = new deleteData();
            d.execute("test");


        } else {
            return false;
        }
        return true;
    }
}
