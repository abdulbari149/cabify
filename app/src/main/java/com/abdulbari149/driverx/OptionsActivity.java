package com.abdulbari149.driverx;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

public class OptionsActivity extends AppCompatActivity  {
    GoogleSignInClient googleSignInClient;


    private void startVehicleActivityOnClick(int id, String vehicleType) {
        findViewById(id).setOnClickListener(
                v -> {
                    Intent vehicleIntent = new Intent(OptionsActivity.this, DriversActivity.class);
                    vehicleIntent.putExtra("vehicle_type", vehicleType);
                    startActivity(vehicleIntent);
                }
        );
    };

    private final OnSuccessListener<Void> googleLogoutSuccess = o -> {
        finish();
        Toast.makeText(OptionsActivity.this, "Signed out Successfully", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(OptionsActivity.this,LoginActivity.class));
    };

    private final OnFailureListener googleLogoutFailure = e -> Toast.makeText(OptionsActivity.this, "Failed to logout. Please try Again", Toast.LENGTH_SHORT).show();

    private void googleLogout() {
        googleSignInClient.signOut()
                .addOnSuccessListener(googleLogoutSuccess)
                .addOnFailureListener(googleLogoutFailure);
    };

    private void goToRidesScreen() {
        startActivity(new Intent(OptionsActivity.this, RidesActivity.class));
    }

    private final PopupMenu.OnMenuItemClickListener menuItemClickHandler =  new PopupMenu.OnMenuItemClickListener() {
        @SuppressLint("NonConstantResourceId")
        public boolean onMenuItemClick(MenuItem item) {
            switch (item.getItemId()){
                case R.id.menu_item_logout:
                    googleLogout();
                case R.id.menu_item_rides:
                    goToRidesScreen();
                default:
                    return false;
            }
        }
    };

    private final View.OnClickListener menuBtnClickHandler = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            PopupMenu popup = new PopupMenu(OptionsActivity.this, findViewById(R.id.menu_button));
            popup.getMenuInflater().inflate(R.menu.popup, popup.getMenu());
            popup.setOnMenuItemClickListener(menuItemClickHandler);
            popup.show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);
        findViewById(R.id.menu_button).setOnClickListener(this.menuBtnClickHandler);
        startVehicleActivityOnClick(R.id.carBtn, "car");
        startVehicleActivityOnClick(R.id.carPreBtn, "carPre");
        startVehicleActivityOnClick(R.id.autoBtn, "auto");
        startVehicleActivityOnClick(R.id.bikeBtn, "bike");
    }
}