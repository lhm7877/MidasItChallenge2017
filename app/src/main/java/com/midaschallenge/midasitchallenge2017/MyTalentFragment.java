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

import com.midaschallenge.midasitchallenge2017.dto.TalentItem;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by bo on 2017. 5. 27..
 */

public class MyTalentFragment extends Fragment {
    private RecyclerView myTalentRecyclerView;
    private LinearLayoutManager linearLayoutManager;
    private MyTalentActivityAdapter myTalentActivityAdapter;
    private ArrayList<TalentItem> items = new ArrayList<>();


    public static MyTalentFragment newInstance(){
        return new MyTalentFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.from(MidasApplication.getContext()).inflate(R.layout.fragment_my_talent, container, false);
        myTalentRecyclerView = (RecyclerView) view.findViewById(R.id.my_talent_activity_rv);
        myTalentActivityAdapter = new MyTalentActivityAdapter(MidasApplication.getContext());
        linearLayoutManager = new LinearLayoutManager(MidasApplication.getContext(),LinearLayoutManager.VERTICAL,false);
        myTalentRecyclerView.setLayoutManager(linearLayoutManager);
        myTalentRecyclerView.setAdapter(myTalentActivityAdapter);
        for(int i = 1; i <= 10; i++){
            items.add(new TalentItem());
        }
        myTalentActivityAdapter.addItems(items);
        return view;
    }


    private class MyTalentActivityAdapter extends RecyclerView.Adapter<MyTalentActivityAdapter.ViewHolder>{
        private ArrayList<TalentItem> talentItems = new ArrayList<>();
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
            TalentItem talentItem = talentItems.get(position);
            holder.myTalentActivityItemTitle.setText("tltle");
            holder.myTalentActivityItemContent.setText("content");
        }

        @Override
        public int getItemCount() {
            Log.d("datasize", "data size: "+talentItems.size());
            return talentItems.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            private TextView myTalentActivityItemTitle, myTalentActivityItemContent;
            public ViewHolder(View itemView) {
                super(itemView);
                myTalentActivityItemTitle = (TextView) itemView.findViewById(R.id.my_talent_item_title);
                myTalentActivityItemContent = (TextView) itemView.findViewById(R.id.my_talent_item_content);
            }
        }

        public void addItems(ArrayList<TalentItem> items){
            talentItems.addAll(items);
            notifyDataSetChanged();
        }
    }

}
