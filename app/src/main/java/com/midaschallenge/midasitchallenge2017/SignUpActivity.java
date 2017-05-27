package com.midaschallenge.midasitchallenge2017;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.midaschallenge.midasitchallenge2017.dto.Response;
import com.midaschallenge.midasitchallenge2017.dto.SignUpItem;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener{
    private CircleImageView signUpUserImage;
    private EditText signUpUserNameEdit;
    private Button signUpBtn;
    private final int OK = 201;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        signUpUserImage = (CircleImageView) findViewById(R.id.sign_up_user_image);
        signUpUserNameEdit = (EditText) findViewById(R.id.sign_up_user_name_edit);
        signUpBtn = (Button) findViewById(R.id.sign_up_btn);
        signUpBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.sign_up_btn:
                String uuid = PropertyManager.getInstance().getUuid();
                String userName = signUpUserNameEdit.getText().toString();
                SignUpItem signUpItem = new SignUpItem();
                signUpItem.setUuid(uuid);
                signUpItem.setName(userName);
                if(uuid.equals("") || userName.equals("")){
                    Toast.makeText(this, getResources().getString(R.string.sign_up_error), Toast.LENGTH_SHORT).show();
                }else{
                    signUp(signUpItem);
                }
                break;
        }
    }

    private void signUp(final SignUpItem signUpItem){
        TalentDonationService talentDonationService = TalentDonationService.retrofit.create(TalentDonationService.class);
        Call<Response> call = talentDonationService.signUp(signUpItem.getUuid(), signUpItem.getName());
        call.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                if(response.code() == OK){
                    PropertyManager.getInstance().setUserName(signUpItem.getName());
                    login(signUpItem);
                }else{
                    Toast.makeText(MidasApplication.getContext(), "Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                Log.d("ERROR", t.getLocalizedMessage());
            }
        });
    }

    private void login(SignUpItem signUpItem){
        TalentDonationService talentDonationService = TalentDonationService.retrofit.create(TalentDonationService.class);
        Call<Response> call = talentDonationService.login(signUpItem.getUuid(), signUpItem.getName());
        call.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                if(response.code() == OK){
                    Intent intent = new Intent(MidasApplication.getContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(MidasApplication.getContext(), "Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                Log.d("ERROR", t.getLocalizedMessage());
            }
        });
    }
}
