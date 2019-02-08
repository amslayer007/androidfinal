package s.com.sandboxapp;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MeetingActivity extends Activity {

    Button btnAddSlots;
    Button btnViewSlots;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting);

        btnAddSlots = (Button) findViewById(R.id.BookSlot);
        btnViewSlots = (Button) findViewById(R.id.ViewSlots);



        // view products click event
        btnViewSlots.setOnClickListener(new View.OnClickListener() {



            @Override
            public void onClick(View view) {
                // Launching All products Activity
                Intent i = new Intent(getApplicationContext(), ViewSlotsActivity.class);
                startActivity(i);

            }
        });

        // view products click event
        btnViewSlots.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // Launching All products Activity
                Intent i = new Intent(getApplicationContext(), ViewSlotsActivity.class);
                startActivity(i);

            }
        });

        // view products click event
        btnAddSlots.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // Launching create new product activity
                Intent i = new Intent(getApplicationContext(), BookSlotActivity.class);
                startActivity(i);

            }
        });


    }

}


