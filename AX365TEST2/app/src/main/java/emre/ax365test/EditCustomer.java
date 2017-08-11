package emre.ax365test;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class EditCustomer extends AppCompatActivity {

    private Intent intent;
    private Customer Customers;
    LinearLayout linear;
    ArrayList<Values>values;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_customer);

        intent = getIntent();
        Bundle tmp=intent.getBundleExtra("JsonObjects");
        Customers = (Customer) tmp.getSerializable("JSON");
        values=Customers.getValues();
        linear= (LinearLayout) findViewById(R.id.custeditlinear);

        for(int i=1;i<values.size();i++){
            EditText edit=new EditText(this);
            edit.setText(values.get(i).getKey()+" "+values.get(i).getValue());
            linear.addView(edit);

        }
        linear.invalidate();


    }
}
