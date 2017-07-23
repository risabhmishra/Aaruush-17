package com.rm.dell.aaruush17;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Login extends AppCompatActivity {
    EditText email1, password1;
    Button login,signup;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthstatelistener;
    private SignInButton gbutton;
    private final int RC_SIGN_IN = 1;
    private GoogleApiClient mGoogleApiClient;
    private final static String TAG = "Login_Activity";
    private ProgressDialog mprogressdialog;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference mDatabase;

    public static final String mypreference = "mypref";
    public static final String Name = "nameKey";
    public static final String Password = "passKey";
    EditText nameText,passwordText;
    String prefname, prefpassword;
    SharedPreferences sharedPreferences;
    public void Save() {
        String n = nameText.getText().toString();
        String p = passwordText.getText().toString();

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Name, n);
        editor.putString(Password, p);
        editor.apply();
    }

    public void Get() {
        sharedPreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);

        if (sharedPreferences.contains(Name)) {
            nameText.setText(sharedPreferences.getString(Name, ""));
        }
        if (sharedPreferences.contains(Password)) {
            passwordText.setText(sharedPreferences.getString(Password, ""));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login1);

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        changeStatusBarColor();
        email1 = (EditText) findViewById(R.id.et_username);
        password1 = (EditText) findViewById(R.id.et_password);
        login = (Button) findViewById(R.id.btn_sign_in);
        signup = (Button) findViewById(R.id.btn_sign_up);
        mAuth = FirebaseAuth.getInstance();
       gbutton = (SignInButton) findViewById(R.id.google_button);

        nameText = (EditText) findViewById(R.id.et_username);
        passwordText = (EditText) findViewById(R.id.et_password);
        sharedPreferences = getSharedPreferences(mypreference, Context.MODE_PRIVATE);
        Get();

        mprogressdialog = new ProgressDialog(this);
        gbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });

        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(getApplicationContext()).enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
            @Override
            public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

                Toast.makeText(Login.this,"Sign In Failed",Toast.LENGTH_SHORT).show();

            }
        }).addApi(Auth.GOOGLE_SIGN_IN_API,gso).build();

        mAuthstatelistener = new FirebaseAuth.AuthStateListener() {

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    startActivity(new Intent(Login.this, MainActivity.class));
                }
            }
        };



        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OnLogin();


            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
Intent intent = new Intent(Login.this,Register.class);
                startActivity(intent);

            }
        });

    }




    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthstatelistener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthstatelistener != null) {
            mAuth.removeAuthStateListener(mAuthstatelistener);
        }
    }

       public void OnLogin() {

        final String email = email1.getText().toString();
        final String password = password1.getText().toString();

           prefname = nameText.getText().toString();
           prefpassword = passwordText.getText().toString();
           Save();
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){

            Toast.makeText(Login.this,"Fields Are Empty",Toast.LENGTH_LONG).show();
        }
        else{

            mprogressdialog.setMessage("Signing In");
            mprogressdialog.show();
            mprogressdialog.setCanceledOnTouchOutside(false);
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        startActivity(new Intent(Login.this, MainActivity.class));
                        mprogressdialog.dismiss();
                    } else if (!task.isSuccessful()) {
                        Toast.makeText(Login.this, "Sign In Failed!", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            } else {
                // Google Sign In failed, update UI appropriately
                // ...
            }
        }

    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        mprogressdialog.setMessage("Signing In");
        mprogressdialog.show();
        mprogressdialog.setCanceledOnTouchOutside(false);
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                   update (user);

                            mprogressdialog.dismiss();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(Login.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }

                        // ...
                    }
                });
    }

    private void update(FirebaseUser user) {
        String emaill = user.getEmail().toString();
        String fn = user.getDisplayName().toString();
        String curr = user.getUid();


        mDatabase = database.getReference().child("Users").child(curr);

        HashMap<String,String> data = new HashMap<>();
        data.put("First Name",fn);
        data.put("Last Name","");
        data.put("Email",emaill);
        data.put("Image","https://firebasestorage.googleapis.com/v0/b/aaruush-17-17533.appspot.com/o/Profile_pictures%2Fnavlogo.png?alt=media&token=770c9bed-b94d-4f55-9aca-e153efcaf1c1");
        mDatabase.setValue(data).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(Login.this, "Data Updated Succesfully", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(Login.this,MainActivity.class));
                    mprogressdialog.dismiss();
                }
                else {
                    Toast.makeText(Login.this, "Data Update Failed", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

}
