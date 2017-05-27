package com.midaschallenge.midasitchallenge2017;

import android.content.Context;
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

import com.midaschallenge.midasitchallenge2017.dto.CompletedItem;

import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by bo on 2017. 5. 27..
 */

public class MyTalentFragment extends Fragment {
    @BindView(R.id.my_talent_activity_rv)
    protected RecyclerView myTalentRecyclerView;
    private LinearLayoutManager linearLayoutManager;
    private MyTalentActivityAdapter myTalentActivityAdapter;
    private ArrayList<CompletedItem> items = new ArrayList<>();


    public static MyTalentFragment newInstance(){
        return new MyTalentFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.from(MidasApplication.getContext()).inflate(R.layout.fragment_my_talent, container, false);
        ButterKnife.bind(this, view);
        myTalentActivityAdapter = new MyTalentActivityAdapter(MidasApplication.getContext());
        linearLayoutManager = new LinearLayoutManager(MidasApplication.getContext(),LinearLayoutManager.VERTICAL,false);
        myTalentRecyclerView.setLayoutManager(linearLayoutManager);
        myTalentRecyclerView.setAdapter(myTalentActivityAdapter);
        myTalentActivityAdapter.addItems(items);
        getCompleted();
        return view;
    }


    private class MyTalentActivityAdapter extends RecyclerView.Adapter<MyTalentActivityAdapter.ViewHolder>{
        private ArrayList<CompletedItem> completedItems = new ArrayList<>();
        private Context context;

        public MyTalentActivityAdapter(Context context) {
            this.context = context;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_talent_activity_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            CompletedItem completedItem = completedItems.get(position);
            holder.myTalentActivityItemTitle.setText(completedItem.getTitle());
            holder.myTalentActivityItemContent.setText(completedItem.getContent());
            holder.complete_date.setText(String.valueOf(new Date(completedItem.getCompleted_at())));
        }

        @Override
        public int getItemCount() {
            Log.d("datasize", "data size: "+completedItems.size());
            return completedItems.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            private TextView myTalentActivityItemTitle, myTalentActivityItemContent,complete_date;
            public ViewHolder(View itemView) {
                super(itemView);
                myTalentActivityItemTitle = (TextView) itemView.findViewById(R.id.my_talent_item_title);
                myTalentActivityItemContent = (TextView) itemView.findViewById(R.id.my_talent_item_content);
                complete_date = (TextView) itemView.findViewById(R.id.complete_date);
            }
        }

        public void addItems(ArrayList<CompletedItem> items){
            completedItems.addAll(items);
            notifyDataSetChanged();
        }
    }

    private void getCompleted(){
        TalentDonationService talentDonationService = TalentDonationModel.makeRetrofitBuild(MidasApplication.getContext());
        Call<ArrayList<CompletedItem>> call = talentDonationService.getCompleted();
        call.enqueue(new Callback<ArrayList<CompletedItem>>(){
            @Override
            public void onResponse(Call<ArrayList<CompletedItem>> call, Response<ArrayList<CompletedItem>> response) {
                int status = response.code();
                if(status == 200){
                    int length = response.body().size();
                    for(int i = 0; i< length; i++){
                        CompletedItem completedItem = new CompletedItem();
                        completedItem.setCompleted_at(response.body().get(i).getCompleted_at());
                        completedItem.setContributor_id(response.body().get(i).getContributor_id());
                        completedItem.setId(response.body().get(i).getId());
                        completedItem.setTalent_id(response.body().get(i).getTalent_id());
                        completedItem.setTitle(response.body().get(i).getTitle());
                        completedItem.setContent(response.body().get(i).getContent());
                        items.add(completedItem);
                    }
                    myTalentActivityAdapter.addItems(items);
                }else if(status == 401){

                }else {
                    Toast.makeText(MidasApplication.getContext(), "ERROR", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<CompletedItem>> call, Throwable t) {
                Log.d("ERROR", t.getLocalizedMessage());
            }
        });

    }

}
