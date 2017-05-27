package com.midaschallenge.midasitchallenge2017;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.midaschallenge.midasitchallenge2017.dto.PointActivityItem;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by bo on 2017. 5. 27..
 */

public class MyPointFragment extends Fragment {
    @BindView(R.id.my_point_activity_rv)
    protected RecyclerView myPointActivityRecyclerView;
    private LinearLayoutManager linearLayoutManager;
    private MyPointActivityAdatper myPointActivityAdatper;
    private ArrayList<PointActivityItem> items = new ArrayList<>();

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

        for(int i = 1; i <= 10; i++) {
            items.add(new PointActivityItem());
        }
        myPointActivityAdatper.addItems(items);
        return view;
    }

    private class MyPointActivityAdatper extends RecyclerView.Adapter<MyPointActivityAdatper.ViewHolder>{
        ArrayList<PointActivityItem> pointActivityItems = new ArrayList<>();

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_point_activity_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            PointActivityItem pointActivityItem = pointActivityItems.get(position);
            holder.myPointActivityItemContent.setText("1000포인트를 AA나눔재단에 기부하셨습니다");
        }

        @Override
        public int getItemCount() {
            return pointActivityItems.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            private TextView myPointActivityItemContent;

            public ViewHolder(View itemView) {
                super(itemView);
                myPointActivityItemContent = (TextView) itemView.findViewById(R.id.my_point_activity_item_content);
            }
        }

        public void addItems(ArrayList<PointActivityItem> items){
            pointActivityItems.addAll(items);
            notifyDataSetChanged();
        }
    }
}
