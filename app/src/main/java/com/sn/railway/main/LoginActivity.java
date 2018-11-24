package com.sn.railway.main;

import android.os.Bundle;

import android.util.Log;

import android.view.View;

import android.widget.ImageView;
import android.widget.Toast;

import com.sn.railway.Base_Activity;
import com.sn.railway.R;

import com.sn.railway.app.RailwayApplication;
import com.sn.railway.constant.Constant;
import com.sn.railway.constant.SharedPreferanceClass;
import com.sn.railway.custom.SnEditText;
import com.sn.railway.custom.SnTextView;

import com.sn.railway.objects.Login_Object;


import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;


public class LoginActivity extends Base_Activity implements View.OnClickListener {


    @Bind(R.id.edtEmailId)
    SnEditText edtEmailId;

    @Bind(R.id.edtPassword)
    SnEditText edtPassword;

    @Bind(R.id.btnLogin)
    ImageView btnLogin;


    @Bind(R.id.tvRegister)
    SnTextView tvRegister;


    @Bind(R.id.tvForgotPassword)
    SnTextView tvForgotPassword;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        getSupportActionBar().hide();

        btnLogin.setOnClickListener(this);
        tvForgotPassword.setOnClickListener(this);
        tvRegister.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvForgotPassword:
                callActivity(ForgotPasswordActivity.class, null);
                break;
            case R.id.btnLogin:
                loginProcess();
                break;
            case R.id.tvRegister:
                callActivity(RegisterActivity.class, null);
                break;
        }
    }



    public void loginProcess() {
        if (isNetworkAvailable()) {
            if (isEditTextValid(edtEmailId,getString(R.string.validation_email))) {
                if (isValidEmail(edtEmailId,getString(R.string.validation_valid_email))) {
                    if (isEditTextValid(edtPassword,getString(R.string.validation_password))) {
                        if (isValidPassWordLength(edtPassword,getString(R.string.validation_password_length))) {
                            Login_Object login_object = new Login_Object();
                            login_object.setUserId(1);
                            login_object.setEmail(getEditText(edtEmailId));
                            login_object.setFirstName("Abdullah");
                            login_object.setLastName("Alajmi");
                            login_object.setPhone("66558951");
                            SharedPreferanceClass.setCustomObject(getApplicationContext(), SharedPreferanceClass.LOGIN, login_object);
                            callAndFinishActivity(HomeActivity.class, null);
                           // callLoginAPI();
                        }
                    }
                }
            }
        }
    }


    public void callLoginAPI() {
        if (isNetworkAvailable()) {
            showDialog();
            Call<Login_Object> call = RailwayApplication.getRestClient().getApplicationServices().loginUser(
                    getEditText(edtEmailId),
                    getEditText(edtPassword), Constant.GCM_TOKEN
            );

            call.enqueue(new Callback<Login_Object>() {
                @Override
                public void onResponse(Response<Login_Object> response, Retrofit retrofit) {
                    Login_Object object = response.body();
                    if (object != null && !object.isError()) {
                        SharedPreferanceClass.setCustomObject(getApplicationContext(), SharedPreferanceClass.LOGIN, object);
                        callAndFinishActivity(HomeActivity.class, null);
                        Toast.makeText(getApplicationContext(), object.getMessage(), Toast.LENGTH_SHORT).show();
                    } else if (response.errorBody() != null) {
                        try {
                            showErrorMsg(response.errorBody().string());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    dismissDialog();
                }

                @Override
                public void onFailure(Throwable t) {
                    dismissDialog();
                    showToast(t.getMessage());
                    Log.d("CallBack", " Throwable is " + t);
                }
            });


        }
    }


}