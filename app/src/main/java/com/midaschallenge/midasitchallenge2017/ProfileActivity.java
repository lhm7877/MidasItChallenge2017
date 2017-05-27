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
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

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
    private ProfileViewPagerAdapter profileViewPagerAdapter;
    private FragmentManager fragmentManager;
    private String userName;

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
