package com.midaschallenge.midasitchallenge2017;

import com.midaschallenge.midasitchallenge2017.dto.Response;
import com.midaschallenge.midasitchallenge2017.dto.TalentDonationDTO;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
* Created by Hooo on 2017-05-27.
*/
public interface TalentDonationService {

    @GET("/users/test_list")
    Call<ArrayList<TalentDonationDTO>> talentDonationList();

    @FormUrlEncoded
    @POST("users/sign_up")
    Call<Void> signUp(@Field("uuid") String uuid, @Field("name") String name);

    @FormUrlEncoded
    @POST("users/login")
    Call<Void> login(@Field("uuid") String uuid, @Field("name") String name);

    @POST("/test_list")
    Call<TalentDonationDTO> updateTalentDonation(
            @Field("title") String title,
            @Field("contents") String contents,
            @Field("contents") Long startDate,
            @Field("contents") Long endDate
    );

    @DELETE("users/user/{uuid}")
    Call<Void> delete(@Path("uuid") String uuid);
}
