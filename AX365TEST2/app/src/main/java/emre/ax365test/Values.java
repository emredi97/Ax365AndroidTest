package emre.ax365test;

import java.io.Serializable;

/**
 * Created by emreh_000 on 11.08.2017.
 */

public class Values implements Serializable {

    String Key;
    String Value;

    public Values(String key, String value) {
        Key = key;
        Value = value;

    }

    public String getKey() {
        return Key;
    }

    public String getValue() {
        return Value;
    }


}
