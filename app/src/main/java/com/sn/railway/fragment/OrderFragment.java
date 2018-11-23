package com.sn.railway.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.sn.railway.Base_Activity;
import com.sn.railway.R;
import com.sn.railway.app.RailwayApplication;
import com.sn.railway.custom.SnTextView;
import com.sn.railway.objects.Train_Object;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class OrderFragment extends Fragment {


    @Bind(R.id.rv)
    RecyclerView rv;

    @Bind(R.id.linSearch)
    LinearLayout linSearch;

    MyRecyclerViewAdapter myRecyclerViewAdapter;

    public interface MyClickListener {
        public void onItemClick(int position, View v);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recyclerview, container, false);
    }




    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        setHasOptionsMenu(false);


        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);

        linSearch.setVisibility(View.GONE);

        List<Train_Object> myDataset = new ArrayList<>();
        myRecyclerViewAdapter = new MyRecyclerViewAdapter(myDataset);

        rv.setAdapter(myRecyclerViewAdapter);

    }

    @Override
    public void onResume() {
        super.onResume();
        ((MyRecyclerViewAdapter) myRecyclerViewAdapter).setOnItemClickListener(new MyClickListener() {
            @Override
            public void onItemClick(int position, View v) {

            }
        });
    }
    public void getTrainList(double lat,double lon) {
        if (Base_Activity.isNetworkAvailable()) {
            Base_Activity.showDialog();
            Call<Train_Object> call = RailwayApplication.getRestClient().getApplicationServices().getTrainList(lat,lon

            );

            call.enqueue(new Callback<Train_Object>() {
                @Override
                public void onResponse(Response<Train_Object> response, Retrofit retrofit) {
                    Train_Object object = response.body();
                    if (object != null && !object.isError()) {
                        //getmanageUsers();
                        Toast.makeText(getActivity(), object.getMessage(), Toast.LENGTH_SHORT).show();
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

    public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.DataObjectHolder> {

        private List<Train_Object> mDataset;

        private MyClickListener myClickListener;

        public class DataObjectHolder extends RecyclerView.ViewHolder
                implements View
                .OnClickListener {


            @Bind(R.id.tvFrom)
            SnTextView tvFrom;

            @Bind(R.id.tvFromTime)
            SnTextView tvFromTime;

            @Bind(R.id.tvToTime)
            SnTextView tvToTime;

            @Bind(R.id.tvTo)
            SnTextView tvTo;


            @Bind(R.id.tvName)
            SnTextView tvName;


            @Bind(R.id.tvDuration)
            SnTextView tvDuration;



            public DataObjectHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
                itemView.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                myClickListener.onItemClick(getPosition(), v);
            }
        }

        public void setOnItemClickListener(MyClickListener myClickListener) {
            this.myClickListener = myClickListener;
        }

        public Train_Object getSelectedItem(int position) {
            return mDataset.get(position);
        }

        public MyRecyclerViewAdapter(List<Train_Object> myDataset) {
            mDataset = myDataset;
        }

        public void update(List<Train_Object> myDataset) {
            mDataset = myDataset;
            notifyDataSetChanged();
        }

        @Override
        public MyRecyclerViewAdapter.DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fragment_home_list_item, parent, false);


            MyRecyclerViewAdapter.DataObjectHolder dataObjectHolder = new MyRecyclerViewAdapter.DataObjectHolder(view);
            return dataObjectHolder;
        }

        @Override
        public void onBindViewHolder(final MyRecyclerViewAdapter.DataObjectHolder holder, int position) {
           /* final Train_Object object = mDataset.get(position);
            holder.tvName.setText(object.getName() );
            */
        }

        public void addItem(Train_Object dataObj, int index) {
            mDataset.add(dataObj);
            notifyItemInserted(index);

        }

        public void deleteItem(int index) {
            mDataset.remove(index);
            notifyItemRemoved(index);
        }

        @Override
        public int getItemCount() {
            return 100;
            //return mDataset.size();
        }
    }



}
