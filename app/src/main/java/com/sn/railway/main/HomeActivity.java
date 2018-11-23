package com.sn.railway.main;

import android.os.Bundle;
import android.support.v7.app.ActionBar;

import com.sn.railway.Base_Activity;
import com.sn.railway.R;
import com.sn.railway.objects.Login_Object;

import butterknife.ButterKnife;

public class HomeActivity extends Base_Activity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);


    }


}