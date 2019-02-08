package s.com.sandboxapp2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void events (View view) {
        Intent randomIntent = new Intent(this, EventActivity.class);
        startActivity(randomIntent);
    }
    public void Mentors (View view) {
        Intent randomIntent = new Intent(this, MainActivity.class);
        startActivity(randomIntent);
    }
    public void MeetingRoom (View view) {
        Intent randomIntent = new Intent(this, MeetingActivity.class);
        startActivity(randomIntent);
    }
    public void StartupCommittees (View view) {
        Intent randomIntent = new Intent(this, MainActivity.class);
        startActivity(randomIntent);
    }
}
