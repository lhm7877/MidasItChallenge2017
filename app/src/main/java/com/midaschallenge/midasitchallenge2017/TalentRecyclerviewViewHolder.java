package com.midaschallenge.midasitchallenge2017;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Hooo on 2017-05-27.
 */

public class TalentRecyclerviewViewHolder extends RecyclerView.ViewHolder {
    public TextView tv_item_title;
    public TextView tv_item_contents;
    public TalentRecyclerviewViewHolder(View itemView) {
        super(itemView);

        tv_item_title = (TextView) itemView.findViewById(R.id.tv_item_title);
        tv_item_contents = (TextView) itemView.findViewById(R.id.tv_item_contents);
    }
}
