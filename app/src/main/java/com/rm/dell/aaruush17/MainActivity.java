package com.rm.dell.aaruush17;

import android.*;
import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.text.SimpleDateFormat;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.rm.dell.aaruush17.Login.Name;
import static com.rm.dell.aaruush17.Login.Password;
import static com.rm.dell.aaruush17.Login.mypreference;

public class MainActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    Toolbar toolbar;
    ActionBarDrawerToggle actionBarDrawerToggle;
    FragmentTransaction fragmentTransaction;
    NavigationView navigationView;
    CircleImageView profile;
    TextView uname;
    private FirebaseAuth mAuth;
    private boolean doubleBackToExitPressedOnce = false;
    private FirebaseUser current;
    private StorageReference mImagestorage;
    private ProgressDialog mprogressdialog;
    private DatabaseReference mDatabase;
    private TextView txtTimerDay, txtTimerHour, txtTimerMinute, txtTimerSecond;
    private TextView tvEvent;
    private Handler handler;
    private Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FontsOverride.setDefaultFont(this, "MONOSPACE", "M_R.ttf");

        mAuth = FirebaseAuth.getInstance();
        mImagestorage = FirebaseStorage.getInstance().getReference();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.main_container, new Home_Zero());
        fragmentTransaction.commit();
        getSupportActionBar().setTitle("Home");
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        View hView = navigationView.getHeaderView(0);
        profile = (CircleImageView) hView.findViewById(R.id.logo);
        uname = (TextView) hView.findViewById(R.id.uname);




        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home_id:
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.main_container, new Home_Zero());
                        fragmentTransaction.commit();
                        getSupportActionBar().setTitle("Home");
                        item.setChecked(true);
                        drawerLayout.closeDrawers();
                        break;

                    case R.id.domains_id:
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.main_container, new domains_fragment());
                        fragmentTransaction.commit();
                        getSupportActionBar().setTitle("Events");
                        item.setChecked(true);
                        drawerLayout.closeDrawers();
                        break;

                    case R.id.workshops_id:
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.main_container, new workshops_fragment());
                        fragmentTransaction.commit();
                        getSupportActionBar().setTitle("Workshops");
                        item.setChecked(true);
                        drawerLayout.closeDrawers();
                        break;

                    case R.id.game:
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.main_container, new Game_Home());
                        fragmentTransaction.commit();
                        getSupportActionBar().setTitle("Stock Market");
                        item.setChecked(true);
                        drawerLayout.closeDrawers();
                        break;

                    case R.id.favourites_id:
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.main_container, new Sponsors_fragment());
                        fragmentTransaction.commit();
                        getSupportActionBar().setTitle("Sponsers");
                        item.setChecked(true);
                        drawerLayout.closeDrawers();
                        break;

                    case R.id.highlights_id:
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.main_container, new highlights_fragment());
                        fragmentTransaction.commit();
                        getSupportActionBar().setTitle("Highlights");
                        item.setChecked(true);
                        drawerLayout.closeDrawers();
                        break;

                    case R.id.sponsors_id:
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.main_container, new about_us());
                        fragmentTransaction.commit();
                        getSupportActionBar().setTitle("About Us");
                        item.setChecked(true);
                        drawerLayout.closeDrawers();
                        break;

                    case R.id.patrons_id:
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.main_container, new Initiatives());
                        fragmentTransaction.commit();
                        getSupportActionBar().setTitle("Initiatives");
                        item.setChecked(true);
                        drawerLayout.closeDrawers();
                        break;

                    case R.id.team_id:
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.main_container, new team_fragment());
                        fragmentTransaction.commit();
                        getSupportActionBar().setTitle("Team");
                        item.setChecked(true);
                        drawerLayout.closeDrawers();
                        break;

                    case R.id.credits_id:
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.main_container, new credits_fragment());
                        fragmentTransaction.commit();
                        getSupportActionBar().setTitle("Credits");
                        item.setChecked(true);
                        drawerLayout.closeDrawers();
                        break;

                    case R.id.contactus_id:
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.main_container, new Contactus());
                        fragmentTransaction.commit();
                        getSupportActionBar().setTitle("Contact Us");
                        item.setChecked(true);
                        drawerLayout.closeDrawers();
                        break;

                    case R.id.camera:
                        check();
                        break;
                    case R.id.settings_id:
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.main_container, new SettingsFragment());
                        fragmentTransaction.commit();
                        getSupportActionBar().setTitle("Settings");
                        item.setChecked(true);
                        drawerLayout.closeDrawers();
                        break;
                }


                return true;
            }
        });

        current = mAuth.getCurrentUser();
        String currentuser = current.getUid();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        mDatabase = database.getReference().child("Users").child(currentuser);
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name = dataSnapshot.child("First Name").getValue().toString() + " " + dataSnapshot.child("Last Name").getValue().toString();
                String image = dataSnapshot.child("Image").getValue().toString();
                Picasso.with(MainActivity.this).load(image).into(profile);
                uname.setText(name);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(MainActivity.this, "Database Error", Toast.LENGTH_LONG).show();

            }
        });


    }



    private void check() {
        ActivityCompat.requestPermissions(MainActivity.this,
                new String[]{android.Manifest.permission.CAMERA},
                1);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startActivity(new Intent(MainActivity.this, Camera.class));

                } else {
                    Toast.makeText(MainActivity.this, "Permission denied to access Camera", Toast.LENGTH_SHORT).show();
                }
                return;
            }

        }
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {

                mprogressdialog = new ProgressDialog(MainActivity.this);
                mprogressdialog.setTitle("Uploading Image...");
                mprogressdialog.setMessage("Please wait while we upload the image");
                mprogressdialog.setCanceledOnTouchOutside(false);
                mprogressdialog.show();

                Uri resultUri = result.getUri();
                String currentusers = current.getUid();
                StorageReference filepath = mImagestorage.child("Profile_pictures").child(currentusers + ".jpg");
                filepath.putFile(resultUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        if (task.isSuccessful()) {

                            String download_uri = task.getResult().getDownloadUrl().toString();
                            mDatabase.child("Image").setValue(download_uri).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(MainActivity.this, "Uploading Successful", Toast.LENGTH_LONG).show();
                                    mprogressdialog.dismiss();

                                }
                            });


                        } else {
                            Toast.makeText(MainActivity.this, "Uploading Failed", Toast.LENGTH_LONG).show();
                            mprogressdialog.dismiss();
                        }
                    }
                });

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentuser = mAuth.getCurrentUser();
        if (currentuser == null) {
            sendtostart();
        }

    }

    private void sendtostart() {
        Intent startintent = new Intent(MainActivity.this, Login.class);
        startActivity(startintent);
        finish();

    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
        } else {
            doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Press again to exit", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        }
    }

}