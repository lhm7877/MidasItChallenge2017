package com.midaschallenge.midasitchallenge2017;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity implements View.OnTouchListener {
    private CircleImageView profileUserImage;
    private TextView profileUserName, profileUserPoint;
    private TabLayout profileTabLayout;
    private ViewPager profileViewPager;
    private ProfileViewPagerAdapter profileViewPagerAdapter;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        profileUserImage = (CircleImageView) findViewById(R.id.profile_user_image);
        profileUserName = (TextView) findViewById(R.id.profile_user_name_txt);
        profileUserPoint = (TextView) findViewById(R.id.profile_user_point_txt);
        profileUserPoint.setOnTouchListener(this);
        profileTabLayout = (TabLayout) findViewById(R.id.profile_tab_layout);
        profileViewPager = (ViewPager) findViewById(R.id.profile_viewpager);
        fragmentManager = getSupportFragmentManager();
        profileViewPagerAdapter = new ProfileViewPagerAdapter(fragmentManager);
        profileTabLayout.setupWithViewPager(profileViewPager);
        setProfileViewPager(profileViewPager);

    }

    private void setProfileViewPager(ViewPager viewPager){
        profileViewPagerAdapter.addProfileFragment(MyTalentRequestFragment.newInstance(), getResources().getString(R.string.profile_tab_1));
        profileViewPagerAdapter.addProfileFragment(MyTalentFragment.newInstance(), getResources().getString(R.string.profile_tab_2));
        profileViewPagerAdapter.addProfileFragment(MyPointFragment.newInstance(), getResources().getString(R.string.profile_tab_3));
        viewPager.setAdapter(profileViewPagerAdapter);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int action = event.getAction();
        switch (v.getId()){
            case R.id.profile_user_point_txt:
                if(action == MotionEvent.ACTION_UP){
                    Intent intent = new Intent(this, CurrentServiceActivity.class);
                    startActivity(intent);
                }
                return true;
        }
        return false;
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
