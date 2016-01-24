package coffee.gaius.firebasetest;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.Map;

import coffee.gaius.app;

public class LoginActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText loginTxtUsername = (EditText) findViewById(R.id.loginTxtUsername);
        final EditText loginTxtPassword = (EditText) findViewById(R.id.loginTxtPassword);

        Button loginBtnSubmit = (Button)findViewById(R.id.loginBtnSubmit);
        loginBtnSubmit.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = loginTxtUsername.getText().toString();
                String password = loginTxtPassword.getText().toString();

                ((app)LoginActivity.this.getApplication()).getDb().auth(username, password,
                        new Firebase.AuthResultHandler() {
                            @Override
                            public void onAuthenticated(AuthData authData) {
                                Toast.makeText(getApplicationContext(), "User ID: " + authData.getUid() +
                                        ", Provider: " + authData.getProvider(), Toast.LENGTH_SHORT).show();
                            }
                            @Override
                            public void onAuthenticationError(FirebaseError firebaseError) {
                                Toast.makeText(getApplicationContext(), "Error: " +
                                        firebaseError.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        Button loginBtnRegister = (Button)findViewById(R.id.loginBtnRegister);
        loginBtnRegister.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = loginTxtUsername.getText().toString();
                String password = loginTxtPassword.getText().toString();

                ((app)LoginActivity.this.getApplication()).getDb().register(username, password,
                        new Firebase.ValueResultHandler<Map<String, Object>>() {
                            @Override
                            public void onSuccess(Map<String, Object> result) {
                                Toast.makeText(getApplicationContext(), "User ID: " +
                                        result.get("uid"), Toast.LENGTH_SHORT).show();
                            }
                            @Override
                            public void onError(FirebaseError firebaseError) {
                                Toast.makeText(getApplicationContext(), "Error: " +
                                        firebaseError.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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
        }

        return super.onOptionsItemSelected(item);
    }
}
