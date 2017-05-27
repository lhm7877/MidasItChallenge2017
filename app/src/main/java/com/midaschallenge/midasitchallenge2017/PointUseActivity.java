package com.midaschallenge.midasitchallenge2017;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarDataSet;

import butterknife.OnClick;

public class PointUseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_point_use);
    }

    @OnClick(R.id.current_service_request_btn)
    void clickCurrentSeviceRequest(){

    }

}
