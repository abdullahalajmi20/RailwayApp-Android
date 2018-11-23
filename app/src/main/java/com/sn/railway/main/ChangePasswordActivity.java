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


public class ChangePasswordActivity extends Base_Activity {

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

    @Bind(R.id.edtNewPassword)
    SnEditText edtNewPassword;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changepassword);

        ButterKnife.bind(this);


        String title = "Profile";

        ActionBar actionBar = getSupportActionBar();

        actionBar.setDisplayHomeAsUpEnabled(true);

        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(title);
        edtEmailId.setText(getUserObject().getEmail());

    }



    @OnClick(R.id.btnLogin)
    public void onClickLogin(View view) {
        createUserProccess();
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

    public void createUserProccess() {
        if (Base_Activity.isNetworkAvailable()) {

            if (Base_Activity.isEditTextValid(edtPassword, "Please enter old password")) {
                if (Base_Activity.isValidPassWordLength(edtPassword, getString(R.string.validation_password_length))) {
                    if (Base_Activity.isEditTextValid(edtNewPassword, "Please enter new password")) {
                        if (Base_Activity.isValidPassWordLength(edtNewPassword, getString(R.string.validation_password_length))) {
                            register();
                        }
                    }
                }
            }

        }
    }

    public void register() {
        if (Base_Activity.isNetworkAvailable()) {
            Base_Activity.showDialog();
            Call<Login_Object> call = RailwayApplication.getRestClient().getApplicationServices().changePassword(
                    Base_Activity.getEditText(edtEmailId),
                    Base_Activity.getEditText(edtPassword),
                    Base_Activity.getEditText(edtNewPassword)
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
                    Log.d("CallBack", " response is " + response);
                }

                @Override
                public void onFailure(Throwable t) {
                    Base_Activity.dismissDialog();
                    Base_Activity.showToast(t.getMessage());
                    //listView.onRefreshComplete();
                    Log.d("CallBack", " Throwable is " + t);
                }
            });


        }
    }




}
