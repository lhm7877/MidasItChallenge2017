package com.midaschallenge.midasitchallenge2017;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

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
}
