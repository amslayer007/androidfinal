package s.com.sandboxapp;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class EventActivity extends Activity {

    Button btnAddEvents;
    Button btnViewEvents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        btnAddEvents = (Button) findViewById(R.id.button1);
        btnViewEvents = (Button) findViewById(R.id.button4);



        // view products click event
        btnViewEvents.setOnClickListener(new View.OnClickListener() {



            @Override
            public void onClick(View view) {
                // Launching All products Activity
                Intent i = new Intent(getApplicationContext(), ViewEventsActivity.class);
                startActivity(i);

            }
        });

        // view products click event
        btnViewEvents.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // Launching All products Activity
                Intent i = new Intent(getApplicationContext(), ViewEventsActivity.class);
                startActivity(i);

            }
        });

        // view products click event
        btnAddEvents.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // Launching create new product activity
                Intent i = new Intent(getApplicationContext(), AddActivity.class);
                startActivity(i);

            }
        });


    }

}

