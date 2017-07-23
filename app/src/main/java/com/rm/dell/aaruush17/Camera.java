package com.rm.dell.aaruush17;

import android.*;
import android.content.pm.PackageManager;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.IdRes;
import android.support.media.ExifInterface;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Camera extends AppCompatActivity implements View.OnClickListener{


    public static final String TAG = "CamActivity";
    private boolean isFilterOpen=false;
    private ImageView filterButton;
    ImageView imageView;
    ImageView captureButton;
    LayerDrawable layerDrawable ;
    Drawable d;
    ImageButton store;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private Bitmap mImageBitmap;
    private String mCurrentPhotoPath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        imageView = (ImageView)findViewById(R.id.imageView);

        // Add a listener to the Capture button
        captureButton = (ImageView) findViewById(R.id.button_capture);
        if (captureButton != null) {
            captureButton.setOnClickListener(this);
        }
        // Add listener to filter button
        filterButton = (ImageView) findViewById(R.id.filterButton);
        if (filterButton != null) {
            filterButton.setOnClickListener(this);
        }

        store = (ImageButton)findViewById(R.id.bu_save);
        if(store!= null){
            store.setOnClickListener(this);
        }


    }

    // Method for set the camera filters
    public void colorEffectFilter(View v){
        try {
            switch (v.getId()) {
                case R.id.effectNone:
                    Drawable[] layers = new Drawable[2];
                    layers[0] = d;
                    layers[1] = getResources().getDrawable(R.drawable.f1);
                    layerDrawable = new LayerDrawable(layers);
                    imageView.setImageDrawable(layerDrawable);
                    findViewById(R.id.record_filter_layout).setVisibility(View.GONE);
                    break;
                case R.id.effectAqua:
                    Drawable[] layers1 = new Drawable[2];
                    layers1[0] = d;
                    layers1[1] = getResources().getDrawable(R.drawable.f2);
                    layerDrawable = new LayerDrawable(layers1);
                    imageView.setImageDrawable(layerDrawable);
                    findViewById(R.id.record_filter_layout).setVisibility(View.GONE);
                    break;
                case R.id.effectBlackboard:
                    Drawable[] layers2 = new Drawable[2];
                    layers2[0] = d;
                    layers2[1] = getResources().getDrawable(R.drawable.f3);
                    layerDrawable = new LayerDrawable(layers2);
                    imageView.setImageDrawable(layerDrawable);
                    findViewById(R.id.record_filter_layout).setVisibility(View.GONE);
                    break;
                case R.id.effectMono:
                    Drawable[] layers3 = new Drawable[2];
                    layers3[0] = d;
                    layers3[1] = getResources().getDrawable(R.drawable.f4);
                    layerDrawable = new LayerDrawable(layers3);
                    imageView.setImageDrawable(layerDrawable);
                    findViewById(R.id.record_filter_layout).setVisibility(View.GONE);
                    break;
                case R.id.effectNegative:
                    Drawable[] layers4 = new Drawable[2];
                    layers4[0] = d;
                    layers4[1] = getResources().getDrawable(R.drawable.f5);
                    layerDrawable = new LayerDrawable(layers4);
                    imageView.setImageDrawable(layerDrawable);
                    findViewById(R.id.record_filter_layout).setVisibility(View.GONE);
                    break;
            }
        }catch (Exception ex){
            Log.d(TAG,ex.getMessage());
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.filterButton:
                if (!isFilterOpen){
                    findViewById(R.id.record_filter_layout).setVisibility(View.VISIBLE);
                    filterButton.setImageResource(R.drawable.filter_on);
                    isFilterOpen=true;
                }
                else {
                    findViewById(R.id.record_filter_layout).setVisibility(View.GONE);
                    filterButton.setImageResource(R.drawable.filter_off);
                    isFilterOpen=false;
                }
                break;
            case R.id.button_capture:
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (cameraIntent.resolveActivity(getPackageManager()) != null) {
                    // Create the File where the photo should go
                    File photoFile = null;
                    try {
                        photoFile = createImageFile();
                    } catch (IOException ex) {
                        // Error occurred while creating the File
                        Log.i(TAG, "IOException");
                    }
                    // Continue only if the File was successfully created
                    if (photoFile != null) {
                        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                        startActivityForResult(cameraIntent, REQUEST_IMAGE_CAPTURE);
                    }
                }
                break;
            case R.id.bu_save:
                check();
                break;

        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  // prefix
                ".jpg",         // suffix
                storageDir      // directory
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        return image;
    }

    private void check() {
        ActivityCompat.requestPermissions(Camera.this,
                new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                2);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 2: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    store((LayerDrawable) imageView.getDrawable());
                } else {
                    Toast.makeText(Camera.this, "Permission denied to To Write to External Storage", Toast.LENGTH_SHORT).show();
                }
                return;
            }

        }
    }

    private void store(LayerDrawable myLayerDrawable) {


        final int width = myLayerDrawable.getIntrinsicWidth();
        final int height = myLayerDrawable.getIntrinsicHeight();

        final Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        myLayerDrawable.setBounds(0, 0, width, height);
        myLayerDrawable.draw(new Canvas(bitmap));


        MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "Aaruush-17" , "SRM University");

        Toast.makeText(Camera.this, "Image Saved Successfully", Toast.LENGTH_LONG).show();

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){
            try {
                mImageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), Uri.parse(mCurrentPhotoPath));
                imageView.setImageBitmap(mImageBitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
rotate(mImageBitmap);



        }

    }

    private void rotate(Bitmap mImageBitmap) {

        Matrix matrix = new Matrix();

        matrix.postRotate(90);

        Bitmap scaledBitmap = Bitmap.createScaledBitmap(mImageBitmap,mImageBitmap.getWidth(),mImageBitmap.getHeight(),true);

        Bitmap rotatedBitmap = Bitmap.createBitmap(scaledBitmap , 0, 0, scaledBitmap .getWidth(), scaledBitmap .getHeight(), matrix, true);

        imageView.setImageBitmap(rotatedBitmap);
        d =  new BitmapDrawable(rotatedBitmap);
    }


}





