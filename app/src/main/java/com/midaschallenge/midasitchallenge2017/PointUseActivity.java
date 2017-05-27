package com.midaschallenge.midasitchallenge2017;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarDataSet;

public class PointUseActivity extends AppCompatActivity implements View.OnClickListener{
//    private BarChart pointUseBarChart;
    private Button currentServiceRequestBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_point_use);
//        pointUseBarChart = (BarChart) findViewById(R.id.point_user_barchart);
        currentServiceRequestBtn = (Button) findViewById(R.id.current_service_request_btn);
        currentServiceRequestBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.current_service_request_btn:
                break;
        }
    }
}
