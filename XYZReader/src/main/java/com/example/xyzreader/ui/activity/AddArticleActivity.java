package com.example.xyzreader.ui.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.xyzreader.R;
import com.example.xyzreader.data.FirebaseDatabaseXyzReader;
import com.example.xyzreader.model.ListResponsData;
import com.example.xyzreader.model.ListResponsDataAbout;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddArticleActivity extends AppCompatActivity {

    @Bind(R.id.input_author)
    EditText input_author;
    @Bind(R.id.input_title)
    EditText input_title;
    @Bind(R.id.input_date)
    EditText input_date;
    @Bind(R.id.input_body)
    EditText input_body;
    @Bind(R.id.input_image)
    ImageView input_image;
    @Bind(R.id.imageButton_open_gallery)
    ImageButton imageButton_open_gallery;
    @Bind(R.id.btn_save)
    Button btn_save;

    private static int RESULT_LOAD_IMG = 1;
    private Uri selectedImage;
    private float aspect_ratio;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_article);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_add_article);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        ButterKnife.bind(this);
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }
        FirebaseDatabaseXyzReader.getFirebaseDatabaseInstance().logIn();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        FirebaseDatabaseXyzReader.getFirebaseDatabaseInstance().articleRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
             /*   String text = dataSnapshot.getValue(String.class);
                textView_result.setText(text);*/

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @OnClick(R.id.imageButton_open_gallery)
    public void setOnClick_imageButton_open_gallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        // Start the Intent
        startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
    }

    @OnClick(R.id.btn_save)
    public void setOnClick_btnsave() {

    }

    @Override
    protected void onActivityResult(int requestCode, final int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            // When an Image is picked
            if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK
                    && null != data) {
                // Get the Image from data

                selectedImage = data.getData();
                Glide.with(this).load(selectedImage)
                        .asBitmap()
                        .listener(new RequestListener<Uri, Bitmap>() {
                            @Override
                            public boolean onException(Exception e, Uri model, Target<Bitmap> target, boolean isFirstResource) {
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Bitmap resource, Uri model, Target<Bitmap> target, boolean isFromMemoryCache, boolean isFirstResource) {
                                aspect_ratio = (float) resource.getWidth() / resource.getHeight();
                                return false;
                            }
                        })
                        .into(input_image);


            } else {
                Toast.makeText(this, "You haven't picked Image",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
                    .show();
        }

    }

    public void uploadImageToDataStorage() {

        showProgresDialog();
        final StorageReference photoRef = FirebaseDatabaseXyzReader.getFirebaseDatabaseInstance().storageRef.child(selectedImage.getLastPathSegment());
        // Upload file to Firebase Storage
        photoRef.putFile(selectedImage)
                .addOnSuccessListener(this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // When the image has successfully uploaded, we get its download URL
                        Uri downloadUrl = taskSnapshot.getDownloadUrl();
                        // Set the download URL to the message box, so that the user can send it to the database
                        Log.d("DownloadLink", downloadUrl.toString());
                        saveData(downloadUrl.toString());
                    }
                });
    }


    @OnClick(R.id.btn_save)
    public void setOnClick_btn_save() {

        uploadImageToDataStorage();

    }

    public void saveData(String imageUrl) {

        ListResponsData listResponsData = new ListResponsData();
        listResponsData.setAuthor(input_author.getText().toString());
        listResponsData.setTitle(input_title.getText().toString());
        listResponsData.setPublished_date(input_date.getText().toString());
        listResponsData.setBody(input_body.getText().toString());
        listResponsData.setPhoto(imageUrl);
        listResponsData.setThumb(imageUrl);
        listResponsData.setAspect_ratio(String.valueOf(aspect_ratio));
        FirebaseDatabaseXyzReader.getFirebaseDatabaseInstance().articleRef.push().setValue(listResponsData).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                progressDialog.dismiss();
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "Please try again", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void saveAboutData() {
        ListResponsDataAbout listResponsDataAbout = new ListResponsDataAbout();
        listResponsDataAbout.setName_of_project("");
        listResponsDataAbout.setDescription("");
        listResponsDataAbout.setDeveloper_name("Adonis Arifi");
        listResponsDataAbout.setGithub_link("");
        listResponsDataAbout.setPhoto("");
        FirebaseDatabaseXyzReader.getFirebaseDatabaseInstance().abouteRef.push().setValue(listResponsDataAbout);

    }

    public void showProgresDialog() {
        progressDialog = new ProgressDialog(this, android.support.v7.appcompat.R.style.Base_Theme_AppCompat_Light_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
    }
}
