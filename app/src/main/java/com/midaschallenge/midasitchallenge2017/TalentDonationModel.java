package com.midaschallenge.midasitchallenge2017;

import android.content.Context;

import com.franmontiel.persistentcookiejar.ClearableCookieJar;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.CookiePersistor;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.google.gson.Gson;
import com.midaschallenge.midasitchallenge2017.dto.TalentDonationDTO;

import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by Hooo on 2017-05-27.
 */

public class TalentDonationModel {
    private static TalentDonationService instance;

    private static ClearableCookieJar provideClearableCookieJar(Context context) {
        CookiePersistor cookiePersistor = new SharedPrefsCookiePersistor(context);
        return new PersistentCookieJar(new SetCookieCache(), cookiePersistor);
    }

    private static HttpLoggingInterceptor provideLoggingInterceptor() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return loggingInterceptor;
    }



    private static OkHttpClient provideOkHttpClient(HttpLoggingInterceptor loggingInterceptor, ClearableCookieJar cookieJar) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.addInterceptor(loggingInterceptor);
        builder.cookieJar(cookieJar);
        return builder.build();
    }

    public static TalentDonationService makeRetrofitBuild(Context context) {
        if (instance == null)
            instance = new Retrofit.Builder()
                    .baseUrl("http://192.168.0.22:5000/rest/v0.1/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(provideOkHttpClient(provideLoggingInterceptor(), provideClearableCookieJar(context)))
                    .build()
                    .create(TalentDonationService.class);
        return instance;
    }

}
