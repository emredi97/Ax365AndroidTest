package emre.ax365test;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.HashMap;

public class NotificationSettings extends AppCompatActivity {

    HashMap<String, Boolean> hmap = new HashMap<String, Boolean>();
    boolean writedValues;
    SQLiteDatabase myDB;
    Cursor c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_settings);
        createDatabase();
        onDebCheckBoxClick();
        readData();
    }

    public void onDebCheckBoxClick() {
        final SQLiteDatabase myDB = this.openOrCreateDatabase("AX365AndroidDatabase", MODE_PRIVATE, null);
        final CheckBox DebCheckBox = (CheckBox) findViewById(R.id.DebCheckBox);
        DebCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentValues values = new ContentValues();
                if(DebCheckBox.isChecked()) {
                    values.put("Subscriped", "1");
                    //myDB.update("Subscription",values,"SubscriptionName='Debitor'",null);
                    myDB.execSQL("update Subscription set Subscriped=1 where SubscriptionName='Debitor'");
                    String refreshedToken = FirebaseInstanceId.getInstance().getToken();
                    FirebaseMessaging.getInstance().subscribeToTopic("Debitor");
                    Log.i("test","sql");

                }else{
                    values.put("Subscriped", "0");
                    myDB.execSQL("update Subscription set Subscriped=0 where SubscriptionName='Debitor'");
                    FirebaseMessaging.getInstance().unsubscribeFromTopic("Debitor");


                }

            }
        });
    }

    public void onVendCheckBoxClick() {
        SQLiteDatabase myDB = this.openOrCreateDatabase("AX365AndroidDatabase", MODE_PRIVATE, null);
        final CheckBox VendCheckBox = (CheckBox) findViewById(R.id.KredCheckBox);
    }

    public void createDatabase() {
        myDB = this.openOrCreateDatabase("AX365AndroidDatabase", MODE_PRIVATE, null);
        c = myDB.rawQuery("SELECT * FROM Subscription", null);
        c.moveToFirst();
   /* Create a Table in the Database. */
       if (c == null) {
            myDB.execSQL("CREATE TABLE IF NOT EXISTS Subscription(SubscriptionName VARCHAR Primary Key,Subscriped BOOLEAN)");
            myDB.execSQL("INSERT INTO Subscription(SubscriptionName, Subscriped)VALUES('Debitor',0)");
            myDB.execSQL("INSERT INTO Subscription(SubscriptionName, Subscriped)VALUES('Kreditor',0)");

            int Column1 = c.getColumnIndex("SubscriptionName");
            int Column2 = c.getColumnIndex("Subscriped");
        }
    }
    public void readData(){
        CheckBox DebCheckBox = (CheckBox) findViewById(R.id.DebCheckBox);
        myDB.query("Subscription",new String[]{"*"},null,null,null,null,null);
        if (c.moveToFirst()) {
            do {
                Log.i("status", String.valueOf(c.getInt(1)));
                switch(c.getString(0)){
                    case "Debitor":{
                        if(c.getInt(1)==1){
                            DebCheckBox.setChecked(true);

                        }
                        break;
                    }
                    case "Kreditor":{
                        break;
                    }
                }

            }while(c.moveToNext());
        }
    }

}
