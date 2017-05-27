package com.midaschallenge.midasitchallenge2017;

import com.midaschallenge.midasitchallenge2017.dto.CompletedItem;
<<<<<<< HEAD
import com.midaschallenge.midasitchallenge2017.dto.DonationItem;
=======
import com.midaschallenge.midasitchallenge2017.dto.CurrentServiceItem;
>>>>>>> 2c1b9757f6d49761d581d5b3d65b8797bc589d26
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
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

/**
* Created by Hooo on 2017-05-27.
*/
public interface TalentDonationService {

    @GET("talent/donation_list")
    Call<ArrayList<CurrentServiceItem>> cuArrayListCall();

    @GET("talent/list")
    Call<ArrayList<TalentDonationDTO>> talentDonationList();

    @GET("talent/my_requests")
    Call<ArrayList<MyTalentRequestItem>> myTalentRequestList();

    @FormUrlEncoded
    @POST("users/sign_up")
    Call<Void> signUp(@Field("uuid") String uuid, @Field("name") String name);

    @FormUrlEncoded
    @POST("users/login")
    Call<UserItem> login(@Field("uuid") String uuid, @Field("name") String name);

    @FormUrlEncoded
    @POST("talent/apply_donation")
    Call<Void> applyDonation(@Field("talent_id") int talent_id);


    @FormUrlEncoded
    @POST("talent/req_donation")
    Call<Void> updateTalentDonation(
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

    @GET("talent/completed")
    Call<ArrayList<CompletedItem>> getCompleted();

    @PUT("talent/{talent_id}")
    Call<Void> completeDonation(@Path("talent_id") int id);

    @GET("users/donations")
    Call<ArrayList<DonationItem>> getDonations();

    @PUT("talent/donate_point")
    Call<Void> donatePoint(@Path("place_id") int id,@Path("point") int point);
}
