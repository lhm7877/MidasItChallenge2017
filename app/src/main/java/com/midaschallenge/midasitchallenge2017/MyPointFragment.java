package com.midaschallenge.midasitchallenge2017;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.midaschallenge.midasitchallenge2017.dto.DonationItem;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by bo on 2017. 5. 27..
 */

public class MyPointFragment extends Fragment {
    @BindView(R.id.my_point_activity_rv)
    protected RecyclerView myPointActivityRecyclerView;
    private LinearLayoutManager linearLayoutManager;
    private MyPointActivityAdatper myPointActivityAdatper;
    private ArrayList<DonationItem> items = new ArrayList<>();

    public static MyPointFragment newInstance(){
        return new MyPointFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.from(MidasApplication.getContext()).inflate(R.layout.fragment_my_point, container, false);
        ButterKnife.bind(this, view);
        linearLayoutManager = new LinearLayoutManager(MidasApplication.getContext(), LinearLayoutManager.VERTICAL, false);
        myPointActivityAdatper = new MyPointActivityAdatper();
        myPointActivityRecyclerView.setLayoutManager(linearLayoutManager);
        myPointActivityRecyclerView.setAdapter(myPointActivityAdatper);
        myPointActivityAdatper.addItems(items);
        getMyDonations();
        return view;
    }

    private class MyPointActivityAdatper extends RecyclerView.Adapter<MyPointActivityAdatper.ViewHolder>{
        ArrayList<DonationItem> donationItems = new ArrayList<>();

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_point_activity_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            DonationItem donationItem = donationItems.get(position);
            holder.donation_title.setText(donationItem.getTitle());
            holder.donation_content.setText(donationItem.getContents());
            holder.donation_target.setText(String.valueOf(donationItem.getTarget_point()));
            holder.donation_owned.setText(String.valueOf(donationItem.getOwned_point()));
            holder.donation_point.setText(String.valueOf(donationItem.getContri_point()));
            Calendar startCalendar = Calendar.getInstance();
            startCalendar.setTimeInMillis(donationItem.getDue_date()*1000);
            holder.donation_due.setText(startCalendar.get(Calendar.YEAR)+"."+(startCalendar.get(Calendar.MONTH)+1)+"."+startCalendar.get(Calendar.DAY_OF_MONTH));
        }

        @Override
        public int getItemCount() {
            return donationItems.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            private TextView donation_title, donation_content,donation_target,donation_owned,donation_point,donation_due;


            public ViewHolder(View itemView) {
                super(itemView);
                donation_title = (TextView) itemView.findViewById(R.id.donation_title);
                donation_content = (TextView) itemView.findViewById(R.id.donation_content);
                donation_target = (TextView) itemView.findViewById(R.id.donation_target);
                donation_owned = (TextView) itemView.findViewById(R.id.donation_owned);
                donation_point = (TextView) itemView.findViewById(R.id.donation_point);
                donation_due = (TextView) itemView.findViewById(R.id.donation_due);
            }
        }

        public void addItems(ArrayList<DonationItem> items){
            donationItems.addAll(items);
            notifyDataSetChanged();
        }
    }

    private void getMyDonations(){
        TalentDonationService talentDonationService = TalentDonationModel.makeRetrofitBuild(MidasApplication.getContext());
        Call<ArrayList<DonationItem>> call = talentDonationService.getDonations();
        call.enqueue(new Callback<ArrayList<DonationItem>>() {
            @Override
            public void onResponse(Call<ArrayList<DonationItem>> call, Response<ArrayList<DonationItem>> response) {
                int status = response.code();
                if(status == 200){
                    int length = response.body().size();
                    for(int i = 0; i < length; i++){
                        DonationItem donationItem = new DonationItem();
                        donationItem.setId(response.body().get(i).getId());
                        donationItem.setTitle(response.body().get(i).getTitle());
                        donationItem.setContents(response.body().get(i).getContents());
                        donationItem.setDue_date(response.body().get(i).getDue_date());
                        donationItem.setTarget_point(response.body().get(i).getTarget_point());
                        donationItem.setOwned_point(response.body().get(i).getOwned_point());
                        items.add(donationItem);
                    }
                    myPointActivityAdatper.addItems(items);
                }else if(status == 401){

                }else{
                    Toast.makeText(MidasApplication.getContext(), "ERROR", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<DonationItem>> call, Throwable t) {
                Log.d("ERROR", t.getLocalizedMessage());
            }
        });
    }
}
