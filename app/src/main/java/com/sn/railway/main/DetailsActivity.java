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

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;


public class DetailsActivity extends Base_Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);


        String title = getResources().getString(R.string.traindetails);

        ActionBar actionBar = getSupportActionBar();

        actionBar.setDisplayHomeAsUpEnabled(true);

        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(title);


    }

    @OnClick(R.id.linBookTicket)
    public void onClicklinBookTicket(View view) {
        showToastWithClose("Ticket Booked Successfully");
        //callBookTrain();
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




    public void callBookTrain(int userId,int trainId) {
        if (Base_Activity.isNetworkAvailable()) {
            Base_Activity.showDialog();
            Call<Login_Object> call = RailwayApplication.getRestClient().getApplicationServices().bookTrain(
                   userId,trainId
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
