package com.midaschallenge.midasitchallenge2017;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.midaschallenge.midasitchallenge2017.dto.SignUpItem;
import com.midaschallenge.midasitchallenge2017.dto.UserItem;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {
    private final int PERMISSIONS_REQUEST = 1000;
    private final int PICK_FROM_CAMERA = 1001;
    private final int PICK_FROM_ALBUM = 1002;
    private final int CROP_FROM_CAMERA = 1003;
    private DisplayMetrics metrics;
    private Uri imageCaptureUri;
    private Uri cropImageUri;
    @BindView(R.id.sign_up_user_image)
    protected CircleImageView signUpUserImage;
    @BindView(R.id.sign_up_user_name_edit)
    protected EditText signUpUserNameEdit;
    private final int OK = 201;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);
        metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
    }

    @OnClick(R.id.sign_up_btn)
    void clickSignUp(){
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
    }

    @OnClick(R.id.delete_user_btn)
    void clickDelete(){
        deleteUser(PropertyManager.getInstance().getUuid());
    }
    @OnClick(R.id.sign_up_user_image)
    void clickUserImage(){
        checkPermission();
    }

    private void signUp(final SignUpItem signUpItem){
        TalentDonationService talentDonationService = TalentDonationModel.makeRetrofitBuild(this);
        Call<Void> call = talentDonationService.signUp(signUpItem.getUuid(), signUpItem.getName());
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, retrofit2.Response<Void> response) {
                int status = response.code();
                if(status == OK){
                    if(cropImageUri != null){
                        uploadProfile(signUpItem);
                    }else{
                        login(signUpItem);
                    }
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

    private void login(SignUpItem signUpItem){
        TalentDonationService talentDonationService = TalentDonationModel.makeRetrofitBuild(this);
        Call<UserItem> call = talentDonationService.login(signUpItem.getUuid(), signUpItem.getName());
        call.enqueue(new Callback<UserItem>() {
            @Override
            public void onResponse(Call<UserItem> call, retrofit2.Response<UserItem> response) {
                int status = response.code();
                if(status == 200){
                    PropertyManager.getInstance().setUserName(response.body().getName());
                    PropertyManager.getInstance().setUuid(response.body().getUuid());
                    PropertyManager.getInstance().setUserID(response.body().getId());
                    PropertyManager.getInstance().setUserPoint(response.body().getPoint());
                    Intent intent = new Intent(MidasApplication.getContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(MidasApplication.getContext(), "Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserItem> call, Throwable t) {
                Log.d("ERROR", t.getLocalizedMessage());
            }
        });
    }

    private void deleteUser(String uuid){
        TalentDonationService talentDonationService = TalentDonationModel.makeRetrofitBuild(this);
        Call<Void> call = talentDonationService.delete(uuid);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, retrofit2.Response<Void> response) {
                if(response.code() == 200){
                    PropertyManager.getInstance().setUserName("");
                    PropertyManager.getInstance().setUuid("");
                    Toast.makeText(MidasApplication.getContext(), "삭제완료", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MidasApplication.getContext(), "ERROR", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d("ERROR", t.getLocalizedMessage());
            }
        });
    }

    private void uploadProfile(final SignUpItem signUpItem){
        File file = new File(cropImageUri.getPath());
        // create RequestBody instance from file
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);

        // MultipartBody.Part is used to send also the actual file name
        MultipartBody.Part body = MultipartBody.Part.createFormData("profile", file.getName(), requestFile);
        TalentDonationService talentDonationService = TalentDonationModel.makeRetrofitBuild(this);
        Call<Void> call = talentDonationService.uploadUserImage(body);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                int status = response.code();
                if(status == 201){
                    Log.d("STATUS", "업로드 완료");
                    PropertyManager.getInstance().setUserName(signUpItem.getName());
                    login(signUpItem);
                }else if(status == 401){

                }else{
                    Toast.makeText(MidasApplication.getContext(), "ERROR", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(MidasApplication.getContext(), "ERROR", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void showPostDialog(){
        String take_photo  = getResources().getString(R.string.take_photo);
        String from_album = getResources().getString(R.string.from_album);
        String cancel = getResources().getString(R.string.cancel);
        String post = getResources().getString(R.string.posting);
        final CharSequence[] items = {take_photo, from_album,cancel};
        new AlertDialog.Builder(this)
                .setTitle(post)
                .setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case 0:
                                doTaskPhotoAction();
                                break;
                            case 1:
                                doTaskAlbumAction();
                                break;
                            case 2:
                                dialog.dismiss();
                                break;
                        }
                    }
                }).show();
    }

    private void checkPermission(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSIONS_REQUEST);
        }else{
            showPostDialog();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case PERMISSIONS_REQUEST:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    Toast.makeText(this, "권한이 허가돼서 서비스 사용할 수 있어요!", Toast.LENGTH_SHORT).show();
                    showPostDialog();

                } else {
                    Toast.makeText(this,"권한이 거부되면 서비스를 사용할 수 없어요 ㅜㅜ", Toast.LENGTH_SHORT).show();

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                break;
        }
    }

    public void doTaskPhotoAction(){
        imageCaptureUri = createSaveCropFile();
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT,imageCaptureUri);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, PICK_FROM_CAMERA);
        }
    }

    public void doTaskAlbumAction(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, PICK_FROM_ALBUM);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode != RESULT_OK){
            return;
        }
        switch (requestCode){
            case PICK_FROM_ALBUM:
                imageCaptureUri = data.getData();
//                Glide.with(MidasApplication.getContext()).load(imageCaptureUri).into(signUpUserImage);
            case PICK_FROM_CAMERA:
                cropImageUri = createSaveCropFile();
//                sendBroadcast(new Intent( Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, imageCaptureUri) );
                Intent intent = new Intent("com.android.camera.action.CROP");
                intent.setDataAndType(imageCaptureUri, "image/*");
                intent.putExtra("outputX", metrics.widthPixels); // crop한 이미지의 x축 크기
                intent.putExtra("outputY", metrics.widthPixels); // crop한 이미지의 y축 크기
                intent.putExtra("aspectX", 1); // crop 박스의 x축 비율
                intent.putExtra("aspectY", 1); // crop 박스의 y축 비율
                intent.putExtra("scale", true);
                intent.putExtra("output",cropImageUri);
                startActivityForResult(intent, CROP_FROM_CAMERA);
                break;
            case CROP_FROM_CAMERA:
                sendBroadcast(new Intent( Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, cropImageUri));
                Glide.with(MidasApplication.getContext()).load(cropImageUri).into(signUpUserImage);
                break;
        }
    }

    private Uri createSaveCropFile(){
        Uri uri;
        String url = "tmp_" + String.valueOf(System.currentTimeMillis()) + ".jpg";
        uri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), url));
        return uri;
    }

}
