package com.sn.railway.main;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.sn.railway.Base_Activity;
import com.sn.railway.R;
import com.sn.railway.app.RailwayApplication;
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


public class ForgotPasswordActivity extends Base_Activity {

    @OnClick(R.id.imgClose)
    public void imgClose(View view) {
        finishActivity();
    }

    @Bind(R.id.edtEmailId)
    SnEditText edtEmailId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        ButterKnife.bind(this);


        String title = getResources().getString(R.string.forgotpassword);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        actionBar.setDisplayHomeAsUpEnabled(true);

        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(title);


    }

    @OnClick(R.id.btnLogin)
    public void onClickLogin(View view) {
        forgotPasswordProcess();
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


    public void forgotPasswordProcess() {
        if (Base_Activity.isNetworkAvailable()) {
            if (Base_Activity.isEditTextValid(edtEmailId, getString(R.string.validation_email))) {
                if (Base_Activity.isValidEmail(edtEmailId, getString(R.string.validation_valid_email))) {
                    //callForgotPasswordAPI();
                    showToastWithClose("Email sent to your email.");
                }
            }
        }
    }

    public void callForgotPasswordAPI() {
        if (Base_Activity.isNetworkAvailable()) {
            Base_Activity.showDialog();
            Call<Login_Object> call = RailwayApplication.getRestClient().getApplicationServices().forgotPassword(
                    Base_Activity.getEditText(edtEmailId)
            );

            call.enqueue(new Callback<Login_Object>() {
                @Override
                public void onResponse(Response<Login_Object> response, Retrofit retrofit) {
                    Login_Object object = response.body();
                    if (object != null && !object.isError()) {
                        showToastWithClose(object.getMessage());
                    } else if (response.errorBody() != null) {
                        try {
                            Base_Activity.showErrorMsg(response.errorBody().string());
                        } catch (Exception e) {

                        }
                    }
                    Base_Activity.dismissDialog();
                }

                @Override
                public void onFailure(Throwable t) {
                    Base_Activity.dismissDialog();
                    Base_Activity.showToast(t.getMessage());
                    Log.d("CallBack", " Throwable is " + t);
                }
            });


        }
    }
}
