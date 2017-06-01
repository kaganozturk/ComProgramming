package kagan.comprogramming;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import static kagan.comprogramming.GroupAdapter.calculateInSampleSize;

public class Register extends AppCompatActivity {

    public static final int REQUEST = 1;
    ImageView imageView;
    Uri imageUri;
    Person person;
    EditText fname;
    EditText lname;
    EditText email;
    EditText nickname;
    EditText password;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        person = new Person();

        fname = (EditText) findViewById(R.id.editText3);
        lname = (EditText) findViewById(R.id.editText4);
        email = (EditText) findViewById(R.id.email);
        nickname = (EditText) findViewById(R.id.nickname);
        password = (EditText) findViewById(R.id.registerpassword);


        imageView = (ImageView) findViewById(R.id.imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(intent, REQUEST);

            }
        });


        Button button = (Button) findViewById(R.id.create);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                person.setFname(fname.getText().toString());
                person.setLname(lname.getText().toString());
                person.setEmail(email.getText().toString());
                person.setNickname(nickname.getText().toString());
                person.setPassword(password.getText().toString());
                if (bitmap != null)
                    saveToInternalStorage(bitmap);


                new AsyncTask<Void, Void, Void>() {
                    @Override
                    protected Void doInBackground(Void... voids) {
                        DbManager.createUser(Register.this, person);
                        return null;
                    }
                }.execute();
                Intent intent = new Intent(Register.this, RegisterSuccessful.class);
                intent.putExtra("person", person);
                startActivity(intent);


            }
        });

        Spinner spinner = (Spinner) findViewById(R.id.cityspinner);
        ArrayAdapter<CharSequence> cities = ArrayAdapter.createFromResource(this, R.array.cities, android.R.layout.simple_spinner_item);
        cities.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(cities);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                person.setCity(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST && resultCode == RESULT_OK) {

            imageUri = data.getData();
            imageView.setImageBitmap(getBitmap(imageUri, 100, 100));
            person.setImageUri(imageUri.toString());
        }
    }


    private boolean isFieldsNull() {

        return person.getFname() == null || person.getLname() == null || person.getCity() == 0 ||
                person.getEmail() == null || person.getPassword() == null || person.getNickname() == null ||
                person.getImageUri() == null;

    }

    public Bitmap getBitmap(Uri imageUri, int width, int height) {
        InputStream image_stream = null;
        try {
            image_stream = getContentResolver().openInputStream(imageUri);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(image_stream, null, options);
        try {
            if (image_stream != null)
                image_stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            image_stream = getContentResolver().openInputStream(imageUri);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        options.inSampleSize = calculateInSampleSize(options, width, height);
        options.inJustDecodeBounds = false;
        bitmap = BitmapFactory.decodeStream(image_stream, null, options);
        try {
            if (image_stream != null)
                image_stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    private void saveToInternalStorage(Bitmap bitmapImage) {
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imageDirpersons", Context.MODE_PRIVATE);
        // Create imageDir
        File mypath = new File(directory, "" + person.getNickname() + person.getPassword());

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.JPEG, 60, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null)
                    fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



}
