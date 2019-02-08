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

public class EditStartupActivity extends Activity {

    EditText EditID;
    EditText EditStartupName;
    EditText EditDescription;
    EditText EditStartupFounder;
    EditText EditWebsite;
    EditText txtCreatedAt;
    Button btnSave;
    Button btnDelete;

    String ID;

    // Progress Dialog
    private ProgressDialog pDialog;

    // JSON parser class
    JSONParser jsonParser = new JSONParser();

    // single product url
    private static final String url_startup_detials = "http://192.168.3.217:8080/Sandbox_Connect/get_startup_details.php";

    // url to update product
    private static final String url_update_startups = "http://192.168.3.217:8080/Sandbox_Connect/update_startups.php";

    // url to delete product
    private static final String url_delete_startup = "http://192.168.3.217:8080/Sandbox_Connect/delete_startup.php";

    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_ADD_STARTUP = "add_startup";
    private static final String TAG_SPID = "ID";
    private static final String TAG_SPNAME = "StartupName";
    private static final String TAG_SPDESCRIPTION = "Description";
    private static final String TAG_SPFOUNDER = "StartupFounder";
    private static final String TAG_SPWEBSITE = "Website";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_startup);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        // save button
        btnSave = (Button) findViewById(R.id.btnSave);
        btnDelete = (Button) findViewById(R.id.btnDelete);

        // getting product details from intent
        Intent i = getIntent();

        // getting product id (pid) from intent
        ID = i.getStringExtra(TAG_SPID);

        // Getting complete product details in background thread
        new EditStartupActivity.GetStartupDetails().execute();

        // save button click event
        btnSave.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // starting background task to update product
                new EditStartupActivity.SaveStartupDetails().execute();
            }
        });

        // Delete button click event
        btnDelete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // deleting product in background thread
                new EditStartupActivity.DeleteStartup().execute();
            }
        });

    }

    /**
     * Background Async Task to Get complete product details
     * */
    class GetStartupDetails extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(EditStartupActivity.this);
            pDialog.setMessage("Loading startup details. Please wait...");
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
                                url_startup_detials, "GET", params);

                        // check your log for json response
                        Log.d("Single startup Details", json.toString());

                        // json success tag
                        success = json.getInt(TAG_SUCCESS);
                        if (success == 1) {
                            // successfully received product details
                            JSONArray startupObj = json
                                    .getJSONArray(TAG_ADD_STARTUP); // JSON Array

                            // get first product object from JSON Array
                            JSONObject startup = startupObj.getJSONObject(0);

                            // product with this pid found
                            // Edit Text
                            EditID = (EditText) findViewById(R.id.SPID);
                            EditStartupName = (EditText) findViewById(R.id.SPName);
                            EditDescription = (EditText) findViewById(R.id.SPDescription);
                            EditStartupFounder = (EditText) findViewById(R.id.SPFounder);
                            EditWebsite = (EditText) findViewById(R.id.SPWebsite);


                            // display product data in EditText
                            EditID.setText(startup.getString(TAG_SPID));
                            EditStartupName.setText(startup.getString(TAG_SPNAME));
                            EditDescription.setText(startup.getString(TAG_SPDESCRIPTION));
                            EditStartupFounder.setText(startup.getString(TAG_SPFOUNDER));
                            EditWebsite.setText(startup.getString(TAG_SPWEBSITE));


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
    class SaveStartupDetails extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(EditStartupActivity.this);
            pDialog.setMessage("Saving startup ...");
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
            String StartupName = EditStartupName.getText().toString();
            String Description = EditDescription.getText().toString();
            String StartupFounder = EditStartupFounder.getText().toString();
            String Website = EditWebsite.getText().toString();

            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair(TAG_SPID, ID));
            params.add(new BasicNameValuePair(TAG_SPNAME, StartupName));
            params.add(new BasicNameValuePair(TAG_SPDESCRIPTION, Description));
            params.add(new BasicNameValuePair(TAG_SPFOUNDER, StartupFounder));
            params.add(new BasicNameValuePair(TAG_SPWEBSITE, Website));


            // sending modified data through http request
            // Notice that update product url accepts POST method
            JSONObject json = jsonParser.makeHttpRequest(url_update_startups,
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
    class DeleteStartup extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(EditStartupActivity.this);
            pDialog.setMessage("Deleting Startup...");
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
                        url_delete_startup, "POST", params);

                // check your log for json response
                Log.d("Delete Startup", json.toString());

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


