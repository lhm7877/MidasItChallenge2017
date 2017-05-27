package com.midaschallenge.midasitchallenge2017;

import android.content.Context;

import com.franmontiel.persistentcookiejar.ClearableCookieJar;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.CookiePersistor;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.midaschallenge.midasitchallenge2017.dto.TalentDonationDTO;

import java.util.ArrayList;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by Hooo on 2017-05-27.
 */

public interface TalentDonationService {
    public static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://192.168.0.22:5000/rest/v0.1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    @GET("/users/test_list")
    Call<ArrayList<TalentDonationDTO>> talentDonationList();

    @POST("/test_list")
    Call<TalentDonationDTO> updateTalentDonation (
            @Field("title") String title,
            @Field("contents") String contents

    );
}
