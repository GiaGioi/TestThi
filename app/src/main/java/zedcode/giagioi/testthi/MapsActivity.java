package zedcode.giagioi.testthi;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.List;

import zedcode.giagioi.testthi.database.DatabaseHelper;
import zedcode.giagioi.testthi.model.AddMap;
import zedcode.giagioi.testthi.sqlDAO.AddMapDAO;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Boolean mLoactionGranted = false;
    final Context context = this;
    private AddMapDAO addMapDAO;
    List<AddMap> addMaps;
    Dialog dialog;
    DatabaseHelper database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        dialog=new Dialog(MapsActivity.this);
        addMaps=new ArrayList<>();
        database = new DatabaseHelper(this);
        addMapDAO = new AddMapDAO(database);
        AddMap addMap = new AddMap((int) 21.0266667, (int) 105.748809);
        addMapDAO.insertMap(addMap);
        addMaps.add(addMap);
        Log.e("insertMap",String.valueOf(addMap));

        addMaps = addMapDAO.getAllMap();


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }

    private void moveCamera(LatLng latLng, float zoom, String title) {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
        MarkerOptions markerOptions = new MarkerOptions().position(latLng).
                title(title);
        mMap.addMarker(markerOptions);
    }

//    private void getDeviceLoction() {
//        FusedLocationProviderClient fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            return;
//        }
//        Task loction = fusedLocationProviderClient.getLastLocation();
//        loction.addOnCompleteListener(new OnCompleteListener() {
//            @Override
//            public void onComplete(@NonNull Task task) {
//                if (task.isSuccessful()) {
//                    Location location = (Location) task.getResult();
//                    moveCamera(new LatLng(location.getLatitude(), location.getLongitude()), 15f, "Location");
//                }
//            }
//        });
//
//    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(21.0339371, 105.7662822);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Ha Noi"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 15));

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(final Marker marker) {
                final Dialog dialog = new Dialog(MapsActivity.this);
                dialog.setContentView(R.layout.dialog);
                dialog.findViewById(R.id.them).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.findViewById(R.id.layout).setVisibility(View.VISIBLE);
                        dialog.findViewById(R.id.layout1).setVisibility(View.GONE);
                        dialog.findViewById(R.id.thaydoi).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                EditText editText = dialog.findViewById(R.id.latitude);
                                EditText editText1 = dialog.findViewById(R.id.longitude);
                                String latitude = editText.getText().toString();
                                String longitude = editText1.getText().toString();
                                AddMap addMap = new AddMap(-34,151);
                                addMapDAO.insertMap(addMap);
                                if (!latitude.isEmpty() && !longitude.isEmpty()) {
                                    final LatLng sydney1 = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));
                                    mMap.addMarker(new MarkerOptions().position(sydney1).title("Haha"));
                                    dialog.dismiss();
                                }
                            }
                        });
                    }
                });
                dialog.findViewById(R.id.sua).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final EditText editText = dialog.findViewById(R.id.latitude);
                        final EditText editText1 = dialog.findViewById(R.id.longitude);
                        dialog.findViewById(R.id.layout).setVisibility(View.VISIBLE);
                        dialog.findViewById(R.id.layout1).setVisibility(View.GONE);
                        final LatLng latLng = marker.getPosition();
                        editText.setText(latLng.latitude + "");
                        editText1.setText(latLng.longitude + "");
                        dialog.findViewById(R.id.thaydoi).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String latitude = editText.getText().toString();
                                String longitude = editText1.getText().toString();
                                
                                if (!latitude.isEmpty() && !longitude.isEmpty()) {
                                    final LatLng sydney1 = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));
                                    if (!latLng.toString().equals(sydney1.toString())) {
                                        Log.e("OK", "OK");
                                        marker.setPosition(sydney1);
                                        dialog.dismiss();
                                    } else {
                                        Log.e("TAG", "MỜI BẠN NHẬP VỊ TRÍ KHÁC");
                                    }
                                }
                            }
                        });
                    }
                });


                dialog.findViewById(R.id.xoa).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        delete(marker);
                        dialog.dismiss();
                    }
                });
                dialog.show();
                return true;
            }
        });
    }

    public void delete(final Marker marker) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

        alertDialogBuilder.setTitle("Your Title");

        // set dialog message
        alertDialogBuilder
                .setMessage("Do you want delete Marker?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                       marker.remove();
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();

        alertDialog.show();
    }
    private void updateMap(String map, int position) {
        AddMap n = addMaps.get(position);
        // updating note text
        n.setLatitude(Double.parseDouble(map));
        n.setLongitude(Double.parseDouble(map));

        // updating note in db
        addMapDAO.updateMap(n);


        // refreshing the list
        addMaps.set(position, n);

    }
}
