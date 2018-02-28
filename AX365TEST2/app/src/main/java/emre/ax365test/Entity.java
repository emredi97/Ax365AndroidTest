package emre.ax365test;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by emreh_000 on 11.08.2017.
 */

public class Entity implements Serializable {

    private static final long serialVersionUID = -7060210544600464481L;


    ArrayList < KeyPairsJson > values;
    Iterator i;
    String Name;

    Entity(JSONObject cust,String orderName) {
        i = cust.keys();

        values = new ArrayList < KeyPairsJson > ();

        try {
            Name = cust.getString(orderName);
            for (int k = 0; k < cust.length(); k++) {

                String key = i.next().toString();
                String value = cust.getString(key);
                KeyPairsJson tmpKeyPairsJson = new KeyPairsJson(key, value);
                values.add(tmpKeyPairsJson);
                Log.i(key, value);
            }
            i = null;


        } catch (JSONException e) {
            Log.i("Json Log", e.getMessage());
        }

    }

    public String getName() {
        return Name;
    }

    public ArrayList < KeyPairsJson > getValues() {
        return values;
    }
}