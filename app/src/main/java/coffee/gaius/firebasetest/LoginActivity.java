package coffee.gaius.firebasetest;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

import coffee.gaius.app;
import coffee.gaius.dbAsync;
import coffee.gaius.dbCommands;
import coffee.gaius.dbRequest;
import coffee.gaius.dbResponse;

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
                HashMap<String, String> parameters = new HashMap<String, String>();
                parameters.put("username", loginTxtUsername.getText().toString());
                parameters.put("password", loginTxtPassword.getText().toString());
                new dbAsync(){
                    @Override
                    protected void onPostExecute(dbResponse result) {
                        if (result.hasFailed()) {
                            Toast.makeText(getApplicationContext(),
                                    "Error: " +  result.getParams().get("error"),
                                    Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getApplicationContext(),
                                    "User ID: " +  result.getParams().get("uid") +
                                    ", Provider: " + result.getParams().get("provider"),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                }.execute(new dbRequest((app)LoginActivity.this.getApplication(),
                        dbCommands.userAuth, parameters));
            }
        });

        Button loginBtnRegister = (Button)findViewById(R.id.loginBtnRegister);
        loginBtnRegister.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, String> parameters = new HashMap<String, String>();
                parameters.put("username", loginTxtUsername.getText().toString());
                parameters.put("password", loginTxtPassword.getText().toString());
                new dbAsync(){
                    @Override
                    protected void onPostExecute(dbResponse result) {
                        if (result.hasFailed()) {
                            Toast.makeText(getApplicationContext(),
                                    "Error: " +  result.getParams().get("error"),
                                    Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getApplicationContext(),
                                    "User ID: " +  result.getParams().get("uid"),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                }.execute(new dbRequest((app)LoginActivity.this.getApplication(),
                        dbCommands.userRegister, parameters));
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
