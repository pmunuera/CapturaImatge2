package com.example.capturaimatge2;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    ActivityResultLauncher<Intent> someActivityResultLauncher;
    ActivityResultLauncher<Intent> someActivityResultLauncher2;
    public static int RC_PHOTO_PICKER = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        someActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            // There are no request codes
                            Intent data = result.getData();
                            Uri uri = data.getData();
                            ImageView imageView = findViewById(R.id.img);
                            imageView.setImageURI(uri);
                        }
                    }
                });
        someActivityResultLauncher2 = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        Bundle extras = result.getData().getExtras();
                        Bitmap imageBitmap = (Bitmap) extras.get("data");
                        ImageView img = findViewById(R.id.img);
                        img.setImageBitmap(imageBitmap);
                    }
                });
        Button button = findViewById(R.id.buttonGallery);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                openSomeActivityForResult(findViewById(R.id.img));
            }
        });
        Button button2 = findViewById(R.id.buttonCamera);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSomeActivityForResult2(findViewById(R.id.img));
            }
        });

    }
    public void openSomeActivityForResult(View view) {
        //Create Intent
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/jpg");
        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
        //Launch activity to get result
        //ImageView img = findViewById(R.id.img);
        someActivityResultLauncher.launch(intent);
    }
    public void openSomeActivityForResult2(View view) {
        //Create Intent
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //Launch activity to get result
        //ImageView img = findViewById(R.id.img);
        someActivityResultLauncher2.launch(intent);
    }

}