package com.sn.railway.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.sn.railway.Base_Activity;
import com.sn.railway.R;

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

public class LoginFragment extends Fragment implements View.OnClickListener {


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
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        {
            return inflater.inflate(R.layout.activity_login, container, false);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        setHasOptionsMenu(true);



        btnLogin.setOnClickListener(this);
        tvForgotPassword.setOnClickListener(this);
        tvRegister.setOnClickListener(this);



    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }


    public void forgotPasswordProcess() {
        if (Base_Activity.isNetworkAvailable()) {
            if (Base_Activity.isEditTextValid(edtEmailId)) {
                if (Base_Activity.isValidEmail(edtEmailId)) {
                    //   callForgotPasswordAPI();
                } else {
                    Base_Activity.showToast(getString(R.string.validation_valid_email));
                }
            } else {
                Base_Activity.showToast(getString(R.string.validation_email));
            }
        }
    }







}