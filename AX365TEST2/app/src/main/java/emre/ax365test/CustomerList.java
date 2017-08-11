package emre.ax365test;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class CustomerList extends AppCompatActivity {

    ArrayList<Customer> Customers;
    Intent intent;
    ArrayAdapter adapter;
    ArrayList<String> CustomerNames;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_list);
        intent = getIntent();
        Bundle tmp=intent.getBundleExtra("JsonObjects");
        Customers = (ArrayList<Customer>) tmp.getSerializable("JSON");
        CustomerNames=new ArrayList<String>();


        for (int i = 0; i < Customers.size(); i++) {
            CustomerNames.add(Customers.get(i).getName());
        }

        adapter = new ArrayAdapter<String>(this, R.layout.activity_customerlist_textview, CustomerNames);

        listView = (ListView) findViewById(R.id.list);


        listView.setAdapter(adapter);
        registerForContextMenu(listView);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent in =new Intent(view.getContext(),EditCustomer.class);
                Bundle mbundle=new Bundle();
                mbundle.putSerializable("JSON",Customers.get(i));
                in.putExtra("JsonObjects",mbundle);
                startActivity(in);
            }
        });

    }
}
