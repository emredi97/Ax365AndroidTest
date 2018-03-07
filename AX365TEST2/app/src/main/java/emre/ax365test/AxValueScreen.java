package emre.ax365test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AxValueScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ax_value_screen);

        Button b =(Button)findViewById(R.id.login);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText auth_url = (EditText)findViewById(R.id.AuthorityUrl);
                EditText Ax_url = (EditText)findViewById(R.id.axUrl);
                EditText clientId = (EditText) findViewById(R.id.clientId);
                EditText redirectUrl = (EditText) findViewById(R.id.redirectUrl);

                Constants.AUTHORITY_URL=auth_url.getText().toString();
                Constants.REDIRECT_URL = redirectUrl.getText().toString();
                Constants.RESOURCE_ID = Ax_url.getText().toString();
                Constants.CLIENT_ID = clientId.getText().toString();

                finish();
            }
        });

    }
}
