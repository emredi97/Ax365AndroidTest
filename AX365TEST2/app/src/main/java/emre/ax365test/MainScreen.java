package emre.ax365test;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
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

public class MainScreen extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private com.microsoft.aad.adal.AuthenticationContext mAuthContext;
    TextView result;
    String bcode;
    JSONArray requestedEntity;
    ArrayList<Entity> entities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        entities = new ArrayList<Entity>();
        try {

            mAuthContext = new AuthenticationContext(this, Constants.AUTHORITY_URL,
                    false);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        }
        result = (TextView) findViewById(R.id.reult);

        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        FirebaseMessaging.getInstance().subscribeToTopic("news");
        Log.i("firebase", "Refreshed token: " + refreshedToken);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }else if(id==R.id.Entity_list){

        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
            Intent intent = new Intent(this, BarcodeActivity.class);//Startet Barcode Activty
            startActivityForResult(intent, 0);
        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.Push_Settings) {
            Intent intent=new Intent(this,NotificationSettings.class);
            startActivity(intent);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void Login(View V) {
       /* CookieSyncManager.createInstance(MainScreen.this);
        CookieManager cookieManager = CookieManager.getInstance();          //Wenn man sich jedesmal anmelden möchte auskommentieren
        cookieManager.removeAllCookie();
        CookieSyncManager.getInstance().sync();
        mAuthContext.getCache().removeAll();*/

        mAuthContext.acquireToken(MainScreen.this, Constants.RESOURCE_ID, Constants.CLIENT_ID,//Token vom Server holen
                Constants.REDIRECT_URL, Constants.USER_HINT, "nux=1&" + Constants.EXTRA_QP, new AuthenticationCallback<AuthenticationResult>() {
                    @Override
                    public void onSuccess(AuthenticationResult result) {
                        Constants.CURRENT_RESULT = result;
                        Log.i("USER",result.getUserInfo().getDisplayableId());
                        Log.i("Token",result.getAccessToken());
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
        if (Constants.CURRENT_RESULT != null) {
            getJson a = new getJson();
            String RequestData;
            TextView text = (TextView) findViewById(R.id.get);
            RequestData = text.getText().toString();
            Button listButton = (Button) findViewById(R.id.show_list_button);
            listButton.setText(RequestData + " Liste");

            try {
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
        } else {
            Snackbar mySnackbar = Snackbar.make(V, "Bitte loggen sie sich ein", Snackbar.LENGTH_SHORT);
            mySnackbar.setAction("Einloggen", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Login(view);
                }
            });
            mySnackbar.show();
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

    public void onListButtonClick(View V) {
        try {
            Intent i = new Intent(MainScreen.this, RecordList.class);
            Bundle mbundle = new Bundle();
            mbundle.putSerializable("JSON", entities);
            i.putExtra("JsonObjects", mbundle);
            Log.i("test","tet");
            startActivity(i);
        }catch(Exception i){
          Log.i("Fehler",i.getMessage());
        }

    }

    public void req(View V) {
        createData d = new createData();
        try {
            d.execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

}
