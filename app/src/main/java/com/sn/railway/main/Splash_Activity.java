package com.sn.railway.main;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;

import com.google.firebase.iid.FirebaseInstanceId;
import com.sn.railway.Base_Activity;
import com.sn.railway.R;
import com.sn.railway.constant.Constant;

import com.sn.railway.objects.Login_Object;

import butterknife.ButterKnife;

public class Splash_Activity extends Base_Activity {
    private Handler mHandler;
    private Runnable mRunnable;
    public int SPLASH_DURATION = 1500;


    private int LOCATION_PERMISSION_CODE = 23;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        setHeaderTransparent();


        Constant.GCM_TOKEN = FirebaseInstanceId.getInstance().getToken();
        if (Constant.GCM_TOKEN == null) {
            Constant.GCM_TOKEN = "12345";
        } else if (Constant.GCM_TOKEN != null && Constant.GCM_TOKEN.length() == 0) {
            Constant.GCM_TOKEN = "12345";
        }


        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        final Login_Object login_object = getUserObject();


        mHandler = new Handler();
        mRunnable = new Runnable() {
            @Override
            public void run() {


                if (login_object == null) {

                    callAndFinishActivity(LoginActivity.class, null);
                } else {
                    callAndFinishActivity(HomeActivity.class, null);

                }

                finishAffinity();
            }
        };

    }


    private boolean isReadLocationAllowed() {
        //If permission is granted returning true
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
            return true;

       return false;
    }

    //Requesting permission
    private void requestLocationPermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.ACCESS_FINE_LOCATION)) {

        }

        //And finally ask for the permission
        ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_CODE);
    }

    //This method will be called when the user will tap on allow or deny
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == LOCATION_PERMISSION_CODE) {
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                mHandler.postDelayed(mRunnable, SPLASH_DURATION);
            } else {
                showToast("Oops you just denied the permission for app you must have to enable location.");
            }

        }
    }

    public void callGPS() {
        if (isReadLocationAllowed()) {

            if (isGPSEnable(this)) {

                mHandler.postDelayed(mRunnable, SPLASH_DURATION);
            } else {

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(R.string.app_name)
                        .setMessage("Your location is off.Please turn on from setting")
                        .setPositiveButton(R.string.yes,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                                    }
                                })
                        .setNegativeButton("Close App",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                        finishActivity();
                                    }
                                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        } else {
            requestLocationPermission();
        }
    }



    protected void onResume() {
        super.onResume();
        callGPS();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mHandler.removeCallbacks(mRunnable);
    }
}
