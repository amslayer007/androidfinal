package s.com.sandboxapp;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MentorActivity extends AppCompatActivity {

    Button btnAddMentors;
    Button btnViewMentors;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentor);

        btnAddMentors = (Button) findViewById(R.id.AddMent);
        btnViewMentors = (Button) findViewById(R.id.ViewMent);



        // view products click event
        btnViewMentors.setOnClickListener(new View.OnClickListener() {



            @Override
            public void onClick(View view) {
                // Launching All products Activity
                Intent i = new Intent(getApplicationContext(), ViewMentorsActivity.class);
                startActivity(i);

            }
        });

        // view products click event
        btnViewMentors.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // Launching All products Activity
                Intent i = new Intent(getApplicationContext(), ViewMentorsActivity.class);
                startActivity(i);

            }
        });

        // view products click event
        btnAddMentors.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // Launching create new product activity
                Intent i = new Intent(getApplicationContext(), AddMentActivity.class);
                startActivity(i);

            }
        });


    }

}

