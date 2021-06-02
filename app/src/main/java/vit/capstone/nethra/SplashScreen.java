package vit.capstone.nethra;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.widget.Toast;

public class SplashScreen extends AppCompatActivity implements TextToSpeech.OnInitListener  {
    private TextToSpeech mTts;



    public void checkPermission(String permission, int requestCode)
    {
        // Checking if permission is not granted
        if (ContextCompat.checkSelfPermission(SplashScreen.this, permission) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(SplashScreen.this, new String[] { permission }, requestCode);
        }
        else {
            Toast.makeText(SplashScreen.this, "Permission already granted", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        mTts = new TextToSpeech(SplashScreen.this, SplashScreen.this);
    }

    @Override
    protected void onPause() {
        if(mTts != null){
            mTts.stop();
            mTts.shutdown();
        }
        super.onPause();
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            mTts.setOnUtteranceProgressListener(new UtteranceProgressListener() {
                @Override
                public void onDone(String utteranceId) {
                    Intent intent=new Intent(SplashScreen.this,HomePage.class);
                    startActivity(intent);
                    finish();
                }

                @Override
                public void onError(String utteranceId) {
                    //Log.e(TAG, "Error:  " + utteranceId);
                }
                @Override
                public void onStart(String utteranceId) {
                    //Log.i(TAG, "Started:  " + utteranceId);
                }
            });
            mTts.speak("Welcome User. I am Nethra. I will be helping you throughout this app.", TextToSpeech.QUEUE_ADD, null, "DEFAULT");

        } else {
            //Log.e(TAG, "Failed");
        }
    }
}
