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

public class AddMentActivity extends Activity {

    private ProgressDialog pDialog;

    JSONParser jsonParser = new JSONParser();
    EditText EditID;
    EditText EditName;
    EditText EditDesignation;
    EditText EditURL;

    private static String url_Add_Mentor = "http://192.168.3.217:8080/Sandbox_Connect/addmentor.php";

    private static final String TAG_SUCCESS = "success";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ment);

        EditID = (EditText) findViewById(R.id.MentID);
        EditName = (EditText) findViewById(R.id.MentName);
        EditDesignation = (EditText) findViewById(R.id.MentDesignation);
        EditURL = (EditText) findViewById(R.id.MentURL);

        Button btnAddMentor = (Button) findViewById(R.id.AddMentor);

        btnAddMentor.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                new AddNewMentor().execute();
            }
        });
    }

    class AddNewMentor extends AsyncTask<String, String, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(AddMentActivity.this);
            pDialog.setMessage("Creating Mentor..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        protected String doInBackground(String... args) {
            String ID = EditID.getText().toString();
            String Name = EditName.getText().toString();
            String Designation = EditDesignation.getText().toString();
            String URL = EditURL.getText().toString();


            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("ID", ID));
            params.add(new BasicNameValuePair("Name", Name));
            params.add(new BasicNameValuePair("Designation", Designation));
            params.add(new BasicNameValuePair("URL", URL));


            // getting JSON Object
            // Note that create product url accepts POST method
            JSONObject json = jsonParser.makeHttpRequest(url_Add_Mentor,
                    "POST", params);

            // check log cat fro response
            Log.d("Create Response", json.toString());

            // check for success tag
            try {
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    // successfully created product
                    Intent i = new Intent(getApplicationContext(), ViewMentorsActivity.class);
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


