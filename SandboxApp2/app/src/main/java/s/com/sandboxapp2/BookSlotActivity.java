package s.com.sandboxapp2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class BookSlotActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_slot);
    }
    public void BookSlot (View view) {
        Intent randomIntent = new Intent(this, MeetingActivity.class);
        startActivity(randomIntent);
    }
}
