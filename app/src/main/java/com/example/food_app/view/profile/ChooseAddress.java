package com.example.food_app.view.profile;

import static androidx.compose.ui.graphics.CanvasKt.Canvas;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.food_app.R;
import com.example.food_app.base.BaseActivity;
import com.example.food_app.databinding.ActivityChooseAddressBinding;
import com.example.food_app.databinding.ActivityHomeBinding;
import com.example.food_app.utils.CommonUtils;
import com.example.food_app.utils.Constant;
import com.example.food_app.utils.SharePreferenceUtils;
import com.example.food_app.view.home.HomeActivity;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.io.IOException;
import java.util.List;

public class ChooseAddress extends BaseActivity<ActivityChooseAddressBinding> implements OnMapReadyCallback {
    Bundle savedInstance = null;
    private boolean booleanShow = false;

    private GoogleMap googleMap;
    private FusedLocationProviderClient fusedLocationClient;
    private LocationRequest locationRequest;
    private Geocoder geocoder = null;
    private String addressValue = "";
    private BottomSheetDialog bottomSheetDialog;
    private String searchLocation = "";
    private int mapType = 0;

    private String city = "";
    private String location = "";
    private String lat = "";
    private String lon = "";
    private String saveAddress;

    //    private List<Search> listSearch = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        savedInstance = savedInstanceState;
    }

    @Override
    protected ActivityChooseAddressBinding setViewBinding() {
        return ActivityChooseAddressBinding.inflate(LayoutInflater.from(this));
    }

    @Override
    protected void initView() {

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED) {
            requestPermissionLocation();
        }

        if (!checkTurnOnLocation()) {
            openPopUpLocationSetting(this);
        }
        binding.mapView.onCreate(savedInstance);
        binding.mapView.getMapAsync(this);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        locationRequest = LocationRequest.create().setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY).setInterval(1000);

        mapType = SharePreferenceUtils.getTypeMap();

        geocoder = new Geocoder(this);
    }

    @Override
    protected void viewListener() {
        binding.btnDone.setOnClickListener(v -> {
            Intent intent = new Intent(ChooseAddress.this, ChangeInfoActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString(Constant.ADDRESS,addressValue);
            intent.putExtras(bundle);
            startActivity(intent);
            finishAffinity();
        });

        binding.btnBack.setOnClickListener(v -> {
            onBackPressed();
        });
        binding.edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable text) {
                // Implementation for afterTextChanged
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Implementation for beforeTextChanged
            }

            @Override
            public void onTextChanged(CharSequence location, int start, int before, int count) {
                searchLocation = location.toString();
                binding.recyclerView.setVisibility(location.toString().isEmpty() ? View.GONE : View.VISIBLE);
            }
        });

        binding.btnSearch.setOnClickListener(v -> {
            searchLocationFromString();
        });

        binding.btnLocation.setOnClickListener(v -> {
            getCurrentLocation();
        });
    }
    private void requestPermissionLocation() {
        if (!checkTurnOnLocation()) {
            openPopUpLocationSetting(ChooseAddress.this);
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                ActivityCompat.requestPermissions(
                        ChooseAddress.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        1444
                );
            } else {
            }
        }
    }


    private void searchLocationFromString() {
        CommonUtils.hideSoftKeyboard(this);
        try {
            if (geocoder != null) {
                List<Address> addresses = geocoder.getFromLocationName(searchLocation.toString(), 1);
                if (addresses != null && addresses.size() > 0) {
                    Address address = addresses.get(0);
                    LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                    if (googleMap != null) {
                        googleMap.clear();
                        // Get the Drawable from resources
                        Drawable dr = ContextCompat.getDrawable(this, R.drawable.ic_marker);
                        // Convert Drawable to Bitmap
                        Bitmap bitmap = Bitmap.createBitmap(150, 150, Bitmap.Config.ARGB_8888);
                        Canvas canvas = new Canvas(bitmap);
                        dr.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
                        dr.draw(canvas);
                        // Convert Bitmap to Drawable
                        Drawable  circleDrawable = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 150, 150, true));
                        // Convert Drawable to BitmapDescriptor
                        BitmapDescriptor markerIcon = getMarkerIconFromDrawable(circleDrawable);
                        googleMap.addMarker(new MarkerOptions().position(latLng).title(searchLocation.toString()).icon(markerIcon));
                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14f));
                    }
                    binding.edtSearch.getText().clear();
                    binding.loBottomLocation.setVisibility(View.VISIBLE);
                    Animation slideAnimation = AnimationUtils.loadAnimation(this, R.anim.side_slide);
                    binding.loBottomLocation.startAnimation(slideAnimation);
                    addressValue = address.getAddressLine(0);
                    binding.layoutBottomLocation.tvLocation.setText(addressValue);
                    binding.layoutBottomLocation.tvLat.setText(address.getLatitude() + "°");
                    binding.layoutBottomLocation.tvLong.setText(address.getLongitude() + "°");
                    String[] separated = addressValue.split(",");
                    binding.layoutBottomLocation.tvCity.setText(separated[separated.length - 2].trim() + ", " + separated[separated.length - 1].trim());
                    city = separated[separated.length - 2].trim() + ", " + separated[separated.length - 1].trim();
//                    //save data search near
//                    listSearch.add(new AnimationSearch.Search(searchLocation, address.getAddressLine(0)));
//                    setPref(this, Config.LIST_SEARCH, new Gson().toJson(listSearch));
//                    getListSearch();
//                    binding.recyclerView.setVisibility(View.GONE);
                }
            }
        } catch (Exception e) {
            Log.d("aaaa", e.getMessage());
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED) {
            try {
                if (geocoder != null) {
                    fusedLocationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                        @Override
                        public void onComplete(@NonNull Task<Location> task) {
                            if (task.isSuccessful() && task.getResult() != null) {
                                Location lastLocation = task.getResult();
                                LatLng latLng = new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude());
                                if (googleMap != null) {
                                    googleMap.clear();
                                    // Get the Drawable from resources
                                    Drawable dr = ContextCompat.getDrawable(ChooseAddress.this, R.drawable.ic_marker);
                                    // Convert Drawable to Bitmap
                                    Bitmap bitmap = Bitmap.createBitmap(150, 150, Bitmap.Config.ARGB_8888);
                                    Canvas canvas = new Canvas(bitmap);
                                    dr.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
                                    dr.draw(canvas);
                                    // Convert Bitmap to Drawable
                                    Drawable circleDrawable = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 150, 150, true));
                                    // Convert Drawable to BitmapDescriptor
                                    BitmapDescriptor markerIcon = getMarkerIconFromDrawable(circleDrawable);
                                    googleMap.addMarker(new MarkerOptions().position(latLng).title("Current Location").icon(markerIcon));
                                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14f));
                                }
                                binding.loBottomLocation.setVisibility(View.VISIBLE);
                                Animation slideAnimation = AnimationUtils.loadAnimation(ChooseAddress.this, R.anim.side_slide);
                                binding.loBottomLocation.startAnimation(slideAnimation);
                                // Reverse geocoding to get address from location
                                List<Address> addresses = null;
                                try {
                                    addresses = geocoder.getFromLocation(lastLocation.getLatitude(), lastLocation.getLongitude(), 1);
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                                if (addresses != null && addresses.size() > 0) {
                                    Address address = addresses.get(0);
                                    addressValue = address.getAddressLine(0);
                                    binding.layoutBottomLocation.tvLocation.setText(addressValue);
                                    binding.layoutBottomLocation.tvLat.setText(String.valueOf(address.getLatitude()) + "°");
                                    binding.layoutBottomLocation.tvLong.setText(String.valueOf(address.getLongitude()) + "°");
                                    String[] separated = addressValue.split(",");
                                    binding.layoutBottomLocation.tvCity.setText(separated[separated.length - 2].trim() + ", " + separated[separated.length - 1].trim());
                                    city = separated[separated.length - 2].trim() + ", " + separated[separated.length - 1].trim();
                                }
                            } else {
                                Log.d("aaaa", "No location found.");
                                Toast.makeText(ChooseAddress.this, "No location found.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            } catch (Exception e) {
                Log.d("aaaa", e.getMessage());
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }


    private BitmapDescriptor getMarkerIconFromDrawable(Drawable drawable) {
        Canvas canvas = new Canvas();
        Bitmap bitmap = Bitmap.createBitmap(
                drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(),
                Bitmap.Config.ARGB_8888
        );
        canvas.setBitmap(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    private void initLocationGoogleMap() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // Get the current location and move the map to that location
            fusedLocationClient.getLastLocation().addOnSuccessListener(location -> {
                if (location != null) {
                    LatLng latLng;
                    if (!SharePreferenceUtils.getString(Constant.MANUAL_LAT, "").isEmpty()) {
                        latLng = new LatLng(Double.parseDouble(getString(Integer.parseInt(Constant.MANUAL_LAT), "")), Double.parseDouble(getString(Integer.parseInt(Constant.MANUAL_LON), "")));
                    } else {
                        latLng = new LatLng(location.getLatitude(), location.getLongitude());
                    }
                    // Get the Drawable from resources
                    Drawable dr = ContextCompat.getDrawable(this, R.drawable.ic_marker);
                    // Convert Drawable to Bitmap
                    Bitmap bitmap = Bitmap.createBitmap(150, 150, Bitmap.Config.ARGB_8888);
                    Canvas canvas = new Canvas(bitmap);
                    dr.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
                    dr.draw(canvas);
                    // Convert Bitmap to Drawable
                    Drawable  circleDrawable = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 150, 150, true));
                    // Convert Drawable to BitmapDescriptor
                    BitmapDescriptor markerIcon = getMarkerIconFromDrawable(circleDrawable);
                    if (googleMap != null) {
                        googleMap.addMarker(new MarkerOptions().position(latLng).title("Bạn ở đây").icon(markerIcon));
                        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10f));
                    }
                }
            });

            if (locationRequest != null) {
                fusedLocationClient.requestLocationUpdates(locationRequest, new LocationCallback() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onLocationResult(LocationResult locationResult) {
                        super.onLocationResult(locationResult);
                        for (Location location : locationResult.getLocations()) {
                            // Handle location updates
                        }
                    }
                }, null);
            }
        }
    }

    private void initLocationGoogleMapCurrent() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED) {

            fusedLocationClient.getCurrentLocation(LocationRequest.PRIORITY_HIGH_ACCURACY,null).addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                        LatLng currentLatLng = new LatLng(location.getLatitude(), location.getLongitude());

//                        // Get the Drawable from resources
//                        Drawable dr = ContextCompat.getDrawable(ChooseAddress.this, R.drawable.ic_marker);
//                        // Convert Drawable to Bitmap
//                        Bitmap bitmap = Bitmap.createBitmap(150, 150, Bitmap.Config.ARGB_8888);
//                        Canvas canvas = new Canvas(bitmap);
//                        dr.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
//                        dr.draw(canvas);
//                        // Convert Bitmap to Drawable
//                        Drawable  circleDrawable = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 150, 150, true));
//                        // Convert Drawable to BitmapDescriptor
//                        BitmapDescriptor markerIcon = getMarkerIconFromDrawable(circleDrawable);
//
//                        googleMap.addMarker(new MarkerOptions().position(currentLatLng).title("Bạn ở đây").icon(markerIcon));
//                        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 14f));
                    }
                }
            });

            fusedLocationClient.requestLocationUpdates(
                    locationRequest,
                    new LocationCallback() {
                        @SuppressLint("SetTextI18n")
                        @Override
                        public void onLocationResult(LocationResult locationResult) {
                            super.onLocationResult(locationResult);
                            Address address = null;
                            LatLng latLng = new LatLng(0.0, 0.0);
                            for (Location location : locationResult.getLocations()) {
                                if (geocoder != null) {
                                    try {
                                        List<Address> addresses;
                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                                            addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                                            if (addresses != null && !addresses.isEmpty()) {
                                                address = addresses.get(0);
                                                latLng = new LatLng(address.getLatitude(), address.getLongitude());
                                                setMarkerAndZoomToCurrentLocation(latLng, address);
                                            }
                                        } else {
                                            addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                                            if (addresses != null && !addresses.isEmpty()) {
                                                address = addresses.get(0);
                                                latLng = new LatLng(address.getLatitude(), address.getLongitude());
                                                setMarkerAndZoomToCurrentLocation(latLng, address);
                                            }
                                        }
                                    } catch (Exception e) {
                                        Log.e("iiiiii_GoogleMap_Error", "Something Error");
                                    }
                                }
                            }
                        }
                    },
                    null
            );
        }
    }

    private void setMarkerAndZoomToCurrentLocation(LatLng latLng, Address address) {
        if (!booleanShow) {
            googleMap.clear();
            Drawable dr = ContextCompat.getDrawable(this, R.drawable.ic_marker);
            Bitmap bitmap = null;
            if (dr != null) {
                bitmap = Bitmap.createBitmap(150, 150, Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bitmap);
                dr.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
                dr.draw(canvas);
            }
            BitmapDescriptor markerIcon = null;
            if (bitmap != null) {
                Drawable circleDrawable = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 150, 150, true));
                markerIcon = getMarkerIconFromDrawable(circleDrawable);
            }
            googleMap.addMarker(new MarkerOptions()
                    .position(latLng)
                    .title("Bạn ở đây")
                    .icon(markerIcon));
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14f));

            booleanShow = true;
            try {
                binding.loBottomLocation.setVisibility(View.VISIBLE);
                if (this != null) {
                    Animation slideAnimation = AnimationUtils.loadAnimation(this, R.anim.side_slide);
                    binding.loBottomLocation.startAnimation(slideAnimation);
                }
                String addressValue = address != null ? address.getAddressLine(0) : "";
                binding.layoutBottomLocation.tvLocation.setText(addressValue);
                binding.layoutBottomLocation.tvLat.setText(address != null ? address.getLatitude() + "°" : "");
                binding.layoutBottomLocation.tvLong.setText(address != null ? address.getLongitude() + "°" : "");
                if (addressValue != null) {
                    String[] separated = addressValue.split(",");
                    if (separated.length >= 2) {
                        binding.layoutBottomLocation.tvCity.setText(separated[separated.length - 2].trim() + ", " + separated[separated.length - 1].trim());
                    }
                }
            } catch (Exception ignored) {
            }
        }
    }


    @Override
    public void onMapReady(@NonNull GoogleMap gMap) {
        googleMap = gMap;
        initLocationGoogleMapCurrent();
    }

    @Override
    protected void onResume() {
        super.onResume();
        binding.mapView.onResume();
        binding.edtSearch.setText("");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding.mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        binding.mapView.onLowMemory();
    }

    public boolean checkTurnOnLocation() {
        LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    public void openPopUpLocationSetting(Activity context) {
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(5000);
        locationRequest.setFastestInterval(2000);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);
        LocationSettingsRequest locationSettingsRequest = builder.build();

        Task<LocationSettingsResponse> result = LocationServices.getSettingsClient(context)
                .checkLocationSettings(locationSettingsRequest);

        result.addOnCompleteListener(task -> {
            try {
                LocationSettingsResponse response = task.getResult(ApiException.class);
                Toast.makeText(context, "GPS is On", Toast.LENGTH_SHORT).show();
            } catch (ApiException apiException) {
                int statusCode = apiException.getStatusCode();
                switch (statusCode) {
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        if (apiException instanceof ResolvableApiException) {
                            ResolvableApiException resolvable = (ResolvableApiException) apiException;
                            try {
                                resolvable.startResolutionForResult(context, 1555);
                            } catch (IntentSender.SendIntentException e) {
                                // Ignore the error.
                            }
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        // Location settings are not satisfied. However, we have no way to fix the
                        // settings so we won't show the dialog.
                        break;
                }
            }
        });
    }

}
