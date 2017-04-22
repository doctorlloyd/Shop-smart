package com.example.admin.shopsmart.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import com.example.admin.shopsmart.R;
import com.example.admin.shopsmart.pojos.Image;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import java.io.File;
import static android.app.Activity.RESULT_OK;

public class Gallery extends Activity {

    protected static final int CAMERA_REQUEST = 0;
    protected StorageReference mStorageReference;
    protected DatabaseReference mDatabaseReference;
    protected Bitmap bitmap;
    private Uri imageUri;
    protected ImageView imageToUpload;
    private Button btnCancel,btnSave,btnPicture;
    public Uri downloadUri;
    protected String image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gallery_layout);

        btnCancel = (Button) findViewById(R.id.btn_better_picture);
        btnSave = (Button) findViewById(R.id.btn_save_picture);
        btnPicture = (Button) findViewById(R.id.btn_take_picture);
        imageToUpload = (ImageView) findViewById(R.id.imageViewGallery);

        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Gallery");
        mStorageReference = FirebaseStorage.getInstance().getReference();
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addAnItem();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takePicture();
            }
        });
        btnPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takePicture();
            }
        });
    }

    void takePicture(){
        Intent intent = new Intent(
                MediaStore.ACTION_IMAGE_CAPTURE);
        File f = new File(android.os.Environment
                .getExternalStorageDirectory(), "temp.jpg");
        //TODO .......start
        imageUri = Uri.fromFile(f);
        //TODO ......end
        intent.putExtra(MediaStore.EXTRA_OUTPUT,
                Uri.fromFile(f));
        startActivityForResult(intent,
                CAMERA_REQUEST);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == CAMERA_REQUEST) {
            File f = new File(Environment.getExternalStorageDirectory().toString());
            for (File temp : f.listFiles()) {
                if (temp.getName().equals("temp.jpg")) {
                    f = temp;
                    break;
                }
            }

            if (!f.exists()) {
                Toast.makeText(this, "Error while capturing image", Toast.LENGTH_LONG).show();
                return;
            }

            try {
                bitmap = BitmapFactory.decodeFile(f.getAbsolutePath());

//                bitmap = Bitmap.createScaledBitmap(bitmap, 400, 400, true);

                int rotate = 0;
                try {
                    ExifInterface exif = new ExifInterface(f.getAbsolutePath());
                    int orientation = exif.getAttributeInt(
                            ExifInterface.TAG_ORIENTATION,
                            ExifInterface.ORIENTATION_NORMAL);

                    switch (orientation) {
                        case ExifInterface.ORIENTATION_ROTATE_270:
                            rotate = 270;
                            break;
                        case ExifInterface.ORIENTATION_ROTATE_180:
                            rotate = 180;
                            break;
                        case ExifInterface.ORIENTATION_ROTATE_90:
                            rotate = 90;
                            break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Matrix matrix = new Matrix();
                matrix.postRotate(rotate);
                bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
                //TODO what is the use of this image
                imageToUpload.setImageBitmap(bitmap);

                btnCancel.setVisibility(View.VISIBLE);
                btnSave.setVisibility(View.VISIBLE);
                btnPicture.setVisibility(View.GONE);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
    private void addAnItem() {
        StorageReference filePath = mStorageReference.child("Gallery").child(imageUri.getLastPathSegment());
        filePath.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                downloadUri = taskSnapshot.getDownloadUrl();
                assert downloadUri != null;
                image = downloadUri.toString();
                mDatabaseReference.setValue(new Image(image));
                Toast.makeText(getBaseContext(),"Image shared successfully!",Toast.LENGTH_LONG).show();
                startActivity(new Intent(getBaseContext(),Gallery.class));
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(),ShopsRecyclerView.class));
        finish();
    }
}
