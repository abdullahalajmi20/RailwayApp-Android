package com.sn.railway.restservice;

import com.sn.railway.constant.Constant;
import com.sn.railway.objects.CommonResponse_Object;
import com.sn.railway.objects.Login_Object;
import com.sn.railway.objects.Train_Object;


import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.POST;


public interface ApplicationServices {
    @POST(RestClient.forgotPassword)
    @FormUrlEncoded
    Call<Login_Object> forgotPassword(
            @Field(Constant.EMAIL) String email
    );

    @POST(RestClient.loginUser)
    @FormUrlEncoded
    Call<Login_Object> loginUser(
            @Field(Constant.EMAIL) String userName,
            @Field(Constant.PASSWORD) String password,
            @Field(Constant.DEVICE_TOKEN) String token
    );
    @POST(RestClient.register)
    @FormUrlEncoded
    Call<Login_Object> register(
            @Field(Constant.firstName) String firstName,
            @Field(Constant.lastName) String lastName,
            @Field(Constant.EMAIL) String email,
            @Field(Constant.PASSWORD) String password,
            @Field(Constant.phone) String phone
    );

    @POST(RestClient.updateProfile)
    @FormUrlEncoded
    Call<Login_Object> updateProfile(
            @Field(Constant.firstName) String firstName,
            @Field(Constant.lastName) String lastName,
            @Field(Constant.EMAIL) String email,
            @Field(Constant.PASSWORD) String password,
            @Field(Constant.phone) String phone,
            @Field(Constant.userId) int userId
    );

    @POST(RestClient.changePassword)
    @FormUrlEncoded
    Call<Login_Object> changePassword(

            @Field(Constant.EMAIL) String email,
            @Field(Constant.oldPassword) String oldPassword,
            @Field(Constant.newPassword) String newPassword
    );
    @POST(RestClient.getTrainList)
    @FormUrlEncoded
    Call<Train_Object> getTrainList(
            @Field(Constant.LAT) double lat,
            @Field(Constant.LON) double lon
    );



}
