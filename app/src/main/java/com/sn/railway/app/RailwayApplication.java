package com.sn.railway.app;

import android.app.Application;

import com.sn.railway.restservice.RestClient;


public class RailwayApplication extends Application
{
    private static RestClient restClient;

    @Override
    public void onCreate()
    {
        super.onCreate();
        restClient = new RestClient();

    }

    public static RestClient getRestClient()
    {
        return restClient;
    }
}
