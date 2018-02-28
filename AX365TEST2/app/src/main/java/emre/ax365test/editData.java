package emre.ax365test;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.util.ArrayList;

public class editData extends AppCompatActivity {

    private Intent intent;
    private Entity customers;
    LinearLayout linear;
    ArrayList < KeyPairsJson > values;
    ArrayList < String > entityKey;
    ArrayAdapter adapter;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_entity_list);
        intent = getIntent();

        Bundle tmp = intent.getBundleExtra("JsonObjects");
        customers = (Entity) tmp.getSerializable("JSON");

        entityKey = new ArrayList < String > ();

        values = customers.getValues();

        for (int i = 1; i < values.size(); i++) {

            entityKey.add(values.get(i).getKey() + ": " + values.get(i).getValue());

        }


        for (int i = 1; i < values.size(); i++) {
            EditText edit = new EditText(this);
            edit.setText(values.get(i).getKey() + " " + values.get(i).getValue());


        }
        adapter = new ArrayAdapter < String > (this, R.layout.activity_entitylist_textview, entityKey);
        listView = (ListView) findViewById(R.id.listEntityEdit);
        listView.setAdapter(adapter);
        registerForContextMenu(listView);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView < ? > adapterView, View view, int i, long l) {

                final EditText input = new EditText(editData.this);
                RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.MATCH_PARENT,
                        RelativeLayout.LayoutParams.MATCH_PARENT);
                input.setText("Neuer Wert");
                input.setLayoutParams(lp);

                AlertDialog.Builder build = new AlertDialog.Builder(editData.this);
                build.setTitle("Wert verändern");
                build.setMessage("Bitte geben sie den neuen Wer ein");
                build.setView(input);
                build.setPositiveButton("Bestätigen", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        updateDataAsync u = new updateDataAsync();
                        u.execute("test");

                    }
                });
                build.setNegativeButton("Abbrechen", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                AlertDialog alert = build.create();
                alert.show();


            }
        });


    }


}