package com.midaschallenge.midasitchallenge2017;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.midaschallenge.midasitchallenge2017.dto.CurrentServiceItem;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PointUseActivity extends AppCompatActivity {
    @BindView(R.id.tv_point_service_title)
    protected TextView tv_point_service_title;
    @BindView(R.id.tv_point_service_contents)
    protected TextView tv_point_service_contents;
    @BindView(R.id.tv_point_service_point)
    protected TextView tv_point_service_point;


    private CurrentServiceItem currentServiceItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_point_use);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        Intent intent = getIntent();
        currentServiceItem = (CurrentServiceItem) intent.getSerializableExtra("CurrentServiceItem");
        tv_point_service_title.setText(currentServiceItem.getTitle());
        tv_point_service_contents.setText(currentServiceItem.getContents());
        tv_point_service_point.setText("현재 포인트 : " + currentServiceItem.getOwned_point()+"\n"+
                "목표 포인트 : "+currentServiceItem.getTarget_point());
    }

    @OnClick(R.id.current_service_request_btn)
    void clickCurrentSeviceRequest(){
        TalentDonationService talentDonationService = TalentDonationModel.makeRetrofitBuild(this);
        Call<Void> call = talentDonationService.donatePoint(currentServiceItem.getId(),100);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                int status = response.code();
                if(status == 200){
                    Toast.makeText(MidasApplication.getContext(),"기부완료",Toast.LENGTH_SHORT).show();
                    finish();
                }else{
                    Toast.makeText(MidasApplication.getContext(), "ERROR", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });

    }

}
