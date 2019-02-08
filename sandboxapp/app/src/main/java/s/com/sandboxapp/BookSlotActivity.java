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
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class BookSlotActivity extends Activity {

    private ProgressDialog pDialog;

    JSONParser jsonParser = new JSONParser();
    EditText EditID;
    EditText EditDate;
    EditText EditTime;
    EditText EditStartupName;
    EditText EditEmail;


    private RadioGroup radioSlotGroup;
    private RadioButton radioSlotButton;
    private Button btnDisplay;


    private static String url_Add_Slot = "http://192.168.3.217:8080/Sandbox_Connect/addslot.php";

    private static final String TAG_SUCCESS = "success";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_slot);



        EditID = (EditText) findViewById(R.id.BSID);
        EditDate = (EditText) findViewById(R.id.BSDate);
        EditTime = (EditText) findViewById(R.id.BSTime);
        EditStartupName = (EditText) findViewById(R.id.BSName);
        EditEmail = (EditText) findViewById(R.id.BSEmail);
        radioSlotGroup = (RadioGroup) findViewById(R.id.radioslot);




        Button btnAddSlot = (Button) findViewById(R.id.BookSlot1);

        btnAddSlot.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                new BookSlotActivity.AddNewSlot().execute();
            }
        });
    }

    class AddNewSlot extends AsyncTask<String, String, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(BookSlotActivity.this);
            pDialog.setMessage("Creating Slot..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        protected String doInBackground(String... args) {
            String ID = EditID.getText().toString();
            String Date = EditDate.getText().toString();
            String Time = EditTime.getText().toString();
            String StartupName = EditStartupName.getText().toString();
            String Email = EditEmail.getText().toString();
            // get selected radio button from radioGroup
            int selectedId = radioSlotGroup.getCheckedRadioButtonId();

            // find the radiobutton by returned id
            radioSlotButton = (RadioButton) findViewById(selectedId);
            String Slotnumber = (String) radioSlotButton.getText();


            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("ID", ID));
            params.add(new BasicNameValuePair("Date", Date));
            params.add(new BasicNameValuePair("Time", Time));
            params.add(new BasicNameValuePair("StartupName", StartupName));
            params.add(new BasicNameValuePair("Email", Email));
            params.add(new BasicNameValuePair("Slotnumber", Slotnumber));

            // getting JSON Object
            // Note that create product url accepts POST method
            JSONObject json = jsonParser.makeHttpRequest(url_Add_Slot,
                    "POST", params);

            // check log cat fro response
            Log.d("Create Response", json.toString());

            // check for success tag
            try {
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    // successfully created product
                    Intent i = new Intent(getApplicationContext(), ViewSlotsActivity.class);
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


    