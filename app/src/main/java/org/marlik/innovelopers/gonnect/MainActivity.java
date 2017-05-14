package org.marlik.innovelopers.gonnect;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //SAMPLE CODE
        Gonnect.getDataAndLaunchActivity("http://google.com", new Gonnect.ResponseFailureListener() {
            @Override
            public void responseFailed(IOException exception) {

                Toast.makeText(MainActivity.this, "FAILED", Toast.LENGTH_SHORT).show();

            }
        },SecondActivity.class,getApplicationContext());




    }
}
