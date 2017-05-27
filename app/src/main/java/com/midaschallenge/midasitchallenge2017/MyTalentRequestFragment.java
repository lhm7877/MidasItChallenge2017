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

import com.midaschallenge.midasitchallenge2017.dto.MyTalentRequestItem;

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

public class MyTalentRequestFragment extends Fragment{
    @BindView(R.id.my_talent_request_rv)
    protected RecyclerView myTalentRequestRecyclerView;
    private LinearLayoutManager linearLayoutManager;
    private MyTalentRequestAdapter myTalentRequestAdapter;
    private ArrayList<MyTalentRequestItem> items = new ArrayList<>();

    public static MyTalentRequestFragment newInstance(){
        return new MyTalentRequestFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.from(MidasApplication.getContext()).inflate(R.layout.fragment_my_talent_request, container, false);
        ButterKnife.bind(this, view);
        callTalentRequestList();
        linearLayoutManager = new LinearLayoutManager(MidasApplication.getContext(), LinearLayoutManager.VERTICAL, false);
        myTalentRequestAdapter = new MyTalentRequestAdapter();
        myTalentRequestRecyclerView.setLayoutManager(linearLayoutManager);
        myTalentRequestRecyclerView.setAdapter(myTalentRequestAdapter);
//        for(int i = 1; i <= 10; i++){
//            items.add(new MyTalentRequestItem());
//        }
//        myTalentRequestAdapter.addItems(items);
        return view;
    }

    private class MyTalentRequestAdapter extends RecyclerView.Adapter<MyTalentRequestAdapter.ViewHolder>{
        private ArrayList<MyTalentRequestItem> myTalentRequestItems = new ArrayList<>();

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_talent_request_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            MyTalentRequestItem myTalentRequestItem = myTalentRequestItems.get(position);
            holder.myTalentRequestItemTitle.setText(myTalentRequestItem.getTitle());
            holder.myTalentRequestItemContent.setText(myTalentRequestItem.getContents());
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(myTalentRequestItem.getStart_at()*1000);
            holder.start_at.setText(new DateFormat().setDateFormat("EEE MMM d hh:mm:ss O yyyy","yyyy.mm.dd",String.valueOf(myTalentRequestItem.getStart_at()*1000)));
            calendar.setTimeInMillis(myTalentRequestItem.getEnd_at()*1000);
            holder.end_at.setText(calendar.get(Calendar.YEAR)+"."+calendar.get(Calendar.MONTH)+"."+calendar.get(Calendar.DAY_OF_MONTH));
            calendar.setTimeInMillis(myTalentRequestItem.getReq_at());
            holder.req_at.setText(calendar.get(Calendar.YEAR)+"."+calendar.get(Calendar.MONTH)+"."+calendar.get(Calendar.DAY_OF_MONTH));
            Log.d("data", String.valueOf(new Date(myTalentRequestItem.getReq_at())));
        }

        @Override
        public int getItemCount() {
            return myTalentRequestItems.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder{
            private TextView myTalentRequestItemTitle, myTalentRequestItemContent, start_at, end_at, req_at;

            public ViewHolder(View itemView) {
                super(itemView);
                myTalentRequestItemTitle = (TextView) itemView.findViewById(R.id.my_talent_request_item_title);
                myTalentRequestItemContent = (TextView) itemView.findViewById(R.id.my_talent_request_item_content);
                start_at = (TextView) itemView.findViewById(R.id.start_at);
                end_at = (TextView) itemView.findViewById(R.id.end_at);
                req_at = (TextView) itemView.findViewById(R.id.req_at);
            }
        }

        public void addItems(ArrayList<MyTalentRequestItem> items){
            myTalentRequestItems.addAll(items);
            notifyDataSetChanged();
        }
    }

    private void callTalentRequestList(){
        TalentDonationService talentDonationService = TalentDonationModel.makeRetrofitBuild(MidasApplication.getContext());
        Call<ArrayList<MyTalentRequestItem>> call = talentDonationService.myTalentRequestList();
        call.enqueue(new Callback<ArrayList<MyTalentRequestItem>>() {
            @Override
            public void onResponse(Call<ArrayList<MyTalentRequestItem>> call, Response<ArrayList<MyTalentRequestItem>> response) {
                if(response.isSuccessful()){
                    items = response.body();
                    myTalentRequestAdapter.addItems(items);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<MyTalentRequestItem>> call, Throwable t) {

            }
        });
    }
}

