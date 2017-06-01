package kagan.comprogramming;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import static kagan.comprogramming.GroupAdapter.calculateInSampleSize;

public class RegisterSuccessful extends AppCompatActivity {
    Person person;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_successful);

        person = (Person) getIntent().getSerializableExtra("person");

        ImageView imageView = (ImageView) findViewById(R.id.imageView2);
        imageView.setImageBitmap(getBitmap(Uri.parse(person.getImageUri()), 100, 100));

        Button button = (Button) findViewById(R.id.getStarted);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterSuccessful.this, CategoriesActivity.class);
                intent.putExtra("user", person);
                startActivity(intent);
            }
        });
    }

    public Bitmap getBitmap(Uri imageUri, int width, int height) {
        Bitmap bitmap;
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
}
