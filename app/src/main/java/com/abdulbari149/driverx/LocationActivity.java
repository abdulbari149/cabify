/*
 * Copyright 2022 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.abdulbari149.driverx;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;


import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.abdulbari149.driverx.databinding.ActivityLocationBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.AddressComponent;
import com.google.android.libraries.places.api.model.AddressComponents;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.maps.android.SphericalUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static com.google.maps.android.SphericalUtil.computeDistanceBetween;

@SuppressWarnings("FieldCanBeLocal")
public class LocationActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final String TAG = "ADDRESS_AUTOCOMPLETE";
    private static final String MAP_FRAGMENT_TAG = "MAP";
    private LatLng coordinates1;
    public LatLng loc1;
    public LatLng loc2;
    public double distance;
    private boolean checkProximity = false;
    private SupportMapFragment mapFragment;
    private GoogleMap map;
    private Marker marker;
    private PlacesClient placesClient;
    private View mapPanel;
    private LatLng deviceLocation;
    private static final double acceptedProximity = 150;

    private ActivityLocationBinding binding;

    View.OnClickListener startAutocompleteIntentListener1 = view -> {
        view.setOnClickListener(null);
        startAutocompleteIntent1();
    };
    View.OnClickListener startAutocompleteIntentListener2 = view -> {
        view.setOnClickListener(null);
        startAutocompleteIntent2();
    };
    // [START maps_solutions_android_autocomplete_define]
    private final ActivityResultLauncher<Intent> startAutocomplete1 = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent intent = result.getData();
                    if (intent != null) {
                        Place place1 = Autocomplete.getPlaceFromIntent(intent);

                        // Write a method to read the address components from the Place
                        // and populate the form with the address components
                        Log.d(TAG, "Place: " + place1.getAddressComponents());
                        fillInAddress(place1,true);
                    }
                } else if (result.getResultCode() == Activity.RESULT_CANCELED) {
                    // The user canceled the operation.
                    Log.i(TAG, "User canceled autocomplete1");
                }
            });
    private final ActivityResultLauncher<Intent> startAutocomplete2 = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent intent = result.getData();
                    if (intent != null) {
                        Place place2 = Autocomplete.getPlaceFromIntent(intent);

                        // Write a method to read the address components from the Place
                        // and populate the form with the address components
                        Log.d(TAG, "Place: " + place2.getAddressComponents());
                        fillInAddress(place2,false);
                    }
                } else if (result.getResultCode() == Activity.RESULT_CANCELED) {
                    // The user canceled the operation.
                    Log.i(TAG, "User canceled autocomplete2");
                }
            });
    // [END maps_solutions_android_autocomplete_define]

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        binding.autocompleteAddress1.setOnClickListener(startAutocompleteIntentListener1);
        binding.autocompleteAddressv2.setOnClickListener(startAutocompleteIntentListener2);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final String apiKey = BuildConfig.PLACES_API_KEY;

        if (apiKey.equals("")) {
            Toast.makeText(this, "API Key not found", Toast.LENGTH_LONG).show();
            return;
        }

        // Setup Places Client
        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), apiKey);
        }

        binding = ActivityLocationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Retrieve a PlacesClient (previously initialized - see MainActivity)
        placesClient = Places.createClient(this);

        // Attach an Autocomplete intent to the Address 1 EditText field
        binding.autocompleteAddress1.setOnClickListener(startAutocompleteIntentListener1);

        // Update checkProximity when user checks the checkbox
        CheckBox checkProximityBox = findViewById(R.id.checkbox_proximity);
        checkProximityBox.setOnCheckedChangeListener((view, isChecked) -> {
            // Set the boolean to match user preference for when the Submit button is clicked
            checkProximity = isChecked;
        });

        // Submit and optionally check proximity
        Button saveButton = findViewById(R.id.autocomplete_save_button);
        saveButton.setOnClickListener(v -> saveForm());

        // Reset the form
        Button resetButton = findViewById(R.id.autocomplete_reset_button);
        resetButton.setOnClickListener(v -> clearForm());
    }

    // [START maps_solutions_android_autocomplete_intent]
    private void startAutocompleteIntent1() {

        // Set the fields to specify which types of place data to
        // return after the user has made a selection.
        List<Place.Field> fields = Arrays.asList(Place.Field.ADDRESS_COMPONENTS,
                Place.Field.LAT_LNG, Place.Field.VIEWPORT);

        // Build the autocomplete intent with field, country, and type filters applied
        Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fields)
                .setCountry("PK")
                .setTypesFilter(new ArrayList<String>() {{
                    add(TypeFilter.ADDRESS.toString().toLowerCase());
                }})
                .build(this);
        startAutocomplete1.launch(intent);
    }
    private void startAutocompleteIntent2() {

        // Set the fields to specify which types of place data to
        // return after the user has made a selection.
        List<Place.Field> fields = Arrays.asList(Place.Field.ADDRESS_COMPONENTS,
                Place.Field.LAT_LNG, Place.Field.VIEWPORT);

        // Build the autocomplete intent with field, country, and type filters applied
        Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fields)
                .setCountry("PK")
                .setTypesFilter(new ArrayList<String>() {{
                    add(TypeFilter.ADDRESS.toString().toLowerCase());
                }})
                .build(this);
        startAutocomplete2.launch(intent);
    }


    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        map = googleMap;
        try {
            boolean success = map.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(this, R.raw.style_json));

            if (!success) {
                Log.e(TAG, "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e(TAG, "Can't find style. Error: ", e);
        }
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(coordinates1, 15f));
        marker = map.addMarker(new MarkerOptions().position(coordinates1));
    }


private void fillInAddress(Place place , boolean Boolean) {
    AddressComponents components = place.getAddressComponents();
    if (Boolean) {
        StringBuilder address1 = new StringBuilder();
        loc1 = place.getLatLng();

        if (components != null) {
            for (AddressComponent component : components.asList()) {
                String type = component.getTypes().get(0);
                switch (type) {
                    case "street_number": {
                        address1.insert(0, component.getName());
                        break;
                    }

                    case "route": {
                        address1.append(" ");
                        address1.append(component.getShortName());
                        break;
                    }

                }
            }
        }

        binding.autocompleteAddress1.setText(address1.toString());
    } else {
        StringBuilder address2 = new StringBuilder();
        loc2 = place.getLatLng();
        if (components != null) {
            for (AddressComponent component : components.asList()) {
                String type = component.getTypes().get(0);
                switch (type) {
                    case "street_number": {
                        address2.insert(0, component.getName());
                        break;
                    }

                    case "route": {
                        address2.append(" ");
                        address2.append(component.getShortName());
                        break;
                    }

                }
            }
        }

        binding.autocompleteAddressv2.setText(address2.toString());
    }
        binding.autocompleteAddressv2.requestFocus();

        showMap(place,loc1 , loc2);
    }

    private void showMap(Place place , LatLng loc1 , LatLng loc2) {
        coordinates1 = place.getLatLng();

        mapFragment = (SupportMapFragment)
                getSupportFragmentManager().findFragmentByTag(MAP_FRAGMENT_TAG);

        if (mapFragment == null) {
            mapPanel = ((ViewStub) findViewById(R.id.stub_map)).inflate();
            GoogleMapOptions mapOptions = new GoogleMapOptions();
            mapOptions.mapToolbarEnabled(false);

            mapFragment = SupportMapFragment.newInstance(mapOptions);

            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.confirmation_map, mapFragment, MAP_FRAGMENT_TAG)
                    .commit();
            mapFragment.getMapAsync(this);
        } else {
            if (loc1 != null & loc2 != null){
                distance = SphericalUtil.computeDistanceBetween(loc1,loc2);

                updateMap(loc2);
                updateMap(loc1);

            }
        }
    }

    private void updateMap(LatLng latLng) {
        marker.setPosition(latLng);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f));
        if (mapPanel.getVisibility() == View.GONE) {
            mapPanel.setVisibility(View.VISIBLE);
        }
    }

    private void saveForm() {
        Log.d(TAG, "checkProximity = " + checkProximity);

        Intent optionsIntent = getIntent();

        Intent intent = new Intent(LocationActivity.this , ConfirmationActivity.class);
        Bundle args = new Bundle();
        args.putParcelable("pickup",loc1);
        args.putParcelable("dropoff",loc2);
        args.putDouble("distance",distance);
        args.putString("driverId", optionsIntent.getStringExtra("driverId"));
        args.putString("driverName", optionsIntent.getStringExtra("driverName"));
        intent.putExtra("args",args);
        startActivity(intent);


    }

    private void clearForm() {
        binding.autocompleteAddress1.setText("");
        binding.autocompleteAddressv2.setText("");
        if (mapPanel != null) {
            mapPanel.setVisibility(View.GONE);
        }
        binding.autocompleteAddress1.requestFocus();
    }

    private final ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    getAndCompareLocations();
                } else {
                    Log.d(TAG, "User denied permission");
                }
            });

    private void checkLocationPermissions() {
        if (ContextCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            getAndCompareLocations();
        } else {
            requestPermissionLauncher.launch(
                    ACCESS_FINE_LOCATION);
        }
    }

    @SuppressLint("MissingPermission")
    private void getAndCompareLocations() {
        LatLng enteredLocation = coordinates1;
        map.setMyLocationEnabled(true);

        FusedLocationProviderClient fusedLocationClient =
                LocationServices.getFusedLocationProviderClient(this);

        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, location -> {
                    if (location == null) {
                        return;
                    }

                    deviceLocation = new LatLng(location.getLatitude(), location.getLongitude());
                    Log.d(TAG, "device location = " + deviceLocation);
                    Log.d(TAG, "entered location = " + enteredLocation.toString());

                    double distanceInMeters = computeDistanceBetween(deviceLocation, enteredLocation);
                    if (distanceInMeters <= acceptedProximity) {
                        Log.d(TAG, "location matched");
                    } else {
                        Log.d(TAG, "location not matched");
                    }
                });
    }
}