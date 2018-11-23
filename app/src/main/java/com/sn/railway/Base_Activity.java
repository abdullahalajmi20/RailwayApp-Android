package com.sn.railway;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ProgressBar;

import com.google.gson.Gson;
import com.sn.railway.main.Splash_Activity;
import com.sn.railway.constant.SharedPreferanceClass;
import com.sn.railway.custom.SnEditText;
import com.sn.railway.custom.SnTextView;

import com.sn.railway.objects.Login_Object;

import java.util.regex.Pattern;


public class Base_Activity extends AppCompatActivity {
    public static Activity mContext;

    static Dialog alertDialog;
    static View child;

    public void setHeaderTransparent() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }


    public static void showErrorMsg(String response) {
        Gson gson = new Gson();
        Login_Object obj = gson.fromJson(response, Login_Object.class);
        showToast(obj.getMessage());
    }

    public static Login_Object getUserObject() {
        Object object = SharedPreferanceClass.getCustomObject(mContext, SharedPreferanceClass.LOGIN, new Login_Object());
        if (object == null) {
            return null;
        } else {
            return (Login_Object) object;
        }

    }

    public static String intTo4Hex(int value) {
        return String.format("%04x", value & 0xff);
    }

    public static String intTo6Hex(int value) {
        return String.format("%06x", value & 0xff);
    }


    public void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.toggleSoftInput(0, InputMethodManager.HIDE_IMPLICIT_ONLY);
        }
    }

    public void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(
                Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = this;
        child = getLayoutInflater().inflate(R.layout.custom_progress_bar, null);
        ProgressBar progressBar = (ProgressBar) child.findViewById(R.id.progressBar);
        progressBar.getIndeterminateDrawable().setColorFilter(
                getResources().getColor(R.color.white),
                android.graphics.PorterDuff.Mode.SRC_IN);
        SnTextView tvMessage = (SnTextView) child.findViewById(R.id.tvMessage);
        String msg = "";
        if (msg != null && msg.length() > 0) {
            tvMessage.setText(msg);
            tvMessage.setVisibility(View.VISIBLE);
        } else {
            tvMessage.setVisibility(View.GONE);
        }
        alertDialog = new Dialog(new ContextThemeWrapper(Base_Activity.this, R.style.AlertDialogCustom));
        alertDialog.setCancelable(false);
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        alertDialog.setContentView(child);
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(getResources().getColor(R.color.headercolor));
        }
    }

    public static void enableDisableView(View view, boolean enabled) {
        view.setEnabled(enabled);
        if (view instanceof ViewGroup) {
            ViewGroup group = (ViewGroup) view;

            for (int idx = 0; idx < group.getChildCount(); idx++) {
                enableDisableView(group.getChildAt(idx), enabled);
            }
        }
    }

    public String getDistance(double latSource, double lngSource, double latDest, double lonDest) {
        Location selected_location = new Location("locationA");
        selected_location.setLatitude(latSource);
        selected_location.setLongitude(lngSource);
        Location near_locations = new Location("locationA");
        near_locations.setLatitude(latDest);
        near_locations.setLongitude(lonDest);

        double distance = selected_location.distanceTo(near_locations) / 1000;

        return String.format("%.2f", distance);
    }

    public static void dismissDialog() {
        try {
            alertDialog.dismiss();
        } catch (Exception e) {
            System.out.print("Show dismiss=" + e);
        }
    }

    public static boolean isGPSEnable(Context context) {
        int locationMode = 0;
        String locationProviders;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                locationMode = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE);

            } catch (Settings.SettingNotFoundException e) {
                e.printStackTrace();
                return false;
            }

            return locationMode != Settings.Secure.LOCATION_MODE_OFF;

        } else {
            locationProviders = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
            return !TextUtils.isEmpty(locationProviders);
        }
    }


    public static void logOutAlert() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
        alertDialogBuilder.setTitle(mContext.getResources().getString(R.string.app_name));
        alertDialogBuilder
                .setMessage(mContext.getString(R.string.wouldyouliketologout))
                .setCancelable(false)
                .setNegativeButton(mContext.getString(R.string.no), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                })
                .setPositiveButton(mContext.getString(R.string.yes), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        SharedPreferanceClass.setCustomObject(mContext, SharedPreferanceClass.LOGIN, null);
                        Base_Activity.restartActivity(Splash_Activity.class, null);
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mContext = this;
    }

    public static void showDialog() {

        try {
            alertDialog.show();
        } catch (Exception e) {
            child = mContext.getLayoutInflater().inflate(R.layout.custom_progress_bar, null);
            ProgressBar progressBar = (ProgressBar) child.findViewById(R.id.progressBar);
            progressBar.getIndeterminateDrawable().setColorFilter(
                    mContext.getResources().getColor(R.color.white),
                    android.graphics.PorterDuff.Mode.SRC_IN);
            SnTextView tvMessage = (SnTextView) child.findViewById(R.id.tvMessage);
            String msg = "";
            if (msg != null && msg.length() > 0) {
                tvMessage.setText(msg);
                tvMessage.setVisibility(View.VISIBLE);
            } else {
                tvMessage.setVisibility(View.GONE);
            }
            alertDialog = new Dialog(new ContextThemeWrapper(mContext, R.style.AlertDialogCustom));
            alertDialog.setCancelable(false);
            alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            alertDialog.setContentView(child);
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            alertDialog.show();
        }


    }

    public static boolean isNetworkAvailable() {
        if (isNetworkAvailable(mContext)) {
            return true;
        } else {
            showToast(mContext.getString(R.string.networknotavailable));
            return false;
        }
    }

    public static void showToast(String msg) {

        openMessageAlert(msg);
    }

    public static void showToastWithClose(String msg) {

        AlertWithCloseActivity(msg);
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();

            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    Log.i("Class", info[i].getState().toString());
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }


    public static boolean isEditTextValid(SnEditText mEditText) {
        if (mEditText != null && mEditText.getText().toString().trim().length() > 0) {
            return true;
        }
        return false;
    }

    public static boolean isEditTextValid(SnEditText mEditText, String errMsg) {
        if (mEditText != null && mEditText.getText().toString().trim().length() > 0) {
            return true;
        }
        showToast(errMsg);
        return false;
    }

    public static boolean isTextViewValid(SnTextView mEditText) {
        if (mEditText != null && mEditText.getText().toString().trim().length() > 0) {
            return true;
        }
        return false;
    }

    public static boolean isValidPassWordLength(SnEditText mEditText) {
        String password = getEditText(mEditText);
        int length = password.length();
        if (length >= 4) {
            return true;
        }
        return false;
    }

    public static boolean isValidPassWordLength(SnEditText mEditText, String alertMsg) {
        String password = getEditText(mEditText);
        int length = password.length();
        if (length >= 4) {
            return true;
        }
        showToast(alertMsg);
        return false;
    }

    public static boolean isValidMobileLength(SnEditText mEditText) {
        String password = getEditText(mEditText);
        int length = password.length();
        if (length == 9) {
            return true;
        }
        return false;
    }

    public static String getEditText(SnEditText mEditText) {
        return mEditText.getText().toString().trim();
    }

    public static String getTextValue(SnTextView mTextView) {
        return mTextView.getText().toString().trim();
    }

    public static boolean isValidEmail(SnEditText mEditText, String msg) {
        String email = getEditText(mEditText);
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        boolean isTrue = pattern.matcher(email).matches();
        if (isTrue) {
            return true;
        }
        showToast(msg);
        return isTrue;
    }

    public static boolean isValidEmail(SnEditText mEditText) {
        String email = getEditText(mEditText);
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        boolean isTrue = pattern.matcher(email).matches();
        if (isTrue) {
            return true;
        }
        // showToast(msg);
        return isTrue;
    }

    public static boolean isValidMobile(String phone) {

        return Pattern.matches("\\+?[0-9]{6,15}", phone);

    }

    public static boolean isValidMobile(SnEditText phone, String msg) {
        boolean isValid = Pattern.matches("\\+?[0-9]{6,15}", getEditText(phone));
        if (isValid) {
            return true;
        }
        showToast(msg);
        return false;

    }

    public static void callActivity(Class mClass, Bundle mBundle) {
        Intent mIntent = new Intent(mContext, mClass);
        if (mBundle != null) {
            mIntent.putExtras(mBundle);
        }
        mContext.startActivity(mIntent);
    }

    public static void restartActivity(Class mClass, Bundle mBundle) {
        Intent mIntent = new Intent(mContext, mClass);
        if (mBundle != null) {
            mIntent.putExtras(mBundle);
        }
        mContext.startActivity(mIntent);
        mContext.finishAffinity();
    }

    public static void callAndFinishActivity(Class mClass, Bundle mBundle) {
        Intent mIntent = new Intent(mContext, mClass);
        if (mBundle != null) {
            mIntent.putExtras(mBundle);
        }
        mContext.startActivity(mIntent);

        mContext.finish();
    }

    public void finishActivity() {
        finish();
    }

    public static void openMessageAlert(String msg) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
        alertDialogBuilder.setTitle(mContext.getString(R.string.app_name));
        alertDialogBuilder
                .setMessage(msg)
                .setCancelable(false)
                .setPositiveButton(mContext.getString(R.string.ok), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                })
        ;
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public static void openMessageAlertWithClose(final Activity activity, String msg) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
        alertDialogBuilder.setTitle(mContext.getString(R.string.app_name));
        alertDialogBuilder
                .setMessage(msg)
                .setCancelable(false)
                .setPositiveButton(mContext.getString(R.string.ok), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                        activity.finish();
                    }
                })
                ;
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }


    public void AlertForCloseActivity(final Activity mActivity) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mActivity);
        alertDialogBuilder.setTitle(getString(R.string.app_name));
        alertDialogBuilder
                .setMessage(getString(R.string.wouldyouliketoexit))
                .setCancelable(false)
                .setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        mActivity.finish();
                    }
                })
                .setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public static void AlertWithCloseActivity(String msg) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
        alertDialogBuilder.setTitle(mContext.getString(R.string.app_name));
        alertDialogBuilder
                .setMessage(msg)
                .setCancelable(false)
                .setPositiveButton(mContext.getString(R.string.ok), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        mContext.finish();
                    }
                })
        ;
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
