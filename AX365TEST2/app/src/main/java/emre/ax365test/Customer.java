package emre.ax365test;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by emreh_000 on 11.08.2017.
 */

public class Customer implements Serializable {

    private static final long serialVersionUID = -7060210544600464481L;


    ArrayList<Values> values;
    Iterator i;
    String Name;

    Customer(JSONObject cust) {
        i = cust.keys();

        values = new ArrayList<Values>();

        try {
            Name=cust.getString("Name");
            for(int k=0;k<cust.length();k++) {

                String key = i.next().toString();
                String value = cust.getString(key);
                Values tmpValues = new Values(key, value);
                values.add(tmpValues);

            }
            i=null;


        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    public String getName() {
        return Name;
    }

    public ArrayList<Values> getValues() {
        return values;
    }
}
