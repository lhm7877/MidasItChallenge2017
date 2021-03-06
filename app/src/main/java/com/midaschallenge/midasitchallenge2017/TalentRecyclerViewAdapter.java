package com.midaschallenge.midasitchallenge2017;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.midaschallenge.midasitchallenge2017.dto.TalentDonationDTO;

import java.util.ArrayList;

/**
 * Created by Hooo on 2017-05-27.
 */

public class TalentRecyclerViewAdapter extends RecyclerView.Adapter<TalentRecyclerviewViewHolder> {

    private Context mContext;
    private ArrayList<TalentDonationDTO> mTalentDonationDTOs;

    public TalentRecyclerViewAdapter(Context context, ArrayList<TalentDonationDTO> mTalentDonationDTOs) {
        this.mContext = context;
        this.mTalentDonationDTOs = mTalentDonationDTOs;
    }

    @Override
    public TalentRecyclerviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.talent_item_view, parent, false);
        TalentRecyclerviewViewHolder viewHolder = new TalentRecyclerviewViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final TalentRecyclerviewViewHolder holder, int position) {
        final TalentDonationDTO talentDonationDTO = mTalentDonationDTOs.get(position);
        holder.ll_item_talent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,TalentRequestDetailActivity.class);
                intent.putExtra("TalentDonationDTO",talentDonationDTO);
                mContext.startActivity(intent);
            }
        });
        holder.tv_item_title.setText(talentDonationDTO.getTitle());
        holder.tv_item_contents.setText(talentDonationDTO.getContents());
        holder.requester.setText(talentDonationDTO.getName());

    }

    @Override
    public int getItemCount() {
        return mTalentDonationDTOs.size();
    }
}
