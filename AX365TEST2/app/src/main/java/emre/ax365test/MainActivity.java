package emre.ax365test;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;
import com.microsoft.aad.adal.AuthenticationCallback;
import com.microsoft.aad.adal.AuthenticationContext;
import com.microsoft.aad.adal.AuthenticationResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import javax.crypto.NoSuchPaddingException;

public class MainActivity extends AppCompatActivity {

    private com.microsoft.aad.adal.AuthenticationContext mAuthContext;
    TextView result;
    String bcode;
    JSONArray requestedEntity;
    ArrayList<Entity> entities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        entities = new ArrayList<Entity>();
        try {

            mAuthContext = new AuthenticationContext(MainActivity.this, Constants.AUTHORITY_URL,
                    false);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        }
        result = (TextView) findViewById(R.id.reult);

    }


    //LOGIN CLICK
    public void Login(View V) {
      /*  CookieSyncManager.createInstance(MainActivity.this);
        CookieManager cookieManager = CookieManager.getInstance();          //Wenn man sich jedesmal anmelden möchte auskommentieren
        cookieManager.removeAllCookie();
        CookieSyncManager.getInstance().sync();
        mAuthContext.getCache().removeAll();*/


        mAuthContext.getCache().removeAll();//Löscht die Anmeldedaten
        mAuthContext.acquireToken(MainActivity.this, Constants.RESOURCE_ID, Constants.CLIENT_ID,//Token vom Server holen
                Constants.REDIRECT_URL, Constants.USER_HINT, "nux=1&" + Constants.EXTRA_QP, new AuthenticationCallback<AuthenticationResult>() {
                    @Override
                    public void onSuccess(AuthenticationResult result) {
                        Constants.CURRENT_RESULT = result;

                    }

                    @Override
                    public void onError(Exception exc) {

                    }
                });


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0) {
            if (resultCode == CommonStatusCodes.SUCCESS) {
                result = (TextView) findViewById(R.id.reult);
                if (data != null) {//wenn die Activity einen Intent zurückgegeben hat setze den Barcode
                    Barcode b = data.getParcelableExtra("barcode");
                    result.setText(b.displayValue);
                    bcode = b.displayValue;
                } else {

                    result.setText("No Barcode");
                }
            }

        } else {
            super.onActivityResult(requestCode, resultCode, data);
            mAuthContext.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void doRequest(View V) {

        getJson a = new getJson();
        getEntitys g=new getEntitys();
        String RequestData;

        TextView text = (TextView) findViewById(R.id.get);



        RequestData = text.getText().toString();

        try {
            g.execute().get();
            requestedEntity = a.execute(RequestData).get();//Async Task um JSONs zu bekommen
            int ab;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < requestedEntity.length(); i++) {
            try {
                JSONObject tmp = requestedEntity.getJSONObject(i);
                Entity tmpEntity = new Entity(tmp);

                entities.add(tmpEntity);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }


    }

    public void Scan(View V) {

        Intent intent = new Intent(this, BarcodeActivity.class);//Startet Barcode Activty
        startActivityForResult(intent, 0);


    }

    public void onBarcodeclick(View V) {


        Intent i = new Intent(Intent.ACTION_VIEW);//Versucht den QR& Barcode im Browser zu öffnen
        i.setData(Uri.parse(bcode));
        startActivity(i);


    }

    public void onCustomerClick(View V) {

        Intent i = new Intent(this, CustomerList.class);
        Bundle mbundle = new Bundle();
        mbundle.putSerializable("JSON", entities);
        i.putExtra("JsonObjects", mbundle);
        startActivity(i);


    }
    public void req(View V) {


        sendJSON d = new sendJSON();

        try {
            d.execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }


    }
