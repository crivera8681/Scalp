package com.example.scalp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;

public class CameraActivity extends AppCompatActivity {

    private ImageView ticketImageView;
    private static final int REQUEST_IMAGE_CAPTURE = 101;
    SharedPreferences mPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        ticketImageView = findViewById(R.id.ticketView);
    }

    public void takePicture(View view) {
        Intent imageTakenIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (imageTakenIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(imageTakenIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            ticketImageView.setImageBitmap(imageBitmap);

            mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            editor = mPreferences.edit();
            String shImage = BitMapToString(imageBitmap);
            editor.putString(getString(R.string.shImage), shImage);
            editor.commit();


            //getSupportFragmentManager().beginTransaction()
                    //.add(R.id.fragment_container, new PostTicketFragment ()).commit();


            Intent intent = new Intent(this, HomeActivity.class);
            intent.putExtra("notification", true);
            startActivity(intent);

        }
    }
    public String BitMapToString(Bitmap bitmap){
        ByteArrayOutputStream baos=new  ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
        byte [] b=baos.toByteArray();
        String temp= Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }
}