package com.midaschallenge.midasitchallenge2017;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.midaschallenge.midasitchallenge2017.dto.TalentDonationDTO;

import java.util.Calendar;
import java.util.GregorianCalendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RequsetTalentActivity extends AppCompatActivity {

    @BindView(R.id.etv_request_title)
    protected EditText etv_request_title;
    @BindView(R.id.etv_request_contents)
    protected EditText etv_request_contents;

    @BindView(R.id.btn_start_date)
    protected Button btn_start_date;
    @BindView(R.id.btn_start_time)
    protected Button btn_start_time;
    @BindView(R.id.btn_end_date)
    protected Button btn_end_date;
    @BindView(R.id.btn_end_time)
    protected Button btn_end_time;

    private boolean isStartBtn;

    private TalentDonationDTO talentDonationDTO;
    private GregorianCalendar startCalendar, endCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requset_talent);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        startCalendar = new GregorianCalendar();
        endCalendar = new GregorianCalendar();
    }

    @OnClick(R.id.btn_request_accept)
    void clickRequestAccept() {
        uploadReqeust();
    }

    @OnClick(R.id.btn_request_cancel)
    void clickRequestCancel() {
        finish();
    }

    @OnClick(R.id.btn_start_date)
    void clickStartDate() {
        Log.i("mylog", "클릭버튼");
        isStartBtn = true;
        new DatePickerDialog(this, dateSetListener,
                startCalendar.get(Calendar.YEAR),
                startCalendar.get(Calendar.MONTH),
                startCalendar.get(Calendar.DAY_OF_MONTH)
        ).show();
    }

    @OnClick(R.id.btn_start_time)
    void clickStartTime() {
        isStartBtn = true;
        new TimePickerDialog(this, timeSetListener,
                startCalendar.get(Calendar.HOUR_OF_DAY),
                startCalendar.get(Calendar.MINUTE),
                false
        ).show();
    }

    @OnClick(R.id.btn_end_date)
    void clickEndDate() {
        isStartBtn = false;
        new DatePickerDialog(this, dateSetListener,
                endCalendar.get(Calendar.YEAR),
                endCalendar.get(Calendar.MONTH),
                endCalendar.get(Calendar.DAY_OF_MONTH)
        ).show();
    }

    @OnClick(R.id.btn_end_time)
    void clickEndTime() {
        isStartBtn = false;
        new TimePickerDialog(this, timeSetListener,
                endCalendar.get(Calendar.HOUR_OF_DAY),
                endCalendar.get(Calendar.MINUTE),
                false
        ).show();
    }

    private DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            if (isStartBtn) {
                btn_start_date.setText(String.format("%d / %d / %d", year, month + 1, dayOfMonth));
            } else {
                btn_end_date.setText(String.format("%d / %d / %d", year, month + 1, dayOfMonth));
            }
        }
    };


    private TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            if (isStartBtn) {
                btn_start_time.setText(String.format("%d / %d", hourOfDay, minute));
            } else {
                btn_end_time.setText(String.format("%d / %d", hourOfDay, minute));
            }
        }
    };


    private void uploadReqeust() {
        TalentDonationService talentDonationService = TalentDonationModel.makeRetrofitBuild(this);
        Call<TalentDonationDTO> call = talentDonationService.updateTalentDonation(
                etv_request_title.getText().toString(),//제목
                etv_request_contents.getText().toString(),//내용
                startCalendar.getInstance().getTimeInMillis(),
                endCalendar.getInstance().getTimeInMillis()
        );
        call.enqueue(new Callback<TalentDonationDTO>() {
            @Override
            public void onResponse(Call<TalentDonationDTO> call, Response<TalentDonationDTO> response) {
                Toast.makeText(RequsetTalentActivity.this, "업로드 완료", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<TalentDonationDTO> call, Throwable t) {
                Toast.makeText(RequsetTalentActivity.this, "서버 통신 실패", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
