package in.smartglobalsolutions.makejar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import in.smartglobalsolutions.mygenerator.GPSTracker;

public class MainActivity extends AppCompatActivity {
    GPSTracker gpsTracker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
}