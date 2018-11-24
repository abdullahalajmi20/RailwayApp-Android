package com.sn.railway.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sn.railway.Base_Activity;
import com.sn.railway.R;
import com.sn.railway.main.ChangePasswordActivity;
import com.sn.railway.main.UserDetailsActivity;


import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        {
            return inflater.inflate(R.layout.fragment_settings, container, false);
        }
    }

    @OnClick(R.id.linChangePassword)
    public void onClicklinChangePassword(View view) {
        Base_Activity.callActivity(ChangePasswordActivity.class,null);
    }


    @OnClick(R.id.linProfile)
    public void onClicklinProfile(View view) {
        Base_Activity.callActivity(UserDetailsActivity.class,null);
    }

    @OnClick(R.id.linLogOut)
    public void onClicklinLogOut(View view) {
        Base_Activity.logOutAlert();
    }




    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        setHasOptionsMenu(false);


    }

    @Override
    public void onResume() {
        super.onResume();


    }



}
