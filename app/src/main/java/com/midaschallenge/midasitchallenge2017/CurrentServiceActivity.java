package com.midaschallenge.midasitchallenge2017;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.midaschallenge.midasitchallenge2017.dto.CurrentServiceItem;

import java.util.ArrayList;

public class CurrentServiceActivity extends AppCompatActivity {
    private RecyclerView currentServiceRecyclerView;
    private LinearLayoutManager linearLayoutManager;
    private CurrentServiceAdapter currentServiceAdapter;
    private ArrayList<CurrentServiceItem> items = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_service);
        currentServiceRecyclerView = (RecyclerView) findViewById(R.id.current_service_rv);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        currentServiceAdapter = new CurrentServiceAdapter(this);
        currentServiceRecyclerView.setLayoutManager(linearLayoutManager);
        currentServiceRecyclerView.setAdapter(currentServiceAdapter);
        for(int i = 1; i <= 20; i++){
            items.add(new CurrentServiceItem());
        }
        currentServiceAdapter.addItems(items);
    }

    private class CurrentServiceAdapter extends RecyclerView.Adapter<CurrentServiceAdapter.ViewHolder>{
        private ArrayList<CurrentServiceItem> currentServiceItems = new ArrayList<>();
        private Context context;

        public CurrentServiceAdapter(Context context) {
            this.context = context;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.current_service_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            CurrentServiceItem currentServiceItem = currentServiceItems.get(position);
            holder.currentServiceItemContent.setText("현재 AA 나눔재단에서 나눔사업 진행중입니다");
            holder.currentServiceItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, PointUseActivity.class);
                    startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return currentServiceItems.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            private TextView currentServiceItemContent;
            private CardView currentServiceItem;

            public ViewHolder(View itemView) {
                super(itemView);
                currentServiceItemContent = (TextView) itemView.findViewById(R.id.current_service_item_content);
                currentServiceItem = (CardView)itemView;
            }
        }

        public void addItems(ArrayList<CurrentServiceItem> items){
            currentServiceItems.addAll(items);
            notifyDataSetChanged();
        }
    }
}
