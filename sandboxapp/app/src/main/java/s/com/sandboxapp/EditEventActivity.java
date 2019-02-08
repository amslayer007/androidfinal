package s.com.sandboxapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.os.Build.ID;

public class EditEventActivity extends Activity {

    EditText EditID;
    EditText EditDate;
    EditText EditTitle;
    EditText EditDescription;
    EditText EditTime;
    EditText EditLInk;
    EditText EditVenue;
    EditText txtCreatedAt;
    Button btnSave;
    Button btnDelete;

    String ID;

    // Progress Dialog
    private ProgressDialog pDialog;

    // JSON parser class
    JSONParser jsonParser = new JSONParser();

    // single product url
    private static final String url_event_detials = "http://192.168.3.217:8080/Sandbox_Connect/get_event_details.php";

    // url to update product
    private static final String url_update_events = "http://192.168.3.217:8080/Sandbox_Connect/update_events.php";

    // url to delete product
    private static final String url_delete_event = "http://192.168.3.217:8080/Sandbox_Connect/delete_event.php";

    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_ADD_EVENT = "add_event";
    private static final String TAG_ID = "ID";
    private static final String TAG_DATE = "Date";
    private static final String TAG_TITLE = "Title";
    private static final String TAG_DESCRIPTION = "Description";
    private static final String TAG_TIME = "Time";
    private static final String TAG_LINK = "Link";
    private static final String TAG_VENUE = "Venue";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_event);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        // save button
        btnSave = (Button) findViewById(R.id.btnSave);
        btnDelete = (Button) findViewById(R.id.btnDelete);

        // getting product details from intent
        Intent i = getIntent();

        // getting product id (pid) from intent
        ID = i.getStringExtra(TAG_ID);

        // Getting complete product details in background thread
        new GetEventDetails().execute();

        // save button click event
        btnSave.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // starting background task to update product
                new SaveEventDetails().execute();
            }
        });

        // Delete button click event
        btnDelete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // deleting product in background thread
                new DeleteEvent().execute();
            }
        });

    }

    /**
     * Background Async Task to Get complete product details
     * */
    class GetEventDetails extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(EditEventActivity.this);
            pDialog.setMessage("Loading event details. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * Getting product details in background thread
         * */
        protected String doInBackground(String... params) {

            // updating UI from Background Thread
            runOnUiThread(new Runnable() {
                public void run() {
                    // Check for success tag
                    int success;
                    try {
                        // Building Parameters
                        List<NameValuePair> params = new ArrayList<NameValuePair>();
                        params.add(new BasicNameValuePair("ID",ID));

                        // getting product details by making HTTP request
                        // Note that product details url will use GET request
                        JSONObject json = jsonParser.makeHttpRequest(
                                url_event_detials, "GET", params);

                        // check your log for json response
                        Log.d("Single event Details", json.toString());

                        // json success tag
                        success = json.getInt(TAG_SUCCESS);
                        if (success == 1) {
                            // successfully received product details
                            JSONArray eventObj = json
                                    .getJSONArray(TAG_ADD_EVENT); // JSON Array

                            // get first product object from JSON Array
                            JSONObject event = eventObj.getJSONObject(0);

                            // product with this pid found
                            // Edit Text
                            EditID = (EditText) findViewById(R.id.ID);
                            EditDate = (EditText) findViewById(R.id.Date);
                            EditTitle = (EditText) findViewById(R.id.Title);
                            EditDescription = (EditText) findViewById(R.id.Description);
                            EditTime = (EditText) findViewById(R.id.Time);
                            EditLInk = (EditText) findViewById(R.id.Link);
                            EditVenue = (EditText) findViewById(R.id.Venue);


                            // display product data in EditText
                            EditID.setText(event.getString(TAG_ID));
                            EditDate.setText(event.getString(TAG_DATE));
                            EditTitle.setText(event.getString(TAG_TITLE));
                            EditDescription.setText(event.getString(TAG_DESCRIPTION));
                            EditTime.setText(event.getString(TAG_TIME));
                            EditLInk.setText(event.getString(TAG_LINK));
                            EditVenue.setText(event.getString(TAG_VENUE));


                        }else{
                            // product with pid not found
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });

            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once got all details
            pDialog.dismiss();
        }
    }

    /**
     * Background Async Task to  Save product Details
     * */
    class SaveEventDetails extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(EditEventActivity.this);
            pDialog.setMessage("Saving event ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * Saving product
         * */
        protected String doInBackground(String... args) {

            // getting updated data from EditTexts
            String ID = EditID.getText().toString();
            String Date = EditDate.getText().toString();
            String Title = EditTitle.getText().toString();
            String Description = EditDescription.getText().toString();
            String Time = EditTime.getText().toString();
            String Link = EditLInk.getText().toString();
            String Venue = EditVenue.getText().toString();

            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair(TAG_ID, ID));
            params.add(new BasicNameValuePair(TAG_DATE, Date));
            params.add(new BasicNameValuePair(TAG_TITLE, Title));
            params.add(new BasicNameValuePair(TAG_DESCRIPTION, Description));
            params.add(new BasicNameValuePair(TAG_TIME, Time));
            params.add(new BasicNameValuePair(TAG_LINK, Link));
            params.add(new BasicNameValuePair(TAG_VENUE, Venue));


            // sending modified data through http request
            // Notice that update product url accepts POST method
            JSONObject json = jsonParser.makeHttpRequest(url_update_events,
                    "POST", params);

            // check json success tag
            try {
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    // successfully updated
                    Intent i = getIntent();
                    // send result code 100 to notify about product update
                    setResult(100, i);
                    finish();
                } else {
                    // failed to update product
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
            // dismiss the dialog once product uupdated
            pDialog.dismiss();
        }
    }

    /*****************************************************************
     * Background Async Task to Delete Product
     * */
    class DeleteEvent extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(EditEventActivity.this);
            pDialog.setMessage("Deleting Event...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * Deleting product
         * */
        protected String doInBackground(String... args) {

            // Check for success tag
            int success;
            try {
                // Building Parameters
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("ID", ID));

                // getting product details by making HTTP request
                JSONObject json = jsonParser.makeHttpRequest(
                        url_delete_event, "POST", params);

                // check your log for json response
                Log.d("Delete Event", json.toString());

                // json success tag
                success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                    // product successfully deleted
                    // notify previous activity by sending code 100
                    Intent i = getIntent();
                    // send result code 100 to notify about product deletion
                    setResult(100, i);
                    finish();
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
            // dismiss the dialog once product deleted
            pDialog.dismiss();

        }

    }
}



