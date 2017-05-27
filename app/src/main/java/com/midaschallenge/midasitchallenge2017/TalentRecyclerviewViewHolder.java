package com.midaschallenge.midasitchallenge2017;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Hooo on 2017-05-27.
 */

public class TalentRecyclerviewViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.ll_item_talent)
    protected LinearLayout ll_item_talent;
    @BindView(R.id.tv_item_title)
    protected TextView tv_item_title;
    @BindView(R.id.tv_item_contents)
    protected TextView tv_item_contents;

    public TalentRecyclerviewViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }
}
