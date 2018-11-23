package com.sn.railway.restservice;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.logging.HttpLoggingInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import retrofit.Converter;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;


public class RestClient
{


    private ApplicationServices apiByService;

    public final static String DOMAIN_URL = "http://app.railway.com/";
    public final static String BASE_URL = DOMAIN_URL + "railway/api/";

    public static final String register = BASE_URL+"register";
    public static final String updateProfile = BASE_URL+"updateProfile";


     public static final String loginUser = BASE_URL+"login";
    public static final String forgotPassword = BASE_URL+"forgotPassword";


    public static final String changePassword = BASE_URL+"changePassword";




    public RestClient()
    {

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        // set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient httpClient = new OkHttpClient();
        httpClient.setReadTimeout(10, TimeUnit.SECONDS);
        httpClient.setConnectTimeout(10, TimeUnit.SECONDS);
        httpClient.interceptors().add(logging);


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(StringConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build();
        apiByService = retrofit.create(ApplicationServices.class);
    }

    public ApplicationServices getApplicationServices()
    {
        return apiByService;
    }

    public static class StringConverterFactory extends Converter.Factory {
        private  MediaType MEDIA_TYPE = MediaType.parse("text/plain");

        public static StringConverterFactory create() {
            return new StringConverterFactory();
        }
    }
}
