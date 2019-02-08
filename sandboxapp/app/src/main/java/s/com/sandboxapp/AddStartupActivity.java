package s.com.sandboxapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AddStartupActivity extends Activity {

    private ProgressDialog pDialog;

    JSONParser jsonParser = new JSONParser();
    EditText EditID;
    EditText EditStartupName;
    EditText EditDescription;
    EditText EditStartupFounder;
    EditText EditWebsite;


    private static String url_Add_Startup = "http://192.168.3.217:8080/Sandbox_Connect/addstartup.php";

    private static final String TAG_SUCCESS = "success";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_startup);

        EditID = (EditText) findViewById(R.id.SPID);
        EditStartupName = (EditText) findViewById(R.id.SPName);
        EditDescription = (EditText) findViewById(R.id.SPDescription);
        EditStartupFounder = (EditText) findViewById(R.id.SPFounder);
        EditWebsite = (EditText) findViewById(R.id.SPWebsite);

        Button btnAddStartup = (Button) findViewById(R.id.AddStartup1);

        btnAddStartup.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                new AddStartupActivity.AddNewStartup().execute();
            }
        });
    }

    class AddNewStartup extends AsyncTask<String, String, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(AddStartupActivity.this);
            pDialog.setMessage("Creating Startup..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        protected String doInBackground(String... args) {
            String ID = EditID.getText().toString();
            String StartupName = EditStartupName.getText().toString();
            String Description = EditDescription.getText().toString();
            String StartupFounder = EditStartupFounder.getText().toString();
            String Website = EditWebsite.getText().toString();


            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("ID", ID));
            params.add(new BasicNameValuePair("StartupName", StartupName));
            params.add(new BasicNameValuePair("Description", Description));
            params.add(new BasicNameValuePair("StartupFounder", StartupFounder));
            params.add(new BasicNameValuePair("Website", Website));

            // getting JSON Object
            // Note that create product url accepts POST method
            JSONObject json = jsonParser.makeHttpRequest(url_Add_Startup,
                    "POST", params);

            // check log cat fro response
            Log.d("Create Response", json.toString());

            // check for success tag
            try {
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    // successfully created product
                    Intent i = new Intent(getApplicationContext(), ViewStartupsActivity.class);
                    startActivity(i);

                    // closing this screen
                    finish();
                } else {
                    // failed to create product

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once done
            pDialog.dismiss();
        }

    }
}

