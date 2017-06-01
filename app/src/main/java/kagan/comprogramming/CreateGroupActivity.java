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
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import static kagan.comprogramming.GroupAdapter.calculateInSampleSize;

public class CreateGroupActivity extends AppCompatActivity {

    private static final int REQUEST = 1234;
    ImageView imageView;
    Uri imageUri;
    int cat;
    EditText name, description;
    Group group;
    Bitmap bitmap;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);
        group = new Group();

        name = (EditText) findViewById(R.id.groupName);
        description = (EditText) findViewById(R.id.groupDescription);

        imageView = (ImageView) findViewById(R.id.newImageViewGroup);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(intent, REQUEST);
            }
        });


        Spinner spinner = (Spinner) findViewById(R.id.spinner);

        final ArrayAdapter<CharSequence> categories = ArrayAdapter.createFromResource(this, R.array.categories, android.R.layout.simple_spinner_item);

        categories.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(categories);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                cat = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        Button button = (Button) findViewById(R.id.createNewGroup);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                group.setCategory(cat);
                group.setDescription(description.getText().toString());
                group.setName(name.getText().toString());

                if (bitmap != null)
                    saveToInternalStorage(bitmap);
                new AsyncTask<Void, Void, Void>() {
                    @Override
                    protected Void doInBackground(Void... voids) {
                        DbManager.createGroup(CreateGroupActivity.this, group);
                        return null;
                    }
                }.execute();
                Intent i = new Intent(CreateGroupActivity.this, CategoriesActivity.class);
                i.putExtra("user", CategoriesActivity.person);
                startActivity(i);
                Toast.makeText(CreateGroupActivity.this, "Group created!", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST && resultCode == RESULT_OK) {

            imageUri = data.getData();

            imageView.setImageBitmap(getBitmap(imageUri, 100, 100));

            group.setImageUri(imageUri.toString());
        }
    }

    private void saveToInternalStorage(Bitmap bitmapImage) {
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        // Create imageDir
        File mypath = new File(directory, "" + group.getName());

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.JPEG, 60, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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


}
