package s.com.sandboxapp;

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

public class ViewMentorsActivity extends ListActivity {

    // Progress Dialog
    private ProgressDialog pDialog;

    // Creating JSON Parser object
    JSONParser jParser = new JSONParser();

    ArrayList<HashMap<String, String>> mentorsList;

    // url to get all products list
    private static String url_all_mentors = "http://192.168.3.217:8080/Sandbox_Connect/get_all_mentors.php";

    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_ADD_MENTOR = "add_mentor";
    private static final String TAG_MENTID = "ID";
    private static final String TAG_MENTNAME = "Name";

    // products JSONArray
    JSONArray add_mentor = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_mentors);

        // Hashmap for ListView
        mentorsList = new ArrayList<HashMap<String, String>>();

        // Loading products in Background Thread
        new LoadAllMentors().execute();

        // Get listview
        ListView lv = getListView();

        // on seleting single product
        // launching Edit Product Screen
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // getting values from selected ListItem
                String ID = ((TextView) view.findViewById(R.id.MentID)).getText()
                        .toString();

                // Starting new intent
                Intent in = new Intent(getApplicationContext(),
                        EditMentorActivity.class);
                // sending pid to next activity
                in.putExtra(TAG_MENTID, ID);

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
    class LoadAllMentors extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ViewMentorsActivity.this);
            pDialog.setMessage("Loading mentors. Please wait...");
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
            JSONObject json = jParser.makeHttpRequest(url_all_mentors, "GET", params);

            // Check your log cat for JSON reponse
            Log.d("All Mentors: ", json.toString());

            try {
                // Checking for SUCCESS TAG
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    // products found
                    // Getting Array of Products
                    add_mentor = json.getJSONArray(TAG_ADD_MENTOR);

                    // looping through All Products
                    for (int i = 0; i < add_mentor.length(); i++) {
                        JSONObject c = add_mentor.getJSONObject(i);

                        // Storing each json item in variable
                        String ID = c.getString(TAG_MENTID);
                        String Name = c.getString(TAG_MENTNAME);

                        // creating new HashMap
                        HashMap<String, String> map = new HashMap<String, String>();

                        // adding each child node to HashMap key => value
                        map.put(TAG_MENTID, ID);
                        map.put(TAG_MENTNAME, Name);

                        // adding HashList to ArrayList
                        mentorsList.add(map);
                    }
                } else {
                    // no products found
                    // Launch Add New product Activity
                    Intent i = new Intent(getApplicationContext(),
                            AddMentActivity.class);
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
                            ViewMentorsActivity.this, mentorsList,
                            R.layout.list_mentors, new String[] { TAG_MENTID,
                            TAG_MENTNAME},
                            new int[] { R.id.MentID, R.id.MentName });
                    // updating listview
                    setListAdapter(adapter);
                }
            });

        }

    }
}

