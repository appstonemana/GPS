package com.mb.mygps;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LocationListener {

    TextView tv_latlong;

    private String[] permissions = new String[]{
            Manifest.permission.ACCESS_FINE_LOCATION
    };


    private Location mLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_latlong = findViewById(R.id.tv_latlong);


        checkPermissions(permissions);

        if (getLocation() != null) {
            mLocation = getLocation();
            double lat = mLocation.getLatitude();
            double lang = mLocation.getLongitude();

            tv_latlong.setText("Lat:" + lat + "\n" + "Longi:" + lang);
            Toast.makeText(MainActivity.this, "LAT - " + lat + " Longi - " + lang, Toast.LENGTH_SHORT).show();
        }

    }

    private Location getLocation() {
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        assert lm != null;
        boolean isGpsEnable = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);

        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(MainActivity.this, "Permisson no granted", Toast.LENGTH_SHORT).show();
        }
        if (isGpsEnable) {
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 6000, 0, this);
            return lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        } else {
            Toast.makeText(MainActivity.this, "plz enable gps", Toast.LENGTH_SHORT).show();
        }
        return null;
    }

    private void checkPermissions(String[] permissions) {
        int result;
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String p : permissions) {
            result = ActivityCompat.checkSelfPermission(this, p);
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, permissions, 100);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        if (requestCode == 100) {

            if (grantResults.length > 0) {

                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                        checkPermissions(new String[]{permissions[i]});
                    }
                }
            }
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        mLocation = location;

        if (getLocation() != null) {
            double lat = mLocation.getLatitude();
            double lang = mLocation.getLongitude();

            tv_latlong.setText("Lat:" + lat + "\n" + "Longi:" + lang);
            Toast.makeText(MainActivity.this, "LAT - " + lat + " Longi - " + lang, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
