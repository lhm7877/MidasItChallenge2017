package com.midaschallenge.midasitchallenge2017;

import com.midaschallenge.midasitchallenge2017.dto.MyTalentRequestItem;
import com.midaschallenge.midasitchallenge2017.dto.Response;
import com.midaschallenge.midasitchallenge2017.dto.TalentDonationDTO;
import com.midaschallenge.midasitchallenge2017.dto.UserItem;

import java.util.ArrayList;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

/**
* Created by Hooo on 2017-05-27.
*/
public interface TalentDonationService {

    @GET("talent/req_donation")
    Call<ArrayList<TalentDonationDTO>> talentDonationList();

    @GET("talent/my_requests")
    Call<ArrayList<MyTalentRequestItem>> myTalentRequestList();

    @FormUrlEncoded
    @POST("users/sign_up")
    Call<Void> signUp(@Field("uuid") String uuid, @Field("name") String name);

    @FormUrlEncoded
    @POST("users/login")
    Call<UserItem> login(@Field("uuid") String uuid, @Field("name") String name);

    @POST("talent/req_donation")
    Call<TalentDonationDTO> updateTalentDonation(
            @Field("title") String title,
            @Field("contents") String contents,
            @Field("start_at") Long startDate,
            @Field("end_at") Long endDate
    );

    @DELETE("users/user/{uuid}")
    Call<Void> delete(@Path("uuid") String uuid);

    @Multipart
    @POST("users/profile")
    Call<Void> uploadUserImage(@Part MultipartBody.Part profile);

}
