package emre.ax365test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.json.JSONArray;

import java.util.concurrent.ExecutionException;

public class MyProjects extends AppCompatActivity {

    JSONArray requestedEntity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_projects);

        getJson a = new getJson();
        String RequestData="JournalTables";
        try {
            requestedEntity=null;
            requestedEntity   = a.execute(RequestData).get();//Async Task um JSONs zu bekommen
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
    int k=3;
}
