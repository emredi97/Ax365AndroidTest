package emre.ax365test;

import java.io.Serializable;

/**
 * Created by emreh_000 on 28.02.2018.
 */

public class UrlPairs  implements Serializable{

    private static final long serialVersionUID = -7060210454600464481L;
    private String name;
    private String EntityName;
    private String URL;

    public UrlPairs() {
    }

    public UrlPairs(String name,String EntityName, String URL) {
        this.setName(name);
        this.setURL(URL);
        this.setEntityName(EntityName);

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public String getEntityName() {
        return EntityName;
    }

    public void setEntityName(String entityName) {
        EntityName = entityName;
    }
}
