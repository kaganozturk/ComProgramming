package kagan.comprogramming;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    EditText username, password;

    // TODO: 2.05.2017 new group

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView textView = (TextView) findViewById(R.id.invalid);
        textView.setVisibility(View.INVISIBLE);


        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);

        Button login = (Button) findViewById(R.id.signIn);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isUserValid(username.getText().toString(), password.getText().toString())) {
                    Intent intent = new Intent(MainActivity.this, Categories.class);
                    startActivity(intent);
                } else {
                    textView.setVisibility(View.VISIBLE);
                }

            }
        });

        Button signUp = (Button) findViewById(R.id.signUp);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Register.class);
                startActivity(intent);
            }
        });


    }

    public boolean isUserValid(String username, String password) {
        String personUsername = "";
        String personPassword = "";

        return username.equals(personUsername) && password.equals(personPassword);
    }

}
