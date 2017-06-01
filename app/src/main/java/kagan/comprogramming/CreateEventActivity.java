package kagan.comprogramming;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import static kagan.comprogramming.GroupAdapter.calculateInSampleSize;

public class CreateEventActivity extends AppCompatActivity {

    Group group;
    int eventCity;
    EditText editTextDate;
    EditText editTextTime, eventName, address, description;
    Calendar myCalendar;
    ImageView imageView;
    Uri imageUri;
    Event event;
    Bitmap bitmap;
    private static final int REQUEST = 12;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        event = new Event();
        eventName = (EditText) findViewById(R.id.newEventName);
        address = (EditText) findViewById(R.id.eventAddress);
        description = (EditText) findViewById(R.id.eventDescription);

        imageView = (ImageView) findViewById(R.id.newImageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(intent, REQUEST);
            }
        });

        group = (Group) getIntent().getSerializableExtra("asd");
        Button create = (Button) findViewById(R.id.createNewEvent);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CreateEventActivity.this, GroupActivity.class);
                i.putExtra("object", group);

                event.setDate(editTextDate.getText().toString());
                event.setDescription(description.getText().toString());
                event.setAddress(address.getText().toString());
                event.setTime(editTextTime.getText().toString());
                event.setName(eventName.getText().toString());
                event.setCity(eventCity);
                if (bitmap != null)
                    saveToInternalStorage(bitmap);
                new AsyncTask<Void, Void, Void>() {
                    @Override
                    protected Void doInBackground(Void... voids) {
                        DbManager.createEvent(CreateEventActivity.this, event, group);
                        return null;
                    }
                }.execute();
                i.putExtra("group", group);
                startActivity(i);
                Toast.makeText(CreateEventActivity.this, "Event Created!",
                        Toast.LENGTH_SHORT).show();
            }
        });

        Spinner spinner = (Spinner) findViewById(R.id.spinner2);
        ArrayAdapter<CharSequence> cities = ArrayAdapter.createFromResource(this, R.array.cities, android.R.layout.simple_spinner_item);
        cities.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(cities);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                eventCity = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        editTextDate = (EditText) findViewById(R.id.newEventDate);
        editTextTime = (EditText) findViewById(R.id.newEventTime);
        myCalendar = Calendar.getInstance();

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "dd/MM/yyyy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                editTextDate.setText(sdf.format(myCalendar.getTime()));
            }

        };

        editTextDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                new DatePickerDialog(CreateEventActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        final TimePickerDialog.OnTimeSetListener time = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                myCalendar.set(Calendar.HOUR_OF_DAY, i);
                myCalendar.set(Calendar.MINUTE, i1);
                String myFormat = "HH:mm";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                editTextTime.setText(sdf.format(myCalendar.getTime()));

            }
        };

        editTextTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new TimePickerDialog(CreateEventActivity.this, time
                        , myCalendar.get(Calendar.HOUR_OF_DAY), myCalendar.get(Calendar.MINUTE), true).show();
            }
        });


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST && resultCode == RESULT_OK) {

            imageUri = data.getData();
            imageView.setImageBitmap(getBitmap(imageUri, 100, 100));
            event.setImageUri(imageUri.toString());

        }
    }

    private void saveToInternalStorage(Bitmap bitmapImage) {
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imageDirevents", Context.MODE_PRIVATE);
        // Create imageDir
        File mypath = new File(directory, "" + event.getName());

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
