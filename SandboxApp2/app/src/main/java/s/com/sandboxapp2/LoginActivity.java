package s.com.sandboxapp2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    private EditText Name;
    private EditText Password;
    private Button Login;
    private TextView info;
    private int counter=5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Name = (EditText)findViewById(R.id.etname);
        Password = (EditText)findViewById(R.id.etpassword);
        Login = (Button)findViewById(R.id.btnlogin);

//        info.setText("No of attempts remaining: 5");

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate(Name.getText().toString(), Password.getText().toString());
            }
        });
    }
    private void validate(String UserName, String UserPassword){
        System.out.print(UserName);
        System.out.print(UserPassword);
        if((UserName.equals("Customer")) && (UserPassword.equals("1234"))){
            Intent intent=new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        }else{
            counter--;

            // info.setText("No of attempts remaining: " + String.valueOf(counter));
            if(counter==0){
                Login.setEnabled(false);
            }
        }
    }
}
