package s.com.sandboxapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StartupActivity extends AppCompatActivity {

    Button btnAddStartups;
    Button btnViewStartups;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup);

        btnAddStartups = (Button) findViewById(R.id.AddStartup);
        btnViewStartups = (Button) findViewById(R.id.ViewStartup);



        // view products click event
        btnViewStartups.setOnClickListener(new View.OnClickListener() {



            @Override
            public void onClick(View view) {
                // Launching All products Activity
                Intent i = new Intent(getApplicationContext(), ViewStartupsActivity.class);
                startActivity(i);

            }
        });

        // view products click event
        btnViewStartups.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // Launching All products Activity
                Intent i = new Intent(getApplicationContext(), ViewStartupsActivity.class);
                startActivity(i);

            }
        });

        // view products click event
        btnAddStartups.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // Launching create new product activity
                Intent i = new Intent(getApplicationContext(), AddStartupActivity.class);
                startActivity(i);

            }
        });


    }

}

