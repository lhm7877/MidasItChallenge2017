package com.midaschallenge.midasitchallenge2017;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rv_talentDonationList;
    private RecyclerView.LayoutManager rv_layoutManager;
    private TalentRecyclerViewAdapter talentRecyclerViewAdapter;

    private ArrayList<TalentDonationDTO> talentDonationDTOs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {

        rv_layoutManager = new LinearLayoutManager(this);
        rv_talentDonationList = (RecyclerView) findViewById(R.id.rv_main_talent_donation);
        rv_talentDonationList.setLayoutManager(rv_layoutManager);

        //TODO: talentDonationDTOs 받아오기
        talentRecyclerViewAdapter = new TalentRecyclerViewAdapter(this,talentDonationDTOs);
        rv_talentDonationList.setAdapter(talentRecyclerViewAdapter);
    }

}