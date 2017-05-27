package com.midaschallenge.midasitchallenge2017;

import android.content.Intent;
import android.preference.Preference;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.midaschallenge.midasitchallenge2017.dto.TalentDonationDTO;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    @BindView(R.id.btn_talent_request)
    protected Button btn_talent_request;
    @BindView(R.id.profile_user_image)
    protected CircleImageView profileUserImage;

    private TalentDonationDTO talentDonationDTO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_talent_request_detail);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        Intent intent = getIntent();
        talentDonationDTO = (TalentDonationDTO) intent.getSerializableExtra("TalentDonationDTO");

        profile_user_name_txt.setText(talentDonationDTO.getName());
        tv_talent_title.setText(talentDonationDTO.getTitle());
        tv_talent_req_at.setText(String.valueOf(talentDonationDTO.getReq_at()));
        tv_talent_end_at.setText(String.valueOf(talentDonationDTO.getEnd_at()));
        tv_talent_contents.setText(talentDonationDTO.getContents());

        Glide.with(this).load("http://192.168.0.22:5000/rest/v0.1/users/profile/"+talentDonationDTO.getId())
                .apply(new RequestOptions().error(R.drawable.user_image))
                .into(profileUserImage);

        if(talentDonationDTO.isCompleted()){
            btn_talent_request.setText("참가 완료");
            btn_talent_request.setEnabled(false);
            btn_talent_request.setBackgroundColor(getResources().getColor(R.color.disabled));
        }else if(PropertyManager.getInstance().getUserId() == talentDonationDTO.getUser_id()){
            btn_talent_request.setVisibility(View.GONE);
        }
    }
    @OnClick(R.id.btn_talent_request)
    void clickTalentReuest(){
        TalentDonationService talentDonationService = TalentDonationModel.makeRetrofitBuild(this);
        talentDonationService.applyDonation(
                talentDonationDTO.getId()
        ).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Toast.makeText(MidasApplication.getContext(),"요청 성공",Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(MidasApplication.getContext(),"요청 실패",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
