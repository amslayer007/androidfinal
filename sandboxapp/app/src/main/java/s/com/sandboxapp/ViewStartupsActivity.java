package s.com.sandboxapp;

import android.app.Activity;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ViewStartupsActivity extends ListActivity {

    // Progress Dialog
    private ProgressDialog pDialog;

    // Creating JSON Parser object
    JSONParser jParser = new JSONParser();

    ArrayList<HashMap<String, String>> startupsList;

    // url to get all products list
    private static String url_all_startups = "http://192.168.3.217`:8080/Sandbox_Connect/get_all_startups.php";

    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_ADD_STARTUP = "add_startup";
    private static final String TAG_SPID = "ID";
    private static final String TAG_SPNAME = "StartupName";

    // products JSONArray
    JSONArray add_startup = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_startups);

        // Hashmap for ListView
        startupsList = new ArrayList<HashMap<String, String>>();

        // Loading products in Background Thread
        new ViewStartupsActivity.LoadAllStartups().execute();

        // Get listview
        ListView lv = getListView();

        // on seleting single product
        // launching Edit Product Screen
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // getting values from selected ListItem
                String ID = ((TextView) view.findViewById(R.id.SPID)).getText()
                        .toString();

                // Starting new intent
                Intent in = new Intent(getApplicationContext(),
                        EditStartupActivity.class);
                // sending pid to next activity
                in.putExtra(TAG_SPID, ID);

                // starting new activity and expecting some response back
                startActivityForResult(in, 100);
            }
        });

    }

    // Response from Edit Product Activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // if result code 100
        if (resultCode == 100) {
            // if result code 100 is received
            // means user edited/deleted product
            // reload this screen again
            Intent intent = getIntent();
            finish();
            startActivity(intent);
        }

    }

    /**
     * Background Async Task to Load all product by making HTTP Request
     * */
    class LoadAllStartups extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ViewStartupsActivity.this);
            pDialog.setMessage("Loading startups. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        /**
         * getting All products from url
         * */
        protected String doInBackground(String... args) {
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            // getting JSON string from URL
            JSONObject json = jParser.makeHttpRequest(url_all_startups, "GET", params);

            // Check your log cat for JSON reponse
            Log.d("All Startups: ", json.toString());

            try {
                // Checking for SUCCESS TAG
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    // products found
                    // Getting Array of Products
                    add_startup = json.getJSONArray(TAG_ADD_STARTUP);

                    // looping through All Products
                    for (int i = 0; i < add_startup.length(); i++) {
                        JSONObject c = add_startup.getJSONObject(i);

                        // Storing each json item in variable
                        String ID = c.getString(TAG_SPID);
                        String StartupName = c.getString(TAG_SPNAME);

                        // creating new HashMap
                        HashMap<String, String> map = new HashMap<String, String>();

                        // adding each child node to HashMap key => value
                        map.put(TAG_SPID, ID);
                        map.put(TAG_SPNAME, StartupName);

                        // adding HashList to ArrayList
                        startupsList.add(map);
                    }
                } else {
                    // no products found
                    // Launch Add New product Activity
                    Intent i = new Intent(getApplicationContext(),
                            AddStartupActivity.class);
                    // Closing all previous activities
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
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
            // dismiss the dialog after getting all products
            pDialog.dismiss();
            // updating UI from Background Thread
            runOnUiThread(new Runnable() {
                public void run() {
                    /**
                     * Updating parsed JSON data into ListView
                     * */
                    ListAdapter adapter = new SimpleAdapter(
                            ViewStartupsActivity.this, startupsList,
                            R.layout.list_startups, new String[] { TAG_SPID,
                            TAG_SPNAME},
                            new int[] { R.id.SPID, R.id.SPName });
                    // updating listview
                    setListAdapter(adapter);
                }
            });

        }

    }
}


