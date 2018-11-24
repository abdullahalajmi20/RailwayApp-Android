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


    Login_Object login_object;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userdetails);
        ButterKnife.bind(this);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        login_object = getUserObject();

        edtEmailId.setText(login_object.getEmail());
        edtFirstName.setText(login_object.getFirstName());
        edtLastName.setText(login_object.getLastName());
        edtPhone.setText(login_object.getPhone());

    }


    @OnClick(R.id.btnLogin)
    public void onClickLogin(View view) {
        updateProcess();
    }


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


    public void updateProcess() {
        if (isNetworkAvailable()) {
            if (isEditTextValid(edtFirstName, getString(R.string.validation_firstname))) {
                if (isEditTextValid(edtLastName, getString(R.string.validation_lastname))) {
                    if (isEditTextValid(edtEmailId, getString(R.string.validation_email))) {
                        if (isValidEmail(edtEmailId, getString(R.string.validation_valid_email))) {
                            if (isEditTextValid(edtPhone, getString(R.string.validation_mobile))) {
                                if (isValidMobile(edtPhone, getString(R.string.validation_valid_mobile))) {
                                    login_object.setLastName(getEditText(edtLastName));
                                    login_object.setFirstName(getEditText(edtFirstName));
                                    login_object.setPhone(getEditText(edtPhone));
                                    SharedPreferanceClass.setCustomObject(getApplicationContext(), SharedPreferanceClass.LOGIN, login_object);
                                    showToastWithClose("Profile updated");
                                    //   register();
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
                        SharedPreferanceClass.setCustomObject(getApplicationContext(), SharedPreferanceClass.LOGIN, object);
                        finishActivity();

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


}
