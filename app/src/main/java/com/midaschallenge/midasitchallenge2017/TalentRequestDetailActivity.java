package com.midaschallenge.midasitchallenge2017;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.midaschallenge.midasitchallenge2017.dto.TalentDonationDTO;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TalentRequestDetailActivity extends AppCompatActivity {
    @BindView(R.id.profile_user_name_txt)
    protected TextView profile_user_name_txt;
    @BindView(R.id.tv_talent_title)
    protected TextView tv_talent_title;
    @BindView(R.id.tv_talent_req_at)
    protected TextView tv_talent_req_at;
    @BindView(R.id.tv_talent_start_at)
    protected TextView tv_talent_start_at;
    @BindView(R.id.tv_talent_end_at)
    protected TextView tv_talent_end_at;
    @BindView(R.id.tv_talent_contents)
    protected TextView tv_talent_contents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_talent_request_detail);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        Intent intent = getIntent();
        TalentDonationDTO talentDonationDTO = (TalentDonationDTO) intent.getSerializableExtra("TalentDonationDTO");

        profile_user_name_txt.setText(talentDonationDTO.getName());
        tv_talent_title.setText(talentDonationDTO.getTitle());
        tv_talent_req_at.setText(String.valueOf(talentDonationDTO.getReq_at()));
        tv_talent_end_at.setText(String.valueOf(talentDonationDTO.getEnd_at()));
        tv_talent_contents.setText(talentDonationDTO.getContents());
    }
    @OnClick(R.id.btn_talent_request)
    void clickTalentReuest(){
        
    }
}
