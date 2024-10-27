package com.example.mapofcontacts;

import androidx.fragment.app.FragmentActivity;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.app.AlertDialog;
import android.provider.ContactsContract;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Initialize the map
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        showContactsOnMap();
    }

    private void showContactsOnMap() {
        Cursor cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);

        while (cursor != null && cursor.moveToNext()) {
            @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            @SuppressLint("Range") String mobile = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            @SuppressLint("Range") String home = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

            double latitude = Double.parseDouble(mobile);
            double longitude = Double.parseDouble(home);

            LatLng contactLocation = new LatLng(latitude, longitude);
            mMap.addMarker(new MarkerOptions().position(contactLocation).title(name));

            mMap.setOnMarkerClickListener(marker -> {
                new AlertDialog.Builder(MapsActivity.this)
                        .setTitle(marker.getTitle())
                        .setMessage("This is " + marker.getTitle() + "'s location")
                        .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                        .show();
                return false;
            });
        }

        if (cursor != null) {
            cursor.close();
        }
    }
}
