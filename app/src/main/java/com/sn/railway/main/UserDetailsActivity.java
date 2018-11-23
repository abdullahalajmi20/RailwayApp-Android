package com.sn.railway.main;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.sn.railway.Base_Activity;
import com.sn.railway.R;
import com.sn.railway.app.RailwayApplication;
import com.sn.railway.constant.Constant;
import com.sn.railway.constant.SharedPreferanceClass;
import com.sn.railway.custom.SnEditText;
import com.sn.railway.objects.Login_Object;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;


public class UserDetailsActivity extends Base_Activity {

    @OnClick(R.id.imgClose)
    public void imgClose(View view) {
        finishActivity();
    }

    @Bind(R.id.edtFirstName)
    SnEditText edtFirstName;

    @Bind(R.id.edtLastName)
    SnEditText edtLastName;

    @Bind(R.id.edtEmailId)
    SnEditText edtEmailId;

    @Bind(R.id.edtPassword)
    SnEditText edtPassword;

    @Bind(R.id.edtPhone)
    SnEditText edtPhone;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userdetails);
        ButterKnife.bind(this);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();


    }


    @OnClick(R.id.btnLogin)
    public void onClickLogin(View view) {
        forgotPasswordProcess();
    }

    //ESP 4,5,6
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onPause() {
        EventBus.getDefault().unregister(this);
        super.onPause();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(String liveFeed) {
    }

    public void forgotPasswordProcess() {
        if (isNetworkAvailable()) {
            if (isEditTextValid(edtFirstName, getString(R.string.validation_firstname))) {
                if (isEditTextValid(edtLastName, getString(R.string.validation_lastname))) {
                    if (isEditTextValid(edtEmailId, getString(R.string.validation_email))) {
                        if (isValidEmail(edtEmailId, getString(R.string.validation_valid_email))) {
                            if (isEditTextValid(edtPassword, getString(R.string.validation_password))) {
                                if (isValidPassWordLength(edtPassword, getString(R.string.validation_password_length))) {
                                    register();
                                }
                            }

                        }
                    }

                }
            }
        }
    }

    public void register() {
        if (isNetworkAvailable()) {
            showDialog();
            int userId = getUserObject().getUserId();

            Call<Login_Object> call = RailwayApplication.getRestClient().getApplicationServices().updateProfile(
                    getEditText(edtFirstName),
                    getEditText(edtLastName),
                    getEditText(edtEmailId),
                    getEditText(edtPassword),
                    getEditText(edtPhone),
                    userId
            );

            call.enqueue(new Callback<Login_Object>() {
                @Override
                public void onResponse(Response<Login_Object> response, Retrofit retrofit) {
                    Login_Object object = response.body();
                    if (object != null && !object.isError()) {
                        callLoginAPI();
                    } else if (response.errorBody() != null) {
                        try {
                            showErrorMsg(response.errorBody().string());
                        } catch (Exception e) {

                        }
                    }

                    dismissDialog();
                    Log.d("CallBack", " response is " + response);
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
                        restartActivity(HomeActivity.class, null);

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
