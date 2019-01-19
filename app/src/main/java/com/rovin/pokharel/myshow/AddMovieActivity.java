package com.rovin.pokharel.myshow;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.rovin.pokharel.myshow.model.Movie;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.UUID;
import java.util.concurrent.Executor;

public class AddMovieActivity extends AppCompatActivity {
    private TextView tvChooseImg;
    private ImageView chooseImage;
    private EditText movieName,movieDescription, movieDate;
    private Spinner movieType;
    private Button addMovie;

    private static int PICK_IMAGE_REQUEST = 1;
    private Uri filePath;
    private Bitmap itemBitmapImage;

    DatabaseReference databaseItem;
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_movie);

        //firebase
        databaseItem = FirebaseDatabase.getInstance().getReference("movies");
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();

        tvChooseImg = (TextView)findViewById(R.id.tv_chooseimage);
        chooseImage = (ImageView) findViewById(R.id.iv_selectimage);
        movieName = (EditText)findViewById(R.id.et_addmovie);
        movieDescription = (EditText)findViewById(R.id.et_addmoviedesc);
        movieDate = (EditText)findViewById(R.id.et_addscrenningdate);
        movieType = (Spinner) findViewById(R.id.spinner_movie_type);
        addMovie = (Button) findViewById(R.id.btn_addmovie);

        tvChooseImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
            }
        });

        addMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addMovie();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null ){
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                itemBitmapImage = bitmap;
                chooseImage.setImageBitmap(bitmap);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    private void addMovie(){
        final String name = movieName.getText().toString();
        final String description = movieDescription.getText().toString();
        final String date = movieDate.getText().toString();
        final String type = movieType.getSelectedItem().toString();

        if (name.isEmpty()){
            movieName.setError("enter movie name");
            movieName.requestFocus();
            return;
        }

        if (description.isEmpty()){
            movieDescription.setError("enter movie description");
            movieDescription.requestFocus();
            return;
        }

        if (date.isEmpty()){
            movieDate.setError("enter movie screening date");
            movieDate.requestFocus();
            return;
        }

        if (!isValidDate(date)){
            movieDate.setError("Invalid date format");
            movieDate.requestFocus();
            return;
        }

        if (type.isEmpty()){
            Toast.makeText(getApplicationContext(), "Select the showing type.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (filePath != null){
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setCancelable(false);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();
            final ByteArrayOutputStream[] byteArrayOutputStream = {new ByteArrayOutputStream()};
            itemBitmapImage.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream[0]);
            final byte[] imageData = byteArrayOutputStream[0].toByteArray();
            final Uri[] downloadUrl = {null};

            final String uniqueName = "images/" + UUID.randomUUID().toString() + ".jpeg";
            final StorageReference ref = storageReference.child(uniqueName);

            UploadTask uploadTask = ref.putBytes(imageData);

            uploadTask.addOnSuccessListener(AddMovieActivity.this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String url = uri.toString();
                            String id = databaseItem.push().getKey();
                            Movie movie = new Movie(id, name, date, url, description, type);
                            databaseItem.child(id).setValue(movie);
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Uploaded", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(AddMovieActivity.this, Dashboard.class));
                            finish();
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
            .addOnProgressListener((Executor) AddMovieActivity.this, new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
//                    double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
//                    progressDialog.setMessage("Uploaded "+(int)progress+"%");
                    progressDialog.setMessage("Uploading...");
                }
            });
        }




    }

    private boolean isValidDate(String date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        simpleDateFormat.setLenient(false);
        try {
            simpleDateFormat.parse(date.trim());
        } catch (ParseException e) {
            return false;
        }
        return true;
    }
}
