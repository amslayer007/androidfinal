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
import android.widget.RadioGroup;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class EditSlotActivity extends Activity {

    EditText EditID;
    EditText EditDate;
    EditText EditTime;
    EditText EditStartupName;
    EditText EditEmail;
    EditText txtCreatedAt;
    Button btnSave;
    Button btnDelete;

   // private RadioGroup radioSlotGroup;

    String ID;

    // Progress Dialog
    private ProgressDialog pDialog;

    // JSON parser class
    JSONParser jsonParser = new JSONParser();

    // single product url
    private static final String url_slot_detials = "http://192.168.3.217:8080/Sandbox_Connect/get_slot_details.php";

    // url to update product
    private static final String url_update_slots = "http://192.168.3.217:8080/Sandbox_Connect/update_slots.php";

    // url to delete product
    private static final String url_delete_slot = "http://192.168.3.217:8080/Sandbox_Connect/delete_slot.php";

    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_ADD_SLOT = "add_slot";
    private static final String TAG_BSID = "ID";
    private static final String TAG_BSDATE = "Date";
    private static final String TAG_BSTIME = "Time";
    private static final String TAG_BSNAME = "StartupName";
    private static final String TAG_BSEMAIL = "Email";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_slot);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        // save button
        btnSave = (Button) findViewById(R.id.btnSave);
        btnDelete = (Button) findViewById(R.id.btnDelete);

        // getting product details from intent
        Intent i = getIntent();

        // getting product id (pid) from intent
        ID = i.getStringExtra(TAG_BSID);

        // Getting complete product details in background thread
        new EditSlotActivity.GetSlotDetails().execute();

        // save button click event
        btnSave.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // starting background task to update product
                new EditSlotActivity.SaveSlotDetails().execute();
            }
        });

        // Delete button click event
        btnDelete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // deleting product in background thread
                new EditSlotActivity.DeleteSlot().execute();
            }
        });

    }

    /**
     * Background Async Task to Get complete product details
     * */
    class GetSlotDetails extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(EditSlotActivity.this);
            pDialog.setMessage("Loading slot details. Please wait...");
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
                                url_slot_detials, "GET", params);

                        // check your log for json response
                        Log.d("Single slot Details", json.toString());

                        // json success tag
                        success = json.getInt(TAG_SUCCESS);
                        if (success == 1) {
                            // successfully received product details
                            JSONArray slotObj = json
                                    .getJSONArray(TAG_ADD_SLOT); // JSON Array

                            // get first product object from JSON Array
                            JSONObject slot = slotObj.getJSONObject(0);

                            // product with this pid found
                            // Edit Text
                            EditID = (EditText) findViewById(R.id.BSID);
                            EditDate = (EditText) findViewById(R.id.BSDate);
                            EditTime = (EditText) findViewById(R.id.BSTime);
                            EditStartupName = (EditText) findViewById(R.id.BSName);
                            EditEmail = (EditText) findViewById(R.id.BSEmail);


                            // display product data in EditText
                            EditID.setText(slot.getString(TAG_BSID));
                            EditDate.setText(slot.getString(TAG_BSDATE));
                            EditTime.setText(slot.getString(TAG_BSTIME));
                            EditStartupName.setText(slot.getString(TAG_BSNAME));
                            EditEmail.setText(slot.getString(TAG_BSEMAIL));


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
    class SaveSlotDetails extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(EditSlotActivity.this);
            pDialog.setMessage("Saving slot ...");
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
            String Time = EditTime.getText().toString();
            String StartupName = EditStartupName.getText().toString();
            String Email = EditEmail.getText().toString();

            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair(TAG_BSID, ID));
            params.add(new BasicNameValuePair(TAG_BSDATE, Date));
            params.add(new BasicNameValuePair(TAG_BSTIME, Time));
            params.add(new BasicNameValuePair(TAG_BSNAME, StartupName));
            params.add(new BasicNameValuePair(TAG_BSEMAIL, Email));


            // sending modified data through http request
            // Notice that update product url accepts POST method
            JSONObject json = jsonParser.makeHttpRequest(url_update_slots,
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
    class DeleteSlot extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(EditSlotActivity.this);
            pDialog.setMessage("Deleting Slot...");
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
                        url_delete_slot, "POST", params);

                // check your log for json response
                Log.d("Delete Slot", json.toString());

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




