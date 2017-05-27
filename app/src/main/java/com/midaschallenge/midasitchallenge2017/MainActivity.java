package com.midaschallenge.midasitchallenge2017;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener{

    private RecyclerView rv_talentDonationList;
    private RecyclerView.LayoutManager rv_layoutManager;
    private TalentRecyclerViewAdapter talentRecyclerViewAdapter;
    private ImageButton mainProfileBtn;

    private ArrayList<TalentDonationDTO> talentDonationDTOs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {

        mainProfileBtn = (ImageButton) findViewById(R.id.main_profile_btn);
        mainProfileBtn.setOnTouchListener(this);

        rv_layoutManager = new LinearLayoutManager(this);
        rv_talentDonationList = (RecyclerView) findViewById(R.id.rv_main_talent_donation);
        rv_talentDonationList.setLayoutManager(rv_layoutManager);


        callSticker();
        //TODO: talentDonationDTOs 받아오기
        talentRecyclerViewAdapter = new TalentRecyclerViewAdapter(this,talentDonationDTOs);
        rv_talentDonationList.setAdapter(talentRecyclerViewAdapter);
    }

    private void callSticker(){
        TalentDonationService talentDonationService = TalentDonationService.retrofit.create(TalentDonationService.class);
        Call<ArrayList<TalentDonationDTO>> call = talentDonationService.talentDonationList();
        call.enqueue(new Callback<ArrayList<TalentDonationDTO>>() {
            @Override
            public void onResponse(Call<ArrayList<TalentDonationDTO>> call, Response<ArrayList<TalentDonationDTO>> response) {
                if(response.isSuccessful()){
                    talentDonationDTOs = response.body();

                }
            }

            @Override
            public void onFailure(Call<ArrayList<TalentDonationDTO>> call, Throwable t) {

            }
        });
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int action = event.getAction();
        switch (v.getId()) {
            case R.id.main_profile_btn:
                if (action == MotionEvent.ACTION_UP) {
                    Intent intent = new Intent(this, ProfileActivity.class);
                    startActivity(intent);
                }
                return true;
        }
        return false;
    }
}