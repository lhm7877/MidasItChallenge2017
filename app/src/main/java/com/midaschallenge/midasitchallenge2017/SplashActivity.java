package com.midaschallenge.midasitchallenge2017;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.midaschallenge.midasitchallenge2017.dto.Response;
import com.midaschallenge.midasitchallenge2017.dto.SignUpItem;

import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;

public class SplashActivity extends AppCompatActivity {
    private final int OK = 201;
    private final int SPLASH_DELAY_TIME = 1500;
    private final int PERMISSIONS_REQUEST_READ_PHONE_STATE = 1000;
    private String Uuid;
    Handler handler = new Handler(Looper.getMainLooper());
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            Log.d("LOG", "UUID: " + PropertyManager.getInstance().getUuid()+ " " + "NAME" + PropertyManager.getInstance().getUserName());
            if(!PropertyManager.getInstance().getUuid().equals("") && !PropertyManager.getInstance().getUserName().equals("")){
                SignUpItem signUpItem = new SignUpItem();
                signUpItem.setName(PropertyManager.getInstance().getUserName());
                signUpItem.setUuid(PropertyManager.getInstance().getUuid());
                login(signUpItem);
            }else{
                Intent intent = new Intent(MidasApplication.getContext(), SignUpActivity.class);
                startActivity(intent);
                finish();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        checkPermission();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        handler.removeCallbacks(runnable);
    }

    private void checkPermission(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, PERMISSIONS_REQUEST_READ_PHONE_STATE);
        }else{
            process();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case PERMISSIONS_REQUEST_READ_PHONE_STATE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    Toast.makeText(this, "권한이 허가돼서 서비스 사용할 수 있어요!", Toast.LENGTH_SHORT).show();
                    process();

                } else {
                    Toast.makeText(this,"권한이 거부되면 서비스를 사용할 수 없어요 ㅜㅜ", Toast.LENGTH_SHORT).show();
                    finish();
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                break;
        }
    }

    private String GetDevicesUUID(Context mContext){
        final TelephonyManager tm = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
        final String tmDevice, tmSerial, androidId;
        tmDevice = "" + tm.getDeviceId();
        tmSerial = "" + tm.getSimSerialNumber();
        androidId = "" + android.provider.Settings.Secure.getString(getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
        UUID deviceUuid = new UUID(androidId.hashCode(), ((long)tmDevice.hashCode() << 32) | tmSerial.hashCode());
        String deviceId = deviceUuid.toString();
        return deviceId;
    }

    private void process(){
        Uuid = GetDevicesUUID(MidasApplication.getContext());
        PropertyManager.getInstance().setUuid(Uuid);
        handler.postDelayed(runnable, SPLASH_DELAY_TIME);
    }

    private void login(SignUpItem signUpItem){
        TalentDonationService talentDonationService = TalentDonationModel.makeRetrofitBuild(this);
        Call<Void> call = talentDonationService.login(signUpItem.getUuid(), signUpItem.getName());
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, retrofit2.Response<Void> response) {
                if(response.code() == OK){
                    Intent intent = new Intent(MidasApplication.getContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(MidasApplication.getContext(), "Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d("ERROR", t.getLocalizedMessage());
            }
        });
    }
}
