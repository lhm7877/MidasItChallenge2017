package com.midaschallenge.midasitchallenge2017;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.midaschallenge.midasitchallenge2017.dto.MyPointHistoryItem;

import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {
    @BindView(R.id.profile_user_image)
    protected CircleImageView profileUserImage;
    @BindView(R.id.profile_user_name_txt)
    protected TextView profileUserName;
    @BindView(R.id.profile_tab_layout)
    protected TabLayout profileTabLayout;
    @BindView(R.id.profile_viewpager)
    protected ViewPager profileViewPager;
    @BindView(R.id.profile_user_point_txt)
    protected TextView profile_user_point_txt;
    @BindView(R.id.lc_profile)
    protected LineChart lc_profile;

    private ProfileViewPagerAdapter profileViewPagerAdapter;
    private FragmentManager fragmentManager;
    private String userName;

    private ArrayList<MyPointHistoryItem> myPointHistoryItems;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);
        Glide.with(MidasApplication.getContext())
                .load("http://192.168.0.22:5000/rest/v0.1/users/profile/"+PropertyManager.getInstance().getUserId())
                .apply(new RequestOptions().error(R.drawable.user_image)).into(profileUserImage);
        userName = PropertyManager.getInstance().getUserName();
        if(!userName.equals("")){
            profileUserName.setText(userName);
        }
        profile_user_point_txt.setText(String.valueOf(PropertyManager.getInstance().getUserPoint()) + " 사용가능");
        fragmentManager = getSupportFragmentManager();
        profileViewPagerAdapter = new ProfileViewPagerAdapter(fragmentManager);
        profileTabLayout.setupWithViewPager(profileViewPager);
        setProfileViewPager(profileViewPager);
        callMyPointHistory();
    }

    private void callMyPointHistory() {
        TalentDonationService talentDonationService = TalentDonationModel.makeRetrofitBuild(MidasApplication.getContext());
        Call<ArrayList<MyPointHistoryItem>> call = talentDonationService.callMyPointHistory();
        call.enqueue(new Callback<ArrayList<MyPointHistoryItem>>() {
            @Override
            public void onResponse(Call<ArrayList<MyPointHistoryItem>> call, Response<ArrayList<MyPointHistoryItem>> response) {
                if(response.isSuccessful()){
                    myPointHistoryItems = response.body();

                    ArrayList<String> labels = new ArrayList<String>();
                    ArrayList<Entry> entries = new ArrayList<Entry>();


                    for(int i =0; i<myPointHistoryItems.size(); i++){
                        Calendar startCalendar = Calendar.getInstance();
                        startCalendar.setTimeInMillis(myPointHistoryItems.get(i).getDate()*1000);

                        labels.add(startCalendar.get(Calendar.YEAR)+"."+(startCalendar.get(Calendar.MONTH)+1)
                                +"."+startCalendar.get(Calendar.DAY_OF_MONTH));
                        entries.add(new Entry(myPointHistoryItems.get(i).getPoint(),i));
                    }

                    LineDataSet lineDataSet = new LineDataSet(entries,"차트");
                    lineDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
                    lineDataSet.setDrawCircles(true);
                    lineDataSet.setDrawFilled(false);

                    ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
                    dataSets.add(lineDataSet);

                    LineData lineData = new LineData(dataSets);
                    lc_profile.setData(lineData);
                    lc_profile.animateXY(2000,2000);
                    lc_profile.invalidate();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<MyPointHistoryItem>> call, Throwable t) {

            }
        });

    }

    private void setProfileViewPager(ViewPager viewPager){
        profileViewPagerAdapter.addProfileFragment(MyTalentRequestFragment.newInstance(), getResources().getString(R.string.profile_tab_1));
        profileViewPagerAdapter.addProfileFragment(MyTalentFragment.newInstance(), getResources().getString(R.string.profile_tab_2));
        profileViewPagerAdapter.addProfileFragment(MyPointFragment.newInstance(), getResources().getString(R.string.profile_tab_3));
        viewPager.setAdapter(profileViewPagerAdapter);
    }

    @OnClick(R.id.profile_user_point_txt)
    void clickPoint(){
        Intent intent = new Intent(this, CurrentServiceActivity.class);
        startActivity(intent);
    }


    private class ProfileViewPagerAdapter extends FragmentPagerAdapter{
        private ArrayList<Fragment> profileFragment = new ArrayList<>();
        private ArrayList<String> tabTitle = new ArrayList<>();
        private final int PROFILE_VIEWPAGER_COUNT = 3;

        public ProfileViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return profileFragment.get(position);
        }

        @Override
        public int getCount() {
            return PROFILE_VIEWPAGER_COUNT;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitle.get(position);
        }

        public void addProfileFragment(Fragment fragment, String tatTitle){
            profileFragment.add(fragment);
            tabTitle.add(tatTitle);
        }

    }
}
