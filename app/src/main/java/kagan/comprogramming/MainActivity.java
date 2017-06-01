package kagan.comprogramming;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText username, password;
    boolean isValid;
    Person person;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);

        Button login = (Button) findViewById(R.id.signIn);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                person = new Person();
                person.setNickname(username.getText().toString());
                person.setPassword(password.getText().toString());
                isUserValid();


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

    public void isUserValid() {

        new AsyncTask<Void, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... voids) {

                return DbManager.loadUser(MainActivity.this, person);
            }

            @Override
            protected void onPostExecute(Boolean a) {
                super.onPostExecute(a);
                isValid = a;
                if (isValid) {
                    Intent intent = new Intent(MainActivity.this, CategoriesActivity.class);
                    intent.putExtra("user", person);
                    startActivity(intent);
                } else {
                    Toast.makeText(MainActivity.this, "The email or password you entered is invalid", Toast.LENGTH_SHORT).show();
                }

            }
        }.execute();

    }

}
