package s.com.sandboxapp;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class AddActivity extends Activity {

    private ProgressDialog pDialog;

    JSONParser jsonParser = new JSONParser();
    EditText EditID;
    EditText EditDate;
    EditText EditTitle;
    EditText EditDescription;
    EditText EditTime;
    EditText EditLink;
    EditText EditVenue;


    private static String url_Add_Event = "http://192.168.3.217:8080/Sandbox_Connect/addevent.php";

    private static final String TAG_SUCCESS = "success";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        EditID = (EditText) findViewById(R.id.ID);
        EditDate = (EditText) findViewById(R.id.Date);
        EditTitle = (EditText) findViewById(R.id.Title);
        EditDescription = (EditText) findViewById(R.id.Description);
        EditTime = (EditText) findViewById(R.id.Time);
        EditLink = (EditText) findViewById(R.id.Link);
        EditVenue = (EditText) findViewById(R.id.Venue);

        Button btnAddEvent = (Button) findViewById(R.id.AddEvent);

        btnAddEvent.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                new AddNewEvent().execute();
            }
        });
    }

    class AddNewEvent extends AsyncTask<String, String, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(AddActivity.this);
            pDialog.setMessage("Creating Event..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        protected String doInBackground(String... args) {
            String ID = EditID.getText().toString();
            String Date = EditDate.getText().toString();
            String Title = EditTitle.getText().toString();
            String Description = EditDescription.getText().toString();
            String Time = EditTime.getText().toString();
            String Link = EditLink.getText().toString();
            String Venue = EditVenue.getText().toString();


            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("ID", ID));
            params.add(new BasicNameValuePair("Date", Date));
            params.add(new BasicNameValuePair("Title", Title));
            params.add(new BasicNameValuePair("Description", Description));
            params.add(new BasicNameValuePair("Time", Time));
            params.add(new BasicNameValuePair("Link", Link));
            params.add(new BasicNameValuePair("Venue", Venue));

            // getting JSON Object
            // Note that create product url accepts POST method
            JSONObject json = jsonParser.makeHttpRequest(url_Add_Event,
                    "POST", params);

            // check log cat fro response
            Log.d("Create Response", json.toString());

            // check for success tag
            try {
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    // successfully created product
                    Intent i = new Intent(getApplicationContext(), ViewEventsActivity.class);
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



