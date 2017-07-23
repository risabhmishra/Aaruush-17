package com.rm.dell.aaruush17;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by Arko Chatterjee on 14-06-2017.
 */

public class SplashScreen extends AppCompatActivity {

    private static int SPLASH_DISPLAY_LENGTH = 1000;
    @Override

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashscreen);



       /* new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                Intent mainIntent = new Intent(SplashScreen.this,Login.class);
                SplashScreen.this.startActivity(mainIntent);

                SplashScreen.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);

    }
}

